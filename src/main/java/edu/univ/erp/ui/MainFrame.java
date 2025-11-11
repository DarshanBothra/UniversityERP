package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;
import edu.univ.erp.ui.auth.LoginScreen;
import edu.univ.erp.ui.auth.SignupScreen;
import edu.univ.erp.ui.student.StudentDashboard;
import edu.univ.erp.ui.instructor.InstructorDashboard;
import edu.univ.erp.ui.admin.AdminDashboard;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("University ERP System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add screens
        LoginScreen login = new LoginScreen(this);
        SignupScreen signup = new SignupScreen(this);
        StudentDashboard studentDashboard = new StudentDashboard(this);
        InstructorDashboard instructorDashboard = new InstructorDashboard(this);
        AdminDashboard adminDashboard = new AdminDashboard(this);

        mainPanel.add(login, "login");
        mainPanel.add(signup, "signup");
        mainPanel.add(studentDashboard, "student");
        mainPanel.add(instructorDashboard, "instructor");
        mainPanel.add(adminDashboard, "admin");

        add(mainPanel);
        showScreen("login");
    }

    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
