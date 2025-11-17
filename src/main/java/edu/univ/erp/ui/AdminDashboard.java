package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome, Admin", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton userMgmt = new JButton("Manage Users");
        JButton courseMgmt = new JButton("Manage Courses");
        JButton sectionMgmt = new JButton("Manage Sections");

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(title);
        panel.add(userMgmt);
        panel.add(courseMgmt);
        panel.add(sectionMgmt);

        add(panel);
    }
}
