package connecttojdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;

/**
 * Main class that demonstrates connecting to SQLite and executing queries
 * @author Your Name
 * @since DD/MM/YYYY
 */
public class Main {
    
    // Path to the SQLite database file (relative to project root)
    private String dbFilePath = "db/database.sqlite";
    
    // Store the connection to the DB
    private Connection conn = null;
    
    /**
     * Main method that serves as the entry point for the application
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create an instance of the Main class and run it
        new Main().run();
    }
    
    /**
     * Run method that contains the main application logic
     */
    public void run() { 
        // Check if SQLite driver is available
        if (!DBConnect.isSQLiteDriverAvailable()) {
            System.err.println("SQLite JDBC driver not available. Please add it to your classpath.");
            return;
        }
        
        // Connect to SQLite DB
        conn = DBConnect.connect(dbFilePath);
        if (conn == null) {
            // Try with absolute path if relative path doesn't work
            System.out.println("Trying with absolute path...");
            conn = DBConnect.connect(DBConnect.getAbsolutePath(dbFilePath));
            
            if (conn == null) {
                System.err.println("Failed to connect to the database. Exiting application.");
                return;
            }
        }
        
        try {
            // Display database schema
            DBCommand.displayDatabaseSchema(conn);
            
            // Execute queries
            executeAndDisplayQueries();
        } finally {
            // Disconnect from DB (ensure this happens even if an exception occurs)
            DBConnect.disconnect(conn);
        }
    }
    
    /**
     * Execute all required queries and display their results
     */
    private void executeAndDisplayQueries() {
        // Execute and display first query - Simple SELECT
        executeSimpleSelectQuery();
        
        // Execute and display second query - JOIN query
        executeJoinQuery();
        
        // Add more query methods here for your 10 required queries
        
        // Example of a prepared statement query
        executePreparedStatementQuery();
    }
    
    /**
     * Execute a simple SELECT query and display the results
     */
    private void executeSimpleSelectQuery() {
        String query = "SELECT * FROM games";
        ResultSet resultSet = DBCommand.executeQuery(conn, query);
        DBOutputFormatter.showAllGames("Query 1: Simple SELECT", resultSet);
    }
    
    /**
     * Execute a JOIN query and display the results
     */
    private void executeJoinQuery() {
        String query = "SELECT Players.FirstName, PlayerGames.Score, Games.GameName "
                + "FROM Players "
                + "JOIN PlayerGames ON Players.PlayerID = PlayerGames.PlayerID "
                + "JOIN Games ON PlayerGames.GameID = Games.GameID "
                + "ORDER BY Players.FirstName ASC";
        ResultSet resultSet = DBCommand.executeQuery(conn, query);
        DBOutputFormatter.showAllScores("Query 2: JOIN Query", resultSet);
    }
    
    /**
     * Execute a query using a prepared statement and display the results
     */
    private void executePreparedStatementQuery() {
        String query = "SELECT * FROM Games WHERE Genre = ? AND ReleaseDate > ?";
        Object[] params = {"Action", "2020-01-01"};
        
        ResultSet resultSet = DBCommand.executePreparedQuery(conn, query, params);
        
        // You'll need to create a custom formatter for this query
        // For example: DBOutputFormatter.showGamesByGenreAndDate("Query with Prepared Statement", resultSet);
        
        // For now, just print a message
        System.out.println("\nExecuted prepared statement query: " + query);
        try {
            int count = 0;
            while (resultSet != null && resultSet.next()) {
                count++;
            }
            System.out.println("Results: " + count + " rows returned");
        } catch (SQLException e) {
            System.err.println("Error processing result set: " + e.getMessage());
        }
    }
    
    /**
     * Tests the SQLite database connection
     * @return boolean true if connection test is successful, false otherwise
     */
    private boolean testSQLiteConnection() {
        boolean success = false;
        Connection testConn = null;
        try {
            // Test connection
            testConn = DBConnect.connect(dbFilePath);
            if (testConn != null) {
                System.out.println("SQLite connection test successful!");
                // Test a simple query
                ResultSet rs = DBCommand.executeQuery(testConn, "SELECT sqlite_version();");
                if (rs.next()) {
                    System.out.println("SQLite Version: " + rs.getString(1));
                }
                success = true;
            }
        } catch (Exception e) {
            System.err.println("Connection test failed: " + e.getMessage());
        } finally {
            DBConnect.disconnect(testConn);
        }
        return success;
    }
}
