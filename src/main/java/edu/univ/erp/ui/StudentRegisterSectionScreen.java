package edu.univ.erp.ui;

import edu.univ.erp.service.StudentService;
import edu.univ.erp.domain.SectionDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;

public class StudentRegisterSectionScreen extends JFrame {

    private final StudentService studentService;
    private final int studentId;

    public StudentRegisterSectionScreen(int studentId) {
        this.studentId = studentId;
        this.studentService = new StudentService();

        setTitle("Register for a Section");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 240, 255));

        JLabel header = new JLabel("Register for a Section", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 26));
        header.setForeground(new Color(0, 70, 160));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Table
        String[] columns = {"Section ID", "Course Code", "Title", "Instructor", "Day-Time", "Room", "Capacity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        // Table cell background (light themed blue)
        table.setBackground(new Color(235, 245, 255));      // very light blue
        table.setSelectionBackground(new Color(180, 210, 255));
        table.setSelectionForeground(Color.BLACK);

        // NEW: Apply input-theme colors (light blue + black text)
        Color inputBg = new Color(0xDDEEFF);
        Color inputFg = Color.BLACK;

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tbl, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(inputBg);
                    c.setForeground(inputFg);
                }

                return c;
            }
        });

        // Editor color fix (in case table is editable in future)
        JTextField editorField = new JTextField();
        editorField.setBackground(inputBg);
        editorField.setForeground(inputFg);
        table.setDefaultEditor(Object.class, new DefaultCellEditor(editorField));

        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);

        loadSections(model);

        // Register Button
        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Arial", Font.BOLD, 18));
        registerBtn.setBackground(new Color(30, 140, 255));
        registerBtn.setForeground(Color.white);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        registerBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a section.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int sectionId = (int) model.getValueAt(row, 0);

            String result = studentService.registerSection(studentId, sectionId);

            JOptionPane.showMessageDialog(this, result);
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(220, 230, 255));
        bottom.add(registerBtn);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadSections(DefaultTableModel model) {
        List<SectionDetail> list = studentService.getCatalog();
        model.setRowCount(0);

        for (SectionDetail sd : list) {
            model.addRow(new Object[]{
                    sd.getSectionId(),
                    sd.getCourseCode(),
                    sd.getCourseTitle(),
                    sd.getInstructorName(),
                    sd.getDayTime(),
                    sd.getRoom(),
                    sd.getCapacity()
            });
        }
    }
}
