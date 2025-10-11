package edu.univ.erp.data;

import com.sun.source.doctree.ReferenceTree;
import edu.univ.erp.domain.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public Course mapResultToCourse(ResultSet rs) throws SQLException {
        Course c = new Course(rs.getString("code"), rs.getString("title"), rs.getInt("credits"));
        c.setCourseId(rs.getInt("course_id"));

        return c;
    }

    public int insertCourse(Course c){
        String sql = "INSERT INTO courses (code, title, credits) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, c.getCode());
            stmt.setString(2, c.getTitle());
            stmt.setInt(3, c.getCredits());

            int affected = stmt.executeUpdate();
            if (affected > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                int courseId = rs.getInt(1);
                c.setCourseId(courseId);
                return courseId;
            }

        } catch (SQLException e){
            System.err.println("Error inserting course: " + e.getMessage());
        }
        return -1; // error inserting course
    }

    public Course getCourseById(int courseId){
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return mapResultToCourse(rs);
            }
        } catch (SQLException e){
            System.err.println("Error fetching course: " + e.getMessage());
        }
        return null;
    }

    public Course getCourseByCode(String code){
        String sql = "SELECT * FROM courses WHERE code = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return mapResultToCourse(rs);
            }
        } catch (SQLException e){
            System.err.println("Error fetching course: " + e.getMessage());
        }
        return null;
    }

    public List<Course> getAllCourses(){
        List<Course> retList = new ArrayList<Course>();
        String sql = "SELECT * FROM courses";

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                retList.add(mapResultToCourse(rs));
            }
        } catch (SQLException e){
            System.err.println("Error fetching courses: " + e.getMessage());
        }
        return retList;

    }

    public boolean deleteCourse(String code){
        String sql = "DELETE FROM courses WHERE code = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error deleting course: " + e.getMessage());
        }
        return false;
    }

    public boolean updateTitle(String code, String newTitle){
        String sql = "UPDATE courses SET title = ? WHERE code = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newTitle);
            stmt.setString(2, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating course title: " + e.getMessage());
        }
        return false;
    }

    public boolean updateCredits(String code, int credits){
        String sql = "UPDATE courses SET credits = ? WHERE code = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, credits);
            stmt.setString(2, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating course title: " + e.getMessage());
        }
        return false;
    }

}
