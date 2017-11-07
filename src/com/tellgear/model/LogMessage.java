package com.tellgear.model;

import com.tellgear.net.Message;
import com.tellgear.util.Constants;
import com.tellgear.util.Utilities;
import com.tellgear.view.UserOverviewController;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LogMessage {

    private static double size = 4;
    private static String LAST = "";

    public enum Type{
        FILE, IMAGE, LINK, TEXT, GIF
    }

    private Message message;
    private Type type;

    public LogMessage(Message message){
        this(message, Type.TEXT);
    }

    public LogMessage(Message message, Type type) {
        this.message = message;
        this.type = type;
    }

    /**
     * Add the message, of any type, to the parent pane
     * @param parentPane the container that have all the message panes
     */
    public void show(AnchorPane parentPane){
        switch (type){
            case TEXT:
                showAsText(parentPane);
                break;
            case IMAGE:
                break;
            case GIF:
                break;
            case FILE:
                break;
            case LINK:
                break;
            default:
                break;
        }
    }

    /**
     * Add the message pane to the history pane
     * @param parentPane the container that have all the message panes
     */
    private void showAsText(AnchorPane parentPane){
        Pane pane = new Pane();
        parentPane.getChildren().add(pane);

        Label date = new Label(Utilities.getDate());
        date.setTextFill(Color.GRAY);
        date.setFont(Constants.DATE_FONT);

        HBox dn = new HBox();
        if(!message.sender.equals(UserOverviewController.USERNAME)){
            Label name_lbl = new Label(message.sender);
            name_lbl.setMaxWidth(parentPane.getPrefWidth()/6*3);
            name_lbl.setFont(Constants.NAME_FONT);
            name_lbl.setTextFill(Color.valueOf(User.findUser(message.sender).getColor()));
            pane.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 4;");

            HBox.setMargin(name_lbl, new Insets(0, 3, 0, 3));
            HBox.setMargin(date, new Insets(0, 3, 0, 3));
            dn.getChildren().addAll(name_lbl, date);
        }else{
            pane.setStyle("-fx-background-color: #86e0fd; -fx-background-radius: 4;");
            HBox.setMargin(date, new Insets(0, 3, 0, 3));
            dn.getChildren().add(date);
        }
        pane.getChildren().add(dn);

        Label label = new Label(Utilities.decrypt(message.content));
        label.setWrapText(true);
        label.setMaxWidth(parentPane.getPrefWidth()/6*5);
        label.setFont(Constants.MSG_FONT);
        label.setPadding(new Insets(3, 3, 3, 3));
        pane.getChildren().add(label);

        dn.applyCss();
        dn.layout();
        pane.applyCss();
        pane.layout();
        parentPane.applyCss();
        parentPane.layout();

        label.setLayoutY(dn.getHeight()+3);

        pane.setPrefHeight(label.getHeight()+dn.getHeight()+3);
        double offset = parentPane.getPrefWidth()-(date.getWidth() >= label.getWidth() ? date.getWidth()+6 : label.getWidth());
        pane.setLayoutX(!message.sender.equals(UserOverviewController.USERNAME) ? 8 : offset-12);
        pane.setLayoutY(size);

        size += pane.getPrefHeight()+(LAST.equals(message.sender) ? 4 : 8);
        LAST = message.sender;

    }

}
