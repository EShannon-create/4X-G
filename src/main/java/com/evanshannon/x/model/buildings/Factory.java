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
import com.jme3.scene.shape.Cylinder;
import com.jme3.texture.Texture;

public class Factory extends Building {
    public static Node getModel(Texture texture){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Box box = new Box(1f,2f/3f,2f/3f);
        Geometry g = new Geometry("Building",box);
        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        g.setMaterial(m);
        node.attachChild(g);

        Cylinder c = new Cylinder(4, 24, 0.2f,0.5f,true);
        g = new Geometry("Smokestack",c);
        g.setMaterial(m);
        Quaternion q = new Quaternion();
        q.fromAngles(FastMath.HALF_PI,0f,0f);
        g.setLocalRotation(q);
        g.setLocalTranslation(0.7f,2f/3f+0.25f,0f);
        node.attachChild(g);

        c = new Cylinder(4, 24, 0.2f,0.5f,true);
        g = new Geometry("Smokestack",c);
        g.setMaterial(m);
        g.setLocalRotation(q);
        g.setLocalTranslation(0.2f,2f/3f+0.25f,0f);
        node.attachChild(g);

        c = new Cylinder(4, 24, 0.2f,4f/3f+0.125f,true);
        g = new Geometry("Window",c);
        m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", TextureHandler.GLASS);
        g.setMaterial(m);
        g.setShadowMode(RenderQueue.ShadowMode.Off);
        g.setLocalTranslation(-2f/3f,1f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4, 24, 0.2f,4f/3f+0.125f,true);
        g = new Geometry("Window",c);
        m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", TextureHandler.GLASS);
        g.setMaterial(m);
        g.setShadowMode(RenderQueue.ShadowMode.Off);
        g.setLocalTranslation(-1f/6f,1f/3f,0f);
        node.attachChild(g);

        node.setLocalScale(1f/5f);
        node.setLocalTranslation(0.5f,2f/15f,-0.5f);

        Node n = new Node();
        n.attachChild(node);
        n.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        return n;
    }
    public Factory(Player player, Tile tile){
        super(player,tile);
    }
    @Override
    public Node getModel() {
        return getModel(player.getTexture());
    }
}
