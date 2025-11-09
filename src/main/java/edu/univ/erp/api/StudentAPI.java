package edu.univ.erp.api;

import edu.univ.erp.domain.*;
import edu.univ.erp.service.StudentService;

import java.util.List;

public class StudentAPI{
    private final StudentService studentService = new StudentService();

    public List<SectionDetail> getCatalog(){
        return studentService.getCatalog();
    }

    public String registerSection(int studentId, int sectionId){
        try{
            return studentService.registerSection(studentId, sectionId);
        } catch (Exception e){
            return "Error during registrations: " + e.getMessage();
        }
    }

    public String dropSection(int studentId, int sectionId){
        try {
            return studentService.dropSection(studentId, sectionId);
        } catch (Exception e){
            return "Error during drop: " + e.getMessage();
        }
    }

    public List<SectionDetail> getMyRegistrations(int studentId){
        try {
            return studentService.getMyRegistrations(studentId);
        } catch (Exception e) {
            System.err.println("Error fetching registrations: " + e.getMessage());
            return List.of();
        }
    }

    public List<Grade> getMyGrades(int studentId){
        try {
            return studentService.getMyGrades(studentId);
        } catch (Exception e){
            System.err.println("Error fetching grades: " + e.getMessage());
            return List.of();
        }
    }

    public boolean generateTranscript(int studentId, String filePath){
        try {
            return studentService.generateTranscript(studentId, filePath);
        } catch (Exception e){
            System.err.println("Error generating transcript: " + e.getMessage());
            return false;
        }
    }

    public Student getProfile(int studentId){
        try {
            return studentService.getProfile(studentId);
        } catch (Exception e){
            System.err.println("Error fetching profile: " + e.getMessage());
            return null;
        }
    }
}