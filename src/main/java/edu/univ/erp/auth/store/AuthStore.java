package edu.univ.erp.auth.store;

import edu.univ.erp.auth.AuthService;
import edu.univ.erp.data.InstructorDAO;
import edu.univ.erp.data.StudentDAO;
import edu.univ.erp.domain.LoginStatus;
import edu.univ.erp.domain.Role;
import edu.univ.erp.domain.User;
import edu.univ.erp.data.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthStore {

    public AuthStore(){};

    public User mapResultToUser(ResultSet rs) throws SQLException{
        User u = new User(rs.getString("username"), Role.fromString(rs.getString("role")));
        return u;
    }

    public int insertUser(String username, Role role, String passwordHash){
        String sql = "INSERT INTO users_auth (username, role, password_hash) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, username);
            stmt.setString(2, role.getDbValue());
            stmt.setString(3, passwordHash);

            int affected = stmt.executeUpdate();
            if (affected > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()){
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e){
            System.err.println("Error inserting user: " + e.getMessage());
        }
        return -1;
    }

    public Role getRole(String username){
        String sql = "SELECT role FROM users_auth WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return Role.fromString(rs.getString("role"));
            }
        } catch (SQLException e){
            System.err.println("Error fetching role: " + e.getMessage());
        }
        return Role.fromString("Unknown"); // if result set is empty (code will never reach here)
    }

    public String getPasswordHash(String username){
        String sql = "SELECT password_hash FROM users_auth WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getString("password_hash");
            }
        } catch (SQLException e){
            System.err.println("Error fetching password hash: " + e.getMessage());
        }
        return null;
    }

    public int getUserId(String username){
        String sql = "SELECT user_id FROM users_auth where username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("user_id");
            }
        } catch (SQLException e){
            System.err.println("Error fetching user id: " + e.getMessage());
        }
        return -1; // empty result set
    }

    public LoginStatus getCurrentStatus(String username){
        String sql = "SELECT status FROM users_auth where username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return LoginStatus.fromString(rs.getString("status"));
            }
        } catch (SQLException e){
            System.err.println("Error fetching user status: " + e.getMessage());
        }
        return LoginStatus.INACTIVE; // empty result set
    }

    public Timestamp getLastLogin(String username){
        String sql = "SELECT last_login FROM users_auth WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getTimestamp(1);
            }
        } catch (SQLException e){
            System.err.println("Error fetching last login timestamp: " + e.getMessage());
        }
        return null; // empty result set returned
    }

    public boolean updatePasswordHash(String username, String newPasswordHash){
        String sql = "UPDATE users_auth SET password_hash = ? WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newPasswordHash);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating password hash: " + e.getMessage());
        }
        return false;
    }

    public boolean updateRole(String username, Role newRole){
        String sql = "UPDATE users_auth SET role = ? WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newRole.getDbValue());
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating user role: " + e.getMessage());
        }
        return false;
    }

    public boolean updateStatus(String username, LoginStatus newStatus){
        String sql = "UPDATE users_auth SET status = ? WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newStatus.getDbValue());
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating user status: " + e.getMessage());
        }
        return false;
    }

    public boolean updateLastLogin(String username, Timestamp newLastLogin){
        String sql = "UPDATE users_auth SET last_login = ? WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setTimestamp(1, newLastLogin);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error updating user status: " + e.getMessage());
        }
        return false;
    }

    public boolean incrementInvalidLogin(String username){
        String sql = "UPDATE users_auth SET failed_attempts = failed_attempts + 1 where username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error incrementing invalid login attempts: " + e.getMessage());
        }
        return false;
    }

    public boolean resetLoginAttempts(String username){
        String sql = "UPDATE users_auth SET failed_attempts = 0 where username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e){
            System.err.println("Error resting login attempts: " + e.getMessage());
        }
        return false;
    }

    public int getFailedAttempts(String username){
        String sql = "SELECT * FROM users_auth WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("failed_attempts");
            }
        } catch (SQLException e){
            System.err.println("Error fetching failed attempts: " + e.getMessage());
        }
        return -1;
    }
    public boolean userExists(String username){
        String sql = "SELECT * FROM users_auth WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e){
            System.err.println("Error checking for user: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String username){
        AuthService authService = new AuthService();
        int id = authService.getSessionUser().getUserId();
        Role role = authService.getSessionUser().getRole();
        try{
            if (role == Role.STUDENT){
                StudentDAO studentDAO = new StudentDAO();
                studentDAO.deleteStudent(studentDAO.getStudentById(id).getRollNo());
            }else if (role == Role.INSTRUCTOR){
                InstructorDAO instructorDAO = new InstructorDAO();
                instructorDAO.deleteInstructor(instructorDAO.getInstructorById(id).getInstructorId());
            }
        } catch (Exception e){
            System.err.println("Error deleting user: " + e.getMessage());
        }
        String sql = "DELETE FROM users_auth WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.err.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    public User getUserByUsername(String username){
        String sql = "SELECT * FROM users_auth WHERE username = ?";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultToUser(rs);
            }
        } catch (SQLException e){
            System.err.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers(){
        List<User> retList = new ArrayList<User>();
        String sql = "SELECT * FROM users_auth";
        try (Connection conn = DBConnection.getAuthConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                retList.add(mapResultToUser(rs));
            }
        } catch (SQLException e){
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return retList;
    }

}
