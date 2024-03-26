package com.evanshannon.x.model.buildings;

import com.evanshannon.x.TextureHandler;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

public abstract class Building {
    final Tile tile;

    protected Building(Tile tile) {
        this.tile = tile;
        tile.setBuilding(this);
    }

    public abstract Node getModel();
    public Player getOwner(){
        return tile.getOwner();
    }
    Texture getTexture(){
        Player owner = getOwner();
        if(owner == null) return TextureHandler.WHITE;
        else return owner.getTexture();
    }
}
