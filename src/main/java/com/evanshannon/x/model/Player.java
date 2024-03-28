package com.evanshannon.x.model;

import com.evanshannon.x.model.pieces.Commander;
import com.evanshannon.x.model.pieces.Piece;
import com.jme3.texture.Texture;

import java.util.HashSet;

public class Player {
    private final Texture texture;
    private final String name;
    private HashSet<Commander> moved;
    private int builds;
    private HashSet<Piece> pieces;

    public Player(String name, Texture texture){
        this.name = name;
        this.texture = texture;
        this.pieces = new HashSet<>();
        endTurn();
    }
    public String getName(){
        return name;
    }
    public Texture getTexture(){
        return texture;
    }
    public boolean canMove(Piece piece){
        return !moved.contains(piece.getCommander());
    }
    public void onMove(Commander commander){
        moved.add(commander);
    }
    public void endTurn(){
        moved = new HashSet<>();
        builds = 2;
    }
    public boolean onBuild(){
        if(builds <= 0) return false;
        builds--;
        return true;
    }
    public int getBuilds(){
        return builds;
    }
    public void registerPiece(Piece piece){
        pieces.add(piece);
    }
    public HashSet<Piece> getPieces(){
        return pieces;
    }
    public void unregisterPiece(Piece piece){
        pieces.remove(piece);
    }
}
