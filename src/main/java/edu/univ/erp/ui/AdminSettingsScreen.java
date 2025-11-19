package edu.univ.erp.ui;

import edu.univ.erp.service.AdminService;
import edu.univ.erp.auth.session.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * AdminSettingsScreen
 * - Toggle maintenance (toggleMaintenance)
 * - Set registration and drop deadlines (setRegistrationDeadline, setDropDeadline)
 */
public class AdminSettingsScreen extends JFrame {

    private final AdminService adminService = new AdminService();
    private final int adminId = SessionManager.getActiveSession() == null ? -1 : SessionManager.getActiveSession().getUserId();

    private final JLabel lblMaintenance = new JLabel("Maintenance: ?");
    private final JTextField txtRegDeadline = new JTextField(20);
    private final JTextField txtDropDeadline = new JTextField(20);

    public AdminSettingsScreen() {
        setTitle("Admin — System Settings");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240,244,249));
        setLayout(new BorderLayout(8,8));

        JLabel header = new JLabel("System Settings", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(Color.WHITE);
        center.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                BorderFactory.createEmptyBorder(12,12,12,12)
        ));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Maintenance line
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.setBackground(Color.WHITE);
        JLabel lbl = new JLabel("Maintenance Mode: ");
        row1.add(lbl);
        row1.add(lblMaintenance);
        JButton btnToggle = new JButton("Toggle Maintenance");
        btnToggle.setBackground(new Color(52,152,219));
        btnToggle.setForeground(Color.WHITE);
        row1.add(btnToggle);
        center.add(row1);

        // deadlines
        center.add(Box.createRigidArea(new Dimension(0,8)));
        JLabel l2 = new JLabel("Deadlines (ISO format: 2025-12-01T23:59)");
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        center.add(l2);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.setBackground(Color.WHITE);
        row2.add(new JLabel("Registration deadline:"));
        row2.add(txtRegDeadline);
        JButton btnRegSet = new JButton("Set");
        btnRegSet.setBackground(new Color(52,152,219));
        btnRegSet.setForeground(Color.WHITE);
        row2.add(btnRegSet);
        center.add(row2);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.setBackground(Color.WHITE);
        row3.add(new JLabel("Drop deadline:"));
        row3.add(txtDropDeadline);
        JButton btnDropSet = new JButton("Set");
        btnDropSet.setBackground(new Color(52,152,219));
        btnDropSet.setForeground(Color.WHITE);
        row3.add(btnDropSet);
        center.add(row3);

        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(new Color(240,244,249));
        JButton btnRefresh = new JButton("Refresh Status");
        btnRefresh.setBackground(new Color(52,152,219));
        btnRefresh.setForeground(Color.WHITE);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        // Actions
        btnToggle.addActionListener(e -> {
            boolean ok = adminService.toggleMaintenance();
            JOptionPane.showMessageDialog(this, ok ? "Maintenance toggled." : "Toggle failed.");
            refreshStatus();
        });

        btnRegSet.addActionListener(e -> {
            try {
                LocalDateTime dt = LocalDateTime.parse(txtRegDeadline.getText().trim());
                boolean ok = adminService.setRegistrationDeadline(dt);
                JOptionPane.showMessageDialog(this, ok ? "Registration deadline set." : "Set failed.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid format. Use ISO local date-time: 2025-12-01T23:59", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDropSet.addActionListener(e -> {
            try {
                LocalDateTime dt = LocalDateTime.parse(txtDropDeadline.getText().trim());
                boolean ok = adminService.setDropDeadline(dt);
                JOptionPane.showMessageDialog(this, ok ? "Drop deadline set." : "Set failed.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid format. Use ISO local date-time: 2025-12-01T23:59", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRefresh.addActionListener(e -> refreshStatus());

        refreshStatus();

        setVisible(true);
    }

    private void refreshStatus() {
        try {
            boolean m = (edu.univ.erp.access.AccessControl.isMaintenanceOn());
            lblMaintenance.setText(m ? "ON" : "OFF");
        } catch (Exception e) {
            lblMaintenance.setText("?");
        }
    }
}
