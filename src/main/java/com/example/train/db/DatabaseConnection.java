package com.example.train.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/train_system";
    private static final String USER = "root";
    private static final String PASSWORD = "banmi";

    public static Connection getConnection () throws SQLException{
     try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } 
    catch  (ClassNotFoundException exception) {
        throw new SQLException("MySQL Driver not found", exception);
    }
    return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
