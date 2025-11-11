package edu.univ.erp.ui;

import edu.univ.erp.ui.common.MainFrame;
import edu.univ.erp.ui.common.MenuBar;
import java.awt.*;
import javax.swing.*;

public class StudentDashboard extends JPanel {
    public StudentDashboard(MainFrame frame) {
        setLayout(new BorderLayout());
        frame.setJMenuBar(new MenuBar(e -> frame.showScreen("login")));

        JLabel title = new JLabel("Student Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel buttons = new JPanel();
        JButton registerCourse = new JButton("Register for Course");
        JButton viewGrades = new JButton("View Grades");
        buttons.add(registerCourse);
        buttons.add(viewGrades);

        add(title, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);

        registerCourse.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Course Registration coming soon...")
        );
        viewGrades.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Grade Viewer coming soon...")
        );
    }
}
