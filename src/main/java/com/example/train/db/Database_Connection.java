package com.example.train.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// JBDC import so the code in running (connections with database)



public class Database_Connection 
{
    private static final String sqlURL = "jdbc:mysql://localhost:3306/train_system";
    //port 
    private static final String sqlUSER = "root";
    // username for the database
    private static final String sqlPASSWORD = "banmi";

    public Connection getConnection() throws SQLException
    // expectation if nothing found 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Load driver
        } 
        catch (ClassNotFoundException exception) 
        {
            throw new SQLException("MySQL not found", exception);
        }
        return DriverManager.getConnection(sqlURL, sqlUSER, sqlPASSWORD);
    }
}
