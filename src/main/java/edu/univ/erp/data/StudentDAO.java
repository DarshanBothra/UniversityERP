package edu.univ.erp.data;

import edu.univ.erp.domain.Program;
import edu.univ.erp.domain.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Student entity.
 * Handles all CRUD (Create, Read, Update, Delete) operations (Create, Read, Update, Delete) for the students table in erp_db.
 */

public class StudentDAO {

    public Student mapResultToStudentSet(ResultSet rs) throws SQLException {
        Student s = new Student(rs.getInt("user_id"), rs.getString("name"), rs.getString("username"), rs.getInt("roll_no"), Program.fromString(rs.getString("program")), rs.getInt("current_year"));

        s.setStudentId(rs.getInt("student_id"));

        return s;
    }

    public int insertStudent(Student s){
        String sql = "INSERT INTO students (user_id, name, username, roll_no, program, current_year) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, s.getUserId());
            stmt.setString(2, s.getName());
            stmt.setString(3, s.getUsername());
            stmt.setInt(4, s.getRollNo());
            stmt.setString(5, s.getProgram().getDbValue());
            stmt.setInt(6, s.getCurrentYear());

            int affected = stmt.executeUpdate(); // for insert and updates

            if (affected > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()){
                    int generatedId =rs.getInt(2);
                    s.setStudentId(generatedId);
                    return generatedId; // return the student id of student inserted
                }
            }
        } catch (SQLException e){
            System.err.println("Error inserting student: " + e.getMessage());
        }
        return -1; // student was not inserted
    }

    public Student getStudentById(int user_id){
        String sql = "SELECT * FROM students WHERE user_id = ?";
        try(Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return mapResultToStudentSet(rs);
            }

        } catch (SQLException e){
            System.err.println("Error fetching student: " + e.getMessage());
        }

        return null;
    }

    public Student getStudentByRollNo(int rollNo){
        String sql = "SELECT * FROM students WHERE roll_no = ?";
        try(Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, rollNo);
            ResultSet rs = stmt.executeQuery(); // for reads

            if (rs.next()){
                return mapResultToStudentSet(rs);
            }
        } catch (SQLException e){
            System.err.println("Error fetching student: " + e.getMessage());
        }
        return null;
    }

    public List<Student> getAllStudents(){
        List<Student> return_list = new ArrayList<Student>();

        String sql = "SELECT * FROM students";
        try(Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){

            while (rs.next()){
                return_list.add(mapResultToStudentSet(rs));
            }

        } catch(SQLException e){
            System.err.println("Error fetching all students: " + e.getMessage());
        }
        return return_list;
    }

    public boolean deleteStudent(int rollNo){
        String sql = "DELETE FROM students WHERE roll_no = ?";
        try(Connection conn = DBConnection.getERPConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, rollNo);
            return stmt.executeUpdate() > 0;

        } catch(SQLException e){
            System.err.println("Error deleting student: " + e.getMessage());
        }

        return false;
    }

    public boolean updateName(int rollNo, String newName){
        String sql = "UPDATE students SET name = ? WHERE roll_no = ?";
        try(Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newName);
            stmt.setInt(2, rollNo);

            return stmt.executeUpdate() > 0;
        } catch(SQLException e){
            System.err.println("Error updating student name: " + e.getMessage());
        }
        return false;
    }

    public boolean updateProgram(int rollNo, Program newProgram){
        String sql = "UPDATE students SET program = ? WHERE roll_no = ?";
        try(Connection conn = DBConnection.getERPConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newProgram.getDbValue());
            stmt.setInt(2, rollNo);

            return stmt.executeUpdate() > 0;
        } catch(SQLException e){
            System.err.println("Error updating student program: " + e.getMessage());
        }
        return false;
    }

    public boolean updateYear(int rollNo, int newCurrentYear){
        String sql = "UPDATE students SET current_year = ? WHERE roll_no = ?";
        try(Connection conn = DBConnection.getERPConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, newCurrentYear);
            stmt.setInt(2, rollNo);

            return stmt.executeUpdate() > 0;
        } catch(SQLException e){
            System.err.println("Error updating student year: " + e.getMessage());
        }
        return false;
    }
}
