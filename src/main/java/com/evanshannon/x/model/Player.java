package com.evanshannon.x.model;

import com.evanshannon.x.MathLib;
import com.evanshannon.x.TextureHandler;
import com.evanshannon.x.model.buildings.Building;
import com.evanshannon.x.model.buildings.Flag;
import com.evanshannon.x.model.pieces.Commander;
import com.evanshannon.x.model.pieces.Piece;
import com.jme3.math.Vector3f;
import com.jme3.texture.Texture;

import java.util.ArrayList;
import java.util.HashSet;

public class Player {
    private final Texture texture;
    private final String name;
    private HashSet<Commander> moved;
    private int builds;
    private HashSet<Piece> pieces;
    private Vector3f location;
    private final ArrayList<Flag> flags = new ArrayList<>();
    private int flagIndex;
    private int factoryLag = 0;
    public static final int FACTORY_LAG = 5;
    public Player(String name, Texture texture){
        this.name = name;
        this.texture = texture;
        this.pieces = new HashSet<>();
        flagIndex = 0;
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
    public boolean beginTurn(){
        if(pieces.size() == 0) return false;
        if(factoryLag > 0){
            factoryLag--;
            return false;
        }
        return true;
    }
    public void endTurn(){
        moved = new HashSet<>();
        builds = findBuilds();
        checkPieceFeeding();
    }
    public boolean onBuild(){
        if(builds <= 0) return false;
        builds--;
        return true;
    }
    private int findBuilds(){
        return Building.getBuilds(this);
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
    public Vector3f getLocation(){
        return location;
    }
    public void setLocation(Vector3f location) {
        this.location = location;
    }
    public int countPieces(){
        int count = 0;
        for(Piece piece : pieces){
            count++;
        }
        return count;
    }
    public int getFood(){
        return Building.getFood(this);
    }
    public int getFoodSurplus(){
        return getFood()-countPieces();
    }
    public void checkPieceFeeding(){
        int pieces = countPieces();
        final int food = getFood();
        //caching the above because those operations can be expensive!

        while(food < pieces){
            final Piece toRemove = (Piece) this.pieces.toArray()[MathLib.roll(0,this.pieces.size())];
            this.pieces.remove(toRemove);
            toRemove.kill();
            pieces--;
        }
    }
    public boolean hasBuild(){
        return builds > 0;
    }
    public void registerFlag(Flag flag){
        this.flags.add(flag);
    }
    public Flag getFlag(int i){
        return flags.get(i%flags.size());
    }
    public void teleportToFlag(Flag flag){
        setLocation(flag.getLocation());
    }
    public Flag randomFlag(){
        return flags.size() < 2 ? flags.get(0) : flags.get(MathLib.roll(0,flags.size()-1));
    }
    public Flag nextFlag(){
        flagIndex++;
        return flags.get(flagIndex);
    }
    public Piece getChecks(){
        for(Piece p : pieces){
            if(!(p instanceof Commander c)) continue;
            if(!c.isChecked()) continue;
            if(p.hasAnyMove()) return p;

            p.kill();
            updateCommanderlessPieces();
        }
        return null;
    }
    public void updateCommanderlessPieces(){
        for(Piece p : pieces){
            if(p.getCommander() == null) p.findCommander();
        }
    }
    public void onFactoryBuild(){
        factoryLag = FACTORY_LAG;
    }
    public void nuke(){
        pieces = new HashSet<>();
    }
    public String getColor(){
        if(texture == TextureHandler.RED) return "Red";
        if(texture == TextureHandler.YELLOW) return "Yellow";
        if(texture == TextureHandler.GREEN) return "Green";
        if(texture == TextureHandler.CYAN) return "Cyan";
        if(texture == TextureHandler.BLUE) return "Blue";
        if(texture == TextureHandler.MAGENTA) return "Magenta";
        if(texture == TextureHandler.BLACK) return "Black";
        if(texture == TextureHandler.WHITE) return "White";

        return "";
    }
}
