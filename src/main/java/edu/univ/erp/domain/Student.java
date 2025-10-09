package edu.univ.erp.domain;

public class Student {
    // fetch from db;

    private int userId; // auto-increment in auth_db.users_auth
    private int studentId; // auto-increment
    private int rollNo;
    private int currentYear;
    private String username;
    private Program program;

    // set by setter
    private String name;

    public Student(int userId, String name, String username, int rollNo, Program program, int currentYear){
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.rollNo = rollNo;
        this.program = program;
        this.currentYear = currentYear;
    }

    // setters

    public void setStudentId(int studentId){
        this.studentId = studentId;
    }

    // getters

    public int getUserId(){
        return this.userId;
    }

    public int getStudentId(){
        return this.studentId;
    }

    public int getRollNo(){
        return this.rollNo;
    }

    public int getCurrentYear(){
        return this.currentYear;
    }

    public String getUsername(){
        return this.username;
    }

    public Program getProgram(){
        return this.program;
    }

    public String getName(){
        return this.name;
    }

    // string Representation

    @Override
    public String toString(){
        return String.format("Student {UserId: %d, StudentId: %d, Name: %s, RollNo: %d, CurrentYear: %d, Username: %s, Program: %s}\n", this.getUserId(), this.getStudentId(), this.getName(), this.getRollNo(), this.getCurrentYear(), this.getUsername(), this.getProgram().getDbValue());
    }


}
