package com.tellgear.model;

import com.tellgear.net.Message;
import com.tellgear.util.Constants;
import com.tellgear.util.Utilities;
import com.tellgear.view.UserOverviewController;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LogMessage {

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

    private void showAsText(AnchorPane parentPane){
        FlowPane pane = new FlowPane();
        parentPane.getChildren().add(pane);

        Label date = new Label(Utilities.getDate());
        date.setTextFill(Color.GRAY);
        date.setPadding(new Insets(3, 3, 3, 3));
        date.setFont(Constants.DATE_FONT);
        pane.getChildren().add(date);

        if(!message.sender.equals(UserOverviewController.USERNAME)){
            Label name_lbl = new Label(message.sender);
            name_lbl.setMaxWidth(parentPane.getPrefWidth()/6*3);
            name_lbl.setFont(Constants.NAME_FONT);
            name_lbl.setTextFill(Color.valueOf(User.findUser(message.sender).getColor()));
            name_lbl.setPadding(new Insets(3, 3, 3, 3));
            pane.getChildren().add(name_lbl);

            pane.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 4;");
        }else{
            pane.setStyle("-fx-background-color: #86e0fd; -fx-background-radius: 4;");
        }

        Label label = new Label(message.content);
        label.setMaxWidth(parentPane.getPrefWidth()/6*5);
        label.setWrapText(true);
        label.setFont(Constants.MSG_FONT);
        label.setPadding(new Insets(3, 3, 3, 3));
        parentPane.getChildren().add(label);

        pane.applyCss();
        pane.layout();
    }

}
