package edu.univ.erp.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Save as ViewEnrolledCoursesFrame.java
public class ViewEnrolledCoursesFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public ViewEnrolledCoursesFrame(StudentDAO studentDAO, int studentId) {
        setTitle("View Enrolled Courses");
        setSize(900, 550);   // SAME SIZE AS DASHBOARD
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // ===== COLORS (Blue Theme) =====
        Color bg = new Color(230, 240, 255);        // very light blue
        Color header = new Color(40, 90, 160);      // dark blue
        Color textBlue = new Color(25, 60, 120);

        setLayout(new BorderLayout());
        getContentPane().setBackground(bg);

        // ===== TITLE PANEL =====
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(header);
        JLabel titleLabel = new JLabel("Your Enrolled Courses");
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

        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.getTableHeader().setBackground(header);
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(bg);

        add(scrollPane, BorderLayout.CENTER);

        // ===== LOAD DATA =====
        loadEnrolledCourses(studentDAO, studentId);

        setVisible(true);
    }

    private void loadEnrolledCourses(StudentDAO studentDAO, int studentId) {
        try {
            List<Course> courseList = studentDAO.getEnrolledCourses(studentId);

            model.setRowCount(0); // clear table first

            for (Course c : courseList) {
                model.addRow(new Object[]{
                        c.getCourseId(),
                        c.getCourseName(),
                        c.getInstructorName(),
                        c.getCredits()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading enrolled courses:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
