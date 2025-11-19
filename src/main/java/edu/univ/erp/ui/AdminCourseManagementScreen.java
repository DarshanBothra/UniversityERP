package edu.univ.erp.ui;

import edu.univ.erp.domain.Course;
import edu.univ.erp.service.AdminService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * AdminCourseManagementScreen
 * - Create course (createCourse)
 * - Edit course (editCourse)
 * - Delete course (deleteCourse)
 */
public class AdminCourseManagementScreen extends JFrame {

    private final AdminService adminService = new AdminService();
    private final DefaultTableModel courseModel;
    private final JTable courseTable;

    private final JTextField txtCode = new JTextField(8);
    private final JTextField txtTitle = new JTextField(12);
    private final JTextField txtCredits = new JTextField(4);

    private final JTextField editCourseIdField = new JTextField(6);
    private final JTextField editTitleField = new JTextField(12);
    private final JTextField editCreditsField = new JTextField(4);

    public AdminCourseManagementScreen() {
        setTitle("Admin — Course Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240,244,249));
        setLayout(new BorderLayout(8,8));

        JLabel header = new JLabel("Course Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(header, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(380);
        split.setLeftComponent(buildCreatePanel());
        split.setRightComponent(buildListPanel());
        add(split, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(new Color(240,244,249));
        JButton btnDelete = new JButton("Delete Selected Course");
        btnDelete.setBackground(new Color(52,152,219));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteSelectedCourse());
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);

        loadCourses();
        setVisible(true);
    }

    private JPanel buildCreatePanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                BorderFactory.createEmptyBorder(12,12,12,12)
        ));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("Create Course");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        p.add(t);
        p.add(Box.createRigidArea(new Dimension(0,8)));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Code:"));
        row.add(txtCode);
        row.add(new JLabel("Title:"));
        row.add(txtTitle);
        row.add(new JLabel("Credits:"));
        row.add(txtCredits);
        p.add(row);

        JButton createBtn = new JButton("Create");
        createBtn.setBackground(new Color(52,152,219));
        createBtn.setForeground(Color.WHITE);
        createBtn.addActionListener(e -> createCourse());
        p.add(createBtn);

        p.add(Box.createRigidArea(new Dimension(0,12)));
        JLabel t2 = new JLabel("Edit Course (provide courseId)");
        t2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        p.add(t2);

        JPanel editRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editRow.setBackground(Color.WHITE);
        editRow.add(new JLabel("Course ID:"));
        editRow.add(editCourseIdField);
        editRow.add(new JLabel("New Title:"));
        editRow.add(editTitleField);
        editRow.add(new JLabel("New Credits:"));
        editRow.add(editCreditsField);
        p.add(editRow);

        JButton editBtn = new JButton("Save Changes");
        editBtn.setBackground(new Color(52,152,219));
        editBtn.setForeground(Color.WHITE);
        editBtn.addActionListener(e -> editCourse());
        p.add(editBtn);

        return p;
    }

    private JPanel buildListPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(240,244,249));
        courseModel = new DefaultTableModel(new String[]{"Course ID", "Code", "Title", "Credits"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        courseTable = new JTable(courseModel);
        courseTable.setRowHeight(26);
        p.add(new JScrollPane(courseTable), BorderLayout.CENTER);
        return p;
    }

    private void loadCourses() {
        try {
            courseModel.setRowCount(0);
            List<Course> list = adminService.courseDAO.getAllCourses(); // courseDAO is public in AdminService
            if (list == null) return;
            for (Course c : list) {
                courseModel.addRow(new Object[]{
                        c.getCourseId(),
                        c.getCode(),
                        c.getTitle(),
                        c.getCredits()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load courses: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createCourse() {
        String code = txtCode.getText().trim();
        String title = txtTitle.getText().trim();
        int credits;
        try {
            credits = Integer.parseInt(txtCredits.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Credits must be numeric.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean ok = adminService.createCourse(code, title, credits);
        JOptionPane.showMessageDialog(this, ok ? "Course created." : "Create failed.");
        if (ok) loadCourses();
    }

    private void editCourse() {
        try {
            int courseId = Integer.parseInt(editCourseIdField.getText().trim());
            String newTitle = editTitleField.getText().trim();
            int newCredits = Integer.parseInt(editCreditsField.getText().trim());
            boolean ok = adminService.editCourse(courseId, adminService.courseDAO.getCourseById(courseId).getCode(), newTitle, newCredits);
            JOptionPane.showMessageDialog(this, ok ? "Course updated." : "Update failed.");
            if (ok) loadCourses();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Course ID and credits must be numeric.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedCourse() {
        int r = courseTable.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a course to delete.");
            return;
        }
        int courseId = (int) courseModel.getValueAt(r, 0);
        boolean ok = adminService.deleteCourse(courseId);
        JOptionPane.showMessageDialog(this, ok ? "Course deleted." : "Delete failed.");
        if (ok) loadCourses();
    }
}
