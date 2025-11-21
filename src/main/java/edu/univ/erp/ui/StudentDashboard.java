package edu.univ.erp.ui;

import edu.univ.erp.auth.AuthService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;
import edu.univ.erp.data.StudentDAO;
import edu.univ.erp.domain.Grade;
import edu.univ.erp.domain.SectionDetail;
import edu.univ.erp.domain.Student;
import edu.univ.erp.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class StudentDashboard extends JFrame {

    private final StudentService studentService = new StudentService();
    private final StudentDAO studentDAO = new StudentDAO();
    private int studentId = studentDAO.getStudentById(SessionManager.getActiveSession().getUserId()).getStudentId();

    // theme colors (blue theme to match login)
    private final Color bg = new Color(240, 244, 249);
    private final Color cardBg = Color.WHITE;
    private final Color primary = new Color(52, 152, 219);
    private final Color sidebar = new Color(30, 61, 89); // #1E3D59

    // main layout components
    private final CardLayout contentCards = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentCards);

    // Catalog table & detail
    private JTable catalogTable;
    private DefaultTableModel catalogModel;
    private JTextArea catalogDetailsArea;

    // Registrations
    private JTable myRegTable;
    private DefaultTableModel myRegModel;

    // Grades
    private JTable gradesTable;
    private DefaultTableModel gradesModel;

    // Profile labels
    private JLabel lblName, lblEmail, lblProgram, lblYear, lblStatus;

    public StudentDashboard() {
        // get logged-in student id from session
        SessionUser user = SessionManager.getActiveSession();
        if (user == null) {
            // fallback: if no session, assume 1 (or you can force login)
            this.studentId = -1;
        } else {
            this.studentId = user.getUserId();
        }

        initializeUI();
        // initial data loads
        loadCatalog();
        loadMyRegistrations();
        loadGrades();
        loadProfile();
    }

    private void initializeUI() {
        setTitle("Student Dashboard — University ERP");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(bg);

        // header
        JLabel header = new JLabel("Student Portal", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(new EmptyBorder(16, 0, 16, 0));
        root.add(header, BorderLayout.NORTH);

        // left sidebar
        JPanel sidebarPanel = buildSidebar();
        root.add(sidebarPanel, BorderLayout.WEST);

        // right content area (card layout)
        contentPanel.setBackground(bg);
        contentPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPanel.add(buildHomePanel(), "home");
        contentPanel.add(buildCatalogPanel(), "catalog");
        contentPanel.add(buildRegisterPanel(), "register");
        contentPanel.add(buildDropPanel(), "drop");
        contentPanel.add(buildTimetablePanel(), "timetable");
        contentPanel.add(buildGradesPanel(), "grades");
        contentPanel.add(buildTranscriptPanel(), "transcript");
        contentPanel.add(buildProfilePanel(), "profile");

        root.add(contentPanel, BorderLayout.CENTER);

        add(root);
    }

    private JPanel buildSidebar() {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(200, 0));
        side.setBackground(sidebar);
        side.setLayout(new BorderLayout());

        // top area with student icon/name
        JPanel top = new JPanel();
        top.setBackground(sidebar);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(18, 12, 18, 12));

        JLabel avatar = new JLabel("👩‍🎓", SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        top.add(avatar);

        JLabel userLabel = new JLabel("Student", SwingConstants.CENTER);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(new EmptyBorder(8, 0, 0, 0));
        top.add(userLabel);

        side.add(top, BorderLayout.NORTH);

        // buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(sidebar);
        btnPanel.setLayout(new GridLayout(0, 1, 6, 6));
        btnPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        String[] names = {
                "Home", "Course Catalog", "Register Section", "Drop Section",
                "My Timetable", "My Grades", "Download Transcript", "Profile", "Logout"
        };
        String[] cards = {
                "home", "catalog", "register", "drop",
                "timetable", "grades", "transcript", "profile", "logout"
        };

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String card = cards[i];
            JButton b = makeSidebarButton(name);
            b.addActionListener(e -> {
                if (card.equals("logout")) {
                    // simple logout: clear session then close
                    AuthService authService = new AuthService();
                    authService.logout();
                    dispose();
                    new LoginFrame().setVisible(true);
                    return;
                }
                contentCards.show(contentPanel, card);
                // lazy reloads when switching
                if (card.equals("catalog")) loadCatalog();
                if (card.equals("timetable")) loadMyRegistrations();
                if (card.equals("grades")) loadGrades();
                if (card.equals("profile")) loadProfile();
            });
            btnPanel.add(b);
        }

        side.add(btnPanel, BorderLayout.CENTER);

        // footer small label
        JLabel footer = new JLabel("University ERP", SwingConstants.CENTER);
        footer.setForeground(new Color(200, 200, 200));
        footer.setBorder(new EmptyBorder(8, 0, 12, 0));
        side.add(footer, BorderLayout.SOUTH);

        return side;
    }

    private JButton makeSidebarButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return b;
    }

    // ------------------ Home Panel ------------------
    private JPanel buildHomePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JPanel card = new JPanel();
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(18, 18, 18, 18)
        ));
        card.setLayout(new BorderLayout(10, 10));

        JLabel welcome = new JLabel("Welcome to the Student Dashboard", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        card.add(welcome, BorderLayout.NORTH);

        JTextArea info = new JTextArea(
                "Use the left menu to navigate.\n\n" +
                        "• Course Catalog: Browse available sections and view details.\n" +
                        "• Register / Drop: Register in or drop a section (backend checks applied).\n" +
                        "• Timetable: Your registered sections.\n" +
                        "• Grades: View your scores and final grades.\n" +
                        "• Transcript: Export CSV of your completed courses."
        );
        info.setEditable(false);
        info.setBackground(cardBg);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        card.add(info, BorderLayout.CENTER);

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    // ------------------ Catalog Panel (table + details) ------------------
    private JPanel buildCatalogPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        // card container
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        // top
        JLabel title = new JLabel("Course Catalog", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        card.add(title, BorderLayout.NORTH);

        // center: split pane with table (top) and details (bottom)
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setDividerSize(6);
        split.setResizeWeight(0.65);

        // table
        String[] cols = {"Section ID", "Course Code", "Course Title", "Instructor", "Semester", "Year", "Capacity"};
        catalogModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        catalogTable = new JTable(catalogModel);
        catalogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(catalogTable);
        split.setTopComponent(tableScroll);

        // detail area
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        catalogDetailsArea = new JTextArea();
        catalogDetailsArea.setEditable(false);
        catalogDetailsArea.setBackground(new Color(250, 250, 250));
        catalogDetailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        detailPanel.add(new JLabel("Section Details:"), BorderLayout.NORTH);
        detailPanel.add(new JScrollPane(catalogDetailsArea), BorderLayout.CENTER);

        split.setBottomComponent(detailPanel);

        card.add(split, BorderLayout.CENTER);

        // bottom: register quick form
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //bottom.setBackground(cardBg);
        //JTextField secIdField = new JTextField(8);
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(primary);
        registerBtn.setForeground(Color.WHITE);
        //bottom.add(new JLabel("Section ID:"));
        //bottom.add(secIdField);
        //bottom.add(registerBtn);

        registerBtn.addActionListener(e -> {
//            String txt = secIdField.getText().trim();
            String txt = "";
            if (txt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Section ID to register.");
                return;
            }
            try {
                int sid = Integer.parseInt(txt);
                String msg = studentService.registerSection(studentId, sid);
                JOptionPane.showMessageDialog(this, msg);
                loadCatalog();
                loadMyRegistrations();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Section ID must be numeric.");
            }
        });

        card.add(bottom, BorderLayout.SOUTH);

        // selection listener updates details area
        catalogTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) updateCatalogDetailsFromSelection();
        });

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    private void updateCatalogDetailsFromSelection() {
        int r = catalogTable.getSelectedRow();
        if (r < 0) {
            catalogDetailsArea.setText("");
            return;
        }
        int sectionId = (Integer) catalogModel.getValueAt(r, 0);
        String courseCode = String.valueOf(catalogModel.getValueAt(r, 1));
        String title = String.valueOf(catalogModel.getValueAt(r, 2));
        String instr = String.valueOf(catalogModel.getValueAt(r, 3));
        String sem = String.valueOf(catalogModel.getValueAt(r, 4));
        String yr = String.valueOf(catalogModel.getValueAt(r, 5));
        String cap = String.valueOf(catalogModel.getValueAt(r, 6));

        StringBuilder sb = new StringBuilder();
        sb.append("Section ID: ").append(sectionId).append("\n");
        sb.append("Course: ").append(courseCode).append(" — ").append(title).append("\n");
        sb.append("Instructor: ").append(instr).append("\n");
        sb.append("Semester: ").append(sem).append("  Year: ").append(yr).append("\n");
        sb.append("Capacity: ").append(cap).append("\n");
        catalogDetailsArea.setText(sb.toString());
    }

    private void loadCatalog() {
        try {
            List<SectionDetail> list = studentService.getCatalog();
            catalogModel.setRowCount(0);
            if (list == null) return;
            for (SectionDetail d : list) {
                catalogModel.addRow(new Object[]{
                        d.getSectionId(),
                        d.getCourseCode() != null ? d.getCourseCode() : d.getCourseTitle(),
                        d.getCourseTitle(),
                        d.getInstructorName(),
                        d.getSemester(),
                        d.getYear(),
                        d.getCapacity()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load catalog: " + e.getMessage());
        }
    }

    // ------------------ Register Panel (alternate) ------------------
    private JPanel buildRegisterPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JPanel card = new JPanel();
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(12, 12, 12, 12)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Register for a Section");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(title);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.setBackground(cardBg);
        JTextField sectionIdField = new JTextField(8);
        JButton regBtn = new JButton("Register");
        regBtn.setBackground(primary);
        regBtn.setForeground(Color.WHITE);

        form.add(new JLabel("Section ID:"));
        form.add(sectionIdField);
        form.add(regBtn);

        regBtn.addActionListener(e -> {
            String txt = sectionIdField.getText().trim();
            if (txt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Section ID.");
                return;
            }
            try {
                int sid = Integer.parseInt(txt);
                String msg = studentService.registerSection(studentId, sid);
                JOptionPane.showMessageDialog(this, msg);
                loadCatalog();
                loadMyRegistrations();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Section ID must be numeric.");
            }
        });

        card.add(form);
        p.add(card, BorderLayout.NORTH);
        return p;
    }

    // ------------------ Drop Panel ------------------
    private JPanel buildDropPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JPanel card = new JPanel();
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(12, 12, 12, 12)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Drop a Registered Section");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(title);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.setBackground(cardBg);
        JTextField secIdField = new JTextField(8);
        JButton dropBtn = new JButton("Drop");
        dropBtn.setBackground(primary);
        dropBtn.setForeground(Color.WHITE);

        form.add(new JLabel("Section ID:"));
        form.add(secIdField);
        form.add(dropBtn);

        dropBtn.addActionListener(e -> {
            String txt = secIdField.getText().trim();
            if (txt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Section ID to drop.");
                return;
            }
            try {
                int sid = Integer.parseInt(txt);
                String msg = studentService.dropSection(studentId, sid);
                JOptionPane.showMessageDialog(this, msg);
                loadCatalog();
                loadMyRegistrations();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Section ID must be numeric.");
            }
        });

        card.add(form);
        p.add(card, BorderLayout.NORTH);
        return p;
    }

    // ------------------ Timetable / My Registrations ------------------
    private JPanel buildTimetablePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JPanel card = new JPanel(new BorderLayout(8,8));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel title = new JLabel("My Registrations / Timetable");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        card.add(title, BorderLayout.NORTH);

        String[] cols = {"Section ID", "Course", "Instructor", "Schedule", "Capacity"};
        myRegModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        myRegTable = new JTable(myRegModel);
        card.add(new JScrollPane(myRegTable), BorderLayout.CENTER);

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    private void loadMyRegistrations() {
        try {
            List<SectionDetail> list = studentService.getMyRegistrations(studentId);
            myRegModel.setRowCount(0);
            if (list == null) return;
            for (SectionDetail d : list) {
                myRegModel.addRow(new Object[]{
                        d.getSectionId(),
                        d.getCourseTitle(),
                        d.getInstructorName(),
                        d.getDayTime(),
                        d.getCapacity()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load registrations: " + e.getMessage());
        }
    }

    // ------------------ Grades Panel ------------------
    private JPanel buildGradesPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JPanel card = new JPanel(new BorderLayout(8,8));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel title = new JLabel("My Grades");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        card.add(title, BorderLayout.NORTH);

        String[] cols = {"Enrollment ID", "Component", "Score", "Final Grade"};
        gradesModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        gradesTable = new JTable(gradesModel);
        card.add(new JScrollPane(gradesTable), BorderLayout.CENTER);

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    private void loadGrades() {
        try {
            List<Grade> list = studentService.getMyGrades(studentId);
            gradesModel.setRowCount(0);
            if (list == null) return;
            for (Grade g : list) {
                gradesModel.addRow(new Object[]{
                        g.getEnrollmentId(),
                        g.getComponent().name(),
                        g.getScore(),
                        g.getFinalGrade()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load grades: " + e.getMessage());
        }
    }

    // ------------------ Transcript Panel ------------------
    private JPanel buildTranscriptPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JPanel card = new JPanel();
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(12, 12, 12, 12)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Download Transcript (CSV)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(title);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(cardBg);
        JButton exportBtn = new JButton("Choose location & Export");
        exportBtn.setBackground(primary);
        exportBtn.setForeground(Color.WHITE);

        exportBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Transcript as CSV");
            int res = chooser.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                boolean ok = studentService.generateTranscript(studentId, f.getAbsolutePath());
                JOptionPane.showMessageDialog(this, ok ? "Transcript exported." : "Export failed.");
            }
        });

        row.add(exportBtn);
        card.add(row);

        p.add(card, BorderLayout.NORTH);
        return p;
    }

    // ------------------ Profile Panel ------------------
    private JPanel buildProfilePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JPanel card = new JPanel();
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(12, 12, 12, 12)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("My Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(title);

        lblName = new JLabel("Name: ");
        lblEmail = new JLabel("Email: ");
        lblProgram = new JLabel("Program: ");
        lblYear = new JLabel("Year: ");
        lblStatus = new JLabel("Status: ");

        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblProgram.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblYear.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(lblName);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(lblEmail);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(lblProgram);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(lblYear);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(lblStatus);

        // logout button
        JButton logout = new JButton("Logout");
        logout.setBackground(primary);
        logout.setForeground(Color.WHITE);
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);
        logout.addActionListener(e -> {
            AuthService authService = new AuthService();
            authService.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });

        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(logout);

        p.add(card, BorderLayout.NORTH);
        return p;
    }

    private void loadProfile() {
        try {
            Student s = studentService.getProfile(studentId);
            if (s == null) {
                lblName.setText("Name: -");
                lblEmail.setText("Email: -");
                lblProgram.setText("Program: -");
                lblYear.setText("Year: -");
                lblStatus.setText("Status: -");
                return;
            }
            lblName.setText("Name: " + s.getName());
            lblEmail.setText("Email: " + s.getUsername());
            lblProgram.setText("Program: " + (s.getProgram() != null ? s.getProgram().toString() : "-"));
            lblYear.setText("Year: " + s.getCurrentYear());
            lblStatus.setText("Status: ACTIVE" );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load profile: " + e.getMessage());
        }
    }

    // ------------------ Launcher (for quick testing) ------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentDashboard d = new StudentDashboard();
            d.setVisible(true);
        });
    }
}



