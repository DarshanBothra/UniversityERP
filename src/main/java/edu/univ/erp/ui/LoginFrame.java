package edu.univ.erp.ui;

import edu.univ.erp.auth.AuthService;
import edu.univ.erp.domain.Role;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthService loginService;

    public LoginFrame() {
        this.loginService = new AuthService();

        setTitle("ERP Login");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel theme
        JPanel panel = new JPanel(new GridLayout(4, 2, 12, 12));
        panel.setBackground(new Color(230, 240, 255));  // light theme

        // Text field styling
        Color fieldColor = new Color(225, 235, 255);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        userField.setBackground(fieldColor);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        passField.setBackground(fieldColor);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(40, 120, 220));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        panel.add(userLabel);
        panel.add(userField);

        panel.add(passLabel);
        panel.add(passField);

        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel);

        // --- LOGIN ACTION ---
        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = String.valueOf(passField.getPassword());

            boolean loggedIn = loginService.login(username, password);

            if (!loggedIn) {
                JOptionPane.showMessageDialog(
                        this,
                        "Incorrect username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Role role = loginService.getSessionUser().getRole();

            dispose();

            switch (role) {
                case ADMIN -> new AdminDashboard().setVisible(true);
                case INSTRUCTOR -> new InstructorDashboard().setVisible(true);
                case STUDENT -> new StudentDashboard().setVisible(true);
            }
        });
    }
}



