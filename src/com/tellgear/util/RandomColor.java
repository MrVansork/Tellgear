package com.tellgear.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomColor {

    private static List<String> used  = new ArrayList<>();
    private static String[] colors = {"#B40404", "#B43104", "#B45F04", "#B18904", "#AEB404", "#86B404", "#5FB404", "#31B404",
    "#04B404", "#04B45F", "#04B4AE", "#0489B1", "#045FB4", "#0431B4", "#3104B4", "#5F04B4", "#8904B1", "#B40486", "#B40431"};

    public static String getRandomColor(){
        if(used.size() >= colors.length){
            return reallyRandomColor();
        }else{
            String color = colors[new Random().nextInt(colors.length)];
            for(int i = 0; i < used.size(); i++){
                if(color.equals(used.get(i))){
                    color = getRandomColor();
                }
            }
            used.add(color);

            return color;
        }
    }


    private static String reallyRandomColor() {
        final Random random = new Random();
        final String[] letters = "0123456789ABCDEF".split("");
        String color = "#";
        for (int i = 0; i < 6; i++) {
            color += letters[Math.round(random.nextFloat() * 15)];
        }
        return color;
    }


}
