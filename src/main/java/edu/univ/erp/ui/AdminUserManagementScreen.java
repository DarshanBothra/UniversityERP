package edu.univ.erp.ui;

import edu.univ.erp.domain.LoginStatus;
import edu.univ.erp.domain.Role;
import edu.univ.erp.domain.Status;
import edu.univ.erp.domain.User;
import edu.univ.erp.service.AdminService;
import edu.univ.erp.auth.session.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * AdminUserManagementScreen
 * - Create users (student / instructor)
 * - List all users
 * - Delete user
 * - Reset login attempts
 *
 * Uses AdminService.createUser, getAllUsers, deleteUser, resetLoginAttempts
 */
public class AdminUserManagementScreen extends JFrame {

    private final AdminService adminService = new AdminService();
    private final int adminId = SessionManager.getActiveSession() == null ? -1 : SessionManager.getActiveSession().getUserId();

    private DefaultTableModel userModel;
    private JTable userTable;

    // form fields
    private final JTextField txtUsername = new JTextField(12);
    private final JPasswordField txtPassword = new JPasswordField(12);
    private final JComboBox<String> cbRole = new JComboBox<>(new String[]{"STUDENT", "INSTRUCTOR"});
    private final JTextField txtName = new JTextField(12);
    private final JTextField txtDeptProg = new JTextField(12);
    private final JTextField txtYear = new JTextField(4);
    private final JTextField txtRoll = new JTextField(6);

    public AdminUserManagementScreen() {
        setTitle("Admin — User Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240,244,249));
        setLayout(new BorderLayout(10,10));

        // header
        JLabel header = new JLabel("User Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(header, BorderLayout.NORTH);

        // center split: left = form, right = table
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(360);
        split.setLeftComponent(buildFormPanel());
        split.setRightComponent(buildTablePanel());
        add(split, BorderLayout.CENTER);

        // bottom controls
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(new Color(240,244,249));
        JButton btnDelete = new JButton("Delete Selected User");
        JButton btnResetAttempts = new JButton("Reset Login Attempts");
        btnDelete.setBackground(new Color(52,152,219));
        btnDelete.setForeground(Color.WHITE);
        btnResetAttempts.setBackground(new Color(52,152,219));
        btnResetAttempts.setForeground(Color.WHITE);

        bottom.add(btnResetAttempts);
        bottom.add(btnDelete);

        add(bottom, BorderLayout.SOUTH);

        // actions
        btnDelete.addActionListener(e -> deleteSelectedUser());
        btnResetAttempts.addActionListener(e -> resetAttemptsForSelectedUser());

        // initial load
        loadUsers();

        setVisible(true);
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                BorderFactory.createEmptyBorder(12,12,12,12)
        ));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("Create New User");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(t);
        p.add(Box.createRigidArea(new Dimension(0,8)));

        JPanel row;

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Username:"));
        row.add(txtUsername);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Password:"));
        row.add(txtPassword);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Role:"));
        row.add(cbRole);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Full Name:"));
        row.add(txtName);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Dept / Program:"));
        row.add(txtDeptProg);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Year (students):"));
        row.add(txtYear);
        p.add(row);

        row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel("Roll (students):"));
        row.add(txtRoll);
        p.add(row);

        p.add(Box.createRigidArea(new Dimension(0,10)));

        JButton createBtn = new JButton("Create User");
        createBtn.setBackground(new Color(52,152,219));
        createBtn.setForeground(Color.WHITE);
        createBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        createBtn.addActionListener(e -> createUserAction());
        p.add(createBtn);

        return p;
    }

    private JPanel buildTablePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(240,244,249));
        p.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

        userModel = new DefaultTableModel(new String[]{"User ID", "Username", "Role", "Status", "Last Login"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        userTable = new JTable(userModel);
        userTable.setRowHeight(26);
        JScrollPane sp = new JScrollPane(userTable);
        p.add(sp, BorderLayout.CENTER);

        return p;
    }

    private void loadUsers() {
        try {
            userModel.setRowCount(0);
            List<User> users = adminService.getAllUsers();
            if (users == null) return;
            for (User u : users) {
                String username = u.getUserId();
                LoginStatus status = adminService.authStore.getCurrentStatus(username);
                userModel.addRow(new Object[]{
                        adminService.authStore.getUserId(username),
                        username,
                        u.getRole().name(),
                        status == null ? "-" : status.toString(),
                        adminService.authStore.getLastLogin(username) == null ? "-" : adminService.authStore.getLastLogin(username)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createUserAction() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String roleStr = (String) cbRole.getSelectedItem();
        String name = txtName.getText().trim();
        String deptprog = txtDeptProg.getText().trim();
        int year = -1;
        int roll = -1;
        try {
            if (!txtYear.getText().trim().isEmpty()) year = Integer.parseInt(txtYear.getText().trim());
            if (!txtRoll.getText().trim().isEmpty()) roll = Integer.parseInt(txtRoll.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year and Roll must be numeric.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || roleStr == null) {
            JOptionPane.showMessageDialog(this, "Please fill required fields: username, password, name, role.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Role role = Role.fromString(roleStr);

        boolean ok = adminService.createUser(username, role, name, password, deptprog, year, roll);
        JOptionPane.showMessageDialog(this, ok ? "User created successfully." : "Failed to create user.");
        if (ok) {
            clearForm();
            loadUsers();
        }
    }

    private void clearForm() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtName.setText("");
        txtDeptProg.setText("");
        txtYear.setText("");
        txtRoll.setText("");
    }

    private void deleteSelectedUser() {
        int r = userTable.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
            return;
        }
        String username = String.valueOf(userModel.getValueAt(r, 1));
        System.out.println(username);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete user '" + username + "' ?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        boolean ok = adminService.deleteUser(username);
        JOptionPane.showMessageDialog(this, ok ? "User deleted." : "Failed to delete user.");
        if (ok) loadUsers();
    }

    private void resetAttemptsForSelectedUser() {
        int r = userTable.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a user to reset attempts.");
            return;
        }
        String username = String.valueOf(userModel.getValueAt(r, 1));
        boolean ok = adminService.resetLoginAttempts(username);
        JOptionPane.showMessageDialog(this, ok ? "Reset successful." : "Reset failed.");
    }
}
