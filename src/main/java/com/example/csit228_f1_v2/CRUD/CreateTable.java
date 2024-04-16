package com.example.csit228_f1_v2.CRUD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        Connection c = MySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS tblusers (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "fname VARCHAR(50) NOT NULL," +
                "lname VARCHAR(50) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "username VARCHAR(100) NOT NULL," +
                "password VARCHAR(100) NOT NULL)";

        try {
            Statement statement = c.createStatement();
            statement.execute(query);
            System.out.println("Table created suck-sis-fully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
