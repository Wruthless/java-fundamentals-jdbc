package org.wruthless;

import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.JdbcRowSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JdbcRowSetTest {

    // JDBC driver name and db url
    private static final String DATABASE_URL = "jdbc:derby:books";
    private static final String USERNAME = "wruth";
    private static final String PASSWORD = "term";

    public static void main(String[] args) {

        // Connect to database and query, try with resources statement.
        // newFactory is a RowSetProvider object that creates RowSets.
        // createJdbcRowSet is the object the factory produces.
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {

            // JdbcRowSet Props
            rowSet.setUrl(DATABASE_URL);
            rowSet.setUsername(USERNAME);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM authors");
            rowSet.execute();

            // process query results
            ResultSetMetaData metaData = rowSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println("Authors Table of Books Database:\n");

            // RowSet headers
            for (int i = 1; i <= numberOfColumns; i++) {
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            }
            System.out.println();

            // Rows
            while(rowSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.printf("%-8s\t", rowSet.getObject(i));
                }
                System.out.println();
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }

    } // end main()


} // end JdbcRowSetTest{}
