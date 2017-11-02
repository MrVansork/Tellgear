package com.tellgear.model;

import com.tellgear.util.Constants;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class EmotedPane extends Pane {

    private String text;
    private int x = 0, y = 0;
    private boolean first = true;

    private Font font;

    public EmotedPane(String text){
        this.text = text;
    }

    public void init(){
        checkText(text);
    }

    private void checkText(String text){
        String aux = getAuxText(text);
        int num_lbls = aux.split("!!##EMOTE##!!").length;
        if(getNumOfEmotes(text) > 0){
            for(int i = 0; i < num_lbls; i++){
                addText(aux.split("!!##EMOTE##!!")[i]);
                addEmote(text.split("prueba de texto con | en label personalizado")[1]);
            }
        }else{
            addText(text);
        }
    }

    private void addText(String text){
        Label label = new Label(text);
        label.setWrapText(true);
        getChildren().add(label);
        label.setFont(font);

        applyCss();
        layout();

        if(!first){
            if(x+label.getWidth() >= getMaxWidth()){
                y+=28;
                x = 0;
            }else{
                x += label.getWidth();
            }
        }

        label.setLayoutX(x);
        label.setLayoutY(y);
        first = false;
    }

    private void addEmote(String code){
        ImageView emote = new ImageView(Constants.EMOJIS.get(code));
        getChildren().add(emote);
        emote.setFitWidth(24);
        emote.setFitHeight(24);

        if(x+24 >= getMaxWidth()){
            y += 28;
            x = 0;
        }

        emote.setLayoutX(x);
        emote.setLayoutY(y);
    }

    public void addFont(Font font){
        this.font = font;
    }

    private String getAuxText(String text){
        String aux = text;
        for(String code:Constants.EMOJIS.keySet()){
            aux = aux.replace(code, "!!##EMOTE##!!");
        }
        return aux;
    }

    private int getNumOfEmotes(String text){
        int result = 0;
        for(String key:Constants.EMOJIS.keySet()){
            if(text.contains(key))
                result++;
        }
        return result;
    }

}
