package com.example.csit228_f1_v2.CRUD;

import com.example.csit228_f1_v2.HELPERS.Post;
import com.example.csit228_f1_v2.HELPERS.SESSION;
import com.example.csit228_f1_v2.HELPERS.User;

import java.sql.*;
import java.util.ArrayList;

public class CRUD {

    private CRUD (){
        super();
    }

    public static void createTable(){

        try (Connection c = MySQLConnection.getConnection()){

            String query1 = "CREATE TABLE IF NOT EXISTS tblusers (" +
                    "userid INT PRIMARY KEY AUTO_INCREMENT," +
                    "fname VARCHAR(50) NOT NULL," +
                    "lname VARCHAR(50) NOT NULL," +
                    "email VARCHAR(100) NOT NULL," +
                    "username VARCHAR(100) NOT NULL," +
                    "password VARCHAR(200) NOT NULL)";

            String query2 = "CREATE TABLE IF NOT EXISTS tblposts (" +
                    "postid INT PRIMARY KEY AUTO_INCREMENT," +
                    "userid INT(100) NOT NULL," +
                    "title VARCHAR(200) NOT NULL," +
                    "body VARCHAR(200) NOT NULL)";

            c.setAutoCommit(false);
            Statement statement = c.createStatement();
            statement.execute(query1);
            statement.execute(query2);
            c.commit();
            System.out.println("Table created successfully. Transaction Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //CREATE
    public static void insertRecord(String ffname, String llname, String eemail, String uusername, String ppassword){

        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO tblusers (fname,lname,email,username,password) VALUES (?,?,?,?,?)")){

            statement.setString(1, ffname);
            statement.setString(2, llname);
            statement.setString(3, eemail);
            statement.setString(4, uusername);
            statement.setString(5, ppassword);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows " +rowsInserted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void insertPost(int userid, String title, String body){

        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO tblposts (userid,title,body) VALUES (?,?,?)")){

            statement.setInt(1, userid);
            statement.setString(2, title);
            statement.setString(3, body);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows " +rowsInserted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //READ
//    public static String readUsers(){
//        StringBuilder results;
//
//        try(Connection c = MySQLConnection.getConnection();
//            Statement statement = c.createStatement()) {
//            String query = "SELECT * FROM tblusers";
//            ResultSet res = statement.executeQuery(query);
//
//            results = new StringBuilder();
//
//            while (res.next()){
//                int id = res.getInt( "userid");
//                String fname = res.getString("fname");
//                String lname = res.getString("lname");
//                String email = res.getString("email");
//                String username = res.getString("username");
//                String password = res.getString("password");
//                results.append("ID: " +
//                        id + "\nFirst Name: " +
//                        fname + "\nLast Name: " +
//                        lname + "\nEmail: " +
//                        email + "\nUsername: " +
//                        username + "\nPassword: " +
//                        password + "\n\n");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return results.toString();
//    }

    public static ArrayList<Post> readPosts() {
        ArrayList<Post> posts = new ArrayList<>();

        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM tblposts")) {

            while (resultSet.next()) {
                posts.add(new Post(resultSet.getInt("postid"), resultSet.getInt("userid"), resultSet.getString("title"), resultSet.getString("body")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        posts = reverseArrayList(posts);

        return posts;
    }

    private static ArrayList<Post> reverseArrayList(ArrayList<Post> alist)
    {
        // Arraylist for storing reversed elements
        ArrayList<Post> revArrayList = new ArrayList<>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

    public static User getUser(int userid){
        try(Connection c = MySQLConnection.getConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM tblusers WHERE userid=?")) {
            statement.setInt(1,userid);
            ResultSet result = statement.executeQuery();
            result.next();

            int data_userid = result.getInt("userid");
            String fname = result.getString("fname");
            String lname = result.getString("lname");
            String email = result.getString("email");
            String username = result.getString("username");
            String password = result.getString("password");

            return new User(data_userid, fname,lname,email,username,password );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User validateLogIn(String username, String password){
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            while (res.next()){
                int userid = res.getInt("userid");
                String fname = res.getString("fname");
                String lname = res.getString("lname");
                String email = res.getString("email");
                String data_username = res.getString("username");
                String data_password = res.getString("password");

                if(data_username.equals(username) && data_password.equals(password)){
                    return new User(userid, fname, lname, email, data_username, data_password);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    //UPDATE
//    public static void updateData(String table, String column, int bounds, String new_data, String operation){
//        try(Connection c = MySQLConnection.getConnection();
//            PreparedStatement statement = c.prepareStatement("UPDATE " + table + " SET " + column + "=? WHERE id" + operation + "?")) {
//
//            statement.setString(1, new_data);
//            statement.setInt(2, bounds);
//
//            int rowsUpdated = statement.executeUpdate();
//            System.out.println("Rows Updated: " + rowsUpdated);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void updateProfileData(int userid, String fname, String lname, String email, String username, String password){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET fname=?, lname=?, email=?, username=?, password=? WHERE userid=?")) {

            statement.setString(1, fname);
            statement.setString(2, lname);
            statement.setString(3, email);
            statement.setString(4, username);
            statement.setString(5, password);
            statement.setInt(6, userid);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows Updated: " + rowsUpdated);

            User user = new User(userid, fname, lname, email, username, password);
            SESSION.getInstance().setUser(user);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void updateData(String column, int bounds1, int bounds2, String new_data, String operation1, String operation2, String operator){
//        try(Connection c = MySQLConnection.getConnection();
//            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET " + column + "=? WHERE id" + operation1 + "? " + operator + " id" + operation2 + "?")) {
//
//            statement.setString(1, new_data);
//            statement.setInt(2, bounds1);
//            statement.setInt(3, bounds2);
//
//            int rowsUpdated = statement.executeUpdate();
//            System.out.println("Rows Updated: " + rowsUpdated);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void updatePost(String t, String b, int postid){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblposts SET title=?, body=? WHERE postid=?")) {

            statement.setString(1, t);
            statement.setString(2, b);
            statement.setInt(3, postid);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //DELETE
//    public static void deleteData(int bounds1, int bounds2, String operator1, String operator2, String operation){
//        try(Connection c = MySQLConnection.getConnection();
//            PreparedStatement statement = c.prepareStatement("DELETE FROM tblusers WHERE userid" + operator1 +"? " + operation + " id" + operator2 + "?")) {
//
//            statement.setInt(1,bounds1);
//            statement.setInt(2,bounds2);
//            int rowsDeleted = statement.executeUpdate();
//            System.out.println("Rows Deleted: " + rowsDeleted);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void deleteUser(int userid){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement1 = c.prepareStatement("DELETE FROM tblusers WHERE userid=?");
            PreparedStatement statement2 = c.prepareStatement("DELETE FROM tblposts WHERE userid=?")) {

            c.setAutoCommit(false);

            statement1.setInt(1,userid);
            statement2.setInt(1,userid);
            statement1.executeUpdate();
            statement2.executeUpdate();

            c.commit();
            System.out.println("Transaction Complete");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deletePost(Post post){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("DELETE FROM tblposts WHERE postid=?")) {

            statement.setInt(1,post.postid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
