package edu.univ.erp.domain;

/**
 * DTO Class to combine section, course, and instructor details
 */

public class SectionDetail {

    private int sectionId; // auto-increment in erp_db.sections
    private int courseId; // query courses table with course code to fetch courseId
    private int instructorId; // instructor table with username
    private int capacity; // fetch capacity by counting the number of students enrolled in the section
    private int year;
    private String dayTime;
    private String room;
    private String semester;
    private String name;

    private String courseCode;
    private String courseTitle;
    private int courseCredits;

    private String instructorName;
    private String instructorDepartment;

    public SectionDetail(int sectionId, int courseId, int instructorId, int capacity, int year, String dayTime, String room, String semester, String name, String courseCode, String courseTitle, int courseCredits, String instructorName, String instructorDepartment){
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.instructorId = instructorId;
        this.capacity = capacity;
        this.year = year;
        this.dayTime = dayTime;
        this.instructorName = instructorName;
        this.courseTitle = courseTitle;
        this.courseCredits = courseCredits;
        this.room = room;
        this.semester = semester;
        this.name = name;
        this.instructorDepartment = instructorDepartment;
    }

    public int getSectionId(){
        return this.sectionId;
    }

    public int getCourseId(){return this.courseId;}

    public int getInstructorId(){return this.instructorId;}

    public int getCapacity(){return this.capacity;}

    public int getYear(){return this.year;}

    public int getCourseCredits(){return this.courseCredits;}

    public String getDayTime(){return this.dayTime;}

    public String getRoom(){return this.room;}

    public String getSemester(){return this.semester;}

    public String getName(){return this.name;}

    public String getCourseCode(){return this.courseCode;}

    public String getCourseTitle(){return this.courseTitle;}

    public String getInstructorName(){return this.instructorName;}

    public String getInstructorDepartment(){return this.instructorDepartment;}



}
