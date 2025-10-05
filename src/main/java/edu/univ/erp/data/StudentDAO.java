package edu.univ.erp.data;

import edu.univ.erp.domain.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Student entity.
 * Handles all CRUD (Create, Read, Update, Delete) operations (Create, Read, Update, Delete) for the students table in erp_db.
 */

public class StudentDAO {

    public boolean insertStudent(Student student){
        String query = "INSERT INTO students (user_id, name, username, roll_no, program, current_year) values (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, student.getUserId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getUsername());
            stmt.setInt(4, student.getRollNo());
            stmt.setString(5, student.getProgram());
            stmt.setInt(6, student.getCurrentYear());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error inserting student: " + e.getMessage());
            return false;
        }
    }
}
