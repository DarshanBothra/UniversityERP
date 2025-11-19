package edu.univ.erp.ui;

import edu.univ.erp.domain.Section;
import edu.univ.erp.domain.SectionDetail;
import edu.univ.erp.service.AdminService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * AdminSectionManagementScreen
 * - Create section (createSection)
 * - Edit section (editSection)
 * - Delete section (deleteSection)
 * - Assign instructor (assignInstructor)
 */
public class AdminSectionManagementScreen extends JFrame {

    private final AdminService adminService = new AdminService();
    private final DefaultTableModel sectionModel;
    private final JTable sectionTable;

    // create fields
    private final JTextField txtCourseId = new JTextField(6);
    private final JTextField txtInstructorId = new JTextField(6);
    private final JTextField txtName = new JTextField(10);
    private final JTextField txtCapacity = new JTextField(6);
    private final JTextField txtYear = new JTextField(4);
    private final JTextField txtDayTime = new JTextField(12);
    private final JTextField txtRoom = new JTextField(8);
    private final JTextField txtSemester = new JTextField(6);

    // edit fields
    private final JTextField editSectionId = new JTextField(6);
    private final JTextField editCourseId = new JTextField(6);
    private final JTextField editInstructorId = new JTextField(6);
    private final JTextField editName = new JTextField(10);
    private final JTextField editCapacity = new JTextField(6);
    private final JTextField editYear = new JTextField(4);
    private final JTextField editDayTime = new JTextField(12);
    private final JTextField editRoom = new JTextField(8);
    private final JTextField editSemester = new JTextField(6);

    public AdminSectionManagementScreen() {
        setTitle("Admin — Section Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240,244,249));
        setLayout(new BorderLayout(8,8));

        JLabel header = new JLabel("Section Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(header, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(420);
        split.setLeftComponent(buildFormPanel());
        split.setRightComponent(buildListPanel());
        add(split, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(new Color(240,244,249));
        JButton btnDelete = new JButton("Delete Selected Section");
        btnDelete.setBackground(new Color(52,152,219));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteSelectedSection());
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);

        loadSections();
        setVisible(true);
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                BorderFactory.createEmptyBorder(12,12,12,12)
        ));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("Create Section");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        p.add(t);
        p.add(Box.createRigidArea(new Dimension(0,8)));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Course ID:"));
        row.add(txtCourseId);
        row.add(new JLabel("Instructor ID:"));
        row.add(txtInstructorId);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Name:"));
        row.add(txtName);
        row.add(new JLabel("Capacity:"));
        row.add(txtCapacity);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Year:"));
        row.add(txtYear);
        row.add(new JLabel("DayTime:"));
        row.add(txtDayTime);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Room:"));
        row.add(txtRoom);
        row.add(new JLabel("Semester:"));
        row.add(txtSemester);
        p.add(row);

        JButton createBtn = new JButton("Create Section");
        createBtn.setBackground(new Color(52,152,219));
        createBtn.setForeground(Color.WHITE);
        createBtn.addActionListener(e -> createSection());
        p.add(createBtn);

        p.add(Box.createRigidArea(new Dimension(0,12)));
        JLabel t2 = new JLabel("Edit Section (provide sectionId)");
        t2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        p.add(t2);

        JPanel editRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editRow.setBackground(Color.WHITE);
        editRow.add(new JLabel("Section ID:"));
        editRow.add(editSectionId);
        editRow.add(new JLabel("Course ID:"));
        editRow.add(editCourseId);
        editRow.add(new JLabel("Instructor ID:"));
        editRow.add(editInstructorId);
        p.add(editRow);

        JPanel editRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editRow2.setBackground(Color.WHITE);
        editRow2.add(new JLabel("Name:"));
        editRow2.add(editName);
        editRow2.add(new JLabel("Capacity:"));
        editRow2.add(editCapacity);
        p.add(editRow2);

        JPanel editRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editRow3.setBackground(Color.WHITE);
        editRow3.add(new JLabel("Year:"));
        editRow3.add(editYear);
        editRow3.add(new JLabel("DayTime:"));
        editRow3.add(editDayTime);
        p.add(editRow3);

