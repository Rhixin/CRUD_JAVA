package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.CRUD.CRUD;
import com.example.csit228_f1_v2.CRUD.MySQLConnection;
import com.example.csit228_f1_v2.HELPERS.Post;
import com.example.csit228_f1_v2.HELPERS.SESSION;
import com.example.csit228_f1_v2.HELPERS.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Facebook extends Application {
    public static Stage stage;

    Button log_in_btn;
    Button create_acc_btn;
    TextField log_uname;
    TextField log_password;

    @Override
    public void start(Stage stage) {
        Facebook.stage = stage;
        goHere();
    }

    public void goHere(){
        CRUD.createTable();
        FXMLLoader fxmlLoader = new FXMLLoader(Facebook.class.getResource("log_in.fxml"));
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root, 600, 410);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("ZHAZTED'S FACEBOOK LITE");
        stage.setScene(scene);

        log_in_btn = (Button) root.lookup("#log_in_btn");
        create_acc_btn = (Button) root.lookup("#create_account_btn");
        log_uname = (TextField) root.lookup("#log_uname");
        log_password = (TextField) root.lookup("#log_password");

        log_in_btn.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = log_uname.getText();
                String password = log_password.getText();

                User attempt_user = CRUD.validateLogIn(username, password);

                if (attempt_user != null) {
                    try {
                        SESSION.getInstance().setUser(attempt_user);
                        SESSION.printData();
                        Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
                        Scene s = new Scene(p);
                        stage.setScene(s);
                        stage.show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        create_acc_btn.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Sign Up");
                try {
                    Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));
                    Scene s = new Scene(p);
                    s.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
                    stage.setScene(s);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.show();
    }


}
