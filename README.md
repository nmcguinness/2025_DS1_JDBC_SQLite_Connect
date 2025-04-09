# SQLite Integration Guide for Database Systems Project

## Table of Contents
- [Introduction](#introduction)
- [Project Requirements](#project-requirements)
- [Setting Up SQLite](#setting-up-sqlite)
- [NetBeans Integration Guide](#netbeans-integration-guide)
- [VSCode Integration Guide](#vscode-integration-guide)
- [Working with SQLite in Java](#working-with-sqlite-in-java)
- [Common Issues and Solutions](#common-issues-and-solutions)
- [Project Structure Overview](#project-structure-overview)
- [Database Conversion Guide](#database-conversion-guide)

## Introduction

This guide provides step-by-step instructions for integrating SQLite with your Java application for Stage 3 of the Database Systems Project. It includes detailed setup instructions for both NetBeans and VSCode environments, specifically designed for first-year students who are new to Java and database connectivity.

## Project Requirements

Stage 3 requires you to:
1. Implement your final 3NF schema in SQLite
2. Develop 10 advanced SQL queries
3. Build a Java application using JDBC to execute those queries
4. Document everything in a comprehensive report

## Setting Up SQLite

### SQLite JDBC Driver

Before connecting to SQLite, you need to add the SQLite JDBC driver to your project:

1. **Download the SQLite JDBC Driver**
   - Visit: [https://github.com/xerial/sqlite-jdbc/releases](https://github.com/xerial/sqlite-jdbc/releases)
   - Download the latest JAR file (e.g., `sqlite-jdbc-3.40.0.0.jar`)

2. **Project Setup**
   - Create a `lib` folder in your project
   - Place the downloaded JAR file in the `lib` folder

## NetBeans Integration Guide

### Creating a New Project in NetBeans

1. **Launch NetBeans IDE**
   - Open NetBeans from your Start Menu, Desktop, or Applications folder

2. **Create a New Project**
   - Click on "File" > "New Project" (or use Ctrl+Shift+N)
   - In the Categories panel, select "Java"
   - In the Projects panel, select "Java with Maven" (if Java Application is not available)
   - Click "Next"

3. **Configure Maven Project**
   - Enter a Project Name (e.g., "DBS1_GCA_Stage3")
   - Set the Group ID (e.g., "com.dbs1")
   - Set the Version (leave as default "1.0-SNAPSHOT")
   - Choose a Project Location (where you want to save the project)
   - Click "Next"

4. **Maven Settings**
   - Leave the Maven settings as default
   - Click "Finish"

5. **Add SQLite Dependency to pom.xml**
   - Open the `pom.xml` file in your project
   - You need to add a `<dependencies>` section between the closing `</properties>` tag and the closing `</project>` tag:
   - Add the SQLite JDBC dependency:
   ```xml
    </properties>      
        <dependencies>
            <!-- Add SQLite JDBC dependency here -->
            <dependency>
                <groupId>org.xerial</groupId>
                <artifactId>sqlite-jdbc</artifactId>
                <version>3.40.0.0</version>
            </dependency>
        </dependencies>     
    </project>
   ```
   - Save the `pom.xml` file
   - In NetBeans IDE, right-click on your project name in the Projects panel (usually located on the left side of the screen). 
   - From the context menu that appears, select the option "Clean and Build"

### Creating the Package and Java Files

1. **Create a Package**
   - Right-click on "Source Packages" (or "src/main/java") in your project
   - Select "New" > "Java Package"
   - Name the package `connecttojdbc`
   - Click "Finish"

2. **Create Java Classes**
   - Right-click on the `connecttojdbc` package
   - Select "New" > "Java Class"
   - Enter the class name (e.g., "DBConnect")
   - Repeat for "DBCommand", "DBOutputFormatter", and "Main"
   - For each class, copy and paste the code from the provided files

3. **Create Project Folders**
   - For Maven projects, resources should be stored in `src/main/resources`
   - Right-click on "Other Sources" > "src/main/resources"
   - Select "New" > "Folder"
   - Create folders named `db`, `sql`, and `docs`

### Testing in NetBeans

To verify your SQLite connection in NetBeans:

1. **Create a test method**:
   ```java
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
               DBCommand.displayDatabaseSchema(testConn);
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
   ```

2. **Call the test method**:
   ```java
   if (!testConnection()) {
       System.err.println("Database connection test failed. Please check your setup.");
       return;
   }
   ```

3. **Run in debug mode**:
   - Set a breakpoint in your test method
   - Right-click the project and select "Debug"
   - Watch the Variables window to examine connection objects

## VSCode Integration Guide

### Installing VSCode and Extensions

1. **Download and Install VSCode**
   - Visit [https://code.visualstudio.com/](https://code.visualstudio.com/)
   - Download the appropriate version for your operating system
   - Follow the installation instructions

2. **Install Required Extensions**
   - Open VSCode
   - Click on the Extensions icon in the sidebar (or press Ctrl+Shift+X)
   - Search for and install:
     - "Extension Pack for Java" by Microsoft
     - "SQLite" by alexcvzz
     - "SQLite Viewer" by Florian Klampfer

### Creating a New Java Project in VSCode

1. **Open VSCode**
   - Launch VSCode from your Start Menu, Desktop, or Applications folder

2. **Create a New Java Project**
   - Press Ctrl+Shift+P to open the Command Palette
   - Type "Java: Create Java Project" and select it
   - Select "No build tools" for a simple project
   - Choose a location for your project
   - Enter a project name (e.g., "DBS1_GCA_Stage3")

3. **Create the Package Structure**
   - Open the Explorer view (Ctrl+Shift+E)
   - Navigate to the `src` folder
   - Right-click on the `src` folder and select "New Folder"
   - Create a folder named `connecttojdbc`

4. **Create Java Files**
   - Right-click on the `connecttojdbc` folder
   - Select "New File"
   - Create files named:
     - `DBConnect.java`
     - `DBCommand.java`
     - `DBOutputFormatter.java`
     - `Main.java`
   - Copy and paste the provided code into each file

5. **Create Project Folders**
   - Right-click on your project root in Explorer
   - Select "New Folder"
   - Create folders named:
     - `db` (for your SQLite database file)
     - `sql` (for your SQL scripts)
     - `docs` (for your documentation)
     - `lib` (for the JDBC driver)

### Adding the Driver to VSCode

1. **Download the SQLite JDBC Driver**
   - Visit the [SQLite JDBC GitHub releases page](https://github.com/xerial/sqlite-jdbc/releases)
   - Download the latest JAR file (e.g., `sqlite-jdbc-3.40.0.0.jar`)

2. **Add the Driver to Your Project**
   - Copy the downloaded JAR file to the `lib` folder in your project
   - Right-click on the JAR file in the VSCode Explorer
   - Select "Add to Java Build Path"

3. **Verify the Reference Library**
   - Check the Java Projects panel in VSCode
   - Expand your project > "Referenced Libraries"
   - You should see the SQLite JDBC JAR file listed there

### Using SQLite Viewer in VSCode

1. Open the SQLite Viewer extension
2. Click "Open Database"
3. Navigate to your `database.sqlite` file
4. Click on the file to open it
5. Click on "New Query" to write and execute SQL queries directly

## Working with SQLite in Java

### Understanding the Java Files

The project includes four main Java files:

1. **DBConnect.java** - Manages database connections
   - Contains methods to connect to and disconnect from the SQLite database
   - Handles JDBC driver loading and connection creation

2. **DBCommand.java** - Executes SQL queries
   - Provides methods to run SELECT and UPDATE queries
   - Includes support for prepared statements
   - Contains a method to display the database schema

3. **DBOutputFormatter.java** - Formats query results
   - Displays query results in a readable format
   - Includes specialized formatters for different query types

4. **Main.java** - The entry point for your application
   - Contains the main method to start your program
   - Demonstrates how to connect to the database and run queries

### Connection Best Practices

When connecting to SQLite, follow these best practices:

1. **Check for driver availability before connecting**
   ```java
   if (!DBConnect.isSQLiteDriverAvailable()) {
       System.err.println("SQLite JDBC driver not available.");
       return;
   }
   ```

2. **Use both relative and absolute paths**
   ```java
   // Try relative path first
   conn = DBConnect.connect(dbFilePath);
   if (conn == null) {
       // Fall back to absolute path
       conn = DBConnect.connect(DBConnect.getAbsolutePath(dbFilePath));
   }
   ```

3. **Always close connections in a finally block**
   ```java
   try {
       // Execute queries
   } finally {
       DBConnect.disconnect(conn);
   }
   ```

### Using Prepared Statements

For queries with user input, use prepared statements for better security and performance:

```java
// This is safer than building SQL strings with user input
String query = "SELECT * FROM Games WHERE Genre = ? AND ReleaseDate > ?";
Object[] params = {"Action", "2020-01-01"};
ResultSet resultSet = DBCommand.executePreparedQuery(conn, query, params);
```

### Displaying Query Results

The `DBOutputFormatter` class provides methods for displaying different types of query results:

- `showAllGames()` - For displaying games table data
- `showAllScores()` - For displaying player scores data
- `showGenericQueryResult()` - For any type of query result
- `showAggregationResults()` - For GROUP BY queries
- `showSubqueryResults()` - For subquery results
- `showCaseStatementResults()` - For queries with CASE statements
- `showCTEResults()` - For Common Table Expression queries
- `showViewResults()` - For queries against views

### Running Your First Query

Here's a simple example of how to run a query:

```java
// Connect to the database
Connection conn = DBConnect.connect("db/database.sqlite");

// Execute a simple query
String query = "SELECT * FROM Games";
ResultSet resultSet = DBCommand.executeQuery(conn, query);

// Display the results
DBOutputFormatter.showAllGames("All Games", resultSet);

// Close the connection
DBConnect.disconnect(conn);
```

## Common Issues and Solutions

### Database File Not Found

If you see "Database file not found" errors:

1. **Verify the database file exists**:
   - Check that your `database.sqlite` file is in the `db` folder
   - Make sure the filename matches exactly (SQLite is case-sensitive)

2. **Check the file path**:
   - Print the working directory to debug path issues:
     ```java
     System.out.println("Working directory: " + System.getProperty("user.dir"));
     ```
   - Try using the absolute path helper method:
     ```java
     String absolutePath = DBConnect.getAbsolutePath("db/database.sqlite");
     conn = DBConnect.connect(absolutePath);
     ```

3. **Create the database file if it doesn't exist**:
   - If you're starting from scratch, make sure your code creates the database file
   - Verify file permissions (make sure you have write access to the folder)

### Driver Not Found

If you see "ClassNotFoundException: org.sqlite.JDBC":

1. **Check the JDBC driver**:
   - Verify the SQLite JDBC JAR is added to your project
   - In NetBeans: Right-click project > Properties > Libraries > Compile
   - In VSCode: Check Referenced Libraries in Java Projects view

2. **Troubleshoot the driver**:
   - Try downloading the JAR file again
   - Use a specific version (e.g., sqlite-jdbc-3.36.0.3.jar)
   - Add a simple test to verify driver loading:
     ```java
     try {
         Class.forName("org.sqlite.JDBC");
         System.out.println("Driver loaded successfully!");
     } catch (ClassNotFoundException e) {
         System.err.println("Driver not found: " + e.getMessage());
     }
     ```

### Connection Issues

If your program connects but queries don't work:

1. **Check database schema**:
   - Use the `displayDatabaseSchema()` method to verify tables exist
   - Compare table and column names (case-sensitive)

2. **Verify file corruption**:
   - Open the SQLite file with DB Browser for SQLite
   - If it's corrupted, you may need to recreate it

## Project Structure Overview

For Stage 3, maintain this structure:

```
project_root/
  ├── src/
  │   └── main/
  │       ├── java/
  │       │   └── connecttojdbc/
  │       │       ├── DBConnect.java
  │       │       ├── DBCommand.java
  │       │       ├── DBOutputFormatter.java
  │       │       └── Main.java
  │       └── resources/
  │           ├── db/
  │           │   └── database.sqlite
  │           ├── sql/
  │           │   ├── schema.sql
  │           │   ├── populate.sql
  │           │   └── queries.sql
  │           └── docs/
  │               └── group_number_GCA_Stage3_Report.pdf
  ├── pom.xml
  └── target/
```

Note: This structure follows Maven conventions. If using a non-Maven project, your structure will be simpler as shown in the VSCode section.

## Database Conversion Guide

### Converting MySQL to SQLite

After creating your database in PHPMyAdmin:

1. **Export from PHPMyAdmin**
   - Open your database in PHPMyAdmin
   - Click on the "Export" tab
   - Choose "Quick" export method
   - Select "SQL" format
   - Click "Export" and download the `.sql` file

   ![PHPMyAdmin Export](https://i.imgur.com/1r34eif.png)

2. **Convert to SQLite**
   - Use an online converter: [https://www.rebasedata.com/convert-mysql-to-sqlite-online](https://www.rebasedata.com/convert-mysql-to-sqlite-online)
   - Upload your MySQL `.sql` file
   - Download the converted `.sqlite` file

   ![SQLite Conversion](https://i.imgur.com/WQ8jXbF.png)

3. **Test the Converted Database**
   - Download and install [DB Browser for SQLite](https://sqlitebrowser.org/dl/) if you don't have it
   - Open the `.sqlite` file with DB Browser for SQLite
   - Check the "Database Structure" tab to verify tables exist
   - Go to the "Browse Data" tab to verify data was imported correctly
   - Try running some test queries in the "Execute SQL" tab

   ![DB Browser for SQLite](https://i.imgur.com/CSLEbRN.png)
