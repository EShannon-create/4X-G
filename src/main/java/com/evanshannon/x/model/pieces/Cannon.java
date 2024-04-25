package com.evanshannon.x.model.pieces;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.X;
import com.evanshannon.x.TextureHandler;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.evanshannon.x.model.buildings.Barracks;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.*;
import com.jme3.texture.Texture;

public class Cannon extends LandPiece{
    private Node model = null;

    public Cannon(Player player) {
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

        n2.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        return n2;
    }
    @Override
    public Node getModel(){
        if(model == null) model = getModel(player.getTexture());
        return model;
    }

    @Override
    public int[][] moveMap(boolean onMove) {
        int[][] moves = new int[5][5];
        for(int i = 1; i < 4; i++){
            for(int j = 1; j < 4; j++){
                final int x = getX()+i-moves.length/2;
                final int y = getY()+j-moves.length/2;

                Tile t = X.getInstance().world.getAt(x,y,true);
                if(t.hasBuilding() && !(t.getBuilding() instanceof Barracks) && onMove) continue;
                if(!t.hasPiece() && t.isLand()) moves[i][j] = GOTO;
                if(t.isLand() && t.hasPiece() && t.getPiece().getPlayer() != getPlayer()){
                    moves[i][j] = GOTO;
                }
            }
        }
        Tile t;
        Tile t2;

        t = X.getInstance().world.getAt(getX()+1,getY(),true);
        t2 = X.getInstance().world.getAt(getX()+2,getY(),true);
        if(t.hasPiece() || t.hasWall() && t2.isLand()) moves[4][2] = JUMP;

        t = X.getInstance().world.getAt(getX()-1,getY(),true);
        t2 = X.getInstance().world.getAt(getX()-2,getY(),true);
        if(t.hasPiece() || t.hasWall() && t2.isLand()) moves[0][2] = JUMP;

        t = X.getInstance().world.getAt(getX(),getY()+1,true);
        t2 = X.getInstance().world.getAt(getX(),getY()+2,true);
        if(t.hasPiece() || t.hasWall() && t2.isLand()) moves[2][4] = JUMP;

        t = X.getInstance().world.getAt(getX(),getY()-1,true);
        t2 = X.getInstance().world.getAt(getX(),getY()-2,true);
        if(t.hasPiece() || t.hasWall() && t2.isLand()) moves[2][0] = JUMP;

        return moves;
    }
    @Override
    public String getCode(){
        return "Cannon";
    }
}
