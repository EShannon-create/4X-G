package com.evanshannon.x.model.pieces;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.X;
import com.evanshannon.x.model.Player;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

public class Galley extends SeaPiece{
    public static Node getModel(Texture texture){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);




        Node n = new Node();
        n.attachChild(node);
        return n;
    }


    public Galley(Player player) {
        super(player);
    }

    @Override
    public Node getModel() {
        return null;
    }

    @Override
    int[][] moveMap(boolean onMove) {
        return new int[0][];
    }
}
