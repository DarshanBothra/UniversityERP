package edu.univ.erp.data;

import edu.univ.erp.domain.Grade;
import edu.univ.erp.domain.Component;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    public Grade mapResultToGrade(ResultSet rs) throws SQLException{
        Grade g = new Grade(rs.getInt("enrollment_id"), Component.fromString(rs.getString("component")), rs.getDouble("score"), rs.getDouble("final_grade"));

        g.setGradeId(rs.getInt("grade_id"));

        return g;
    }

    public int insertGrade(Grade g){
        String sql = "INSERT INTO grades (enrollment_id, component, score, final_grade) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, g.getEnrollmentId());
            stmt.setString(2, g.getComponent().getDbValue());
            stmt.setDouble(3, g.getScore());
            stmt.setDouble(4, g.getFinalGrade());

            int affected = stmt.executeUpdate();
            if (affected > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                int gradeId = rs.getInt(1);
                g.setGradeId(gradeId);
                return gradeId;
            }
        } catch (SQLException e){
            System.err.println("Error inserting grade: " + e.getMessage());
        }
        return -1;
    }

    public Grade getGradeById(int gradeId){
        String sql = "SELECT * FROM grades WHERE grade_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, gradeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultToGrade(rs);
            }
        } catch (SQLException e){
            System.err.println("Error fetching grade: " + e.getMessage());
        }
        return null;
    }

    public List<Grade> getAllGrade(){
        String sql = "SELECT * FROM grades";
        List<Grade> retList = new ArrayList<Grade>();

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                retList.add(mapResultToGrade(rs));
            }
        } catch (SQLException e){
            System.err.println("Error fetching grades: " + e.getMessage());
        }
        return retList;
    }

    public boolean deleteGrade(int gradeId){
        String sql = "DELETE FROM grades WHERE grade_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, gradeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error deleting grade: " + e.getMessage());
        }
        return false;
    }

    public boolean updateComponent(int gradeId, Component newComponent){
        String sql = "UPDATE grades SET component = ? WHERE grade_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newComponent.getDbValue());
            stmt.setInt(2, gradeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating grade component: " + e.getMessage());
        }
        return false;
    }

    public boolean updateScore(int gradeId, double newScore){
        String sql = "UPDATE grades SET score = ? WHERE grade_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setDouble(1, newScore);
            stmt.setInt(2, gradeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating score: " + e.getMessage());
        }
        return false;
    }

    public boolean updateFinalGrade(int gradeId, double final_grade){
        String sql = "UPDATE grades SET final_grade = ? WHERE grade_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setDouble(1, final_grade);
            stmt.setInt(2, gradeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating score: " + e.getMessage());
        }
        return false;
    }
}
