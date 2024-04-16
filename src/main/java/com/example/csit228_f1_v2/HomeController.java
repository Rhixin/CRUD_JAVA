package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.CRUD.InsertData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

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

        System.out.println(f_email);

        InsertData insertdata = new InsertData();
        insertdata.insertRecord(f_fname,f_lname,f_email,f_username,f_password);
    }


}
