package com.evanshannon.x.model.buildings;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.TextureHandler;
import com.evanshannon.x.X;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class Barracks extends Building{
    public static Node getModel(Texture texture){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", texture == TextureHandler.BLACK ? TextureHandler.WHITE : TextureHandler.BLACK);

        Box box = new Box(1f,2f/3f,1f);
        Geometry g = new Geometry("Main",box);
        g.setMaterial(m);
        node.attachChild(g);

        Box tower = new Box(1f/3,1f,1f/3);
        g = new Geometry("Tower 1",tower);
        g.setMaterial(m);
        g.setLocalTranslation(1f,1f/3f,1f);
        node.attachChild(g);

        g = new Geometry("Tower 2",tower);
        g.setMaterial(m);
        g.setLocalTranslation(-1f,1f/3f,1f);
        node.attachChild(g);

        g = new Geometry("Tower 3",tower);
        g.setMaterial(m);
        g.setLocalTranslation(1f,1f/3f,-1f);
        node.attachChild(g);

        g = new Geometry("Tower 4",tower);
        g.setMaterial(m);
        g.setLocalTranslation(-1f,1f/3f,-1f);
        node.attachChild(g);

        Box railing = new Box(1f,1f/10f,1f/10f);
        g = new Geometry("Railing 1",railing);
        g.setMaterial(m2);
        g.setLocalTranslation(0,2f/3f,1f);
        node.attachChild(g);

        g = new Geometry("Railing 2",railing);
        g.setMaterial(m2);
        g.setLocalTranslation(0,2f/3f,-1f);
        node.attachChild(g);

        g = new Geometry("Railing 3",railing);
        g.setMaterial(m2);
        g.setLocalTranslation(1f,2f/3f,0f);
        Quaternion q = new Quaternion();
        q.fromAngles(0f, FastMath.HALF_PI,0f);
        g.setLocalRotation(q);
        node.attachChild(g);

        g = new Geometry("Railing 4",railing);
        g.setMaterial(m2);
        g.setLocalTranslation(-1f,2f/3f,0f);
        g.setLocalRotation(q);
        node.attachChild(g);

        node.setLocalScale(1f/4f);
        node.setLocalTranslation(0.5f,1f/6f,-0.5f);
        node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Node n = new Node();
        n.attachChild(node);
        return n;
    }
    @Override
    public Node getModel() {
        return getModel(getTexture());
    }
    public Barracks(Tile tile){
        super(tile);
    }
}
