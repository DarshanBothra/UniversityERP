package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    public StudentDashboard() {

        setTitle("Student Dashboard");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome, Student", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton viewCatalog = new JButton("View Course Catalog");
        JButton myRegs = new JButton("My Registrations");
        JButton myGrades = new JButton("My Grades");
        JButton transcript = new JButton("Generate Transcript");

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.add(title);
        panel.add(viewCatalog);
        panel.add(myRegs);
        panel.add(myGrades);
        panel.add(transcript);

        add(panel);
    }
}


