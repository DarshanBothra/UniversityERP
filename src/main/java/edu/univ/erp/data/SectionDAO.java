package edu.univ.erp.data;

import edu.univ.erp.domain.Section;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    public Section mapResultToSection(ResultSet rs) throws SQLException {
        Section s = new Section(rs.getInt("course_id"), rs.getInt("instructor_id"), rs.getString("name"), rs.getInt("capacity"), rs.getInt("current_year"), rs.getString("day_time"), rs.getString("room"), rs.getString("semester"));

        s.setSectionId(rs.getInt("section_id"));

        return s;
    }

    public int insertSection(Section s){
        String sql = "INSERT INTO sections (course_id, instructor_id, name, day_time, room, capacity, semester, current_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setInt(1, s.getCourseId());
            stmt.setInt(2, s.getInstructorId());
            stmt.setString(3, s.getName());
            stmt.setString(4, s.getDayTime());
            stmt.setString(5, s.getRoom());
            stmt.setInt(6, s.getCapacity());
            stmt.setString(7, s.getSemester());
            stmt.setInt(8, s.getYear());

            int affected = stmt.executeUpdate();
            if (affected > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                int sectionId = rs.getInt(1);
                s.setSectionId(sectionId);
                return sectionId;
            }
        } catch (SQLException e){
            System.err.println("Error inserting Section: " + e.getMessage());
        }
        return -1;
    }

    public Section getSectionById(int sectionId){
        String sql = "SELECT * FROM sections WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultToSection(rs);
            }
        } catch (SQLException e){
            System.err.println("Error fetching section: " + e.getMessage());
        }
        return null;
    }

    public List<Section> getAllSections(){
        String sql = "SELECT * FROM sections";
        List<Section> retList = new ArrayList<Section>();
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                retList.add(mapResultToSection(rs));
            }
        } catch (SQLException e){
            System.err.println("Error fetching sections: " + e.getMessage());
        }
        return retList;
    }

    public boolean deleteSection(int sectionId){
        String sql = "DELETE FROM sections WHERE section_id = ?";

        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error deleting section: " + e.getMessage());
        }
        return false;
    }

    public boolean updateCourseId(int sectionId, int newCourseId){
        String sql = "UPDATE sections SET course_id = ? WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, newCourseId);
            stmt.setInt(2, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating section course: " + e.getMessage());
        }
        return false;
    }

    public boolean updateInstructorId(int sectionId, int newInstructorId){
        String sql = "UPDATE sections SET instructor_id = ? WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, newInstructorId);
            stmt.setInt(2, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating section instructor: " + e.getMessage());
        }
        return false;
    }

    public boolean updateName(int sectionId, String newName){
        String sql = "UPDATE sections SET name = ? WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newName);
            stmt.setInt(2, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating section name: " + e.getMessage());
        }
        return false;
    }

    public boolean updateDayTime(int sectionId, String newDayTime){
        String sql = "UPDATE sections SET day_time = ? WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newDayTime);
            stmt.setInt(2, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating section day time: " + e.getMessage());
        }
        return false;
    }

    public boolean updateRoom(int sectionId, String newRoom){
        String sql = "UPDATE sections SET room = ? WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newRoom);
            stmt.setInt(2, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating section room: " + e.getMessage());
        }
        return false;
    }

    public boolean updateCapacity(int sectionId, int newCapacity){
        String sql = "UPDATE sections SET capacity = ? WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, newCapacity);
            stmt.setInt(2, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating section capacity: " + e.getMessage());
        }
        return false;
    }

    public boolean updateYear(int sectionId, int newYear){
        String sql = "UPDATE sections SET current_year = ? WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, newYear);
            stmt.setInt(2, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating section year: " + e.getMessage());
        }
        return false;
    }

    public boolean updateSemester(int sectionId, String newSemester){
        String sql = "UPDATE sections SET semester = ? WHERE section_id = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newSemester);
            stmt.setInt(2, sectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating section semester: " + e.getMessage());
        }
        return false;
    }

}
