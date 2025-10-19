package edu.univ.erp.auth.session;

import edu.univ.erp.domain.Role;
import java.time.LocalDateTime;

public class SessionUser{
    private final int userId;
    private final String username;
    private final Role role;
    private final LocalDateTime loginTime;

    public SessionUser(int userId, String username, Role role, LocalDateTime loginTime){
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.loginTime = loginTime;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getUsername(){
        return this.username;
    }

    public Role getRole(){
        return this.role;
    }

    public LocalDateTime getLoginTime(){
        return this.loginTime;
    }

    @Override
    public String toString(){
        return String.format("SessionUser(UserId: %d, Username: %s, Role: %s, LoginTime: %s)", this.getUserId(), this.getUsername(), this.getRole().getDbValue(), this.getLoginTime().toString());
    }


}