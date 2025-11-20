package edu.univ.erp.ui;

import edu.univ.erp.auth.AuthService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;

import javax.swing.*;
import java.awt.*;

/**
 * AdminDashboard
 * - Matches Student/Instructor dashboards (blue theme, sidebar/main layout)
 * - Shows only the features present in AdminService:
 *   • User Management
 *   • Course Management
 *   • Section Management
 *   • System Settings (toggle maintenance, set deadlines)
 *
 * Buttons open the corresponding screens (use these exact class names):
 *   - AdminUserManagementScreen
 *   - AdminCourseManagementScreen
 *   - AdminSectionManagementScreen
 *   - AdminSettingsScreen
 *
 * Place additional admin screens under: edu.univ.erp.ui.admin
 *
 * NOTE: Database diagram uploaded at: /mnt/data/Database.png
 */
public class AdminDashboard extends JFrame {

    private final int adminId;

    // theme colors (blue theme)
    private final Color bg = new Color(240, 244, 249);
    private final Color primary = new Color(52, 152, 219);
    private final Color sidebar = new Color(30, 61, 89); // #1E3D59

    public AdminDashboard() {
        SessionUser u = SessionManager.getActiveSession();
        this.adminId = u == null ? -1 : u.getUserId();

        setTitle("Admin Dashboard — University ERP");
        setSize(900, 600); // keep same size as other dashboards
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        getContentPane().setBackground(bg);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));
        add(header, BorderLayout.NORTH);

        // Sidebar (left)
        JPanel side = new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(220, 0));
        side.setBackground(sidebar);

        // Top user block
        JPanel top = new JPanel();
        top.setBackground(sidebar);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(18, 12, 18, 12));
        JLabel avatar = new JLabel("🛠️", SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        top.add(avatar);
        JLabel userLabel = new JLabel("Administrator", SwingConstants.CENTER);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        top.add(userLabel);
        side.add(top, BorderLayout.NORTH);

        // Buttons list
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(sidebar);
        btnPanel.setLayout(new GridLayout(0, 1, 6, 6));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JButton btnUsers = makeSidebarButton("User Management");
        JButton btnCourses = makeSidebarButton("Course Management");
        JButton btnSections = makeSidebarButton("Section Management");
        JButton btnSettings = makeSidebarButton("System Settings");
        JButton btnRefresh = makeSidebarButton("Refresh (Users)");
        JButton btnLogout = makeSidebarButton("Logout");

        btnPanel.add(btnUsers);
        btnPanel.add(btnCourses);
        btnPanel.add(btnSections);
        btnPanel.add(btnSettings);
        btnPanel.add(btnRefresh);
        btnPanel.add(btnLogout);

        side.add(btnPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel("University ERP", SwingConstants.CENTER);
        footer.setForeground(new Color(200,200,200));
        footer.setBorder(BorderFactory.createEmptyBorder(8,0,12,0));
        side.add(footer, BorderLayout.SOUTH);

        add(side, BorderLayout.WEST);

        // Main content area (simple welcome card like others)
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bg);
        main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                BorderFactory.createEmptyBorder(18,18,18,18)
        ));

        JLabel welcome = new JLabel("<html><center>Welcome, Admin<br/>Use the left menu to manage users, courses, sections, and system settings.</center></html>", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        card.add(welcome, BorderLayout.CENTER);

        main.add(card, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);

        // Button actions (open screens with exact names)
        btnUsers.addActionListener(e -> {
            // opens the user management screen (must be implemented)
            new AdminUserManagementScreen().setVisible(true);
        });

        btnCourses.addActionListener(e -> {
            new AdminCourseManagementScreen().setVisible(true);
        });

        btnSections.addActionListener(e -> {
            new AdminSectionManagementScreen().setVisible(true);
        });

        btnSettings.addActionListener(e -> {
            new AdminSettingsScreen().setVisible(true);
        });

        btnRefresh.addActionListener(e -> {
            // small convenience action — open user management then refresh there
            new AdminUserManagementScreen().setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            AuthService authService = new AuthService();
            authService.logout();
            dispose();
            // reuse LoginFrame class from UI package
            new LoginFrame().setVisible(true);
        });
    }

    private JButton makeSidebarButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboard d = new AdminDashboard();
            d.setVisible(true);
        });
    }
}
