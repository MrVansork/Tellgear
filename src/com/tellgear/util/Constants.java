package com.tellgear.util;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;

public class Constants {

    public static final String VERSION = "0.0.01";
    public static final String SECRET_KEY = "12345";

    public static final String SERVER_ADDR = "localhost";//"192.168.0.104";
    public static final int PORT = 4097;

    public static final Font MSG_FONT = Font.font("Amble CN", FontWeight.NORMAL, 14);
    public static final Font DATE_FONT = Font.font("Amble CN", FontWeight.LIGHT, 10);
    public static final Font NAME_FONT = Font.font("Amble CN", FontWeight.BOLD, 14);

    public static final HashMap<String, Image> EMOJIS = new HashMap<String, Image>();

}
