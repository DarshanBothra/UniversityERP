package edu.univ.erp.ui;

import edu.univ.erp.auth.AuthService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;
import edu.univ.erp.service.InstructorService;

import javax.swing.*;
import java.awt.*;

public class InstructorDashboard extends JFrame {
    
    private JButton createMenuButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(21, 101, 192));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return b;
    }

    private final InstructorService instructorService;
    private final SessionUser sessionUser;

    public InstructorDashboard() {

        this.instructorService = new InstructorService();
        this.sessionUser = SessionManager.getActiveSession();

        setTitle("Instructor Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ------------------------------
        // LEFT SIDEBAR (BLUE THEME)
        // ------------------------------
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(25, 118, 210)); // blue shade
        sidebar.setPreferredSize(new Dimension(230, 650));
        sidebar.setLayout(new GridLayout(10, 1, 0, 10));

        JLabel title = new JLabel("Instructor Menu", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sidebar.add(title);

        JButton btnMySections = createMenuButton("📘  View My Sections");
        JButton btnStudentList = createMenuButton("🧑‍🎓  Enrolled Students");
        JButton btnGradeEntry = createMenuButton("📝  Enter Grades");
        JButton btnStats = createMenuButton("📊  Grade Statistics");
        JButton btnExport = createMenuButton("📄  Export Grades");
        JButton btnProfile = createMenuButton("👤  My Profile");
        JButton btnLogout = createMenuButton("🚪  Logout");

        sidebar.add(btnMySections);
        sidebar.add(btnStudentList);
        sidebar.add(btnGradeEntry);
        sidebar.add(btnStats);
        sidebar.add(btnExport);
        sidebar.add(btnProfile);
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // ------------------------------
        // MAIN PANEL
        // ------------------------------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());

        JLabel welcome = new JLabel(
                "<html><center>Welcome, " + sessionUser.getUsername() +
                        "<br>(Instructor Dashboard)</center></html>",
                SwingConstants.CENTER
        );
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mainPanel.add(welcome, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // ------------------------------
        // BUTTON ACTIONS
        // ------------------------------

        btnMySections.addActionListener(e ->
                new InstructorViewSectionsScreen().setVisible(true)
        );

        btnStudentList.addActionListener(e ->
                new InstructorEnrolledStudentsScreen().setVisible(true)
        );

        btnGradeEntry.addActionListener(e ->
                new InstructorGradeEntryScreen().setVisible(true)
        );

        btnStats.addActionListener(e ->
                new InstructorGradeStatisticsScreen().setVisible(true)
        );

        btnExport.addActionListener(e ->
                new InstructorExportGradesScreen().setVisible(true)
        );

        btnProfile.addActionListener(e ->
                new InstructorProfileScreen().setVisible(true)
        );

        btnLogout.addActionListener(e -> {
            AuthService authService = new AuthService();
            authService.logout();
            JOptionPane.showMessageDialog(this, "Logged Out Successfully!");

            dispose();
            new LoginFrame().setVisible(true);   // <-- added to return to login screen
        });

    };
}



