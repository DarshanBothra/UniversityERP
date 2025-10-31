package edu.univ.erp.domain;

public class User {
    private String username;
    private Role role;
    
    public User(String username, Role role){
        setUserId(username);
        setRole(role);
    }
    
    public void setUserId(String username){
        this.username = username;
    }
    public void setRole(Role role){
        this.role = role;
    }
    public String getUserId(){
        return this.username;
    }
    public Role getRole(){
        return this.role;
    }

    @Override
    public String toString(){
        return String.format("User (Username: %s, Role: %s)\n", this.getUserId(), this.getRole());
    }
}
