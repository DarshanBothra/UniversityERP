package edu.univ.erp.service;

import edu.univ.erp.data.*;
import edu.univ.erp.domain.*;
import edu.univ.erp.util.ExportUtility;
import edu.univ.erp.util.ValidationUtility;

import java.util.*;


public class InstructorService {

    private final InstructorDAO instructorDAO;
    private final SectionDAO sectionDAO;
    private final EnrollmentDAO enrollmentDAO;
    private final GradeDAO gradeDAO;
    private final SettingDAO settingDAO;

    public InstructorService(){
        this.instructorDAO = new InstructorDAO();
        this.sectionDAO = new SectionDAO();
        this.enrollmentDAO = new EnrollmentDAO();
        this.gradeDAO = new GradeDAO();
        this.settingDAO = new SettingDAO();
    }

    public List<SectionDetail> getMySections(int instructorId){
        return sectionDAO.getSectionsByInstructorId(instructorId)
                .stream()
                .map(s -> sectionDAO.getAllSectionsWithDetails().stream()
                        .filter(d -> d.getSectionId() == s.getSectionId())
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    public List<Enrollment> getEnrollmentsForMySections(int instructorId){
        List<Enrollment> retList = new ArrayList<Enrollment>();
        List<Section> sections = sectionDAO.getSectionsByInstructorId(instructorId);
        for (Section s: sections){
            retList.addAll(enrollmentDAO.getAllEnrollmentsForSection(s.getSectionId()));
        }
        return retList;
    }

    public String enterScore(int instructorId, int enrollmentId, Component component, double score){
        if (ValidationUtility.isMaintenanceActiveForRole(Role.INSTRUCTOR)){
            return "Grade entry is disabled during maintenance.";
        }
        Enrollment enrollment = enrollmentDAO.getEnrollmentById(enrollmentId);

        if (enrollment==null){
            return "Invalid Enrollment ID.";
        }

        // validate instructor
        Section section = sectionDAO.getSectionById(enrollment.getSectionId());
        if (section == null || section.getInstructorId() != instructorId){
            return "Not your section.";
        }

        // validate score
        if (!ValidationUtility.isValidScore(score)){
            return "Invalid score.";
        }

        double finalGrade = Grade.computeFinalGrade(score, component);
        Grade g = new Grade(enrollmentId, component, score, finalGrade);

        int gradeId = gradeDAO.insertGrade(g);
        if (gradeId >= 0){
            return "Score entered successfully.";
        }
        return "Failed to enter score.";
    }

    public Map<String, Double> getGradeStatistics(int sectionId){
        Section s = sectionDAO.getSectionById(sectionId);
        int totalEnrolled = sectionDAO.getEnrollmentCount(sectionId);
        List<Grade> sectionGrades = gradeDAO.getGradesForSection(sectionId);
        if (sectionGrades.isEmpty()){
            return Map.of("average", 0.0, "median", 0.0, "mode", 0.0);
        }

        List<Double> grades = new ArrayList<Double>();
        for (Grade g: sectionGrades){
            grades.add(g.getFinalGrade());
        }

        // compute statistics
        double average = grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        grades.sort(Double::compareTo);
        double median;
        int size = grades.size();
        if (size%2 == 0){
            median = (grades.get(size/2) + grades.get(size/2-1))/2.0;
        }
        else{
            median = grades.get(size/2);
        }

        Map <Double, Integer> freqMap = new HashMap<>();
        for (double g: grades) freqMap.put(g, freqMap.getOrDefault(g, 0) + 1);
        double mode = grades.getFirst();
        int maxFreq = 0;
        for(Map.Entry<Double, Integer> e: freqMap.entrySet()){
            if (e.getValue() > maxFreq){
                maxFreq = e.getValue();
                mode = e.getKey();
            }
        }

        return Map.of("average", average, "median", median, "mode", mode);
    }

    public boolean exportGrades(int instructorId, String filePath){
        return ExportUtility.exportInstructorTranscriptCSV(instructorId, filePath);
    }

    public Instructor getProfile(int instructorId){
        return instructorDAO.getInstructorById(instructorId);
    }
}
