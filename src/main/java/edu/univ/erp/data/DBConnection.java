package edu.univ.erp.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DBConnection class loads database credentials from db_config.properties
 * and provides methods to connect to Auth DB and ERP DB.
 */
public class DBConnection {

    private static String HOST;
    private static String PORT;
    private static String AUTH_DB;
    private static String ERP_DB;
    private static String USER;
    private static String PASSWORD;

    static {
        loadProperties();
    }

    // Load configuration from db_config.properties
    private static void loadProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config/db_config.properties")) {
            props.load(fis);
            HOST = props.getProperty("db.host", "localhost");
            PORT = props.getProperty("db.port", "3306");
            AUTH_DB = props.getProperty("db.auth_db");
            ERP_DB = props.getProperty("db.erp_db");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error loading db_config.properties: " + e.getMessage());
        }
    }

    private static Connection getConnection(String dbName) throws SQLException {
        String url = String.format("jdbc:mysql://%s:%s/%s", HOST, PORT, dbName);
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    /** Connects to Auth DB (for users_auth table) */
    public static Connection getAuthConnection() throws SQLException {
        return getConnection(AUTH_DB);
    }

    /** Connects to ERP DB (for student/instructor/course data) */
    public static Connection getERPConnection() throws SQLException {
        return getConnection(ERP_DB);
    }

}
