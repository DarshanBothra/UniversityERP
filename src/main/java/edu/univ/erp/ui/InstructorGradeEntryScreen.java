package edu.univ.erp.ui;

import edu.univ.erp.domain.Component;
import edu.univ.erp.service.InstructorService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;

import javax.swing.*;
import java.awt.*;

/**
 * Simple grade entry UI:
 * Instructor selects an enrollment ID, picks component and enters score.
 * Calls InstructorService.enterScore(instructorId, enrollmentId, component, score)
 */
public class InstructorGradeEntryScreen extends JFrame {

    private final InstructorService service = new InstructorService();
    private final int instructorId;

    public InstructorGradeEntryScreen() {
        SessionUser u = SessionManager.getActiveSession();
        this.instructorId = u == null ? -1 : u.getUserId();

        setTitle("Enter Grades");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 244, 249));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Enter Student Score", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        form.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Enrollment ID:"), gbc);
        gbc.gridx = 1;
        JTextField enrollmentField = new JTextField(12);
        form.add(enrollmentField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Component:"), gbc);
        gbc.gridx = 1;
        JComboBox<Component> compBox = new JComboBox<>(Component.values());
        form.add(compBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Score (numeric):"), gbc);
        gbc.gridx = 1;
        JTextField scoreField = new JTextField(12);
        form.add(scoreField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton submit = new JButton("Submit Score");
        submit.setBackground(new Color(52,152,219));
        submit.setForeground(Color.WHITE);
        form.add(submit, gbc);

        add(form, BorderLayout.CENTER);

        submit.addActionListener(e -> {
            try {
                int enrollmentId = Integer.parseInt(enrollmentField.getText().trim());
                Component comp = (Component) compBox.getSelectedItem();
                double score = Double.parseDouble(scoreField.getText().trim());

                String msg = service.enterScore(instructorId, enrollmentId, comp, score);
                JOptionPane.showMessageDialog(this, msg);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for enrollment id and score.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
