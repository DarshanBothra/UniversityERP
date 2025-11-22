package edu.univ.erp.ui;

import edu.univ.erp.data.InstructorDAO;
import edu.univ.erp.domain.SectionDetail;
import edu.univ.erp.service.InstructorService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * View sections taught by the logged-in instructor.
 * Uses InstructorService.getMySections(instructorId)
 */
public class InstructorViewSectionsScreen extends JFrame {

    private final InstructorService service = new InstructorService();
    private final int instructorId;
    private final DefaultTableModel model;

    public InstructorViewSectionsScreen() {
        SessionUser u = SessionManager.getActiveSession();
        InstructorDAO instructorDAO = new InstructorDAO();
        this.instructorId = u == null ? -1 : instructorDAO.getInstructorById(u.getUserId()).getInstructorId();

        setTitle("My Sections");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(240, 244, 249));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("My Sections", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(16, 0, 12, 0));
        add(header, BorderLayout.NORTH);

        String[] cols = {"Section ID", "Course Code", "Course Title", "Day/Time", "Room", "Capacity", "Semester", "Year"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(26);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(new Color(240, 244, 249));
        JButton refresh = new JButton("Refresh");
        refresh.setBackground(new Color(52,152,219));
        refresh.setForeground(Color.BLACK);
        bottom.add(refresh);
        add(bottom, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadSections());

        loadSections();
        setVisible(true);
    }

    private void loadSections() {
        try {
            List<SectionDetail> list = service.getMySections(instructorId);
            model.setRowCount(0);
            if (list == null) return;
            for (SectionDetail d : list) {
                model.addRow(new Object[]{
                        d.getSectionId(),
                        d.getCourseCode(),
                        d.getCourseTitle(),
                        d.getDayTime(),
                        d.getRoom(),
                        d.getCapacity(),
                        d.getSemester(),
                        d.getYear()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load sections: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
