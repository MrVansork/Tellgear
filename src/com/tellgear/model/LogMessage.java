package com.tellgear.model;

import javafx.scene.layout.AnchorPane;

public class LogMessage {

    public enum Type{
        FILE, IMAGE, LINK, TEXT, GIF
    }

    private String message;
    private String sender;
    private Type type;

    public LogMessage(String message, Type type) {
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

    public void showAsText(AnchorPane parentPane){

    }

}
