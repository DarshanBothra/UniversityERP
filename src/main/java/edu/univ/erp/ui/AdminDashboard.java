package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;
import edu.univ.erp.ui.common.MainFrame;
import edu.univ.erp.ui.common.MenuBar;

public class AdminDashboard extends JPanel {
    public AdminDashboard(MainFrame frame) {
        setLayout(new BorderLayout());
        frame.setJMenuBar(new MenuBar(e -> frame.showScreen("login")));

        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel buttons = new JPanel();
        JButton manageUsers = new JButton("Manage Users");
        JButton settings = new JButton("Settings");
        buttons.add(manageUsers);
        buttons.add(settings);

        add(title, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);

        manageUsers.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "User Management coming soon...")
        );
        settings.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Settings Panel coming soon...")
        );
    }
}
