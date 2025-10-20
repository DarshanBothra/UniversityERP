package edu.univ.erp.util;

import edu.univ.erp.data.EnrollmentDAO;
import edu.univ.erp.data.SectionDAO;
import edu.univ.erp.domain.Role;
import edu.univ.erp.access.AccessControl;
import java.time.LocalDateTime;

/**
 * ValidationUtility provides reusable validation and rule-checking
 * methods for the ERP Service layer.
 *
 * It encapsulates domain-specific checks such as section capacity,
 * enrollment duplication, and deadline enforcement.
 */

public class ValidationUtility {

    private static final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private static final SectionDAO sectionDAO = new SectionDAO();

    public static boolean isValidCapacity(int capacity){
        return capacity > 0;
    }

    public static boolean isSectionFull(int sectionId){
        return sectionDAO.isSectionFull(sectionId);
    }

    public static boolean isDuplicateEnrollment(int studentId, int sectionId){
        return !enrollmentDAO.isStudentEnrolledInSection(studentId, sectionId);
    }

    public static boolean isBeforeDeadline(LocalDateTime deadline){
        return LocalDateTime.now().isBefore(deadline) || LocalDateTime.now().isEqual(deadline);
    }

    public static boolean isMaintenanceActiveForRole(Role role){
        try {
            if (role == null) {
                return false;
            }
            return AccessControl.isViewOnlyMode();
        } catch (Exception e){
            System.err.println("Error checking maintenance status: " + e.getMessage());
        }
        return false;
    }

}
