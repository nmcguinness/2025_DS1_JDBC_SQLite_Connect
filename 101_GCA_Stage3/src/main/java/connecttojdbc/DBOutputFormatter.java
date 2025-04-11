
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * This class is used to format and output the result of each query run on the database.
 * @author NMCG
 * @since 09/04/2025
 * @see Main
 */
public class DBOutputFormatter {
      

    /**
     * Generic method to display any query result in a tabular format
     * @param title String title for the output
     * @param resultSet ResultSet object containing query results
     * @return int row count, or -1 if no results
     */
    public static int showGenericQueryResult(String title, ResultSet resultSet) {
        if(resultSet == null) {
            System.out.println("No results were returned!");
            return -1;
        }
        
        int rowCount = 0;

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            // Print header
            System.out.println("");
            System.out.println(title);
            System.out.println("");
            
            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i));
                if (i < columnCount) System.out.print("\t\t");
            }
            System.out.println();
            
            // Print separator line
            for (int i = 1; i <= columnCount; i++) {
                for (int j = 0; j < metaData.getColumnName(i).length(); j++) {
                    System.out.print("-");
                }
                if (i < columnCount) System.out.print("\t\t");
            }
            System.out.println();
            
            // Print data
            while (resultSet.next()) {
                rowCount++;
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    System.out.print(value == null ? "NULL" : value.toString());
                    if (i < columnCount) System.out.print("\t\t");
                }
                System.out.println();
            }
            
            System.out.println("\nTotal rows: " + rowCount);
        } catch (SQLException e) {
            System.err.println("Error displaying query results: " + e.getMessage());
        }
        
        return rowCount;
    }
    
        /**
     * This method is used to show the output of the query "SELECT * FROM games"
     * @param title String title for the output
     * @param resultSet ResultSet object containing query results
     * @return int row count, or -1 if no results
     */
    public static int showAllGames(String title, ResultSet resultSet) {
        if(resultSet == null) {
            System.out.println("No results were returned!");
            return -1;
        }
        
        int rowCount = 0;

        //print header
        System.out.println("");
        System.out.println(title);  
        System.out.println("");
        System.out.println("Game ID\t\t\tGame Name\t\tRelease Date\t\t\tGenre");
        System.out.println("-------\t\t\t---------\t\t------------\t\t\t-----");
        
        //print results
        try {
            while(resultSet.next()) {
                rowCount++;
                
                System.out.print(resultSet.getInt("GameID"));
                System.out.print("\t\t\t");
                
                System.out.print(resultSet.getString("GameName"));
                System.out.print("\t\t");
                
                System.out.print(resultSet.getDate("ReleaseDate"));
                System.out.print("\t\t\t");
                
                System.out.print(resultSet.getString("Genre"));
                System.out.print("\n");
            }
            
            System.out.println("\nTotal rows: " + rowCount);
        }
        catch(SQLException e) {
            System.err.println("Error displaying games: " + e.getMessage());
        }
        
        //return the number of rows output
        return rowCount;
    }
    
    /**
     * This method displays results from a GROUP BY query with aggregation
     * @param title String title for the output
     * @param resultSet ResultSet object containing query results
     * @return int row count, or -1 if no results
     */
    public static int showAggregationResults(String title, ResultSet resultSet) {
        if(resultSet == null) {
            System.out.println("No results were returned!");
            return -1;
        }
        
        int rowCount = 0;

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            // Print header
            System.out.println("");
            System.out.println(title);
            System.out.println("");
            
            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i));
                if (i < columnCount) System.out.print("\t\t");
            }
            System.out.println();
            
            // Print separator line
            for (int i = 1; i <= columnCount; i++) {
                for (int j = 0; j < metaData.getColumnName(i).length(); j++) {
                    System.out.print("-");
                }
                if (i < columnCount) System.out.print("\t\t");
            }
            System.out.println();
            
            // Print data
            while (resultSet.next()) {
                rowCount++;
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    System.out.print(value == null ? "NULL" : value.toString());
                    if (i < columnCount) System.out.print("\t\t");
                }
                System.out.println();
            }
            
            System.out.println("\nTotal groups: " + rowCount);
        } catch (SQLException e) {
            System.err.println("Error displaying aggregation results: " + e.getMessage());
        }
        
        return rowCount;
    }

    /**
     * This method displays results from a subquery
     * @param title String title for the output
     * @param resultSet ResultSet object containing query results
     * @return int row count, or -1 if no results
     */
    public static int showSubqueryResults(String title, ResultSet resultSet) {
        return showGenericQueryResult(title, resultSet);
    }

    /**
     * This method displays results from a query with CASE statements
     * @param title String title for the output
     * @param resultSet ResultSet object containing query results
     * @return int row count, or -1 if no results
     */
    public static int showCaseStatementResults(String title, ResultSet resultSet) {
        return showGenericQueryResult(title, resultSet);
    }

    /**
     * This method displays results from a Common Table Expression (CTE) query
     * @param title String title for the output
     * @param resultSet ResultSet object containing query results
     * @return int row count, or -1 if no results
     */
    public static int showCTEResults(String title, ResultSet resultSet) {
        return showGenericQueryResult(title, resultSet);
    }

    /**
     * This method displays results from a View
     * @param title String title for the output
     * @param resultSet ResultSet object containing query results
     * @return int row count, or -1 if no results
     */
    public static int showViewResults(String title, ResultSet resultSet) {
        return showGenericQueryResult(title, resultSet);
    }
}
