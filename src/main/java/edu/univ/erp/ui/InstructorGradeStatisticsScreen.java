package edu.univ.erp.ui;

import edu.univ.erp.service.InstructorService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Small UI to request statistics for a sectionId and display average/median/mode
 * Calls InstructorService.getGradeStatistics(sectionId)
 */
public class InstructorGradeStatisticsScreen extends JFrame {

    private final InstructorService service = new InstructorService();
    private final int instructorId;

    public InstructorGradeStatisticsScreen() {
        SessionUser u = SessionManager.getActiveSession();
        this.instructorId = u == null ? -1 : u.getUserId();

        setTitle("Grade Statistics");
        setSize(600, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240,244,249));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Grade Statistics (per Section)", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,8,0));
        add(header, BorderLayout.NORTH);

        JPanel mid = new JPanel();
        mid.setBackground(Color.WHITE);
        mid.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        mid.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        mid.add(new JLabel("Section ID:"), gbc);
        gbc.gridx = 1;
        JTextField secField = new JTextField(10);
        mid.add(secField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton getBtn = new JButton("Get Statistics");
        getBtn.setBackground(new Color(52,152,219));
        getBtn.setForeground(Color.WHITE);
        mid.add(getBtn, gbc);

        add(mid, BorderLayout.CENTER);

        JPanel outPanel = new JPanel();
        outPanel.setBackground(new Color(240,244,249));
        outPanel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        outPanel.setLayout(new GridLayout(3,1,6,6));

        JLabel avg = new JLabel("Average: -");
        JLabel med = new JLabel("Median: -");
        JLabel mode = new JLabel("Mode: -");
        avg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        med.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mode.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        outPanel.add(avg);
        outPanel.add(med);
        outPanel.add(mode);
        add(outPanel, BorderLayout.SOUTH);

        getBtn.addActionListener(e -> {
            try {
                int sid = Integer.parseInt(secField.getText().trim());
                Map<String, Double> stats = service.getGradeStatistics(sid);
                avg.setText(String.format("Average: %.2f", stats.getOrDefault("average", 0.0)));
                med.setText(String.format("Median: %.2f", stats.getOrDefault("median", 0.0)));
                mode.setText(String.format("Mode: %.2f", stats.getOrDefault("mode", 0.0)));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Section ID must be numeric.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error fetching statistics: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
