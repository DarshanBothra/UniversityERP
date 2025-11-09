package edu.univ.erp.api;

import edu.univ.erp.auth.hash.PasswordUtility;
import edu.univ.erp.domain.*;
import edu.univ.erp.service.AdminService;
import edu.univ.erp.util.ValidationUtility;

import java.util.List;


public class AdminAPI {
    private final AdminService adminService = new AdminService();

    public boolean createUser(String username, String role, String name, String plainPassword, String departmentOrProgram, int yearOrNull, int rollOrNull){
        try {
            adminService.createUser(username, Role.fromString(role), name, plainPassword, departmentOrProgram, yearOrNull, rollOrNull);
        } catch (Exception e){
            System.err.println("Error creating new user: " + e.getMessage());
        }
        return false;
    }

    public boolean createCourse(String code, String title, int credits){
        try {
            return adminService.createCourse(code, title, credits);
        } catch (Exception e){
            System.err.println("Error creating course: " + e.getMessage());
        }
        return false;
    }

    public boolean createSection(int courseId, int instructorId, String name, int capacity, int year, String dayTime, String room, String semester){
        try{
            adminService.createSection(courseId, instructorId, name, capacity, year, dayTime, room, semester);
        } catch (Exception e){
            System.err.println("Error creating section: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String username){
        return adminService.deleteUser(username);
    }

    public boolean deleteCourse(int courseId){
        return adminService.deleteCourse(courseId);
    }

    public boolean deleteSection(int sectionId){
        return adminService.deleteSection(sectionId);
    }

    public boolean editCourse(int courseId, String code, String newTitle, int newCredits){

        try {
            return adminService.editCourse(courseId, code, newTitle, newCredits);
        } catch (Exception e){
            System.err.println("Error editing course details: " + e.getMessage());
            return false;
        }
    }

    public boolean editSection(int sectionId, int newCourseId, int newInstructorId, String newName, String newDayTime, String newRoom, int newCapacity, int newYear, String newSemester) {
        try {
            return adminService.editSection(sectionId, newCourseId, newInstructorId, newName, newDayTime, newRoom, newCapacity, newYear, newSemester);
        } catch (Exception e){
            System.err.println("Error updating section details: " + e.getMessage());
            return false;
        }
    }

    public boolean assignInstructor(int sectionId, int instructorId){
        try {
            return adminService.assignInstructor(sectionId, instructorId);
        } catch (Exception e){
            System.err.println("Error assigning instructor: " + e.getMessage());
            return false;
        }
    }

    public boolean toggleMaintenance(){
        try {
            return adminService.toggleMaintenance();
        } catch (Exception e){
            System.err.println("Error toggling maintenance: " + e.getMessage());
            return false;
        }
    }

    public List<User> getAllUsers(){
        try {
            return adminService.getAllUsers();
        } catch (Exception e){
            System.err.println("Error fetching users: " + e.getMessage());
            return List.of();
        }
    }

    public boolean resetLoginAttempts(String username){
        try {
            return adminService.resetLoginAttempts(username);
        } catch (Exception e){
            System.err.println("Error resting login attempts: " + e.getMessage());
            return false;
        }
    }

}
