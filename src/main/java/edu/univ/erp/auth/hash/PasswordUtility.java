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

    // test script for password utility
    /*
    public static void main(String[] args){

        // test

        String myPassword = "Admin1234#";
        String hashedPassword = PasswordUtility.hashPassword(myPassword);

        String passwordTest1 = "adminabc123#";
        String passwordTest2 = "Admin1234#";

        boolean testResult1 = PasswordUtility.checkPassword(passwordTest1, hashedPassword);
        boolean testResult2 = PasswordUtility.checkPassword(passwordTest2, hashedPassword);

        boolean strongPassword = PasswordUtility.isStrongPassword(myPassword);

        // Print report
        System.out.printf(
                """
                        ---------- STATS ----------
                        PASSWORD CHOSEN: %s
                        PASSWORD HASH: %s
                        STRONG PASSWORD: %b
                        
                        ---------- PASSWORD TEST 1 ---------
                        INPUT: %s
                        CORRECT: %b
                        
                        ---------- PASSWORD TEST 2 ---------
                        INPUT: %s
                        CORRECT: %b
                        \n""", myPassword, hashedPassword, strongPassword, passwordTest1, testResult1, passwordTest2, testResult2
        );

    }

 */
}
