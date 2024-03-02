package com.evanshannon.x;

import com.evanshannon.x.model.Octave;
import com.evanshannon.x.model.Perlin;

import java.awt.image.BufferedImage;

public class Sandbox {
    public static void main(String[] args){
        perlinTest();
        octaveTest();
        landTest();
    }
    public static void landTest(){
        final float threshold = 0.0f;

        int width = 1920;
        int height = 1080;

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Octave octave = new Octave();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                final float value = octave.at(x*0.1f,y*0.1f);
                int color = value < threshold ? 0x0000AA : 0x66FF88;
                image.setRGB(x,y,color);
            }
        }
        IO.saveImage(image,"land");
    }
    public static void octaveTest(){
        int width = 1920;
        int height = 1080;

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Octave octave = new Octave();

        float highest = 0f;
        float lowest = 0f;

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                final float value = (octave.at(x*0.1f,y*0.1f)+1)/2;
                if(value > highest) highest = value;
                if(value < lowest) lowest = value;
                int c = (int)(255*value);
                int color = c*256*256+c*256+c;
                image.setRGB(x,y,color);
            }
        }

        IO.print("Lowest: " + lowest);
        IO.print("Highest: " + highest);
        IO.saveImage(image,"octave");
    }
    public static void perlinTest(){
        int width = 1920;
        int height = 1080;

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Perlin perlin = new Perlin();

        float highest = 0f;
        float lowest = 0f;

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                final float value = (perlin.at(x*0.1f,y*0.1f)+1)/2;
                if(value > highest) highest = value;
                if(value < lowest) lowest = value;
                int c = (int)(255*value);
                int color = c*256*256+c*256+c;
                image.setRGB(x,y,color);
            }
        }

        IO.print("Lowest: " + lowest);
        IO.print("Highest: " + highest);
        IO.saveImage(image,"perlin");
    }
}
