package com.evanshannon.x;

import com.evanshannon.x.model.Octave;
import com.evanshannon.x.model.Perlin;
import com.evanshannon.x.model.pieces.Piece;

import java.awt.image.BufferedImage;

public class Sandbox {
    public static void main(String[] args){
        combinationTest();
    }
    public static void combinationTest(){
        int[][] a = {{0,1,0,1,0},{1,0,1,0,1},{0,1,0,1,0},{1,0,1,0,1},{0,1,0,1,0}};
        int[][] b = {{1,1,1,1,1},{0,0,0,0,0},{1,1,1,1,1},{0,0,0,0,0},{1,1,1,1,1}};
        int[][] c = {{0,1,0,1,0},{0,1,0,1,0},{0,1,0,1,0},{0,1,0,1,0},{0,1,0,1,0}};

        int[][] d = Piece.combineMoveSets(a,b);
        for(int i = 0; i < d.length; i++){
            for(int j = 0; j < d[i].length; j++){
                System.out.print(d[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();

        int[][] e = Piece.combineMoveSets(a,c);
        for(int i = 0; i < e.length; i++){
            for(int j = 0; j < e[i].length; j++){
                System.out.print(e[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();

        int[][] f = Piece.combineMoveSets(d,e);
        for(int i = 0; i < f.length; i++){
            for(int j = 0; j < f[i].length; j++){
                System.out.print(f[i][j] + "\t");
            }
            System.out.println();
        }
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
