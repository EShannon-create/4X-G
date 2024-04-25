package com.evanshannon.x.model;

import com.evanshannon.x.X;
import com.evanshannon.x.model.buildings.Barracks;
import com.evanshannon.x.model.buildings.Building;
import com.evanshannon.x.model.buildings.Wall;
import com.evanshannon.x.model.pieces.LandPiece;
import com.evanshannon.x.model.pieces.Piece;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Tile {
    private static Octave octave = new Octave();

    private float height;
    private Piece piece = null;
    private Player cachedOwner = null;
    private Building building = null;
    private final int x;
    private final int y;
    private HashSet<Piece> pieces = new HashSet<>();

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

        if(building != null && building instanceof Barracks b && piece instanceof LandPiece p) b.addPiece(p);
        else {
            this.piece = piece;

            if(piece.getCommander() != null)
                piece.getPlayer().onMove(piece.getCommander());//I think it would be awful if moving into a barracks counted as a move
        }
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
    public boolean hasWall(){
        return building instanceof Wall;
    }
    public void clearPiece(){
        if(piece == null) return;
        this.piece.setLocation(Integer.MIN_VALUE,Integer.MIN_VALUE);//Error avoidance
        this.piece = null;
    }
    public Tile getNorth(){
        return X.getInstance().world.getAt(x,y+1,true);
    }
    public Tile getSouth(){
        return X.getInstance().world.getAt(x,y-1,true);
    }
    public Tile getEast(){
        return X.getInstance().world.getAt(x+1,y,true);
    }
    public Tile getWest(){
        return X.getInstance().world.getAt(x-1,y,true);
    }
    public Player getOwner(){
        if(pieces.size() == 0) return null;

        HashMap<Player,Integer> m = new HashMap<>();
        for(Piece piece : pieces){
            if(m.containsKey(piece.getPlayer())) m.put(piece.getPlayer(),m.get(piece.getPlayer())+1);
            else m.put(piece.getPlayer(),1);
        }
        //for(Player p : m.keySet()){
        //    System.out.print("."+p.getName() + " " + m.get(p) + "\t");
        //}
        //System.out.println();
        int highest = 0;
        Player hp = null;
        for(Player player : m.keySet()){
            if(m.get(player) > highest){
                hp = player;
                highest = m.get(player);
            }
        }
        //System.out.println("="+hp.getName());
        if(cachedOwner != null && m.get(cachedOwner) != null && highest == m.get(cachedOwner)){
            //System.out.println("Cache");
            return cachedOwner;
        }
        else{
            //System.out.println("Switch");
            cachedOwner = hp;
            return hp;
        }
    }
    public HashSet<Piece> getPieces(){
        return pieces;
    }
    public void addControl(Piece piece){
        pieces.add(piece);
    }
    public void removeControl(Piece piece){
        pieces.remove(piece);
    }
    public int[] getLocation(){
        return new int[]{x,y};
    }
    public boolean hasOtherPlayers(Player player){
        for(Piece p : pieces){
            if(p.getPlayer() != player) return true;
        }
        return false;
    }
}
