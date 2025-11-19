package edu.univ.erp.ui;

import edu.univ.erp.domain.Enrollment;
import edu.univ.erp.service.InstructorService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Show all enrollments across sections taught by the instructor.
 * Uses InstructorService.getEnrollmentsForMySections(instructorId)
 */
public class InstructorEnrolledStudentsScreen extends JFrame {

    private final InstructorService service = new InstructorService();
    private final int instructorId;
    private final DefaultTableModel model;

    public InstructorEnrolledStudentsScreen() {
        SessionUser u = SessionManager.getActiveSession();
        this.instructorId = u == null ? -1 : u.getUserId();

        setTitle("Enrolled Students");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240,244,249));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Enrolled Students (All My Sections)", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(header, BorderLayout.NORTH);

        String[] cols = {"Enrollment ID", "Section ID", "Student ID", "Status"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(new Color(240,244,249));
        JButton refresh = new JButton("Refresh");
        refresh.setBackground(new Color(52,152,219));
        refresh.setForeground(Color.WHITE);
        bottom.add(refresh);
        add(bottom, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadEnrollments());

        loadEnrollments();
        setVisible(true);
    }

    private void loadEnrollments() {
        try {
            List<Enrollment> list = service.getEnrollmentsForMySections(instructorId);
            model.setRowCount(0);
            if (list == null) return;
            for (Enrollment en : list) {
                model.addRow(new Object[]{
                        en.getEnrollmentId(),
                        en.getSectionId(),
                        en.getStudentId(),
                        en.getStatus().name()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load enrollments: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

