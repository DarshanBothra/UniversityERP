package edu.univ.erp.domain;

public class Instructor {
    // fetch from db;
    private int userId; // auto-increment in auth_db.users_auth
    private int instructorId; // auto-increment
    private String username;
    private String name;
    private String department;

    public Instructor(int userId, String name, String username, String department){
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.department = department;
    }

    // setters
    public void setInstructorId(int instructorId){
        this.instructorId = instructorId;
    }

    // getters
    public int getUserId(){
        return this.userId;
    }

    public int getInstructorId(){
        return this.instructorId;
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
