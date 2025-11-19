package edu.univ.erp.ui;

import edu.univ.erp.domain.SectionDetail;
import edu.univ.erp.service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentViewCatalogScreen extends JFrame {

    private final StudentService studentService;
    private final int studentId;

    public StudentViewCatalogScreen(int studentId) {
        this.studentId = studentId;
        this.studentService = new StudentService();

        setTitle("Course Catalogue");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // light blue

        JLabel header = new JLabel("Course Catalogue", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 26));
        header.setForeground(new Color(10, 70, 160));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Table Model
        String[] cols = {"Section ID", "Course Code", "Title", "Instructor", "Day-Time", "Room", "Capacity"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadCatalog(model);
        setVisible(true);
    }

    private void loadCatalog(DefaultTableModel model) {
        List<SectionDetail> list = studentService.getCatalog();
        model.setRowCount(0);

        for (SectionDetail sd : list) {
            model.addRow(new Object[]{
                    sd.getSectionId(),
                    sd.getCourseCode(),
                    sd.getCourseTitle(),
                    sd.getInstructorName(),
                    sd.getDayTime(),
                    sd.getRoom(),
                    sd.getCapacity()
            });
        }
    }
}
