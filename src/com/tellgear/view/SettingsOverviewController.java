package com.tellgear.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class SettingsOverviewController {

    @FXML
    private ImageView avatar_64, avatar_32;

    @FXML
    private void initialize(){

    }

    @FXML
    public void loadImage(ActionEvent ae){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Avatar image");
        File defaultDirectory = new File("C:/");
        chooser.setInitialDirectory(defaultDirectory);
        File file = chooser.showOpenDialog(null);
        if(file != null){

        }

    }

}
