package edu.univ.erp.ui;

import edu.univ.erp.domain.Instructor;
import edu.univ.erp.service.InstructorService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;

import javax.swing.*;
import java.awt.*;

/**
 * Shows instructor profile using InstructorService.getProfile(instructorId)
 */
public class InstructorProfileScreen extends JFrame {

    private final InstructorService service = new InstructorService();
    private final int instructorId;

    private JLabel lblName, lblUser, lblDept;

    public InstructorProfileScreen() {
        SessionUser u = SessionManager.getActiveSession();
        this.instructorId = u == null ? -1 : u.getUserId();

        setTitle("My Profile");
        setSize(700, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240,244,249));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Instructor Profile", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(header, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        lblName = new JLabel("Name: ");
        lblUser = new JLabel("Username: ");
        lblDept = new JLabel("Department: ");

        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDept.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        card.add(lblName);
        card.add(Box.createRigidArea(new Dimension(0,8)));
        card.add(lblUser);
        card.add(Box.createRigidArea(new Dimension(0,8)));
        card.add(lblDept);

        add(card, BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        refresh.setBackground(new Color(52,152,219));
        refresh.setForeground(Color.WHITE);
        refresh.addActionListener(e -> loadProfile());

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(240,244,249));
        bottom.add(refresh);
        add(bottom, BorderLayout.SOUTH);

        loadProfile();
        setVisible(true);
    }

    private void loadProfile() {
        try {
            Instructor ins = service.getProfile(instructorId);
            if (ins == null) {
                lblName.setText("Name: -");
                lblUser.setText("Username: -");
                lblDept.setText("Department: -");
                return;
            }
            lblName.setText("Name: " + ins.getName());
            lblUser.setText("Username: " + ins.getUsername());
            lblDept.setText("Department: " + ins.getDepartment());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load profile: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

