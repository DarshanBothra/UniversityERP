package edu.univ.erp.auth.session;

import edu.univ.erp.domain.Role;

public class SessionManager {

    private static SessionUser activeSession = null;
    private SessionManager(){};

    public static void createSession(SessionUser user){
        if (user == null){
            throw new IllegalArgumentException("SessionUser cannot be null.");
        }
        activeSession = user;
        System.out.printf("Session created for user: %s (id: %d, role: %s).\n", activeSession.getUsername(), activeSession.getUserId(), activeSession.getRole().getDbValue());
    }

    public static SessionUser getActiveSession(){
        return activeSession;
    }

    public static void clearSession(){
        if (activeSession != null){
            System.out.printf("Session cleared for user: %s (id: %d, role: %s).\n", activeSession.getUsername(), activeSession.getUserId(), activeSession.getRole().getDbValue());
        }
        activeSession = null;
    }

    public static boolean isLoggedIn(){
        return activeSession != null;
    }

    public static Role getActiveUserRole(){
        if (activeSession != null){
            return activeSession.getRole();
        }
        return null;
    }
}
