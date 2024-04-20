package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.CRUD.CRUD;

import com.example.csit228_f1_v2.CRUD.MySQLConnection;
import com.example.csit228_f1_v2.HELPERS.Post;
import com.example.csit228_f1_v2.HELPERS.SESSION;
import com.example.csit228_f1_v2.HELPERS.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class HomeController {

    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;

    //register here
    public TextField fname;
    public TextField lname;
    public TextField email;
    public TextField username;
    public TextField password;
    public TextField cpassword;
    public TextField post_title;
    public TextArea post_description;
    public VBox main_vbox;
    public Button log_out_btn;

    public TextField prof_fname;
    public TextField prof_lname;
    public TextField prof_username;
    public TextField prof_password;
    public TextField prof_email;

    public Button update_post_btn;
    public Button delete_post_btn;


    public void onSliderChange() {
        double val = slSlider.getValue();
        System.out.println(val);
        piProgress.setProgress(val/100);
        pbProgress.setProgress(val/100);
        if (val == 100) {
            System.exit(0);
        }
    }

    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: BLACK");
            tbNight.setText("DAY");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            tbNight.setText("NIGHT");
        }
    }

    public void insertNewUser(){

        String f_fname = fname.getText();
        String f_lname = lname.getText();
        String f_email = email.getText();
        String f_username = username.getText();
        String f_password = password.getText();
        String f_cpassword = cpassword.getText();

        if(!f_cpassword.equals(f_password)) return;

        CRUD.insertRecord(f_fname,f_lname,f_email,f_username,f_password);
        goBackLogInPage();
    }

    public void insertNewPost(){

        int user_id = SESSION.getInstance().getUser().userid;
        String title = post_title.getText();
        String description = post_description.getText();

        CRUD.insertPost(user_id,title,description);
    }

    public void goBackLogInPage(){
        Facebook fb = new Facebook();
        fb.goHere();
    }

    public void LoadPosts(){
        int posts = 0;
        ArrayList<Post> array_posts;

        try(Connection c = MySQLConnection.getConnection()) {
            c.setAutoCommit(false);

            array_posts = CRUD.readPosts();
            main_vbox.getChildren().clear();

            for(Post p : array_posts){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("post.fxml"));
                AnchorPane customComponent = loader.load();

                Label username_label = (Label) customComponent.lookup("#Username");
                Label title_label = (Label) customComponent.lookup("#Title");
                Label body_label = (Label) customComponent.lookup("#Body");

                User user = CRUD.getUser(p.userid);
                username_label.setText(user.fname + " " + user.lname);
                title_label.setText(p.title);
                body_label.setText(p.body);

                if(p.userid == SESSION.getInstance().getUser().userid){
                    customComponent.getStylesheets().clear();
                    customComponent.getStylesheets().add(Objects.requireNonNull(getClass().getResource("yours.css")).toExternalForm());
                }

                main_vbox.getChildren().add(customComponent);
                posts++;
            }

            c.commit();
            System.out.println("Transaction complete");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        if(posts == 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("empty_news_feed.fxml"));
            AnchorPane customComponent;

            try {
                customComponent = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            main_vbox.getChildren().add(customComponent);
        }


    }

    public void LoadEditMyPosts(){
        int posts = 0;
        ArrayList<Post> array_posts;

        array_posts = CRUD.readPosts();
        main_vbox.getChildren().clear();

        for(Post p : array_posts){

            if(p.userid != SESSION.getInstance().getUser().userid){
                continue;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("update_post.fxml"));
            AnchorPane customComponent;
            try {
                customComponent = loader.load();

                TextField title_field = (TextField) customComponent.lookup("#mypost_title");
                TextField body_field = (TextField) customComponent.lookup("#mypost_body");
                Button update_btn = (Button) customComponent.lookup("#update_post_btn");
                Button delete_btn = (Button) customComponent.lookup("#delete_post_btn");

                update_btn.setOnAction(actionEvent -> updateMyPost(title_field.getText(), body_field.getText(), p.postid));

                delete_btn.setOnAction(actionEvent -> deleteMyPost(p));

                title_field.setText(p.title);
                body_field.setText(p.body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            main_vbox.getChildren().add(customComponent);
            posts++;
        }

        if(posts == 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("no_own_post.fxml"));
            AnchorPane customComponent;

            try {
                customComponent = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            main_vbox.getChildren().add(customComponent);
        }
    }

    public void updateMyPost(String t, String b, int postid){
        CRUD.updatePost(t,b,postid);
        LoadEditMyPosts();
    }

    public void deleteMyPost(Post post){
        CRUD.deletePost(post);
        LoadEditMyPosts();
    }


    public void LoadMyPosts(){
        main_vbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("post_something.fxml"));
        AnchorPane customComponent;

        try {
            customComponent = loader.load();

            Label mind = (Label) customComponent.lookup("#mind");
            mind.setText("What's on your mind " + SESSION.getInstance().getUser().fname + "?");
            main_vbox.getChildren().add(customComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void updatedeleteMyPosts(){
//        main_vbox.getChildren().clear();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("update_post.fxml"));
//        AnchorPane customComponent = null;
//
//        try {
//            customComponent = loader.load();
//            main_vbox.getChildren().add(customComponent);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void myProfile(){
        main_vbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        AnchorPane customComponent;

        try {
            customComponent = loader.load();

            TextField fname = (TextField) customComponent.lookup("#prof_fname");
            TextField lname = (TextField) customComponent.lookup("#prof_lname");
            TextField email = (TextField) customComponent.lookup("#prof_email");
            TextField username = (TextField) customComponent.lookup("#prof_username");
            TextField password = (TextField) customComponent.lookup("#prof_password");

            fname.setText(SESSION.getInstance().getUser().fname);
            lname.setText(SESSION.getInstance().getUser().lname);
            email.setText(SESSION.getInstance().getUser().email);
            username.setText(SESSION.getInstance().getUser().username);
            password.setText(SESSION.getInstance().getUser().password);

            main_vbox.getChildren().add(customComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserProfile(){
        CRUD.updateProfileData(SESSION.getInstance().getUser().userid, prof_fname.getText(), prof_lname.getText(), prof_email.getText(), prof_username.getText(), prof_password.getText());
    }

    public void deleteMyAccount(){
        CRUD.deleteUser(SESSION.getInstance().getUser().userid);

        goBackLogInPage();
    }





}
