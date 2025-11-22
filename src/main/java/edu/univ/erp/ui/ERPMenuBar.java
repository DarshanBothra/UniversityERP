package edu.univ.erp.ui;

import javax.swing.*;

public class ERPMenuBar extends JMenuBar {

    public ERPMenuBar() {
        JMenu file = new JMenu("File");
        JMenuItem logout = new JMenuItem("Logout");
        JMenuItem exit = new JMenuItem("Exit");

        exit.addActionListener(e -> System.exit(0));
        logout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
        });

        file.add(logout);
        file.add(exit);

        add(file);
    }
}
