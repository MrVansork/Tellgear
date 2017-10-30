package com.tellgear.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;

public class Utilities {

    public static String crypt(String text){
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(Constants.SECRET_KEY.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = text.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static String decrypt(String text){
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(text.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(Constants.SECRET_KEY.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static void loadEmojis(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Utilities.class.getResource("../../../res/img/emoji/emoji.cnf").getFile()));

            String line;
            while((line=br.readLine()) != null){
                Constants.EMOJIS.put(line.split("=")[0], new Image(Utilities.class.getResource("../../../res/img/emoji/"+line.split("=")[1]).toExternalForm()));
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
