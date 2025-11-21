package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import edu.univ.erp.domain.Student;
import edu.univ.erp.service.StudentService;

public class DownloadTranscriptFrame extends JFrame {

    private final int studentId;
    private final StudentService studentService = new StudentService();

    public DownloadTranscriptFrame(int studentId, StudentService studentService) {
        this.studentId = studentId;

        setTitle("Download Transcript");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(220, 235, 255));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Download Academic Transcript", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(new Color(0, 70, 140));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        JButton chooseBtn = new JButton("Choose Save Location");
        chooseBtn.setFont(new Font("Arial", Font.BOLD, 16));
        chooseBtn.setBackground(new Color(0, 102, 204));
        chooseBtn.setForeground(Color.WHITE);
        chooseBtn.setFocusPainted(false);

        chooseBtn.addActionListener(e -> openSaveDialog());

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(220, 235, 255));
        centerPanel.add(chooseBtn);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void openSaveDialog() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Transcript");

        // ✅ ONLY CHANGE YOU ASKED FOR:
        chooser.setBackground(Color.WHITE);
        chooser.setForeground(Color.BLACK);

        int result = chooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String path = file.getAbsolutePath();

            if (!path.endsWith(".csv")) {
                path += ".csv";
            }
            boolean ok = studentService.generateTranscript(studentId, path.substring(0, path.length()-4));
            System.out.println(ok);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Transcript saved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to generate transcript.");
            }
        }
    }
}

