package edu.univ.erp.data;

import edu.univ.erp.domain.Setting;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SettingDAO {

    public Setting mapResultToSetting(ResultSet rs) throws SQLException {
        return new Setting(rs.getString("key"), rs.getString("value"));
    }

    public boolean insertSetting(Setting s){
        String sql = "INSERT INTO settings (key, value) VALUES (?, ?)";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, s.getKey());
            stmt.setString(2, s.getValue());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error inserting setting: " + e.getMessage());
        }
        return false;
    }

    public Setting getSettingByKey(String key){
        String sql = "SELECT * FROM settings WHERE key = ?";
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultToSetting(rs);
            }
        } catch (SQLException e){
            System.err.println("Error fetching setting: " + e.getMessage());
        }
        return null;
    }

    public List<Setting> getAllSettings(){
        String sql = "SELECT * FROM settings";
        List<Setting> retList = new ArrayList<Setting>();
        try (Connection conn = DBConnection.getERPConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                retList.add(mapResultToSetting(rs));
            }
        } catch(SQLException e){
            System.err.println("Error fetching settings: " + e.getMessage());
        }
        return retList;
    }

    public boolean deleteSetting(String key){
        String sql = "DELETE FROM settings WHERE key = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, key);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error deleting setting: " + e.getMessage());
        }
        return false;
    }

    public boolean updateSetting(String key, String value){
        String sql = "UPDATE setting SET value = ? WHERE key = ?";
        try (Connection conn = DBConnection.getERPConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, value);
            stmt.setString(2, key);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating setting: " + e.getMessage());
        }
        return false;
    }
}
