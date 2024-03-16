package com.evanshannon.x.model.pieces;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.X;
import com.evanshannon.x.TextureHandler;
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
import com.jme3.scene.shape.*;
import com.jme3.texture.Texture;

public class General extends LandPiece implements Commander{
    private Node model = null;
    private Piece[] connectedPieces = new Piece[MAX_CONNECTED];
    private int[] bounds = new int[4];

    public General(Player player) {
        super(player);
        X.getInstance().world.registerCommander(this);
        updateBounds();
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
        n.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        return n;
    }
    @Override
    public Node getModel(){
        if(model == null) model = getModel(player.getTexture());
        return model;
    }

    @Override
    public int[][] moveMap() {
        int[][] moves = new int[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                final int x = getX()+i-moves.length/2;
                final int y = getY()+j-moves.length/2;

                Tile t = X.getInstance().world.getAt(x,y,true);
                if(!t.hasPiece() && t.isLand()) moves[i][j] = GOTO;
            }
        }
        return moves;
    }

    @Override
    public Piece[] getConnected() {
        return connectedPieces;
    }

    @Override
    public int countConnected() {
        int count = 0;

        for (Piece connectedPiece : connectedPieces) {
            if (connectedPiece != null) count++;
        }
        return count;
    }

    @Override
    public boolean register(Piece piece) {
        if(!inBounds(piece.getX(),piece.getY())) return false;

        for(int i = 0; i < connectedPieces.length; i++){
            if(connectedPieces[i] == null){
                connectedPieces[i] = piece;
                updateBounds();
                piece.setCommander(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Piece piece) {
        for(int i = 0; i < connectedPieces.length; i++){
            if(connectedPieces[i] == piece){
                connectedPieces[i] = null;
                piece.removeCommander();
                updateBounds();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean inBounds(int x, int y) {
        final int x1 = bounds[0];
        final int x2 = bounds[2];
        final int y1 = bounds[1];
        final int y2 = bounds[3];

        final int dx = x-getX();
        final int dy = y-getY();

        return dx >= x1 && dx <= x2 && dy >= y1 && dy <= y2;
    }
    public int[] getBounds(){
        return bounds;
    }

    @Override
    public void updateBounds() {
        if(countConnected() == 0){
            bounds[0] = -7;
            bounds[1] = -7;
            bounds[2] = 7;
            bounds[3] = 7;
            return;
        }

        Piece first = null;
        for(Piece piece : connectedPieces){
            if(piece != null){
                first = piece;
                break;
            }
        }

        Piece lowestX = first;
        Piece lowestY = first;
        Piece highestX = first;
        Piece highestY = first;

        for(Piece piece : connectedPieces){
            if(piece == null) continue;

            final int x = piece.getX();
            final int y = piece.getY();

            if(x < lowestX.getX()) lowestX = piece;
            if(x > highestX.getX()) highestX = piece;
            if(y < lowestY.getY()) lowestY = piece;
            if(y > highestY.getY()) highestY = piece;
        }

        int x1 = lowestX.getX()-getX();
        int x2 = highestX.getX()-getX();
        int y1 = lowestY.getY()-getY();
        int y2 = highestY.getY()-getY();

        if(x2-x1 < 8){
            final int d = 7 - (x2-x1);
            x1 -= d;
            x2 += d;
        }
        if(y2-y1 < 8){
            final int d = 7 - (y2-y1);
            y1 -= d;
            y2 += d;
        }
        bounds[0] = x1;
        bounds[1] = y1;
        bounds[2] = x2;
        bounds[3] = y2;
    }
}
