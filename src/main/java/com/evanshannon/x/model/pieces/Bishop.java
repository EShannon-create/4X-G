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
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class Bishop extends LandPiece{
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

        cylinder = new Cylinder(4,samples,0.5f,2f,false);
        g = new Geometry("Body",cylinder);
        g.setMaterial(m);
        g.setLocalRotation(r);
        g.setLocalTranslation(0,1f+0.5f+0.5f,0);
        n.attachChild(g);

        cylinder = new Cylinder(4,samples,0.75f,0.125f,true);
        g = new Geometry("Collar",cylinder);
        g.setMaterial(m);
        g.setLocalRotation(r);
        g.setLocalTranslation(0,2f+8f/9f-0.25f,0);
        n.attachChild(g);

        cone = new Dome(new Vector3f(0,0,0),samples,samples,2f/3f,false);
        g = new Geometry("Slant 2",cone);
        g.setMaterial(m);
        Quaternion r2 = new Quaternion();
        r2.fromAngles(FastMath.PI,0,0);
        g.setLocalScale(1f,1f,1f);
        g.setLocalRotation(r2);
        g.setLocalTranslation(0,2.5f+8f/9f,0);
        n.attachChild(g);

        cone = new Dome(new Vector3f(0,0,0),2,samples,2f/3f,false);
        g = new Geometry("Top",cone);
        g.setMaterial(m);
        g.setLocalScale(1f,5f/3f,1f);
        g.setLocalTranslation(0,2.5f+8f/9f,0);
        n.attachChild(g);

        Sphere sphere = new Sphere(samples, samples, 1f/3f);
        g = new Geometry("Head",sphere);
        g.setMaterial(m);
        g.setLocalTranslation(0,2.5f+8f/9f+2.5f/3f,0);
        n.attachChild(g);

        n.setLocalScale(1f/3,1f/3,1f/3);

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
                {GOTO,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,GOTO},
                {NONE,GOTO,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,GOTO,NONE},
                {NONE,NONE,GOTO,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,GOTO,NONE,NONE},
                {NONE,NONE,NONE,GOTO,NONE,NONE,NONE,NONE,NONE,NONE,NONE,GOTO,NONE,NONE,NONE},
                {NONE,NONE,NONE,NONE,GOTO,NONE,NONE,NONE,NONE,NONE,GOTO,NONE,NONE,NONE,NONE},
                {NONE,NONE,NONE,NONE,NONE,GOTO,NONE,NONE,NONE,GOTO,NONE,NONE,NONE,NONE,NONE},
                {NONE,NONE,NONE,NONE,NONE,NONE,GOTO,NONE,GOTO,NONE,NONE,NONE,NONE,NONE,NONE},
                {NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE},//This is the middle :)
                {NONE,NONE,NONE,NONE,NONE,NONE,GOTO,NONE,GOTO,NONE,NONE,NONE,NONE,NONE,NONE},
                {NONE,NONE,NONE,NONE,NONE,GOTO,NONE,NONE,NONE,GOTO,NONE,NONE,NONE,NONE,NONE},
                {NONE,NONE,NONE,NONE,GOTO,NONE,NONE,NONE,NONE,NONE,GOTO,NONE,NONE,NONE,NONE},
                {NONE,NONE,NONE,GOTO,NONE,NONE,NONE,NONE,NONE,NONE,NONE,GOTO,NONE,NONE,NONE},
                {NONE,NONE,GOTO,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,GOTO,NONE,NONE},
                {NONE,GOTO,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,GOTO,NONE},
                {GOTO,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,NONE,GOTO}
        };
    }
}
