package edu.univ.erp.util;

import edu.univ.erp.data.*;
import edu.univ.erp.domain.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ExportUtility {
    private static final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private static final GradeDAO gradeDAO = new GradeDAO();
    private static final SectionDAO sectionDAO = new SectionDAO();
    private static final CourseDAO courseDAO = new CourseDAO();
    private static final StudentDAO studentDAO = new StudentDAO();

    public static boolean exportStudentTranscriptCSV(int studentId, String filePath){
        try (FileWriter writer = new FileWriter(filePath + ".csv")){
            // fetch all enrollments for the student with the given student id
            List<Enrollment> studentEnrollments = enrollmentDAO.getAllEnrollmentsForStudent(studentId);
            int totalEnrollments = studentEnrollments.size();

            // get the enrollmentId and sectionId from all enrollments
            List<Integer> enrollmentIds = new ArrayList<Integer>();
            List<Integer> sectionIds = new ArrayList<Integer>();
            for (Enrollment e: studentEnrollments){
                enrollmentIds.add(e.getEnrollmentId());
                sectionIds.add(e.getSectionId());
            }

            // get grade, section for every enrollmentId
            List<List<Grade>> gradeList = new ArrayList<List<Grade>>();
            for (int enrollmentId: enrollmentIds){
                gradeList.add(gradeDAO.getGradesByEnrollmentId(enrollmentId));
            }

            // get section for every enrollment
            List<Section> sectionList = new ArrayList<Section>();
            for (int sectionId: sectionIds){
                sectionList.add(sectionDAO.getSectionById(sectionId));
            }

            // get courses for corresponding section id
            List<Course> courseList = new ArrayList<Course>();
            for (Section s: sectionList){
                courseList.add(courseDAO.getCourseById(s.getCourseId()));
            }


            writer.append("code,title,component,score,final_grade,semester,year\n"); // header
            for (int i = 0; i < totalEnrollments; i++){
                for (Grade g: gradeList.get(i)){
                    String csvLine = "%s,%s,%s,%.2f,%.2f,%s,%d\n";
                    writer.append(String.format(csvLine, courseList.get(i).getCode(), courseList.get(i).getTitle(), g.getComponent().getDbValue(), g.getScore(), g.getFinalGrade(), sectionList.get(i).getSemester(), sectionList.get(i).getYear()));
                }

            }

            return true;
        } catch (IOException e){
            System.err.println("Error exporting student transcript to file: " + e.getMessage());
        } catch (Exception e){
            System.err.println("Error fetching student transcript data: " + e.getMessage());
        }
        return false;
    }

    /**
     * Need to do:
     * - All table backups
     * - All table restore
     */

    public static boolean exportInstructorTranscriptCSV(int instructorId, String filePath){
        try (FileWriter writer = new FileWriter(filePath + ".csv")){

            // get all sections for an instructor
            List<Section> sectionList = sectionDAO.getSectionsByInstructorId(instructorId);

            // for all sections get all enrollments
            List<Enrollment> enrollmentList = new ArrayList<Enrollment>();
            for (Section s: sectionList){
                enrollmentList.addAll(enrollmentDAO.getALlEnrollmentsForSection(s.getSectionId()));
            }
            int totalEnrollments = enrollmentList.size();

            // for all enrollments, get the grades and student roll calls

            List<Integer> rollList = new ArrayList<Integer>();
            List<List<Grade>> gradeList = new ArrayList<List<Grade>>();
            for (Enrollment e: enrollmentList){
                gradeList.add(gradeDAO.getGradesByEnrollmentId(e.getEnrollmentId()));
                rollList.add(studentDAO.getStudentById(e.getStudentId()).getRollNo());
            }

            writer.append("section_name,roll_no,component,score,final_grade,semester,year\n");
            for (int i = 0; i < totalEnrollments; i++){
                for (Grade g: gradeList.get(i)){
                    String csvLine = "%s,%d,%s,%.2f,%.2f,%s,%d\n";
                    writer.append(String.format(csvLine,sectionList.get(i).getName(), rollList.get(i), g.getComponent().toString(), g.getScore(), g.getFinalGrade(), sectionList.get(i).getSemester(), sectionList.get(i).getYear()));
                }
            }

            return true;

        } catch (IOException e){
            System.err.println("Error exporting instructor transcript to file: " + e.getMessage());
        } catch (Exception e){
            System.err.println("Error fetching instructor transcript data: " + e.getMessage());
        }
        return false;
    }

    public static boolean exportFullDatabaseBackup(String baseDirectory){
        try{
            exportTableToCSV("users_auth", baseDirectory + "/users_auth_backup.csv", true);
            exportTableToCSV("students", baseDirectory+"/students_backup.csv", false);
            exportTableToCSV("instructors", baseDirectory+"/students_backup.csv", false);
            exportTableToCSV("courses", baseDirectory+"/students_backup.csv", false);
            exportTableToCSV("sections", baseDirectory+"/students_backup.csv", false);
            exportTableToCSV("enrollments", baseDirectory+"/students_backup.csv", false);
            exportTableToCSV("grades", baseDirectory+"/students_backup.csv", false);

            System.out.println("Database Backup Completed To: " + baseDirectory);

            return true;
        } catch (Exception e){
            System.err.println("Error during backup: " + e.getMessage());
            return false;
        }
    }

    public static void exportTableToCSV(String tableName, String filePath, boolean isAuthDB){
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = (isAuthDB) ? DBConnection.getAuthConnection(): DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        FileWriter writer = new FileWriter(filePath)){
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // write headers
            for (int i = 1; i <= columnCount; i++){
                writer.append(meta.getColumnName(i));
                if (i < columnCount) writer.append(",");
            }
            writer.append("\n");

            // write data
            while (rs.next()){
                for (int i = 1; i <= columnCount; i++){

                    writer.append(rs.getString(i) == null ? "": rs.getString(i));
                    if (i < columnCount) writer.append(",");
                }
                writer.append("\n");
            }
            System.out.println("Exported table " + tableName + " : " + filePath);

        } catch (SQLException e){
            System.err.println("Error fetching table " + tableName + ": "+ e.getMessage());
        } catch (IOException e){
            System.err.println("Error writing to file: " + e.getMessage());
        } catch (Exception e){
            System.err.println("Error during backup: " + e.getMessage());
        }
    }

    public static boolean restoreTableFromCSV(String tableName, String filePath, boolean isAuth) {
        String columnList = getColumnList(tableName, isAuth);
        String sql = String.format(
                "SET FOREIGN_KEY_CHECKS=0; " +
                        "LOAD DATA INFILE '%s' " +
                        "INTO TABLE %s " +
                        "FIELDS TERMINATED BY ',' " +
                        "LINES TERMINATED BY '\\n' " +
                        "IGNORE 1 LINES (%s); " +
                        "SET FOREIGN_KEY_CHECKS=1;",
                filePath.replace("\\", "\\\\"),
                tableName,
                columnList
        );

        try (Connection conn = (isAuth) ? DBConnection.getAuthConnection(): DBConnection.getERPConnection();
        Statement stmt = conn.createStatement()){
            stmt.executeQuery(sql);
            System.out.println("Table restored: " + tableName);
            return true;
        } catch (Exception e){
            System.err.println("Error restoring table " + tableName + ": " + e.getMessage());
            return false;
        }
    }

    public static String getColumnList(String tableName, boolean isAuth){
        try (Connection conn = isAuth ? DBConnection.getAuthConnection(): DBConnection.getERPConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW COLUMNS FROM" + tableName)){
            StringBuilder sb = new StringBuilder();
            while (rs.next()){
                sb.append(rs.getString("Field")).append(",");
            }
            return sb.substring(0, sb.length()-1);

        } catch (Exception e){
            System.err.println("Error retrieving columns for " + tableName + ": " + e.getMessage());
            return "*";
        }
    }
}
