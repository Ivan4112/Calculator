package com.example.calculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public static Connection conDB() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator", "Ivan", "root");
        return con;
    }
}
