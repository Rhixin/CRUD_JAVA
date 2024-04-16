package com.example.csit228_f1_v2.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {

    public InsertData(){
        super();
    }
    public static void main(String[] args) {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO tblusers (fname,lname,email) VALUES (?,?,?)");){
            String fname = "tRYY";
            String lname = "Repunte";
            String email = "frenzrzxzx@gmail.com";

            statement.setString(1,fname);
            statement.setString(2,lname);
            statement.setString(3, email);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows Inserted: " + rowsInserted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRecord(String ffname, String llname, String eemail, String uusername, String ppassword){
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO tblusers (fname,lname,email,username,password) VALUES (?,?,?,?,?)");){
            String fname = ffname;
            String lname = llname;
            String email = eemail;
            String username = uusername;
            String password = ppassword;

            statement.setString(1,fname);
            statement.setString(2,lname);
            statement.setString(3, email);
            statement.setString(4, username);
            statement.setString(5, password);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows Inserted: " + rowsInserted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
