
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

/**
 * This class is used to connect and disconnect from an SQLite database
 * @author NMCG
 * @since 09/04/2025
 * @see DBCommand
 */
public class DBConnect {
    
    /**
     * This method is used to connect to an SQLite database file
     * @param dbFilePath Path to the SQLite database file
     * @return Connection object if successful, null otherwise
     */
    public static Connection connect(String dbFilePath) {
        Connection conn = null;
        try {
            // Check if the database file exists
            File dbFile = new File(dbFilePath);
            if (!dbFile.exists()) {
                System.err.println("Database file does not exist: " + dbFilePath);
                System.err.println("Working directory: " + System.getProperty("user.dir"));
                return null;
            }
            
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Create the connection to the database
            String url = "jdbc:sqlite:" + dbFilePath;
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite database established.");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLite connection error: " + e.getMessage());
        }
        return conn;
    }
    
    /**
     * This method is used to disconnect from the database
     * @param conn Connection object to close
     * @return boolean true if disconnection was successful, false otherwise
     */
    public static boolean disconnect(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection to SQLite database closed.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error closing SQLite connection: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Tests if the SQLite JDBC driver is available
     * @return boolean true if driver is available, false otherwise
     */
    public static boolean isSQLiteDriverAvailable() {
        try {
            Class.forName("org.sqlite.JDBC");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Helper method to get absolute path to database file
     * @param relativePath Relative path to database file
     * @return String absolute path to the database file
     */
    public static String getAbsolutePath(String relativePath) {
        return System.getProperty("user.dir") + File.separator + relativePath;
    }
}
