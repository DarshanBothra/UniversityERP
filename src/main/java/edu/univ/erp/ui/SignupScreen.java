package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;
import edu.univ.erp.ui.common.MainFrame;

public class SignupScreen extends JPanel {
    public SignupScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Sign Up", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        String[] roles = {"student", "instructor", "admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        JButton signupButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back to Login");

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        form.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        form.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        form.add(roleBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        form.add(signupButton, gbc);

        gbc.gridy = 4;
        form.add(backButton, gbc);

        add(form, BorderLayout.CENTER);

        signupButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();
            if(user.isEmpty() || pass.isEmpty()){
                JOptionPane.showMessageDialog(this, "All fields required");
                return;
            }
            JOptionPane.showMessageDialog(this, "User registered successfully!\nRole: " + role);
            frame.showScreen("login");
        });

        backButton.addActionListener(e -> frame.showScreen("login"));
    }
}
