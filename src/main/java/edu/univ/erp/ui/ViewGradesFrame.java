package edu.univ.erp.ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import edu.univ.erp.service.StudentService;
import edu.univ.erp.domain.Grade;

public class ViewGradesFrame extends JFrame {

    private final int studentId;
    private final StudentService studentService;

    public ViewGradesFrame(int studentId, StudentService studentService) {
        this.studentId = studentId;
        this.studentService = studentService;

        setTitle("My Grades");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(220, 235, 255));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("My Grades", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 28));
        header.setForeground(new Color(0, 70, 140));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Table
        String[] columns = {"Course Code", "Course Title", "Grade", "Semester"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        loadGrades(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadGrades(DefaultTableModel model) {
        List<Grade> gradeList = studentService.getMyGrades(studentId);

        for (Grade g : gradeList) {
            model.addRow(new Object[]{
                    g.getCourseCode(),
                    g.getCourseTitle(),
                    g.getLetterGrade(),
                    g.getSemester()
            });
        }
    }
}

