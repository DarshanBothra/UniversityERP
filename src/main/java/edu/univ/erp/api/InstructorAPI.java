package edu.univ.erp.api;

import edu.univ.erp.domain.*;
import edu.univ.erp.service.InstructorService;
import java.util.List;
import java.util.Map;

public class InstructorAPI {

    private final InstructorService instructorService;

    public InstructorAPI() {
        this.instructorService = new InstructorService();
    }


    public Instructor getProfile(int instructorId) {
        try {
            return instructorService.getProfile(instructorId);
        } catch (Exception e) {
            System.err.println("Error fetching instructor profile: " + e.getMessage());
            return null;
        }
    }

    public List<SectionDetail> getInstructorSections(int instructorId) {
        try {
            return instructorService.getMySections(instructorId);
        } catch (Exception e) {
            System.err.println("Error fetching instructor sections: " + e.getMessage());
            return List.of();
        }
    }


    public List<Enrollment> getEnrollmentsForMySections(int instructorId) {
        try {
            return instructorService.getEnrollmentsForMySections(instructorId);
        } catch (Exception e) {
            System.err.println("Error fetching students: " + e.getMessage());
            return List.of();
        }
    }

    public String enterScore(int instructorId, int enrollmentId, Component component, double score) {
        try {
            return instructorService.enterScore(instructorId, enrollmentId, component, score);
        } catch (Exception e) {
            return "Error assigning grade: " + e.getMessage();
        }
    }


    public boolean exportGrades(int instructorId, String filePath) {
        try {
            return instructorService.exportGrades(instructorId, filePath);
        } catch (Exception e) {
            System.err.println("Error exporting transcript: " + e.getMessage());
            return false;
        }
    }

    public Map<String, Double> getGradeStatistics(int sectionId) {
        try {
            return instructorService.getGradeStatistics(sectionId);
        } catch (Exception e) {
            return Map.of("None", 0.0);
        }
    }
}
