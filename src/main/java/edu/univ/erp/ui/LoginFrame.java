package edu.univ.erp.ui;

import edu.univ.erp.auth.AuthService;
import edu.univ.erp.domain.Role;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthService loginService;

    public LoginFrame() {

        this.loginService = new AuthService();

        // ---- WINDOW SETTINGS (same as other dashboards) ----
        setTitle("University ERP - Login");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // Absolute layout to match dashboards

        // ---- BLUE THEME BASE ----
        Color bg = new Color(30, 60, 120);     // Dark blue background
        Color card = new Color(240, 245, 255); // Light card
        Color btn = new Color(25, 118, 210);   // Accent blue
        Color btnHover = new Color(21, 101, 192);

        getContentPane().setBackground(bg);

        // ---- TOP HEADER BAR (same style as dashboards) ----
        JPanel header = new JPanel();
        header.setBackground(new Color(20, 40, 80));
        header.setBounds(0, 0, 1000, 80);
        header.setLayout(null);

        JLabel title = new JLabel("University ERP Login");
        title.setBounds(30, 20, 600, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        header.add(title);
        add(header);

        // ---- LOGIN CARD PANEL ----
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(card);
        cardPanel.setBounds(300, 150, 400, 330);
        cardPanel.setLayout(null);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true)); // rounded card

        // ---- LABELS + FIELDS ----
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(40, 40, 120, 30);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JTextField userField = new JTextField();
        userField.setBounds(40, 75, 300, 35);
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(40, 125, 120, 30);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPasswordField passField = new JPasswordField();
        passField.setBounds(40, 160, 300, 35);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // ---- LOGIN BUTTON (matching dashboard buttons) ----
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(40, 220, 300, 45);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginBtn.setBackground(btn);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder());

        // hover effect
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(btnHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(btn);
            }
        });

        // ---- Add components to card ----
        cardPanel.add(userLabel);
        cardPanel.add(userField);

        cardPanel.add(passLabel);
        cardPanel.add(passField);

        cardPanel.add(loginBtn);

        add(cardPanel);

        // ---- LOGIN ACTION ----
        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = String.valueOf(passField.getPassword());

            boolean loggedIn = loginService.login(username, password);

            if (!loggedIn) {
                return;
            }

            Role role = loginService.getSessionUser().getRole();

            if (role == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
                return;
            }

            dispose();

            switch (role) {
                case ADMIN -> new AdminDashboard().setVisible(true);
                case INSTRUCTOR -> new InstructorDashboard().setVisible(true);
                case STUDENT -> new StudentDashboard().setVisible(true);
            }
        });
    }
}


