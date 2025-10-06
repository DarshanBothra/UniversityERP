package edu.univ.erp.domain;

public class Student {
    // fetch from db;

    private int user_id; // auto-increment in auth_db.users_auth
    private int student_id; // auto-increment
    private int roll_no;
    private int current_year;
    private String username;
    private Program program;

    // set by setter
    private String name;

    public Student(int user_id, String name, String username, int roll_no, Program program, int current_year){
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.roll_no = roll_no;
        this.program = program;
        this.current_year = current_year;
    }

    // setters

    public void setStudentId(int student_id){
        this.student_id = student_id;
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
