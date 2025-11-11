package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;
import edu.univ.erp.ui.common.MainFrame;

public class LoginScreen extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Go to Signup");

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        form.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        form.add(passwordField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
        form.add(loginButton, gbc);

        gbc.gridy = 3;
        form.add(signupButton, gbc);

        add(form, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if(user.isEmpty() || pass.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter username and password");
                return;
            }
            // Mock logic
            if(user.equals("admin")) frame.showScreen("admin");
            else if(user.equals("instructor")) frame.showScreen("instructor");
            else frame.showScreen("student");
        });

        signupButton.addActionListener(e -> frame.showScreen("signup"));
    }
}
