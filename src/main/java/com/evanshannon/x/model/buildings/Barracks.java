package com.evanshannon.x.model.buildings;

import com.evanshannon.x.*;
import com.evanshannon.x.model.PieceContainer;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.evanshannon.x.model.pieces.LandPiece;
import com.evanshannon.x.model.pieces.Piece;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class Barracks extends Building implements PieceContainer {
    public static final int CAPACITY = 8;
    private Piece[] pieces;
    public static Node getModel(Texture texture, Piece[] pieces){
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

        Node p;
        for(int i = 0; i < pieces.length; i++){
            if(pieces[i] == null) continue;
            p = pieces[i].getModel();
            p.setLocalScale(0.15f);

            final float d = i < 4 ? 1f : 0.5f;
            final float x = i % 2 == 0 ? d : -d;
            final float y = i < 4 ? 4f/3f : 2f/3f;
            final float z = i % 4 == 0 || i % 4 == 1 ? d : -d;

            p.setLocalTranslation(x,y,z);

            node.attachChild(p);
        }

        Node n = new Node();
        n.attachChild(node);
        return n;
    }
    @Override
    public Node getModel() {
        return getModel(getTexture(),pieces);
    }
    public Barracks(Tile tile){
        super(tile);
        pieces = new LandPiece[CAPACITY];
    }
    public boolean isFull(){
        return countPieces() >= CAPACITY;
    }
    public int countPieces(){
        int count = 0;
        for (Piece piece : pieces) {
            if (piece != null) count++;
        }
        return count;
    }
    public boolean addPiece(Piece piece){
        for(int i = 0; i < pieces.length; i++) if(pieces[i] == null){
            pieces[i] = piece;
            System.out.println("Moved to barracks "+i);
            return true;
        }
        return false;
    }
    public boolean removePiece(Piece piece){
        for(int i = 0; i < pieces.length; i++){
            if(pieces[i] == piece){
                pieces[i] = null;
                return true;
            }
        }
        return false;
    }
    @Override
    public Player getOwner(){
        for(Piece piece : pieces){
            if(piece != null) return piece.getPlayer();
        }
        return super.getOwner();
    }
    public Piece getPiece(int index){
        return pieces[index];
    }
    public int getIndex(Piece piece){
        for(int i = 0; i < pieces.length; i++){
            if(pieces[i] == piece) return i;
        }
        return -1;
    }
    @Override
    public String getCode(){
        return "Barracks";
    }
    public String getInitString(){
        String init = "";
        for(Piece piece : pieces){
            if(piece != null) init += MessageParser.build(piece,tile.getLocation()[0],tile.getLocation()[1])+'\n';
        }
        return init;
    }
}
