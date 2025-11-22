package edu.univ.erp.data;

import edu.univ.erp.domain.Instructor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorDAO {

    public Instructor mapResultToInstructorSet(ResultSet rs) throws SQLException{
        Instructor i = new Instructor(rs.getInt("user_id"), rs.getString("name"), rs.getString("username"), rs.getString("department"));
        i.setInstructorId(rs.getInt("instructor_id"));

        return i;
    }

    public int insertInstructor(Instructor i){
        String sql = "INSERT INTO instructors (user_id, name, username, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, i.getUserId());
            stmt.setString(2, i.getName());
            stmt.setString(3, i.getUsername());
            stmt.setString(4, i.getDepartment());

            int affected = stmt.executeUpdate();
            if (affected > 0){ // instructor was inserted successfully
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()){
                    int instructorId = rs.getInt(1);
                    i.setInstructorId(instructorId);
                    return instructorId;
                }

            }

        } catch(SQLException e){
            System.err.println("Error inserting instructor: " + e.getMessage());
        }

        return -1; // instructor was not inserted
    }

    public Instructor getInstructorById(int userId){
        String sql = "SELECT * FROM instructors WHERE user_id = ?";
        try(Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultToInstructorSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching instructor: " + e.getMessage());
        }
        return null;
    }

    public List<Instructor> getAllInstructors(){
        String sql = "SELECT * FROM instructors";
        List<Instructor> returnList = new ArrayList<Instructor>();

        try(Connection conn = DBConnection.getERPConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while (rs.next()){
                returnList.add(mapResultToInstructorSet(rs));
            }

        } catch (SQLException e){
            System.err.println("Error fetching instructors: " + e.getMessage());
        }
        return returnList;
    }

    public List<Instructor> getInstructorsByDepartment(String department){
        String sql = "SELECT * FROM instructors WHERE department = ?";
        List<Instructor> returnList = new ArrayList<Instructor>();

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                returnList.add(mapResultToInstructorSet(rs));
            }
        } catch (SQLException e){
            System.err.println("Error fetching instructors: " + e.getMessage());
        }
        return returnList;
    }

    public List<Instructor> getInstructorsByName(String name){
        String sql = "SELECT * FROM instructors WHERE name = ?";
        List<Instructor> returnList = new ArrayList<Instructor>();

        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                returnList.add(mapResultToInstructorSet(rs));
            }
        } catch (SQLException e){
            System.err.println("Error fetching instructors: " + e.getMessage());
        }
        return returnList;
    }

    public boolean deleteInstructor(int instructorId){
        String sql = "DELETE FROM instructors WHERE instructor_id = ?";

        try (Connection conn = DBConnection.getERPConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, instructorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error deleting instructor: " + e.getMessage());
        }
        return false;
    }

    public boolean updateName(int instructorId, String newName){
        String sql = "UPDATE instructors SET name = ? WHERE instructor_id = ?";

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newName);
            stmt.setInt(2, instructorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.out.println("Error updating instructor name: " + e.getMessage());
        }
        return false;
    }

    public boolean updateDepartment(int instructorId, String newDepartment){
        String sql = "UPDATE instructors SET department = ? WHERE instructor_id = ?";

        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newDepartment);
            stmt.setInt(2, instructorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating instructor department: " + e.getMessage());
        }
        return false;
    }

}
