package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;

public class InvalidLoginScreen extends JFrame {

    public InvalidLoginScreen() {

        setTitle("Invalid Login");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("Login Failed", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(200, 0, 0));

        JLabel message = new JLabel(
                "<html><center>Incorrect username or password.<br>Please try again.</center></html>",
                SwingConstants.CENTER
        );
        message.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        message.setForeground(new Color(80, 80, 80));

        JButton retryButton = new JButton("Try Again");
        retryButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        retryButton.setBackground(Color.WHITE);
        retryButton.setForeground(Color.BLACK);
        retryButton.setFocusPainted(false);
        retryButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(message, BorderLayout.CENTER);
        panel.add(retryButton, BorderLayout.SOUTH);

        add(panel);
    }
}
