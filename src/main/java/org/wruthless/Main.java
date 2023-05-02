package org.wruthless;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        final String DATABASE_URL = "jdbc:derby:books";
        final String SELECT_QUERY = "SELECT authorID, firstName, lastName FROM authors";

        // Try-with-resources statement to connect and query the database.
        try (
                // Auto-closable objects. Implements autocloseable interface.
                Connection connection = DriverManager.getConnection(DATABASE_URL, "wruth", "term");

                // After the connection is established, create a statement object which is used to
                // perform queries against the database.
                Statement statement = connection.createStatement();

                // Using statement to perform query.
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
        {
            // Contains various methods for retrieving information about your database.
            // For example, a method for getting the number of columns in the db.
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            System.out.printf("Authors Table of Books Database: %n%n");

            // Print column heads.
            // Columns are indexed at 1 not 0.
            for (int i = 1; i <= numberOfColumns; i++) {
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            }
            System.out.println();

            // Display query results
            // Columns are index at 1 not 0.
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                }
                System.out.println();
            }

        }  // AutoCloseable close method called.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}