package edu.univ.erp.auth;

import edu.univ.erp.auth.hash.PasswordUtility;
import edu.univ.erp.auth.store.AuthStore;
import edu.univ.erp.auth.session.*;
import edu.univ.erp.domain.Role;
import edu.univ.erp.domain.LoginStatus;
import java.time.LocalDateTime;
import java.sql.Timestamp;

public class AuthService {

    private final AuthStore authStore = new AuthStore(); // Data Access Object for Auth DB

    // register new user

    /**
     * Steps to create a new user:
     * 1. take input username, role and plain password
     * 2. check if the username already exists (even if the roles are different, same username is not allowed)
     * 3. Validate password strength (prompt user for weak password (pop up asking to continue or back [use a different password])
     * 4. hash the password
     * 5. insert (username, role, password hash) in table
     */

    public boolean registerUser(String username, String role, String plainPassword){
        if (authStore.userExists(username)){
            System.err.println("Registration Failed: User Already Exists.\n");
            return false;
        }
        if (!PasswordUtility.isStrongPassword(plainPassword)){
            System.err.println("Registration Failed: Weak Password.\n");
            return false;
        }
        try{
            String hashedPassword = PasswordUtility.hashPassword(plainPassword);
            authStore.insertUser(username, Role.fromString(role), hashedPassword);
            return true;
        } catch (Exception e){
            System.err.println("Registration Failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Steps to login :
     * 1. take input username and password (role will be fetched from database)
     * 2. check if username exists and the database
     * 3. check if password hash matches the entered password
     * 4. if match: create a new Session and return, if not match: return false
     */

    public boolean login(String username, String plainPassword){
        try{
            if (!authStore.userExists(username)) {
                System.err.println("Login Failed: User does not exist!");
                return false;
            }
            // user exists
            if (!PasswordUtility.checkPassword(plainPassword, authStore.getPasswordHash(username))) {
                System.err.println("Login Failed: Incorrect Password!");
                return false;
            }
            // password is correct

            // fetch user attributes for session
            int userId = authStore.getUserId(username);
            Role role = authStore.getRole(username);
            LocalDateTime loginTime = LocalDateTime.now();

            // update attributes on auth db table
            authStore.updateStatus(username, LoginStatus.fromString("Active"));
            authStore.updateLastLogin(username, Timestamp.valueOf(loginTime));

            // create and start new session
            SessionUser user = new SessionUser(userId, username, role, loginTime);
            SessionManager.createSession(user);

            System.out.println("Login Successful for user: " + username);

            return true;
        } catch (Exception e){
            System.err.println("Error during login: " + e.getMessage());
            return false;
        }

    }

    /**
     * Logs the currently active user out
     * Steps:
     * 1. Fetch current session user
     * 2. Set user as inactive
     * 3. Clear session
     */

    public void logout(){
        SessionUser user = SessionManager.getActiveSession();
        if (user == null){
            System.err.println("No active session found.");
            return;
        }
        try{
            authStore.updateStatus(user.getUsername(), LoginStatus.INACTIVE);
            SessionManager.clearSession();
            System.out.printf("User logged out: %s (id: %d, role: %s)\n", user.getUsername(), user.getUserId(), user.getRole().getDbValue());
        } catch (Exception e){
            System.err.println("Error logging out: " + e.getMessage());
        }

    }

    public SessionUser getSessionUser(){
        return SessionManager.getActiveSession();
    }

}
