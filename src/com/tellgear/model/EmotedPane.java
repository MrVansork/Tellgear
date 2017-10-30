package com.tellgear.model;

import com.tellgear.util.Constants;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EmotedPane extends Pane {

    private String text;

    private Label[] labels;

    public EmotedPane(String text){
        String aux = text;
        for(String code:Constants.EMOJIS.keySet()){
            aux = aux.replace(code, "!!##emoji##!!");
        }
        if(aux.contains("!!##emoji##!!")){
            int count = aux.split("!!##emoji##!!").length;
            labels = new Label[count];
            labels[0] = new Label(aux.split("!!##emoji##!!")[0]);
            getChildren().add(labels[0]);
            double width = 0;
            for(int i = 1; i < count; i++){
                Label label = new Label(aux.split("!!##emoji##!!")[i]);
                ImageView emote = new ImageView(Constants.EMOJIS.get("&happy&"));
                emote.setFitWidth(32);
                emote.setFitHeight(32);
                emote.setSmooth(true);
                label.setGraphic(emote);
                label.setLayoutX(width);
                label.setLayoutY(32*i);

                applyCss();
                layout();

                width += label.getWidth();
                getChildren().add(label);
            }
        }else{
            Label label = new Label(text);
            getChildren().add(label);
        }
    }

}
