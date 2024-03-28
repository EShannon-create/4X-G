package com.evanshannon.x;

import com.evanshannon.x.model.Octave;
import com.evanshannon.x.model.Perlin;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.evanshannon.x.model.pieces.General;
import com.evanshannon.x.model.pieces.Piece;
import com.jme3.texture.Texture;

import java.awt.image.BufferedImage;

public class Sandbox {
    public static void main(String[] args){
        ownershipTest2();
    }
    public static void ownershipTest2(){
        Tile t = new Tile(0,0);

        Player p1 = new Player("1", null);
        Player p2 = new Player("2", null);

        General g;

        g = new General(p1);
        t.addControl(g);
        IO.print("1. " + t.getOwner().getName());

        t.removeControl(g);
        if(t.getOwner() == null) IO.print("2. " + "No owner");
        else IO.print("2. " + t.getOwner().getName());

        g = new General(p2);
        t.addControl(g);
        IO.print("3. " + t.getOwner().getName());

        t.removeControl(g);
        if(t.getOwner() == null) IO.print("4. No owner");
        else IO.print("4. " + t.getOwner().getName());

        g = new General(p1);
        t.addControl(g);
        IO.print("5. " + t.getOwner().getName());

        g = new General(p2);
        t.addControl(g);
        IO.print("6. " + t.getOwner().getName());

        General g2 = new General(p2);
        t.addControl(g2);
        IO.print("7. " + t.getOwner().getName());

        t.removeControl(g);
        if(t.getOwner() == null) IO.print("8. " + "No owner");
        else IO.print("8. " + t.getOwner().getName());

        t.removeControl(g2);
        if(t.getOwner() == null) IO.print("9. " + "No owner");
        else IO.print("9. " + t.getOwner().getName());
    }
    public static void ownershipTest(){
        Tile t = new Tile(0,0);

        Player p1 = new Player("1", null);
        Player p2 = new Player("2", null);

        General g;

        g = new General(p1);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        g = new General(p2);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        g = new General(p2);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        g = new General(p1);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        g = new General(p1);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        for(Piece piece : t.getPieces()){
            System.out.print(piece.getPlayer().getName()+"\t");
        }
        IO.line();

        g = new General(p1);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        g = new General(p1);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        g = new General(p1);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        g = new General(p1);
        t.addControl(g);
        IO.print(t.getOwner().getName());

        for(Piece piece : t.getPieces()){
            System.out.print(piece.getPlayer().getName()+"\t");
        }
        IO.line();
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
