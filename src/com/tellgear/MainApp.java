package com.tellgear;

import com.tellgear.net.Client;
import com.tellgear.net.Message;
import com.tellgear.view.LoginOverviewController;
import com.tellgear.view.SignUpOverviewController;
import com.tellgear.view.UserOverviewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.HashMap;

public class MainApp extends Application{

    private Stage primaryStage;
    public Client client;

    private HashMap<String, Parent> scenes = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Tell Gear");
        this.primaryStage.setResizable(false);
        //this.primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("../../res/icon.png")));

        primaryStage.setOnCloseRequest(event -> {
            if(client.connected){
                client.send(new Message("message", UserOverviewController.USERNAME, "!!##quit", "SERVER"));
                client.stop();
            }
        });

        initLogin();
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void initLogin() {
        try {
            // Load login overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginOverview.fxml"));
            scenes.put("login", loader.load());

            LoginOverviewController loc = loader.getController();
            loc.setMainApp(this);

            // Load signup overview.
            loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SignUpOverview.fxml"));
            scenes.put("signup", loader.load());

            SignUpOverviewController soc = loader.getController();
            soc.setMainApp(this);

            // Load user overview.
            loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UserOverview.fxml"));
            scenes.put("user", loader.load());

            UserOverviewController uoc = loader.getController();
            uoc.setMainApp(this);
            //Init socket client

            client = new Client(loc, soc, loader.getController());
            client.start();

            // Set person overview into the center of root layout.
            primaryStage.setScene(new Scene(scenes.get("login")));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(String key){
        primaryStage.getScene().setRoot(scenes.get(key));
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
