package edu.univ.erp.ui;

import edu.univ.erp.auth.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthService authService = new AuthService();

    public LoginFrame() {

        setTitle("ERP Login");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 244, 249));   // soft grey-blue
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Login Card
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(400, 350));
        card.setBackground(Color.WHITE);
        card.setLayout(null);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("University ERP Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(60, 20, 280, 40);
        card.add(title);

        // Username Label + Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 90, 100, 25);
        card.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 115, 300, 35);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        card.add(usernameField);

        // Password Label + Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 165, 100, 25);
        card.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 190, 300, 35);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        card.add(passwordField);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 250, 300, 40);
        loginBtn.setBackground(new Color(52, 152, 219)); // nice blue
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        card.add(loginBtn);

        // Action
        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both fields.");
                return;
            }

            boolean login = authService.login(user, pass);

            if (!login) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Login Successful!");

            // Open MainFrame after login
            this.dispose();
            new MainFrame().setVisible(true);
        });

        mainPanel.add(card, gbc);
        add(mainPanel);
    }
}
