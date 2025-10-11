package edu.univ.erp.data;

import edu.univ.erp.domain.Status;
import edu.univ.erp.domain.Enrollment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    public Enrollment mapResultToEnrollment(ResultSet rs) throws SQLException{
        Enrollment e = new Enrollment(rs.getInt("student_id"), rs.getInt("section_id"), Status.fromString(rs.getString("status")));
        e.setEnrollmentId(rs.getInt("enrollment_id"));

        return e;
    }

    public int insertEnrollment(Enrollment e){
        String sql = "INSERT INTO enrollments (student_id, section_id, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, e.getStudentId());
            stmt.setInt(2, e.getSectionId());
            stmt.setString(3, e.getStatus().getDbValue());
            int affected = stmt.executeUpdate();
            if (affected > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                int enrollmentId = rs.getInt(1);
                e.setEnrollmentId(enrollmentId);
                return enrollmentId;
            }
        } catch (SQLException exc){
            System.err.println("Error inserting enrollment: " + exc.getMessage());
        }
        return -1;
    }

    public Enrollment getEnrollmentById(int enrollmentId){
        String sql = "SELECT * FROM enrollments WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, enrollmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultToEnrollment(rs);
            }
        } catch (SQLException e){
            System.err.println("Error fetching enrollment: " + e.getMessage());
        }
        return null;
    }

    public List<Enrollment> getAllEnrollments(){
        String sql = "SELECT * FROM enrollments";
        List<Enrollment> retList = new ArrayList<Enrollment>();
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                retList.add(mapResultToEnrollment(rs));
            }
        } catch (SQLException e){
            System.err.println("Error fetching enrollments: " + e.getMessage());
        }
        return retList;
    }

    public boolean deleteEnrollment(int enrollmentId){
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, enrollmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error deleting enrollment: " + e.getMessage());
        }
        return false;
    }

    public boolean updateStatus(int enrollmentId, Status newStatus){
        String sql = "UPDATE enrollments SET status = ? WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newStatus.getDbValue());
            stmt.setInt(2, enrollmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating enrollment status: " + e.getMessage());
        }
        return false;
    }
}
