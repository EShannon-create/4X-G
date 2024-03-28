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
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

public class Farm extends Building{
    private static final int MAX_ROWS = 6;
    private int farmRows;
    public Farm(Tile tile){
        super(tile);
        farmRows = 1;
    }
    public static Node getModel(Texture texture, int farmRows){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();
        final float DISTANCE = 0.5f;

        Box box = new Box(1,2f/3f,1);
        Geometry geometry = new Geometry("Main",box);
        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        geometry.setMaterial(m);
        node.attachChild(geometry);

        Box box2 = new Box(1.1f,0.1f,1.1f);
        geometry = new Geometry("Roof 1",box2);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", TextureHandler.BLACK);
        geometry.setMaterial(m2);
        geometry.setLocalTranslation(-0.7f,0.85f,0);
        Quaternion q = new Quaternion();
        q.fromAngles(0,0,FastMath.PI/4);
        geometry.setLocalRotation(q);
        node.attachChild(geometry);

        geometry = new Geometry("Roof 1",box2);
        geometry.setMaterial(m2);
        geometry.setLocalTranslation(0.7f,0.85f,0);
        q.fromAngles(0,0,-FastMath.PI/4);
        geometry.setLocalRotation(q);
        node.attachChild(geometry);

        Quad quad = new Quad(FastMath.sqrt(2),FastMath.sqrt(2));
        geometry = new Geometry("Roof front",quad);
        geometry.setMaterial(m);
        Quaternion q2 = new Quaternion();
        q2.fromAngles(0,0,FastMath.PI/4f);
        geometry.setLocalRotation(q2);
        geometry.setLocalTranslation(0,-0.3125f,0.99f);
        node.attachChild(geometry);

        Quad quad2 = new Quad(FastMath.sqrt(2),FastMath.sqrt(2));
        geometry = new Geometry("Roof back",quad2);
        geometry.setMaterial(m);
        Quaternion q3 = new Quaternion();
        q3.fromAngles(0,FastMath.PI,FastMath.PI/4f);
        geometry.setLocalRotation(q3);
        geometry.setLocalTranslation(0,-0.3125f,-0.99f);
        node.attachChild(geometry);

        Node door = new Node();

        Box box3 = new Box(0.0625f,0.4f,0.0625f);
        geometry = new Geometry("Door",box3);
        Material m3 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m3.setTexture("DiffuseMap", TextureHandler.WHITE);
        geometry.setMaterial(m3);
        geometry.setLocalTranslation(0.3f,-0.2666f,1.0125f);
        door.attachChild(geometry);

        Box box4 = new Box(0.0625f,0.4f,0.0625f);
        geometry = new Geometry("Door",box4);
        geometry.setMaterial(m3);
        geometry.setLocalTranslation(-0.3f,-0.2666f,1.0125f);
        door.attachChild(geometry);

        Box box5 = new Box(0.2375f,0.0625f,0.0625f);
        geometry = new Geometry("Door",box5);
        geometry.setMaterial(m3);
        geometry.setLocalTranslation(0,0.07f,1.0125f);
        door.attachChild(geometry);

        geometry = new Geometry("Door",box5);
        geometry.setMaterial(m3);
        geometry.setLocalTranslation(0,-0.605f,1.0125f);
        door.attachChild(geometry);

        Box box6 = new Box(0.3875f,0.0625f,0.0625f);
        geometry = new Geometry("Door",box6);
        geometry.setMaterial(m3);
        geometry.setLocalTranslation(0,-0.25f,1.0125f);
        Quaternion q4 = new Quaternion();
        q4.fromAngles(0,0,FastMath.PI/4f);
        geometry.setLocalRotation(q4);
        door.attachChild(geometry);

        geometry = new Geometry("Door",box6);
        geometry.setMaterial(m3);
        geometry.setLocalTranslation(0,-0.25f,1.0125f);
        Quaternion q5 = new Quaternion();
        q5.fromAngles(0,0,-FastMath.PI/4f);
        geometry.setLocalRotation(q5);
        door.attachChild(geometry);

        node.attachChild(door);

        Node door2 = door.clone(true);
        door2.setLocalScale(0.75f);
        door2.setLocalTranslation(0,0.8f,0.253125f);

        node.attachChild(door2);

        for(int i = 0; i < farmRows; i++){
            Box farmBox = new Box(0.75f,0.0625f,0.125f);
            geometry = new Geometry("Farm",farmBox);
            Material m4= new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
            m4.setTexture("DiffuseMap", TextureHandler.FARM);
            geometry.setMaterial(m4);
            geometry.setLocalTranslation(0f,-0.6f,-1.5f-i*DISTANCE);
            node.attachChild(geometry);
        }

        node.setLocalScale(1f/6);
        node.setLocalTranslation(0.5f,0.135f,-0.5f+DISTANCE*farmRows/12);
        node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Node n = new Node();
        n.attachChild(node);

        return n;
    }
    @Override
    public Node getModel(){
        return getModel(getTexture(),farmRows);
    }
    public int getFarmRows(){
        return farmRows;
    }
    public boolean upgrade(){
        if(farmRows >= MAX_ROWS) return false;
        farmRows++;
        return true;
    }
}
