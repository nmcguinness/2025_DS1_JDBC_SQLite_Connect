

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.Date;

/**
 * This class is used to execute SQL queries and updates on the SQLite database
 * @author NMCG
 * @since 09/04/2025
 * @see DBConnect
 */
public class DBCommand {

    /**
     * This method executes a SELECT query on the SQLite database
     * @param conn Connection object
     * @param query SQL query to execute
     * @return ResultSet object containing query results
     */
    public static ResultSet executeQuery(Connection conn, String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Query execution error: " + e.getMessage());
            System.err.println("Query: " + query);
        }
        return resultSet;
    }

    /**
     * This method executes an INSERT, UPDATE, or DELETE query on the SQLite database
     * @param conn Connection object
     * @param query SQL query to execute
     * @return int number of rows affected
     */
    public static int executeUpdate(Connection conn, String query) {
        int result = -1;
        try {
            Statement statement = conn.createStatement();
            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Update execution error: " + e.getMessage());
            System.err.println("Query: " + query);
        }
        return result;
    }
    
    /**
     * This method executes a SQL query using prepared statements
     * @param conn Connection object
     * @param query Parameterized SQL query
     * @param params Object array containing parameter values
     * @return ResultSet object containing query results
     */
    public static ResultSet executePreparedQuery(Connection conn, String query, Object[] params) {
        ResultSet resultSet = null;
        try {
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // Set parameters
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    if (params[i] instanceof String) {
                        prepStmt.setString(i + 1, (String) params[i]);
                    } else if (params[i] instanceof Integer) {
                        prepStmt.setInt(i + 1, (Integer) params[i]);
                    } else if (params[i] instanceof Double) {
                        prepStmt.setDouble(i + 1, (Double) params[i]);
                    } else if (params[i] instanceof Date) {
                        prepStmt.setDate(i + 1, (Date) params[i]);
                    } else if (params[i] == null) {
                        prepStmt.setNull(i + 1, Types.NULL);
                    }
                    // Add more types as needed
                }
            }
            
            resultSet = prepStmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Prepared query error: " + e.getMessage());
            System.err.println("Query: " + query);
        }
        return resultSet;
    }
    
    /**
     * This method executes an INSERT, UPDATE, or DELETE using prepared statements
     * @param conn Connection object
     * @param query Parameterized SQL query
     * @param params Object array containing parameter values
     * @return int number of rows affected
     */
    public static int executePreparedUpdate(Connection conn, String query, Object[] params) {
        int result = -1;
        try {
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // Set parameters
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    if (params[i] instanceof String) {
                        prepStmt.setString(i + 1, (String) params[i]);
                    } else if (params[i] instanceof Integer) {
                        prepStmt.setInt(i + 1, (Integer) params[i]);
                    } else if (params[i] instanceof Double) {
                        prepStmt.setDouble(i + 1, (Double) params[i]);
                    } else if (params[i] instanceof Date) {
                        prepStmt.setDate(i + 1, (Date) params[i]);
                    } else if (params[i] == null) {
                        prepStmt.setNull(i + 1, Types.NULL);
                    }
                    // Add more types as needed
                }
            }
            
            result = prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Prepared update error: " + e.getMessage());
            System.err.println("Query: " + query);
        }
        return result;
    }
    
}
