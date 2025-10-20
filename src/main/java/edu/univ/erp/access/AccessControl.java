package edu.univ.erp.access;

import edu.univ.erp.auth.*;
import edu.univ.erp.auth.session.*;
import edu.univ.erp.data.SettingDAO;
import edu.univ.erp.domain.Role;

public class AccessControl {
    private static final SettingDAO settingDAO = new SettingDAO();

    private AccessControl(){}

    // check if maintenance is on
    public static boolean isMaintenanceOn(){
        String value = settingDAO.getSettingByKey("maintenance_on").getValue();
        return value!= null && value.equalsIgnoreCase("on");
    }

    // check if there is an active session
    public static boolean isLoggedIn(){
        return SessionManager.isLoggedIn();
    }

    // get session user
    public static SessionUser getActiveSession(){
        return SessionManager.getActiveSession();
    }

    // ------ Role based Access Checks ------

    // check for admin role
    public static boolean isAdmin(){
        return getActiveSession().getRole() == Role.ADMIN;
    }

    // check for instructor role
    public static boolean isInstructor(){
        return getActiveSession().getRole() == Role.INSTRUCTOR;
    }

    // check for student role
    public static boolean isStudent(){
        return getActiveSession().getRole() == Role.STUDENT;
    }

    // ------ Student Operation Based Permission checks ------

    // Can browse course catalog: always allowed if student
    public static boolean canBrowseCatalog(){
        return isStudent();
    }

    /**
     * Can register for a section:
     * - student
     * - capacity is not full or repeated enrollment (checked in logic) &
     * - maintenance is off
     */

    public static boolean canRegisterSection(){
        return isStudent() && (!isMaintenanceOn());
    }

    /**
     * Can Drop a section:
     * - student
     * - done before deadline (checked in logic)
     * - maintenance is off
     */

    public static boolean canDropSection(){
        return isStudent() && (!isMaintenanceOn());
    }

    /**
     * Can view timetable:
     * - if student
     */

    public static boolean canViewTimeTable(){
        return isStudent();
    }

    /**
     * Can view grades:
     * - if student
     */

    public static boolean canViewGrades(){
        return isStudent();
    }

    /**
     * Can download transcript:
     * - if student
     */

    public static boolean canDownloadTranscript(){
        return isStudent();
    }

    // ------ Instructor Operation Based Permissions checks ------

    /**
     * Can View own sections:
     * - Instructor
     * - Sections must have instructor listed as 'section instructor' (handled in logic)
     */

    public static boolean canViewSections(){
        return isInstructor();
    }

    public static boolean canEnterScores(){
        return isInstructor() && (!isMaintenanceOn());
    }

    public static boolean canComputeFinalGrade(){
        return isInstructor() && (!isMaintenanceOn());
    }

    public static boolean canEditSection(int sectionInstructorId, int instructorId){
        return (sectionInstructorId == instructorId) && (!isMaintenanceOn()) && (isInstructor());
    }

    // ------ Admin Operation Based Permission checks ------

    public static boolean canCreatesUser(){
        return isAdmin();
    }

    public static boolean canCreateCourse(){
        return isAdmin();
    }

    public static boolean canCreateSection(){
        return isAdmin();
    }

    public static boolean canEditSection(){
        return isAdmin();
    }

    public static boolean canToggleMaintenance(){
        return isAdmin();
    }

    public static boolean canModifySettings(){
        return isAdmin();
    }

// ------ Generic Access Permissions ------

    public static boolean canAccessDashboard(){
        return isLoggedIn();
    }

    public static boolean isViewOnlyMode(){
        return isMaintenanceOn() && (isInstructor() || isStudent());
    }
}