package com.tellgear.view;

import com.tellgear.MainApp;
import com.tellgear.model.EmotedPane;
import com.tellgear.model.LogMessage;
import com.tellgear.model.User;
import com.tellgear.net.Message;
import com.tellgear.util.Constants;
import com.tellgear.util.Utilities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.util.Duration;

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
    private ScrollPane chat_scroll, emojis_scroll;
    @FXML
    private Label name;
    @FXML
    private ImageView avatar;
    @FXML
    private VBox user_box;
    @FXML
    private AnchorPane emoji_pane;

    private long size = 8;
    private MainApp main;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        chat_log.setPadding(new Insets(3, 3, 3, 3));
        emojis_scroll.setVisible(false);

        double x = 0, y = 0;

        for(String key: Constants.EMOJIS.keySet()){
            ImageView emote_img = new ImageView(Constants.EMOJIS.get(key));
            emote_img.setFitWidth(32);
            emote_img.setFitHeight(32);

            Button emote = new Button("", emote_img);
            emote.setTooltip(new Tooltip(key));
            emote.getStyleClass().add("emote");
            emote.getStylesheets().add(getClass().getResource("styles/buttons.css").toExternalForm());
            emote.setPadding(new Insets(0, 0, 0, 0));
            emote.setPrefSize(38, 38);
            emote.setLayoutX(x);
            emote.setLayoutY(y);
            emote.setFocusTraversable(false);

            emote.setOnAction(event -> message.appendText(key));
            x+=38;
            if(x+38 >= emoji_pane.getPrefWidth()){
                x = 0;
                y+=38;
            }
            emoji_pane.getChildren().add(emote);
        }

    }

    public void show(){
        name.setText(USERNAME);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), event -> consoleApp("Bienvenido al servidor")));
        timeline.play();
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
        //emojis_scroll.setVisible(!emojis_scroll.isVisible());
    }

    @FXML
    public void onSend(ActionEvent ae){
        if(message.getText().trim().equals("")) {
            message.setText("");
            return;
        }

        emojis_scroll.setVisible(false);

        Message msg = new Message("message", USERNAME, Utilities.crypt(message.getText()), "!!##ALL");
        main.client.send(msg);
        message.setText("");
        if(User.findUser(USERNAME).isWriting() == message.getText().trim().equals(""))
            main.client.send(new Message("writing", USERNAME, Utilities.crypt(!message.getText().trim().equals("")+""), "!!##ALL"));

    }

    @FXML
    public void onEnter(KeyEvent ke){
        if(User.findUser(USERNAME).isWriting() == message.getText().trim().equals(""))
            main.client.send(new Message("writing", USERNAME, Utilities.crypt(!message.getText().trim().equals("")+""), "!!##ALL"));

        emojis_scroll.setVisible(false);

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
        if(User.findUser(USERNAME).isWriting() == message.getText().trim().equals(""))
            main.client.send(new Message("writing", USERNAME, Utilities.crypt(!message.getText().trim().equals("")+""), "!!##ALL"));

    }

    public void updateUsers(){
        user_box.getChildren().removeAll(user_box.getChildren());
        user_box.setPrefHeight(User.users.size()*48);
        for(User user:User.users){
            user_box.getChildren().add(user.getUserPane());
        }
    }

    public void removeUser(String user){
        if(!user.equals(UserOverviewController.USERNAME)){
            user_box.getChildren().remove(User.findUser(user));
            User.users.remove(User.findUser(user));
        }
    }

    public void consoleApp(String text){
    }

    public void consoleOther(Message message){
        LogMessage logMessage = new LogMessage(message);
        logMessage.show(chat_log);
    }

    public void consoleMe(Message message){
        LogMessage logMessage = new LogMessage(message);
        logMessage.show(chat_log);
    }

    public void setMainApp(MainApp main){
        this.main = main;
    }

}
