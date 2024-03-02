package com.evanshannon.x;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IO {
    public static void saveImage(BufferedImage image, String name){
        File out = new File(name+".png");
        try{
            javax.imageio.ImageIO.write(image,"png",out);
        }
        catch(IOException e){
            System.out.println("Failed to write image");
        }
    }
    public static void log(String... s){
        print(s);
        line();
    }
    public static void print(String... s){
        for (String value : s) {
            System.out.println(value);
        }
    }
    public static void line(){
        System.out.println();
    }
}
