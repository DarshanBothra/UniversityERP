package edu.univ.erp.service;

import edu.univ.erp.access.AccessControl;
import edu.univ.erp.domain.*;
import edu.univ.erp.data.*;
import edu.univ.erp.auth.store.AuthStore;
import edu.univ.erp.auth.hash.PasswordUtility;
import edu.univ.erp.access.MaintenanceManager;
import edu.univ.erp.util.ValidationUtility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class AdminService {
    public final StudentDAO studentDAO;
    public final InstructorDAO instructorDAO;
    public final CourseDAO courseDAO;
    public final SectionDAO sectionDAO;
    public final SettingDAO settingDAO;
    public final AuthStore authStore;

    public AdminService(){
        this.studentDAO = new StudentDAO();
        this.authStore = new AuthStore();
        this.instructorDAO = new InstructorDAO();
        this.courseDAO = new CourseDAO();
        this.sectionDAO = new SectionDAO();
        this.settingDAO = new SettingDAO();
    }

    public boolean createUser(String username, Role role, String name, String plainPassword, String departmentOrProgram, int yearOrNull, int rollOrNull){
        try {
            if (authStore.userExists(username)){
                System.err.println("User already exists");
                return false;
            }

            String hashedPassword = PasswordUtility.hashPassword(plainPassword);
            int userId = authStore.insertUser(username, role, hashedPassword);

            if (role == Role.STUDENT){
                Student s = new Student(userId, name, username, rollOrNull, Program.fromString(departmentOrProgram), yearOrNull);
                studentDAO.insertStudent(s);
            } else if (role == Role.INSTRUCTOR){
                Instructor i = new Instructor(userId, name, username, departmentOrProgram);
                instructorDAO.insertInstructor(i);
            }
            System.out.printf("New %s created successfully (username: %s, id: %d)\n", role, username, userId);
            return true;
        } catch (Exception e){
            System.err.println("Error creating new user: " + e.getMessage());
        }
        return false;
    }

    public boolean createCourse(String code, String title, int credits){
        try {
            Course c = new Course(code, title, credits);
            int id = courseDAO.insertCourse(c);
            return id >= 0;
        } catch (Exception e){
            System.err.println("Error creating course: " + e.getMessage());
        }
        return false;
    }

    public boolean createSection(int courseId, int instructorId, String name, int capacity, int year, String dayTime, String room, String semester){
        if (!ValidationUtility.isValidCapacity(capacity)){
            System.err.println("Invalid Capacity");
            return false;
        }
        try{
            Section s = new Section(courseId, instructorId, name, capacity, year, dayTime, room, semester);
            int sectionId = sectionDAO.insertSection(s);
            return sectionId >= 0;
        } catch (Exception e){
            System.err.println("Error creating section: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String username){
        return authStore.deleteUser(username);
    }

    public boolean deleteCourse(int courseId){
        return courseDAO.deleteCourse(courseDAO.getCourseById(courseId).getCode());
    }

    public boolean deleteSection(int sectionId){
        return sectionDAO.deleteSection(sectionId);
    }

    public boolean editCourse(int courseId, String code, String newTitle, int newCredits){

        boolean updatedCredits = courseDAO.updateCredits(code, newCredits);
        boolean updatedTitle = courseDAO.updateTitle(code, newTitle);

        if (updatedCredits) {
            System.out.println("Updated course credits");
        }
        if (updatedTitle) {
            System.out.println("Updated course title");
        }
        return updatedCredits || updatedTitle;
    }

    public boolean editSection(int sectionId, int newCourseId, int newInstructorId, String newName, String newDayTime, String newRoom, int newCapacity, int newYear, String newSemester){
        boolean updatedCourse =  sectionDAO.updateCourseId(sectionId, newCourseId);
        boolean updatedInstructor = sectionDAO.updateInstructorId(sectionId, newInstructorId);
        boolean updatedName = sectionDAO.updateName(sectionId, newName);
        boolean updatedDayTime = sectionDAO.updateDayTime(sectionId, newDayTime);
        boolean updatedRoom = sectionDAO.updateRoom(sectionId, newRoom);
        boolean updatedCapacity = sectionDAO.updateCapacity(sectionId, newCapacity);
        boolean updatedSemester = sectionDAO.updateSemester(sectionId, newSemester);
        boolean updatedYear = sectionDAO.updateYear(sectionId, newYear);

        if (updatedCourse){
            System.out.println("Section course updated");
        }
        if (updatedCourse){
            System.out.println("Section course updated");
        }
        if (updatedInstructor){
            System.out.println("Section instructor updated");
        }
        if (updatedName){
            System.out.println("Section name updated");
        }
        if (updatedDayTime){
            System.out.println("Section day time updated");
        }
        if (updatedRoom){
            System.out.println("Section room updated");
        }
        if (updatedCapacity){
            System.out.println("Section capacity updated");
        }
        if (updatedYear){
            System.out.println("Section year updated");
        }

        return updatedCourse || updatedInstructor || updatedName || updatedDayTime  || updatedRoom || updatedCapacity || updatedSemester || updatedYear;

    }

    public boolean assignInstructor(int sectionId, int instructorId){
        return sectionDAO.updateInstructorId(sectionId, instructorId);
    }

    public boolean toggleMaintenance(){
        String state = AccessControl.isMaintenanceOn() ? "OFF": "ON"; // final state after toggling
        System.out.println("Maintenance Mode Toggled: " + state);
        return MaintenanceManager.toggleMaintenanceMode();
    }

    public List<User> getAllUsers(){
        return authStore.getAllUsers();
    }

    public boolean resetLoginAttempts(String username){
        return authStore.resetLoginAttempts(username);
    }

    public boolean setRegistrationDeadline(LocalDateTime deadline){
        String deadlineString = deadline.toString();
        return settingDAO.updateSetting("registration_deadline", deadlineString);
    }

    public boolean setDropDeadline(LocalDateTime deadline){
        String deadlineString = deadline.toString();
        return settingDAO.updateSetting("drop_deadline", deadlineString);
    }
}
