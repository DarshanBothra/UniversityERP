package edu.univ.erp.ui;
import edu.univ.erp.api.StudentAPI;
import edu.univ.erp.data.SectionDAO;
import edu.univ.erp.domain.Course;
import edu.univ.erp.domain.SectionDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Save as RegisterCourseFrame.java
public class RegisterCourseFrame extends JFrame {

    private JTable courseTable;
    private DefaultTableModel model;

    public RegisterCourseFrame(StudentAPI studentAPI, int studentId) {

        setTitle("Register for a Course");
        setSize(900, 550);   // SAME SIZE AS DASHBOARD
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ===== COLORS (Blue Theme) =====
        Color bg = new Color(230, 240, 255);        // light blue
        Color header = new Color(40, 90, 160);      // dark blue

        getContentPane().setBackground(bg);

        // ===== TITLE PANEL =====
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(header);

        JLabel titleLabel = new JLabel("Register for a Course");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));

        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // ===== TABLE MODEL =====
        model = new DefaultTableModel();
        model.addColumn("Course ID");
        model.addColumn("Course Name");
        model.addColumn("Instructor");
        model.addColumn("Credits");

        courseTable = new JTable(model);
        courseTable.setRowHeight(25);
        courseTable.setFont(new Font("SansSerif", Font.PLAIN, 15));
        courseTable.getTableHeader().setBackground(header);
        courseTable.getTableHeader().setForeground(Color.BLACK);
        courseTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.getViewport().setBackground(bg);

        add(scrollPane, BorderLayout.CENTER);

        // ===== REGISTER BUTTON =====
        JButton registerBtn = new JButton("Register for Selected Course");
        registerBtn.setBackground(header);
        registerBtn.setForeground(Color.BLACK);
        registerBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        registerBtn.setFocusPainted(false);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bg);
        bottomPanel.add(registerBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== LOAD AVAILABLE COURSES =====
        loadAvailableCourses(studentAPI);

        // ===== BUTTON ACTION =====
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = courseTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(
                            RegisterCourseFrame.this,
                            "Please select a course to register.",
                            "No Selection",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                int courseId = (int) model.getValueAt(selectedRow, 0);

                try {
                    SectionDAO sectionDAO = new SectionDAO();
                    studentAPI.registerSection(studentId, sectionDAO.getSectionByCourseId(courseId).getSectionId());

                    JOptionPane.showMessageDialog(
                            RegisterCourseFrame.this,
                            "Course registered successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // Refresh table
                    loadAvailableCourses(studentAPI);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            RegisterCourseFrame.this,
                            "Error while registering course:\n" + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        setVisible(true);
    }

    private void loadAvailableCourses(StudentAPI studentAPI) {
        try {
            List<SectionDetail> availableCourses = studentAPI.getCatalog();

            model.setRowCount(0); // clear table

            for (SectionDetail s : availableCourses) {
                model.addRow(new Object[]{
                        s.getCourseCode(),
                        s.getCourseTitle(),
                        s.getInstructorName(),
                        s.getCourseCredits()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading available courses:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
