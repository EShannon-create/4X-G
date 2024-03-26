package com.evanshannon.x.model.buildings;

import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.jme3.scene.Node;

public abstract class Building {
    Player player;
    final Tile tile;

    protected Building(Player player, Tile tile) {
        this.tile = tile;
        tile.setBuilding(this);
        this.player = player;
    }

    public abstract Node getModel();
    public Player getOwner(){
        return player;
    }
}