        JPanel editRow4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editRow4.setBackground(Color.WHITE);
        editRow4.add(new JLabel("Room:"));
        editRow4.add(editRoom);
        editRow4.add(new JLabel("Semester:"));
        editRow4.add(editSemester);
        p.add(editRow4);

        JButton editBtn = new JButton("Save Section Changes");
        editBtn.setBackground(new Color(52,152,219));
        editBtn.setForeground(Color.WHITE);
        editBtn.addActionListener(e -> editSection());
        p.add(editBtn);

        JButton assignBtn = new JButton("Assign Instructor");
        assignBtn.setBackground(new Color(52,152,219));
        assignBtn.setForeground(Color.WHITE);
        assignBtn.addActionListener(e -> assignInstructor());
        p.add(assignBtn);

        return p;
    }

    private JPanel buildListPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(240,244,249));
        sectionModel = new DefaultTableModel(new String[]{"Section ID", "Course", "Name", "Instructor", "DayTime", "Room", "Capacity", "Semester", "Year"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        sectionTable = new JTable(sectionModel);
        sectionTable.setRowHeight(26);
        p.add(new JScrollPane(sectionTable), BorderLayout.CENTER);
        return p;
    }

    private void loadSections() {
        try {
            sectionModel.setRowCount(0);
            List<SectionDetail> list = adminService.sectionDAO.getAllSectionsWithDetails();
            if (list == null) return;
            for (SectionDetail d : list) {
                sectionModel.addRow(new Object[]{
                        d.getSectionId(),
                        d.getCourseCode(),
                        d.getName(),
                        d.getInstructorName(),
                        d.getDayTime(),
                        d.getRoom(),
                        d.getCapacity(),
                        d.getSemester(),
                        d.getYear()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load sections: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createSection() {
        try {
            int courseId = Integer.parseInt(txtCourseId.getText().trim());
            int instructorId = Integer.parseInt(txtInstructorId.getText().trim());
            String name = txtName.getText().trim();
            int capacity = Integer.parseInt(txtCapacity.getText().trim());
            int year = Integer.parseInt(txtYear.getText().trim());
            String dayTime = txtDayTime.getText().trim();
            String room = txtRoom.getText().trim();
            String semester = txtSemester.getText().trim();

            boolean ok = adminService.createSection(courseId, instructorId, name, capacity, year, dayTime, room, semester);
            JOptionPane.showMessageDialog(this, ok ? "Section created." : "Create failed.");
            if (ok) loadSections();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Numeric inputs required for courseId, instructorId, capacity, year.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editSection() {
        try {
            int sectionId = Integer.parseInt(editSectionId.getText().trim());
            int newCourseId = Integer.parseInt(editCourseId.getText().trim());
            int newInstructorId = Integer.parseInt(editInstructorId.getText().trim());
            String newName = editName.getText().trim();
            String newDayTime = editDayTime.getText().trim();
            String newRoom = editRoom.getText().trim();
            int newCapacity = Integer.parseInt(editCapacity.getText().trim());
            int newYear = Integer.parseInt(editYear.getText().trim());
            String newSemester = editSemester.getText().trim();

            boolean ok = adminService.editSection(sectionId, newCourseId, newInstructorId, newName, newDayTime, newRoom, newCapacity, newYear, newSemester);
            JOptionPane.showMessageDialog(this, ok ? "Section updated." : "Update failed.");
            if (ok) loadSections();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Numeric inputs required where appropriate.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void assignInstructor() {
        try {
            int sectionId = Integer.parseInt(editSectionId.getText().trim());
            int instrId = Integer.parseInt(editInstructorId.getText().trim());
            boolean ok = adminService.assignInstructor(sectionId, instrId);
            JOptionPane.showMessageDialog(this, ok ? "Instructor assigned." : "Assign failed.");
            if (ok) loadSections();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Section ID and Instructor ID must be numeric.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedSection() {
        int r = sectionTable.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a section to delete.");
            return;
        }
        int sectionId = (int) sectionModel.getValueAt(r, 0);
        boolean ok = adminService.deleteSection(sectionId);
        JOptionPane.showMessageDialog(this, ok ? "Section deleted." : "Delete failed.");
        if (ok) loadSections();
    }
}
