package edu.univ.erp.domain;

public class Student {
    // fetch from db;
    private int user_id; // auto-increment in auth_db.users_auth
    private int student_id; // auto-increment

    // set by constructor during sign up
    private String username;
    private int roll_no;
    private String program;
    private int current_year;

    Student(String username, int roll_no, String program, int current_year){
        this.username = username;
        this.roll_no = roll_no;
        this.program = program;
        this.current_year = current_year;
    }

    // getters
    public int getUserId(){
        return this.user_id;
    }

    public int getStudentId(){
        return this.student_id;
    }

    public int getRollNo(){
        return this.roll_no;
    }

    public int getCurrentYear(){
        return this.current_year;
    }

    public String getUsername(){
        return this.username;
    }

    public String getProgram(){
        return this.program;
    }

    // string Representation

    @Override
    public String toString(){
        return String.format("Student{UserId: %d, StudentId: %d, RollNo: %d, CurrentYear: %d, Username: %s, Program: %s}\n", this.getUserId(), this.getStudentId(), this.getRollNo(), this.getCurrentYear(), this.getUsername(), this.getProgram());
    }


}
