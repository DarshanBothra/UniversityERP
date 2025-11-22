package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("ERP System - Main Frame");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the menu bar
        setJMenuBar(new ERPMenuBar());

        // Title
        JLabel title = new JLabel("University ERP System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Buttons
        JButton adminBtn = new JButton("Admin Dashboard");
        JButton instructorBtn = new JButton("Instructor Dashboard");
        JButton studentBtn = new JButton("Student Dashboard");

        adminBtn.addActionListener(e -> new AdminDashboard().setVisible(true));
        instructorBtn.addActionListener(e -> new InstructorDashboard().setVisible(true));
        studentBtn.addActionListener(e -> new StudentDashboard().setVisible(true));

        // Layout panel
        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.add(adminBtn);
        panel.add(instructorBtn);
        panel.add(studentBtn);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 60, 60, 60));

        // Add components
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }
}


