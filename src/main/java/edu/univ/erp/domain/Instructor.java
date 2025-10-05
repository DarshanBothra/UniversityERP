package edu.univ.erp.domain;

public class Instructor {
    // fetch from db;
    private int user_id; // auto-increment in auth_db.users_auth
    private int instructor_id; // auto-increment

    // set by constructor during sign up
    private String username;
    private String department;

    Instructor(String username, String department){
        this.username = username;
        this.department = department;
    }

    // getters
    public int getUserId(){
        return this.user_id;
    }

    public int getInstructorId(){
        return this.instructor_id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getDepartment(){
        return this.department;
    }

    // string Representation

    @Override
    public String toString(){
        return String.format("Student{UserId: % InstructorId: %d, Username: %s, Department: %s}\n", this.getUserId(), this.getInstructorId(), this.getUsername(), this.getDepartment());
    }


}
