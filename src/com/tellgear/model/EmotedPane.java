package com.tellgear.model;

import com.tellgear.util.Constants;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EmotedPane extends Pane {

    private String text;
    private Font font;

    public EmotedPane(String text){
        this.text = text;
    }

    public void init(){
        checkText(text);
    }

    private void checkText(String text){
        String aux = getAuxText(text);
        addText(aux.replaceAll("!!##EMOTE##!!", "\t"));
        double x = 0, y = 0;
        for(int i = 0; i < getNumOfEmotes(text); i++){
            x+= getWidthOfText(aux.split("!!##EMOTE##!!")[i])+4;
            if(x>getMaxWidth()){
                x = 0;
                y+= 28;
            }
            addEmote(text.split(aux.split("!!##EMOTE##!!")[i]+"|"+aux.split("!!##EMOTE##!!")[i+1])[1], x,y);
        }

    }

    private void addText(String text){
        Label label = new Label(text);
        label.setMaxWidth(getMaxWidth());
        label.setWrapText(true);
        getChildren().add(label);
        label.setFont(font);
        label.setPadding(new Insets(3, 3, 3, 3));

        applyCss();
        layout();
    }

    private void addEmote(String code, double x, double y){
        ImageView emote = new ImageView(Constants.EMOJIS.get(code));
        getChildren().add(emote);
        emote.setFitWidth(24);
        emote.setFitHeight(24);

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

    private double getWidthOfText(String text){
        Text label = new Text(text);
        label.setFont(font);
        label.applyCss();
        return label.getLayoutBounds().getWidth();
    }

}
