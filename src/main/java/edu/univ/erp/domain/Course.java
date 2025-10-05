package edu.univ.erp.domain;

public class Course {
    // fetch from db
    private int course_id; // auto-increment in erp_db.courses;

    // set by constructor during course creation
    private String code;
    private String title;
    private int credits;

    Course(String code, String title, int credits){
        this.code = code;
        this.title = title;
        this.credits = credits;
    }

    // getters
    public int getCourseId(){
        return this.course_id;
    }

    public String getCode(){
        return this.code;
    }

    public String getTitle(){
        return this.title;
    }

    public int getCredits(){
        return this.credits;
    }

    // String Representation;
    @Override
    public String toString(){
        return String.format("Course {Code: %s, Title: %s, Credits: %d", this.getCode(), this.getTitle(), this.getCredits());
    }
}
