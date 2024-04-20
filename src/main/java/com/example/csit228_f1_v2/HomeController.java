package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.CRUD.CRUD;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

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
    public Button back;


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

        CRUD.insertRecord(f_fname,f_lname,f_email,f_username,f_password);
    }

    public void goBackLogInPage(){
        try {
            Parent p = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene s = new Scene(p);
            HelloApplication.mainStage.setScene(s);
            HelloApplication.mainStage.show();
            Label lbUsername;
            Label lbPassword;
            TextField tfUsername;
            PasswordField pfPassword;


            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            Text txtWelcome = new Text("Welcome to CIT");
            txtWelcome.setFont(Font.font("Chiller", FontWeight.EXTRA_BOLD, 69));
            txtWelcome.setFill(Color.RED);
//        grid.setAlignment();
            grid.setPadding(new Insets(20));
//        grid.
            txtWelcome.setTextAlignment(TextAlignment.CENTER);
            grid.add(txtWelcome, 0, 0, 3, 1);

            lbUsername = new Label("Username: ");
            lbUsername.setTextFill(Color.LIGHTSKYBLUE);
            lbUsername.setFont(Font.font(30));
            grid.add(lbUsername, 0, 1);

             tfUsername = new TextField();
            grid.add(tfUsername, 1, 1);
            tfUsername.setFont(Font.font(30));
//        tfUsername.setMaxWidth(150);

            lbPassword = new Label("Password");
            lbPassword.setFont(Font.font(30));
            lbPassword.setTextFill(Color.CHARTREUSE);
            grid.add(lbPassword, 0, 2);

             pfPassword = new PasswordField();
            pfPassword.setFont(Font.font(30));
            grid.add(pfPassword, 1, 2);

            TextField tmpPassword = new TextField(pfPassword.getText());
            tmpPassword.setFont(Font.font(30));
            grid.add(tmpPassword, 1, 2);
            tmpPassword.setVisible(false);

            ToggleButton btnShow = new ToggleButton("( )");
//        btnShow.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                if (btnShow.isSelected()) {
//                    tmpPassword.setText(pfPassword.getText());
//                    tmpPassword.setVisible(true);
//                } else {
//                    tmpPassword.setVisible(false);
//                    pfPassword.setText(tmpPassword.getText());
//                }
//            }
//        });
            btnShow.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    tmpPassword.setText(pfPassword.getText());
                    tmpPassword.setVisible(true);
                }
            });

            EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    tmpPassword.setVisible(false);
                    pfPassword.setText(tmpPassword.getText());
                }
            };

            btnShow.setOnMouseReleased(release);
            btnShow.setOnMouseExited(release);
            grid.add(btnShow, 2,2);

            Button btnLogin = new Button("Log In");
            btnLogin.setFont(Font.font(40));
            grid.add(btnLogin, 0, 3, 2, 1);

            Button btnSignUp = new Button("Sign up");
            btnSignUp.setFont(Font.font(40));
            grid.add(btnSignUp, 0, 4,3,2);


            btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String username = tfUsername.getText();
                    String password = pfPassword.getText();

                    if(CRUD.validateLogIn(username,password)){
                        try {
                            Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                            Scene s = new Scene(p);
                            HelloApplication.mainStage.setScene(s);
                            HelloApplication.mainStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });

            btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Sign Up");
                    try {
                        Parent p = FXMLLoader.load(getClass().getResource("register.fxml"));
                        Scene s = new Scene(p);
                        HelloApplication.mainStage.setScene(s);
                        HelloApplication.mainStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Scene scene = new Scene(grid, 700, 500, Color.BLACK);
            HelloApplication.mainStage.setScene(scene);
            scene.setFill(Color.CORNFLOWERBLUE);
            HelloApplication.mainStage.show();
            txtWelcome.minWidth(grid.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
