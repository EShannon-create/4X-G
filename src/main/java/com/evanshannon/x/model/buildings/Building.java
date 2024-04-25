package com.evanshannon.x.model.buildings;

import com.evanshannon.x.TextureHandler;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

import java.util.HashSet;

public abstract class Building {
    private static final HashSet<Building> ALL_BUILDINGS = new HashSet<>();
    private static final int BUILDS_PER_FACTORY = 3;
    final Tile tile;

    protected Building(Tile tile) {
        this.tile = tile;
        tile.setBuilding(this);
        registerBuilding(this);
    }

    public abstract Node getModel();
    public Player getOwner(){
        return tile.getOwner();
    }
    public Texture getTexture(){
        Player owner = getOwner();
        if(owner == null) return TextureHandler.WHITE;
        else return owner.getTexture();
    }
    public static void registerBuilding(Building building){
        ALL_BUILDINGS.add(building);
    }
    public static void unregisterBuilding(Building building){
        ALL_BUILDINGS.remove(building);
    }
    public static int getFood(Player player){
        int food = 0;
        for(Building building : ALL_BUILDINGS){
            if(building instanceof Farm farm && building.getOwner() == player){
                food += farm.getFarmRows();
            }
        }
        return food;
    }
    public static int getBuilds(Player player){
        int builds = 0;
        for(Building building : ALL_BUILDINGS){
            if(building instanceof Factory && building.getOwner() == player){
                builds += BUILDS_PER_FACTORY;
            }
        }
        return builds;
    }
    public abstract String getCode();
}
