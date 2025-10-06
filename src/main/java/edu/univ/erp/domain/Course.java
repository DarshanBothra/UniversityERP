package edu.univ.erp.domain;

public class Course {
    // fetch from db

    private int course_id; // auto-increment in erp_db.courses;
    private int credits;
    private String code;
    private String title;


    Course(String code, String title, int credits){
        this.code = code;
        this.title = title;
        this.credits = credits;
    }

    // setters
    public void setCourseId(int course_id){
        this.course_id = course_id;
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
        return String.format("Course {CourseId: %d, Code: %s, Title: %s, Credits: %d}\n", this.getCourseId(), this.getCode(), this.getTitle(), this.getCredits());
    }
}
