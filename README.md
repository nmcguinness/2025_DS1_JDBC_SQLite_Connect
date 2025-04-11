# SQLite Integration Guide for Database Systems 1 - GCA Project

## Table of Contents
- [Introduction](#introduction)
- [Project Structure Overview](#project-structure-overview)
- [NetBeans Integration Guide](#netbeans-integration-guide)
- [VSCode Integration Guide](#vscode-integration-guide)
- [Working with SQLite in Java](#working-with-sqlite-in-java)
- [Common Issues and Solutions](#common-issues-and-solutions)
- [Database Conversion Guide](#database-conversion-guide)

## Introduction

This guide provides step-by-step instructions for integrating SQLite with your Java application for Stage 3 of the Database Systems Project. It includes detailed setup instructions for both NetBeans and VSCode environments, specifically designed for first-year students who are new to Java and database connectivity.

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

Note: This structure follows Maven conventions. 

## Using the Sample Project

- Download or clone this sample project.
- The Java code is ready to connect to `sample_database.sqlite` located in `src/main/resources/db/`.
- Use this structure to build your own project.
- You **must replace** the following:
  - `sample_database.sqlite` → with your own SQLite DB (converted from your MySQL design)
  - SQL scripts in `sql/` → your own `schema.sql`, `populate.sql`, and `queries.sql`
  - Report PDF → your `group_number_GCA_Stage3_Report.pdf`

Do **not** submit the default sample DB or sample queries. Your submission will be evaluated based on your own SQL schema, query design, and ER modeling.

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

6. **Clean and Build to Download Dependencies**
   - In NetBeans IDE, right-click on your project name in the Projects panel (usually located on the left side of the screen)
   - From the context menu that appears, select the option "Clean and Build"
   - This critical step does several important things:
     - Downloads the SQLite JDBC driver from Maven repositories
     - Compiles all your Java source files
     - Creates compiled class files in the target directory
     - Prepares your application for execution
   - If this step fails, check the Output window for error messages
   - Common issues include:
     - Internet connectivity problems preventing dependency downloads
     - Syntax errors in your pom.xml file
     - Permissions issues with your project directory

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

4. **Set the Main Class**
   - Right-click on your project and select "Properties"
   - Go to the "Run" category
   - In the "Main Class" field, browse for or type `connecttojdbc.Main`
   - This ensures NetBeans knows which class contains the main method to run
   - Click "OK" to save these changes

5. **Clean and Build Again After Setting the Main Class**
   - Right-click on your project
   - Select "Clean and Build" to ensure all settings are properly recognized
   - This step is crucial to avoid the "Error: Could not find or load main class" error
   - The Output window should show "BUILD SUCCESS" when complete

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
Connection conn = DBConnect.connect("src/main/resources/db/sample_database.sqlite");

// Execute a simple query
String query = "SELECT * FROM Games";
ResultSet resultSet = DBCommand.executeQuery(conn, query);

// Display the results
DBOutputFormatter.showAllGames("All Games", resultSet);

// Close the connection
DBConnect.disconnect(conn);
```

## Common Issues and Solutions

### "Could not find or load main class" Error

If you see "Error: Could not find or load main class" or "ClassNotFoundException":

1. **Verify Main Class Configuration**:
   - Right-click your project and select "Properties"
   - Go to "Run" category
   - Check that the Main Class field contains the correct class name
     - If using the provided structure, it should be `connecttojdbc.Main`
     - The package name must match your file structure
   - Click "OK" to save changes

2. **Check Package Declaration**:
   - Open your Main.java file
   - Ensure it has the correct package declaration at the top:
     ```java
     package connecttojdbc;  // Must match folder structure
     ```
   - Verify that the class is named `Main` with proper capitalization
   - Check that it includes a proper main method:
     ```java
     public static void main(String[] args) {
         // Your code here
     }
     ```

3. **Perform Clean and Build Again**:
   - Right-click on your project in the Projects panel
   - Select "Clean and Build"
   - This rebuilds all class files and ensures configuration changes take effect
   - Watch the Output window for any compilation errors

4. **Run Project (Not File)**:
   - Use "Run Project" (F6) instead of "Run File"
   - This uses the project's main class configuration

5. **If Problem Persists**:
   - Try running via direct main class specification:
     - Right-click on Main.java
     - Select "Run File"
     - This bypasses project configuration

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
     String absolutePath = DBConnect.getAbsolutePath("src/main/resources/db/database.sqlite");
     conn = DBConnect.connect(absolutePath);
     ```

3. **Create the database file if it doesn't exist**:
   - If you're starting from scratch, make sure your code creates the database file
   - Verify file permissions (make sure you have write access to the folder)

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
