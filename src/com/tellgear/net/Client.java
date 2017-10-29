package com.tellgear.net;

import com.tellgear.model.User;
import com.tellgear.util.Constants;
import com.tellgear.util.Utilities;
import com.tellgear.view.LoginOverviewController;
import com.tellgear.view.SignUpOverviewController;
import com.tellgear.view.UserOverviewController;
import javafx.application.Platform;
import javafx.scene.image.Image;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{

    private Socket socket;
    public boolean running = false, connected = false;
    private History history;
    public ObjectInputStream in;
    public ObjectOutputStream out;

    private UserOverviewController uoc;
    private LoginOverviewController loc;
    private SignUpOverviewController soc;

    public Client(LoginOverviewController loc, SignUpOverviewController soc, UserOverviewController uoc) throws IOException {
        this.uoc = uoc;
        this.soc = soc;
        this.loc = loc;

        socket = new Socket(Constants.SERVER_ADDR, Constants.PORT);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        connected = true;
        running = true;
    }

    @Override
    public void run(){
        while(running){
            try {
                Message msg = (Message) in.readObject();

                if(msg.type.equals("message")){
                    if(msg.sender.equals(UserOverviewController.USERNAME)){
                        Platform.runLater(() -> uoc.consoleMe(Utilities.decrypt(msg.content)));
                    }else{
                        if(msg.recipient.equals(UserOverviewController.USERNAME)){
                            Platform.runLater(() -> uoc.consoleOther(Utilities.decrypt(msg.content), msg.sender, User.findUser(msg.sender).getColor()));
                        }else if(msg.recipient.equals("All")){
                            Platform.runLater(() -> uoc.consoleOther(Utilities.decrypt(msg.content), msg.sender, User.findUser(msg.sender).getColor()));
                        }
                    }
                }

                else if(msg.type.equals("login")){
                    if(msg.content.equals("TRUE")){
                        Platform.runLater(() -> {
                            loc.login();
                            uoc.show();
                        });
                    }
                    else if(msg.content.equals("FALSE:BAD_LOGIN")){
                        Platform.runLater(() -> loc.showAdvise("Username or password was wrong", "general"));
                    }
                    else if(msg.content.equals("FALSE:ON_LINE")){
                        Platform.runLater(() -> loc.showAdvise("Your client are already online", "general"));
                    }
                }

                else if(msg.type.equals("signup")){
                    if(msg.content.equals("TRUE")){
                        Platform.runLater(() -> {
                            soc.signUp();
                        });
                    }
                    else if(msg.content.equals("FALSE:EXISTS")){
                        Platform.runLater(() -> soc.showAdvise("That user already exists"));
                    }
                    else if(msg.content.equals("FALSE:ON_LINE")){
                        Platform.runLater(() -> soc.showAdvise("Your client are now online and already exists"));
                    }
                }

                else if(msg.type.equals("newuser")){
                    if(!User.exists(msg.content)){
                        new User(msg.content, new Image(getClass().getResourceAsStream("../../../res/img/user.png")));
                        Platform.runLater(() -> {
                            uoc.updateUsers();
                        });
                    }
                }
                else if(msg.type.equals("signout")){
                    Platform.runLater(() -> {
                        uoc.removeUser(msg.content);
                        if(!msg.content.equals(UserOverviewController.USERNAME))
                            uoc.consoleApp(msg.content+" se ha desconectado");
                    });
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(Message msg){
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("Outgoing : "+msg.toString());
        }
        catch (IOException ex) {
            System.out.println("Exception SocketClient send()");
        }
    }

    private void close() throws IOException {
        if(in != null)in.close();
        if(out != null)out.close();
        if(socket != null)socket.close();
    }


}
