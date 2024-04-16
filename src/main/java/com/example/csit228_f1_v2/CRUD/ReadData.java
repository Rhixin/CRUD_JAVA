package com.example.csit228_f1_v2.CRUD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadData {
    public static void main(String[] args) {
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            while (res.next()){
                int id = res.getInt( "id");
                String fname = res.getString("fname");
                String lname = res.getString("lname");
                String email = res.getString("email");
                System.out.println("ID: " +
                        id + "\nFirst Name: " +
                        fname + "\nLast Name: " +
                        lname + "\nEmail: " +
                        email + "\n\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readData(){
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            while (res.next()){
                int id = res.getInt( "id");
                String fname = res.getString("fname");
                String lname = res.getString("lname");
                String email = res.getString("email");
                String username = res.getString("username");
                String password = res.getString("password");
                System.out.println("ID: " +
                        id + "\nFirst Name: " +
                        fname + "\nLast Name: " +
                        lname + "\nEmail: " +
                        email + "\nUsername: " +
                        username + "\nPassword: " +
                        password + "\n\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
