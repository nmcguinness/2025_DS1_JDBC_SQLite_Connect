import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Main class that demonstrates connecting to SQLite and executing queries
 * @author NMCG
 * @since 09/04/2025
 */
public class Main {
    
    // Path to the SQLite database file (relative to project root)
    private String dbFilePath = "src/main/resources/db/sample_database.sqlite";
    
    // Store the connection to the DB
    private Connection conn = null;
    
    
    /*****************************************************************/
    /*                       MAIN METHODS                            */
    /*****************************************************************/

    /**
     * Main method that serves as the entry point for the application
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        
        // Create Main
        Main theApp = new Main();
        
        // Test the connection
        System.out.println("\nTesting connection...\n");
        theApp.testConnection();
        
        // Create an instance of the Main class and run it
        System.out.println("\nRun queries...\n");
        theApp.start();
        
        System.out.println("\nGoodbye...\n");
    }
      
    /**
     * Contains the main application logic
     */
    public void start() { 
        
        // Connect to SQLite DB
        conn = DBConnect.connect(dbFilePath);
        
        try 
        {
            // Execute queries
            printQueryHeader(1, "Simple SELECT Query");
            executeSimpleSelectQuery();

            printQueryHeader(2, "JOIN Query - Players, PlayerGames and Games");
            executeJoinQuery();

            printQueryHeader(3, "Prepared Statement Query - Games by Genre and Release Date");
            executePreparedStatementQuery();
        } 
        finally 
        {
            // Disconnect from DB (ensure this happens even if an exception occurs)
            DBConnect.disconnect(conn);           
        }
    }
    
        
    /*****************************************************************/
    /*                       QUERY METHODS                           */
    /*****************************************************************/
        
    /**
     * Execute a simple SELECT query and display the results
     */
    private void executeSimpleSelectQuery() {
        String query = "SELECT * FROM games";
        
        ResultSet resultSet = DBCommand.executeQuery(conn, query);
        DBOutputFormatter.showGenericQueryResult("All table content", resultSet);
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
        DBOutputFormatter.showGenericQueryResult("Players' Scores by Game", resultSet);
    }
    
    /**
     * Execute a query using a prepared statement and display the results
     */
    private void executePreparedStatementQuery() {
        String query = "SELECT * FROM Games WHERE Genre = ? AND ReleaseDate > ?";
        Object[] params = {"Action-Adventure", "1985-01-01"};
           
        ResultSet resultSet = DBCommand.executePreparedQuery(conn, query, params);
        DBOutputFormatter.showGenericQueryResult("Action-Adventure Games Released After 1985", resultSet);
    }
    
    /*****************************************************************/
    /*                       UTILITY METHODS                         */
    /*****************************************************************/
    
    /**
     * Print a formatted header for each query
     * @param queryNumber The sequential number of the query
     * @param description A brief description of the query
     */
    private void printQueryHeader(int queryNumber, String description) {
        System.out.println("\n/*************************************************************************/");
        System.out.println("/* QUERY " + queryNumber + ": " + description);
        System.out.println("/*************************************************************************/");
    }
    /**
     * Test method to see if our NetBeans project is properly setup
     */
    private boolean testConnection() {
        Connection testConn = null;
        try {
            System.out.println("Testing SQLite connection...");
            testConn = DBConnect.connect(dbFilePath);

            if (testConn != null) {
                System.out.println("Connection successful!");

                // Display the SQLite version
                ResultSet rs = DBCommand.executeQuery(testConn, "SELECT sqlite_version()");
                if (rs.next()) {
                    System.out.println("SQLite Version: " + rs.getString(1));
                }

                // Display database schema
                return true;
            }
        } catch (Exception e) {
            System.err.println("Test connection failed: " + e.getMessage());
        } finally {
            if (testConn != null) {
                DBConnect.disconnect(testConn);
            }
        }
        return false;
    }
}