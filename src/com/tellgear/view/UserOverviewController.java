package com.tellgear.view;

import com.tellgear.MainApp;
import com.tellgear.model.User;
import com.tellgear.net.Message;
import com.tellgear.util.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserOverviewController implements Initializable {

    public static String USERNAME = "";

    @FXML
    private TextArea message;
    @FXML
    private AnchorPane chat_log;
    @FXML
    private ScrollPane chat_scroll;
    @FXML
    private Label name;
    @FXML
    private ImageView avatar;
    @FXML
    private Font msg_font, name_font, date_font;
    @FXML
    private VBox user_box;

    private long size = 8;
    private DateFormat format = new SimpleDateFormat("HH:mm");

    private MainApp main;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        date_font = Font.font("Amble CN", FontWeight.LIGHT, 10);
        msg_font = Font.font("Amble CN", FontWeight.NORMAL, 14);
        name_font = Font.font("Amble CN", FontWeight.BOLD, 14);
        chat_log.setPadding(new Insets(3, 3, 3, 3));

    }

    public void show(){
        name.setText(USERNAME);
    }

    @FXML
    public void openSettings(ActionEvent ae){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("SettingsOverview.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    @FXML
    public void onEmoji(){

    }

    @FXML
    public void onSend(ActionEvent ae){
        if(message.getText().trim().equals("")) {
            message.setText("");
            return;
        }

        Message msg = new Message("message", USERNAME, Utilities.crypt(message.getText()), "!!##all");
        main.client.send(msg);
        message.setText("");
    }

    @FXML
    public void onEnter(KeyEvent ke){
        if(ke.isControlDown() && ke.getCode() == KeyCode.ENTER){
            message.appendText("\n");
            return;
        }
        if(ke.getCode() != KeyCode.ENTER)
            return;

        ke.consume();

        if(message.getText().trim().equals("")) {
            message.setText("");
            return;
        }

        Message msg = new Message("message", USERNAME, Utilities.crypt(message.getText()), "!!##ALL");
        main.client.send(msg);
        message.setText("");
    }

    public void updateUsers(){
        user_box.getChildren().removeAll(user_box.getChildren());
        user_box.setPrefHeight(User.users.size()*48);
        for(User user:User.users){
            user_box.getChildren().add(user.getUserPane());
        }
    }

    public void removeUser(String user){
        /*if(!user.equals(UserOverviewController.USERNAME)){
            for(int i = 0; i < data.size(); i++){
                if(data.get(i).equals(user)){
                    data.remove(i);
                }
            }
        }*/
    }

    public void consoleApp(String text){
        Pane pane = new Pane();
        chat_log.getChildren().add(pane);
        pane.setStyle("-fx-background-color: #f8ea72; -fx-background-radius: 8;");

        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setPadding(new Insets(3, 3, 3, 3));
        lbl.setFont(msg_font);
        lbl.setMaxWidth(chat_log.getPrefWidth()/6*5);
        pane.getChildren().add(lbl);

        chat_log.applyCss();
        chat_log.layout();

        pane.applyCss();
        pane.layout();

        pane.setPrefHeight(lbl.getHeight());
        pane.setLayoutX(chat_log.getPrefWidth()/2-lbl.getWidth()/2);
        pane.setLayoutY(size);


        size += lbl.getHeight()+8;
    }

    public void consoleOther(String text, String name, String color){
        Pane pane = new Pane();
        chat_log.getChildren().add(pane);
        pane.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 8;");

        Label nme = new Label(name);
        nme.setTextFill(Color.valueOf(color));
        nme.setPadding(new Insets(3, 3, 3, 3));
        nme.setFont(name_font);
        nme.setMaxWidth(chat_log.getPrefWidth()/6*3);
        pane.getChildren().add(nme);

        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setPadding(new Insets(3, 3, 3, 3));
        lbl.setFont(msg_font);
        lbl.setMaxWidth(chat_log.getPrefWidth()/6*5);
        pane.getChildren().add(lbl);

        Label date = new Label(format.format(new Date()));
        date.setTextFill(Color.GRAY);
        date.setPadding(new Insets(3, 3, 3, 3));
        date.setFont(date_font);
        pane.getChildren().add(date);

        chat_log.applyCss();
        chat_log.layout();

        pane.applyCss();
        pane.layout();

        lbl.setLayoutY(nme.getHeight()-4);
        pane.setPrefHeight(lbl.getHeight()+nme.getHeight());

        if(pane.getWidth() < nme.getWidth()+date.getWidth()+16)
            pane.setPrefWidth(nme.getWidth()+date.getWidth()+16);

        if(pane.getPrefWidth() != -1)
            date.setLayoutX(pane.getPrefWidth()-date.getWidth()-8);
        else
            date.setLayoutX(pane.getWidth()-date.getWidth()-8);

        pane.setLayoutY(size);
        pane.setLayoutX(4);
        size += pane.getPrefHeight()+8;


    }

    public void consoleMe(String text){
        Pane pane = new Pane();
        chat_log.getChildren().add(pane);
        pane.setStyle("-fx-background-color: #86e0fd; -fx-background-radius: 8;");

        Label lbl = new Label("       "+text);
        lbl.setPadding(new Insets(3, 3, 3, 3));
        lbl.setWrapText(true);
        lbl.setFont(msg_font);
        lbl.setMaxWidth(chat_log.getPrefWidth()/6*5);
        pane.getChildren().add(lbl);

        Label date = new Label(format.format(new Date()));
        date.setTextFill(Color.valueOf("#515151"));
        date.setPadding(new Insets(3, 3, 3, 3));
        date.setFont(date_font);
        pane.getChildren().add(date);

        pane.applyCss();
        pane.layout();

        chat_log.applyCss();
        chat_log.layout();


        pane.setPrefHeight(lbl.getHeight());

        pane.setLayoutX(chat_log.getPrefWidth()-lbl.getWidth()-8);
        pane.setLayoutY(size);
        size += lbl.getHeight()+8;

        chat_scroll.setVvalue(1.0d);
    }

    public void setMainApp(MainApp main){
        this.main = main;
    }

}
