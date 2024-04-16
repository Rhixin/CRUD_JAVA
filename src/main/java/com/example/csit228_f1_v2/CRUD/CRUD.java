package com.example.csit228_f1_v2.CRUD;

import java.sql.*;

public class CRUD {

    public CRUD (){
        super();
    }

    public void createTable(){
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

    //CREATE
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

    //READ
    public String readData(){
        StringBuilder results;

        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            results = new StringBuilder();

            while (res.next()){
                int id = res.getInt( "id");
                String fname = res.getString("fname");
                String lname = res.getString("lname");
                String email = res.getString("email");
                String username = res.getString("username");
                String password = res.getString("password");
                results.append("ID: " +
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

        return results.toString();
    }

    public boolean validateLogIn(String username, String password){
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            while (res.next()){
                String data_username = res.getString("username");
                String data_password = res.getString("password");

                if(data_username.equals(username) && data_password.equals(password)){
                    return true;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    //UPDATE
    public void updateData(String column, int bounds, String new_data, String operation){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET " + column + "=? WHERE id" + operation + "?")) {

            statement.setString(1, new_data);
            statement.setInt(2, bounds);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows Updated: " + rowsUpdated);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(String column, int bounds1, int bounds2, String new_data, String operation1, String operation2, String operator){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET " + column + "=? WHERE id" + operation1 + "? " + operator + " id" + operation2 + "?")) {

            statement.setString(1, new_data);
            statement.setInt(2, bounds1);
            statement.setInt(3, bounds2);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows Updated: " + rowsUpdated);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //DELETE
    public void deleteData(int bounds1, int bounds2, String operator1, String operator2, String operation){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("DELETE FROM tblusers WHERE id" + operator1 +"? " + operation + " id" + operator2 + "?")) {

            statement.setInt(1,bounds1);
            statement.setInt(2,bounds2);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows Deleted: " + rowsDeleted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
