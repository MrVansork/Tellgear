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

public class SignUpOverviewController implements Initializable{

    @FXML
    private TextField user;
    @FXML
    private PasswordField passwd, repeat_passwd;
    @FXML
    private Label error;

    private MainApp main;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onCancel(){
        passwd.clear();
        repeat_passwd.clear();
        user.clear();
        error.setText("");

        main.changeScene("login");
    }

    @FXML
    private void onSignUp(){
        if(passwd.getText().equals(repeat_passwd.getText())){
            String password = Utilities.crypt(passwd.getText());
            Message msg = new Message("signup", user.getText(), password, "SERVER");
            main.client.send(msg);
        }else{
            showAdvise("Password must be the same");
        }
    }

    public void signUp(){
        passwd.clear();
        repeat_passwd.clear();
        user.clear();
        error.setText("");

        main.changeScene("login");

    }

    public void showAdvise(String text){
        error.setText(text);
    }

    public void setMainApp(MainApp main){
        this.main = main;
    }

}
