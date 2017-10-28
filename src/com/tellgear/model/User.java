package com.tellgear.model;

import com.tellgear.util.RandomColor;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class User {

    public static List<User> users = new ArrayList<>();

    public static User findUser(String name){
        for(User user:users){
            if(user.getName().equals(name))
                return user;
        }

        return null;
    }

    public static boolean exists(String name){
        for(User user:users){
            if (user.getName().equals(name)){
                return true;
            }
        }

        return false;
    }

    private String name;
    private String permissons;
    private String color;
    private Image avatar;
    private boolean writing = false;

    //PANE
    private ProgressIndicator writing_indicator = new ProgressIndicator();

    public User(String name, Image avatar) {
        this.name = name;
        this.avatar = avatar;
        color = RandomColor.getRandomColor();

        users.add(this);
    }

    public Pane getUserPane(){
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #FFFFFF;");
        pane.setPrefSize(200,48);

        Label name_lbl = new Label(name);
        name_lbl.setPrefSize(90, 17);
        name_lbl.setLayoutX(62);
        name_lbl.setLayoutY(16);
        pane.getChildren().add(name_lbl);

        ImageView image_avatar = new ImageView(avatar);
        image_avatar.setFitWidth(32);
        image_avatar.setFitHeight(32);
        image_avatar.setLayoutX(16);
        image_avatar.setLayoutY(8);
        pane.getChildren().add(image_avatar);

        writing_indicator = new ProgressIndicator();
        writing_indicator.setVisible(writing);
        writing_indicator.setPrefSize(16, 16);
        writing_indicator.setLayoutX(164);
        writing_indicator.setLayoutY(16);
        pane.getChildren().add(writing_indicator);

        return pane;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setWriting(boolean writing) {
        this.writing = writing;
        writing_indicator.setVisible(writing);
    }

    public boolean isWriting() {
        return writing;
    }
}
