package edu.univ.erp.auth.hash;

import org.mindrot.jbcrypt.BCrypt;

/*
 * Utility class for verifying and hashing passwords
 * Involves a strong password checker
 */

public class PasswordUtility {

    public static final int COST = 12;

    private PasswordUtility(){}

    public static String hashPassword(String plainPassword){

        /*
         * Hashes a plain password string
         *
         * params: plainPassword (String)
         * returns: hashedPassword(String)
         */

        if (plainPassword == null || plainPassword.isEmpty()){ // check for invalid password
            throw new IllegalArgumentException("Password Cannot be empty or null.");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(COST));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword){

        /*
         * Compares password hash and plain password
         * params: plainPassword (String), passwordHash(String)
         * returns: plainPassword = passwordHash ? (boolean)
         */
        if (plainPassword == null || hashedPassword == null){
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e){
            System.err.println("Password Verification failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean isStrongPassword(String plainPassword){
        /*
         * Checks if the entered password is strong or not
         * params: plainPassword (String)
         * returns: response boolean (true if the password is strong, else false)
         */

        return plainPassword.length() >= 8 &&
                plainPassword.matches(".*[A-Z].*") &&
                plainPassword.matches(".*[a-z].*") &&
                plainPassword.matches(".*\\d.*") &&
                plainPassword.matches(".*[@#$%^&+=!].*");
    }
}
