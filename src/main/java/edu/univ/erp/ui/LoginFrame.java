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
        setSize(400, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        panel.add(userLabel);
        panel.add(userField);

        panel.add(passLabel);
        panel.add(passField);

        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = String.valueOf(passField.getPassword());

            boolean loggedIn = loginService.login(username, password);

            if (!loggedIn){
                return;
            }

            Role role = loginService.getSessionUser().getRole();
            
            if (role == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
                return;
            }

            JOptionPane.showMessageDialog(this, "Login Successful! Role: " + role);

            dispose();

            switch (role) {
                case ADMIN -> new AdminDashboard().setVisible(true);
                case INSTRUCTOR -> new InstructorDashboard().setVisible(true);
                case STUDENT -> new StudentDashboard().setVisible(true);
            }
        });
    }
}

