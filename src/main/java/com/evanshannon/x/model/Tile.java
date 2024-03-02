package com.evanshannon.x.model;

import com.evanshannon.x.model.pieces.Piece;

public class Tile {
    private static Octave octave = new Octave();

    private float height;
    private Piece piece = null;
    private final int x;
    private final int y;

    public boolean isWater(){
        return height < 0;
    }
    public boolean isLand(){
        return !isWater();
    }
    public Tile(int x, int y){
        height = octave.at(x,y);
        this.x = x;
        this.y = y;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
        piece.setLocation(x,y);
    }
    public Piece getPiece(){
        return piece;
    }
    public boolean hasPiece(){
        return piece != null;
    }
}
