package com.example.csit228_f1_v2.HELPERS;

public class SESSION {
    private static SESSION instance;
    private static User user;

    private SESSION() {
        // Private constructor to prevent instantiation
    }

    public static SESSION getInstance() {
        if (instance == null) {
            instance = new SESSION();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        SESSION.user = user;
    }

    public static void printData(){
        System.out.println("User id: " + user.userid);
        System.out.println("User fname: " + user.fname);
        System.out.println("User lname: " + user.lname);
        System.out.println("User email: " + user.email);
        System.out.println("User username: " + user.username);
        System.out.println("User password: " + user.password);
    }
}
