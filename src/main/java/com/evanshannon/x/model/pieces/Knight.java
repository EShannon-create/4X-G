package com.evanshannon.x.model.pieces;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.X;
import com.evanshannon.x.TextureHandler;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class Knight extends LandPiece{
    private Node model = null;
    public static Node getModel(Texture texture){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);

        Node n = new Node();
        Geometry g;

        Cylinder cylinder = new Cylinder(4,samples,1.0f,0.5f,false);
        g = new Geometry("Base",cylinder);
        g.setMaterial(m);
        Quaternion r = new Quaternion();
        r.fromAngles(-FastMath.HALF_PI, 0,0);
        g.setLocalRotation(r);
        g.setLocalTranslation(0,0.25f,0);
        n.attachChild(g);

        Dome cone = new Dome(new Vector3f(0,0,0),2,samples,1.0f,false);
        g = new Geometry("Slant",cone);
        g.setMaterial(m);
        g.setLocalTranslation(0,0.5f,0);
        n.attachChild(g);

        Box box = new Box(0.5f,1.25f,0.5f);
        g = new Geometry("Body",box);
        g.setMaterial(m);
        g.setLocalTranslation(0,5f/3f+0.625f,0);
        n.attachChild(g);

        Box head = new Box(0.5f,0.25f,0.25f);
        g = new Geometry("Head",head);
        g.setMaterial(m);
        g.setLocalTranslation(0,2f + 8f/9f,0.75f);
        n.attachChild(g);

        Sphere sphere = new Sphere(samples, samples, 1f/8f);
        g = new Geometry("Left Eye",sphere);
        g.setMaterial(m);
        g.setLocalTranslation(-0.3f,3.25f,4f/9f);
        n.attachChild(g);
        g = new Geometry("Right Eye",sphere);
        g.setMaterial(m);
        g.setLocalTranslation(0.3f,3.25f,4f/9f);
        n.attachChild(g);

        Node hat = Pawn.getModel(texture);
        hat.setLocalScale(1f/5f,1f/5f,1f/5f);
        hat.setLocalTranslation(0,2f+8f/9f+2f/3f,0);
        n.attachChild(hat);

        n.setLocalScale(1f/3f,1f/3f,1f/3f);

        return n;
    }
    @Override
    public Node getModel() {
        if(model == null) model = getModel(TextureHandler.YELLOW);
        return model;
    }

    @Override
    public int[][] canMove() {
        return new int[][]{
                {NONE, GOTO, NONE, GOTO, NONE},
                {GOTO, NONE, NONE, NONE, GOTO},
                {NONE, NONE, NONE, NONE, NONE},
                {GOTO, NONE, NONE, NONE, GOTO},
                {NONE, GOTO, NONE, GOTO, NONE}
        };
    }
}
