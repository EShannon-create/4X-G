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

public class General extends LandPiece implements Commander{
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

        Dome collar = new Dome(new Vector3f(0,0,0),2,samples,2f/3f,false);
        g = new Geometry("Collar",collar);
        g.setMaterial(m);
        Quaternion r2 = new Quaternion();
        r2.fromAngles(FastMath.PI,0,0);
        g.setLocalRotation(r2);
        g.setLocalScale(1,0.5f,1);
        g.setLocalTranslation(0,3f,0);
        n.attachChild(g);

        Dome collarTop = new Dome(new Vector3f(0,0,0),2,samples,2f/3f,false);
        g = new Geometry("Collar Top",collarTop);
        g.setMaterial(m);
        g.setLocalScale(1,0.5f,1);
        g.setLocalTranslation(0,3f,0);
        n.attachChild(g);

        cylinder = new Cylinder(4,samples,0.5f,0.75f,false);
        g = new Geometry("Neck",cylinder);
        g.setMaterial(m);
        g.setLocalRotation(r);
        g.setLocalTranslation(0,3.375f,0);
        n.attachChild(g);

        g = new Geometry("Collar",collar);
        g.setMaterial(m);
        g.setLocalRotation(r2);
        g.setLocalScale(1,3f,1);
        g.setLocalTranslation(0,4.125f,0);
        n.attachChild(g);

        cylinder = new Cylinder(4,samples,2f/3f,0.5f,true);
        g = new Geometry("Head",cylinder);
        g.setMaterial(m);
        g.setLocalRotation(r);
        g.setLocalTranslation(0,4.375f,0);
        n.attachChild(g);

        Torus crown = new Torus(samples, samples, 0.075f,0.5f);
        g = new Geometry("Crown",crown);
        g.setMaterial(m);
        g.setLocalTranslation(0,4.625f,0);
        g.setLocalRotation(r);
        n.attachChild(g);

        Box box = new Box(0.075f,0.35f,0.075f);
        g = new Geometry("Hat",box);
        g.setMaterial(m);
        g.setLocalTranslation(0,4+2f/3f+0.175f+0.125f,0);
        n.attachChild(g);

        box = new Box(0.35f,0.075f,0.075f);
        g = new Geometry("Hat 2",box);
        g.setMaterial(m);
        g.setLocalTranslation(0,4+2f/3f+0.175f+0.125f,0);
        n.attachChild(g);

        Sphere ball = new Sphere(samples, samples, 0.075f);

        final int amount = 12;
        for(int i = 0; i < amount; i++){
            float distance = 0.5f;

            float angle = FastMath.TWO_PI * i / amount;
            float x = distance * FastMath.cos(angle);
            float z = distance * FastMath.sin(angle);

            g = new Geometry("Bulb " + i, ball);
            g.setMaterial(m);
            g.setLocalTranslation(x,4.6f+0.125f,z);
            n.attachChild(g);
        }


        n.setLocalScale(1f/3,1f/3,1f/3);

        return n;
    }
    @Override
    public Node getModel() {
        if(model == null) model = getModel(TextureHandler.YELLOW);
        return model;
    }
}
