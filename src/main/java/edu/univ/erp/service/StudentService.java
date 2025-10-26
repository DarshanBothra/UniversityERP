package edu.univ.erp.service;

import edu.univ.erp.data.*;
import edu.univ.erp.domain.*;
import edu.univ.erp.util.ExportUtility;
import edu.univ.erp.util.ValidationUtility;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;
    private final SectionDAO sectionDAO;
    private final EnrollmentDAO enrollmentDAO;
    private final GradeDAO gradeDAO;
    private final SettingDAO settingDAO;

    public StudentService(){
        this.studentDAO = new StudentDAO();
        this.sectionDAO = new SectionDAO();
        this.enrollmentDAO = new EnrollmentDAO();
        this.gradeDAO = new GradeDAO();
        this.settingDAO = new SettingDAO();
    }

    /**
     * Fetch full catalog (for UI Display)
     */

    public List<SectionDetail> getCatalog(){
        return sectionDAO.getAllSectionsWithDetails();
    }

    public String registerSection(int studentId, int sectionId){
        if (ValidationUtility.isMaintenanceActiveForRole(Role.STUDENT)){
            return "Registration is disabled during maintenance.";
        }
        if (!ValidationUtility.isBeforeRegistrationDeadline(settingDAO)){
            return "Registration Deadline has passed.";
        }
        if (ValidationUtility.isDuplicateEnrollment(studentId, sectionId)){
            return "You are already enrolled in this section.";
        }
        if (ValidationUtility.isSectionFull(sectionId)){
            return "This section is full.";
        }
        Enrollment e = new Enrollment(studentId, sectionId, Status.ENROLLED);
        int enrollmentId = enrollmentDAO.insertEnrollment(e);
        if (enrollmentId >= 0){
            return "Registration Successful.";
        }
        return "Registration Failed.";
    }

    public String dropSection(int studentId, int sectionId){
        if (ValidationUtility.isMaintenanceActiveForRole(Role.STUDENT)){
            return "Drop operation is disabled during maintenance.";
        }
        if (!ValidationUtility.isBeforeDropDeadline(settingDAO)){
            return "Drop deadline has passed.";
        }
        List <Enrollment> enrollments = enrollmentDAO.getAllEnrollmentsForStudent(studentId);
        for (Enrollment e: enrollments){
            if (e.getSectionId() == sectionId){
                boolean deleted = enrollmentDAO.deleteEnrollment(e.getEnrollmentId());
                return deleted ? "Section dropped successfully.": "Failed to drop section.";
            }
        }
        return "You are not registered in this section.";
    }

    /**
     * To be used to display timetable.
     * @param studentId
     * @return
     */
    public List<SectionDetail> getMyRegistrations(int studentId){
        List<SectionDetail> retList = new ArrayList<SectionDetail>();
        List<Enrollment> enrollments = enrollmentDAO.getAllEnrollmentsForStudent(studentId);
        List<SectionDetail> allDetails = sectionDAO.getAllSectionsWithDetails();
        for (Enrollment e: enrollments){
            Section s = sectionDAO.getSectionById(e.getSectionId());
            if (s != null){
                allDetails.stream().filter(d -> d.getSectionId() == s.getSectionId()).findFirst().ifPresent(retList::add);
            }
        }
        return retList;
    }

    public List<Grade> getMyGrades(int studentId){
        List<Grade> retList = new ArrayList<Grade>();

        List<Enrollment> enrollments = enrollmentDAO.getAllEnrollmentsForStudent(studentId);

        for (Enrollment e: enrollments){
            List<Grade> grades = gradeDAO.getGradesByEnrollmentId(e.getEnrollmentId());
            retList.addAll(grades);
        }

        return retList;
    }

    public boolean generateTranscript(int studentId, String filePath){
        return ExportUtility.exportStudentTranscriptCSV(studentId, filePath);
    }

    public Student getProfile(int studentId){
        return studentDAO.getStudentById(studentId);
    }

}
