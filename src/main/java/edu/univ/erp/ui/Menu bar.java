package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    public MenuBar(ActionListener logoutListener) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(logoutListener);
        fileMenu.add(logout);
        add(fileMenu);
    }
}
