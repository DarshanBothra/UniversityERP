package edu.univ.erp.ui;

import edu.univ.erp.data.InstructorDAO;
import edu.univ.erp.service.InstructorService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Small UI to export instructor's grades to a CSV file
 * Calls InstructorService.exportGrades(instructorId, filePath)
 */
public class InstructorExportGradesScreen extends JFrame {

    private final InstructorService service = new InstructorService();
    private final int instructorId;

    public InstructorExportGradesScreen() {
        SessionUser u = SessionManager.getActiveSession();
        InstructorDAO instructorDAO = new InstructorDAO();
        this.instructorId = u == null ? -1 : instructorDAO.getInstructorById(u.getUserId()).getInstructorId();

        setTitle("Export Grades");
        setSize(600, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240,244,249));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Export Grades (CSV)", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(header, BorderLayout.NORTH);

        JPanel mid = new JPanel();
        mid.setBackground(new Color(240,244,249));
        JButton choose = new JButton("Choose File & Export");
        choose.setBackground(new Color(52,152,219));
        choose.setForeground(Color.BLACK);
        mid.add(choose);
        add(mid, BorderLayout.CENTER);

        choose.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Grades CSV");
            int res = chooser.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                String path = f.getAbsolutePath();
                if (!path.toLowerCase().endsWith(".csv")) path += ".csv";
                boolean ok = service.exportGrades(instructorId, path);
                JOptionPane.showMessageDialog(this, ok ? "Export successful." : "Export failed.");
            }
        });

        setVisible(true);
    }
}

