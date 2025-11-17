package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;

public class InstructorDashboard extends JFrame {

    public InstructorDashboard() {

        setTitle("Instructor Dashboard");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome, Instructor", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton mySections = new JButton("My Sections");
        JButton enterGrades = new JButton("Enter Grades");
        JButton viewStudents = new JButton("View Students");

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(title);
        panel.add(mySections);
        panel.add(enterGrades);
        panel.add(viewStudents);

        add(panel);
    }
}

