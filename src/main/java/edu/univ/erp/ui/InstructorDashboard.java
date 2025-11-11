package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;
import edu.univ.erp.ui.common.MainFrame;
import edu.univ.erp.ui.common.MenuBar;

public class InstructorDashboard extends JPanel {
    public InstructorDashboard(MainFrame frame) {
        setLayout(new BorderLayout());
        frame.setJMenuBar(new MenuBar(e -> frame.showScreen("login")));

        JLabel title = new JLabel("Instructor Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel buttons = new JPanel();
        JButton viewCourses = new JButton("View Courses");
        JButton enterGrades = new JButton("Enter Grades");
        buttons.add(viewCourses);
        buttons.add(enterGrades);

        add(title, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);

        viewCourses.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Course list coming soon...")
        );
        enterGrades.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Grade Entry coming soon...")
        );
    }
}
