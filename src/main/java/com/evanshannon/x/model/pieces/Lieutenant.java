package com.evanshannon.x.model.pieces;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.X;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Torus;
import com.jme3.texture.Texture;

public class Lieutenant extends LandPiece{
    private Node model = null;

    public Lieutenant(Player player) {
        super(player);
    }

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

        cylinder = new Cylinder(4,samples,0.5f,0.5f,false);
        g = new Geometry("Neck",cylinder);
        g.setMaterial(m);
        g.setLocalRotation(r);
        g.setLocalTranslation(0,3.25f,0);
        n.attachChild(g);

        g = new Geometry("Collar",collar);
        g.setMaterial(m);
        g.setLocalRotation(r2);
        g.setLocalScale(1,3f,1);
        g.setLocalTranslation(0,4f,0);
        n.attachChild(g);

        Dome head = new Dome(new Vector3f(0,0,0),samples,samples,2f/3f,false);
        g = new Geometry("Head",head);
        g.setMaterial(m);
        g.setLocalTranslation(0,4f,0);
        n.attachChild(g);

        Torus crown = new Torus(samples, samples, 0.075f,0.5f);
        g = new Geometry("Crown",crown);
        g.setMaterial(m);
        g.setLocalTranslation(0,4.5f,0);
        g.setLocalRotation(r);
        n.attachChild(g);

        Sphere hat = new Sphere(samples, samples, 0.075f);
        g = new Geometry("Hat",hat);
        g.setMaterial(m);
        g.setLocalTranslation(0,4+2f/3f,0);
        n.attachChild(g);

        final int amount = 12;
        for(int i = 0; i < amount; i++){
            float distance = 0.5f;

            float angle = FastMath.TWO_PI * i / amount;
            float x = distance * FastMath.cos(angle);
            float z = distance * FastMath.sin(angle);

            g = new Geometry("Bulb " + i, hat);
            g.setMaterial(m);
            g.setLocalTranslation(x,4.6f,z);
            n.attachChild(g);
        }


        n.setLocalScale(1f/3,1f/3,1f/3);
        n.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        return n;
    }
    @Override
    public Node getModel(){
        if(model == null) model = getModel(player.getTexture());
        return model;
    }
    @Override
    public int[][] canMove() {
        int[][] moves = new int[17][17];
        for(int i = 1; i < moves.length/2; i++){
            final int x = getX();
            final int y = getY()+i;
            Tile t = X.getInstance().world.getAt(x,y,true);
            if(t.hasPiece() || t.isWater()) break;
            moves[moves.length/2][moves.length/2+i] = GOTO;
        }
        for(int i = 1; i < moves.length/2; i++){
            final int x = getX();
            final int y = getY()-i;
            Tile t = X.getInstance().world.getAt(x,y,true);
            if(t.hasPiece() || t.isWater()) break;
            moves[moves.length/2][moves.length/2-i] = GOTO;
        }
        for(int i = 1; i < moves.length/2; i++){
            final int x = getX()+i;
            final int y = getY();
            Tile t = X.getInstance().world.getAt(x,y,true);
            if(t.hasPiece() || t.isWater()) break;
            moves[moves.length/2+i][moves.length/2] = GOTO;
        }
        for(int i = 1; i < moves.length/2; i++){
            final int x = getX()-i;
            final int y = getY();
            Tile t = X.getInstance().world.getAt(x,y,true);
            if(t.hasPiece() || t.isWater()) break;
            moves[moves.length/2-i][moves.length/2] = GOTO;
        }
        for(int i = 1; i < moves.length/2; i++){
            final int x = getX()+i;
            final int y = getY()+i;
            Tile t = X.getInstance().world.getAt(x,y,true);
            if(t.hasPiece() || t.isWater()) break;
            moves[moves.length/2+i][moves.length/2+i] = GOTO;
        }
        for(int i = 1; i < moves.length/2; i++){
            final int x = getX()-i;
            final int y = getY()-i;
            Tile t = X.getInstance().world.getAt(x,y,true);
            if(t.hasPiece() || t.isWater()) break;
            moves[moves.length/2-i][moves.length/2-i] = GOTO;
        }
        for(int i = 1; i < moves.length/2; i++){
            final int x = getX()+i;
            final int y = getY()-i;
            Tile t = X.getInstance().world.getAt(x,y,true);
            if(t.hasPiece() || t.isWater()) break;
            moves[moves.length/2+i][moves.length/2-i] = GOTO;
        }
        for(int i = 1; i < moves.length/2; i++){
            final int x = getX()-i;
            final int y = getY()+i;
            Tile t = X.getInstance().world.getAt(x,y,true);
            if(t.hasPiece() || t.isWater()) break;
            moves[moves.length/2-i][moves.length/2+i] = GOTO;
        }

        return moves;
    }
}
