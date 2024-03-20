package com.evanshannon.x.model;

import com.evanshannon.x.model.buildings.Building;
import com.evanshannon.x.model.pieces.Piece;

public class Tile {
    private static Octave octave = new Octave();

    private float height;
    private Piece piece = null;
    private Building building = null;
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
        if(this.piece != null) this.piece.onDestroy();
        this.piece = piece;
        piece.setLocation(x,y);
    }
    public boolean setBuilding(Building building){
        if(this.building != null) return false;
        this.building = building;
        return true;
    }
    public Piece getPiece(){
        return piece;
    }
    public Building getBuilding(){
        return building;
    }
    public boolean hasPiece(){
        return piece != null;
    }
    public boolean hasBuilding(){
        return building != null;
    }
    public void clearPiece(){
        this.piece.setLocation(Integer.MIN_VALUE,Integer.MIN_VALUE);//Error avoidance
        this.piece = null;
    }
}
