package com.evanshannon.x.model.buildings;

import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.jme3.scene.Node;

public class Mine extends Building{

    public Mine(Player player, Tile tile) {
        super(tile);
    }

    @Override
    public Node getModel() {
        return null;
    }
    @Override
    public String getCode(){
        return "Mine";
    }
}
