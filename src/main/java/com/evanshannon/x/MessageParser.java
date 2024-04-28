package com.evanshannon.x;

import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.evanshannon.x.model.buildings.*;
import com.evanshannon.x.model.pieces.*;
import com.jme3.math.Vector3f;

import java.util.Arrays;

public class MessageParser {
    public static void parse(String command, String[] args){
        switch(command){
            case "nuke" -> {
                X.getInstance().nuke();
            }
            case "seed" -> {
                X.seed = Integer.parseInt(args[0]);
                IO.print("Seed: " + X.seed);
            }
            case "tp" -> {
                final Player player = X.getInstance().parsePlayer(Integer.parseInt(args[0]));
                final int x = Integer.parseInt(args[1]);
                final int y = Integer.parseInt(args[2]);

                IO.print("Moving player from " + player.getLocation());
                player.setLocation(new Vector3f(x,3f,y));
                X.getInstance().updatePlayerPositions();
                IO.print("Moved player to " + player.getLocation());
            }
            case "build" -> {
                final int x = Integer.parseInt(args[1]);
                final int y = Integer.parseInt(args[2]);
                IO.print("Building at ("+x+","+y+")");

                final Tile tile = X.getInstance().world.getAt(x,y,true);
                final Player player = X.getInstance().parsePlayer(Integer.parseInt(args[3]));

                switch(args[0]){
                    case "Factory" -> {
                        new Factory(tile);
                        assert player != null;
                        player.onFactoryBuild();
                    }
                    case "Farm" -> {
                        if(tile.hasBuilding() && tile.getBuilding() instanceof Farm farm) farm.upgrade();
                        else new Farm(tile);
                    }
                    case "Flag" -> {
                        assert player != null;
                        new Flag(tile,player);
                    }
                    case "Wall" -> {
                        new Wall(tile);
                    }
                    case "Barracks" -> {
                        new Barracks(tile);
                    }
                    case "Amphibian" -> {
                        tile.setPiece(new Transport(player));
                    }
                    case "Bishop" -> {
                        tile.setPiece(new Bishop(player));
                    }
                    case "Cannon" -> {
                        tile.setPiece(new Cannon(player));
                    }
                    case "General" -> {
                        tile.setPiece(new General(player));
                    }
                    case "Knight" -> {
                        tile.setPiece(new Knight(player));
                    }
                    case "Lieutenant" -> {
                        tile.setPiece(new Lieutenant(player));
                    }
                    case "Pawn" -> {
                        tile.setPiece(new Pawn(player));
                    }
                    case "Rook" ->{
                        tile.setPiece(new Rook(player));
                    }
                }
            }
            case "move" -> {
                final int fromX = Integer.parseInt(args[0]);
                final int fromY = Integer.parseInt(args[1]);
                final int toX = Integer.parseInt(args[2]);
                final int toY = Integer.parseInt(args[3]);

                final Piece piece = X.getInstance().world.getAt(fromX,fromY,false).getPiece();
                piece.move(toX, toY,false);
                IO.print("Moved piece from (" + fromX + "," + fromY + ") to (" + toX + "," + toY + ")");
            }
            case "jump" -> {
                final int fromX = Integer.parseInt(args[0]);
                final int fromY = Integer.parseInt(args[1]);
                final int toX = Integer.parseInt(args[2]);
                final int toY = Integer.parseInt(args[3]);

                final Piece piece = X.getInstance().world.getAt(fromX,fromY,false).getPiece();
                piece.jump(toX, toY,false);
                IO.print("Jumped piece from (" + fromX + "," + fromY + ") to (" + toX + "," + toY + ")");
            }
            case "deploy" -> {
                final int barracksX = Integer.parseInt(args[0]);
                final int barracksY = Integer.parseInt(args[1]);
                final int toX = Integer.parseInt(args[2]);
                final int toY = Integer.parseInt(args[3]);
                final int index = Integer.parseInt(args[4]);

                final Barracks barracks = (Barracks)(X.getInstance().world.getAt(barracksX,barracksY,false).getBuilding());
                final Piece piece = barracks.getPiece(index);
                piece.move(toX,toY,false);
                IO.print("Deployed piece from (" + barracksX + "," + barracksY + ") to (" + toX + "," + toY + ")");
            }
        }
    }
    public static String move(final int fromX, final int fromY, final int toX, final int toY){
        return "move " + fromX + " " + fromY + " " + toX + " " + toY;
    }
    public static String deploy(final int fromX, final int fromY, final int toX, final int toY, final int index){
        return "deploy " + fromX + " " + fromY + " " + toX + " " + toY + " " + index;
    }
    public static String jump(final int fromX, final int fromY, final int toX, final int toY){
        return "jump " + fromX + " " + fromY + " " + toX + " " + toY;
    }
    public static String tp(final Player player, final int x, final int y){
        return "tp " + X.getInstance().getPlayerIndex(player) + " " + x + " " + y;
    }
    public static String build(Building b, final int x, final int y){

        final String s;
        if(b instanceof Factory) s = "Factory";
        else if(b instanceof Farm) s = "Farm";
        else if(b instanceof Flag) s = "Flag";
        else if(b instanceof Wall) s = "Wall";
        else if(b instanceof Barracks) s = "Barracks";
        else s = "";

        return "build " + s + " " + x + " " + y + " " + X.getInstance().getPlayerIndex(b.getOwner());
    }
    public static String build(Piece piece, final int x, final int y){
        return "build " + piece.getCode() + " " + x + " " + y + " " + X.getInstance().getPlayerIndex(piece.getPlayer());
    }
    public static String seed(int seed){
        return "seed " + seed;
    }
    public static String nuke(){
        return "nuke";
    }
    public static void parse(String message){
        String[] splitMessage = message.split( " ");
        String[] args = splitMessage.length > 1 ? Arrays.copyOfRange(splitMessage,1,splitMessage.length) : new String[]{};
        String command = splitMessage[0];
        MessageParser.parse(command,args);
    }
}
