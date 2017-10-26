package com.tellgear.view;

import com.tellgear.MainApp;
import com.tellgear.net.Message;
import com.tellgear.util.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginOverviewController implements Initializable{

    private MainApp main;

    @FXML
    private TextField user;
    @FXML
    private PasswordField passwd;
    @FXML
    private Label advise_user, advise_passwd, advise_general;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onLogin(){
        String password = Utilities.crypt(passwd.getText());
        Message msg = new Message("login", user.getText(), password, "SERVER");
        main.client.send(msg);
    }

    @FXML
    private void onSignUp(){
        main.changeScene("signup");

    }

    public void showAdvise(String advise, String type){
        switch (type){
            case "user":
                advise_general.setText("");
                advise_passwd.setText("");
                advise_user.setText(advise);
                break;
            case "passwd":
                advise_general.setText("");
                advise_passwd.setText(advise);
                advise_user.setText("");
                break;
            default:
                advise_general.setText(advise);
                advise_passwd.setText("");
                advise_user.setText("");
                break;
        }
    }

    public void login(){
        UserOverviewController.USERNAME = user.getText();
        main.changeScene("user");

    }

    public void setMainApp(MainApp main){
        this.main = main;
    }

}
