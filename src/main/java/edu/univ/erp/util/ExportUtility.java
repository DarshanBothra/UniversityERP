package edu.univ.erp.util;

import edu.univ.erp.data.*;
import edu.univ.erp.domain.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ExportUtility {
    private static final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private static final GradeDAO gradeDAO = new GradeDAO();
    private static final SectionDAO sectionDAO = new SectionDAO();
    private static final CourseDAO courseDAO = new CourseDAO();

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
            List<Grade> gradeList = new ArrayList<Grade>();
            for (int enrollmentId: enrollmentIds){
                gradeList.add(gradeDAO.getGradeByEnrollmentId(enrollmentId));
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

            /**
             * Indexing for the gradeList and sectionList are the same, hence the data corresponds to the same transcript row
             */

            writer.append("code,title,component,score,final_score,semester,year\n"); // header
            for (int i = 0; i <totalEnrollments; i++){
                String csvLine = "%s,%s,%s,%.2f,%.2f,%s,%d\n";
                writer.append(String.format(csvLine, courseList.get(i).getCode(), courseList.get(i).getTitle(), gradeList.get(i).getComponent().getDbValue(), gradeList.get(i).getScore(), gradeList.get(i).getFinalGrade(), sectionList.get(i).getSemester(), sectionList.get(i).getYear()));
            }


        } catch (IOException e){
            System.err.println("Error exporting transcript to file: " + e.getMessage());
        } catch (Exception e){
            System.err.println("Error fetching transcript data: " + e.getMessage());
        }
        return false;
    }

    /**
     * Need to do:
     * - Instructor Transcript
     * - All table backups
     * - All table restore
     */
}
