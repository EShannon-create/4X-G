package com.evanshannon.x.model.pieces;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.X;
import com.evanshannon.x.view.TextureHandler;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.*;
import com.jme3.texture.Texture;

public class Cannon extends LandPiece{
    private Node model = null;
    public static Node getModel(Texture texture){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);

        Node n = new Node();
        Geometry g;

        Dome base = new Dome(new Vector3f(0,0,0),samples,samples,1f,false);
        g = new Geometry("Base",base);
        g.setMaterial(m);
        Quaternion r = new Quaternion();
        r.fromAngles(FastMath.PI,0,0);
        g.setLocalRotation(r);
        g.setLocalTranslation(0,0,0);
        n.attachChild(g);

        Dome shaft = new Dome(new Vector3f(0,0,0),2,samples,1f,false);
        g = new Geometry("Shaft",shaft);
        g.setMaterial(m);
        g.setLocalTranslation(0,0,0);
        g.setLocalScale(1,3f,1);
        n.attachChild(g);

        Cylinder bevel = new Cylinder(4,samples,0.5f,1.5f,true);
        g = new Geometry("Bevel",bevel);
        g.setMaterial(m);
        g.setLocalTranslation(0,2.25f,0);
        Quaternion r2 = new Quaternion();
        r2.fromAngles(FastMath.HALF_PI,0,0);
        g.setLocalRotation(r2);
        n.attachChild(g);

        Cylinder wheel = new Cylinder(4,samples,1f,0.25f,true);
        g = new Geometry("Wheel 1",wheel);
        g.setMaterial(m);
        g.setLocalTranslation(1,0,0);
        Quaternion r3 = new Quaternion();
        r3.fromAngles(0,FastMath.HALF_PI,0);
        g.setLocalRotation(r3);
        n.attachChild(g);

        g = new Geometry("Wheel 2",wheel);
        g.setMaterial(m);
        g.setLocalTranslation(-1,0,0);
        g.setLocalRotation(r3);
        n.attachChild(g);

        Quaternion r4 = new Quaternion();
        r4.fromAngles(2*FastMath.HALF_PI/3,0f,0f);
        n.setLocalRotation(r4);

        n.setLocalTranslation(0,1f,0);

        Node n2 = new Node();
        n2.attachChild(n);
        n2.setLocalScale(1f/3,1f/3,1f/3);

        return n2;
    }
    @Override
    public Node getModel() {
        if(model == null) model = getModel(TextureHandler.YELLOW);
        return model;
    }
}
