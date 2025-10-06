package edu.univ.erp.domain;

public class Instructor {
    // fetch from db;
    private int user_id; // auto-increment in auth_db.users_auth
    private int instructor_id; // auto-increment
    private String username;
    private String name;
    private String department;

    Instructor(int user_id, String name, String username, String department){
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.department = department;
    }

    // setters
    public void setInstructorId(int instructor_id){
        this.instructor_id = instructor_id;
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

    public String getName(){
        return this.name;
    }

    // string Representation

    @Override
    public String toString(){
        return String.format("Instructor {UserId: %d InstructorId: %d, Name: %s, Username: %s, Department: %s}\n", this.getUserId(), this.getInstructorId(), this.getName(), this.getUsername(), this.getDepartment());
    }


}
