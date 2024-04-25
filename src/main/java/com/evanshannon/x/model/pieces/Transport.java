package com.evanshannon.x.model.pieces;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.TextureHandler;
import com.evanshannon.x.X;
import com.evanshannon.x.model.PieceContainer;
import com.evanshannon.x.model.Player;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class Transport extends SeaPiece implements PieceContainer {
    public static Node getModel(Texture texture, Piece[] pieces){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", TextureHandler.WOOD);

        Cylinder c = new Cylinder(samples,samples,0.25f,1f);
        Geometry g = new Geometry("Hull",c);
        g.setMaterial(m);
        node.attachChild(g);

        Box b = new Box(0.25f,0.125f,0.5f);
        g = new Geometry("Hull 2",b);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0.125f,0f);
        node.attachChild(g);

        Sphere s = new Sphere(samples, samples, 0.25f);
        g = new Geometry("Hull Side 1",s);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0f,0.5f);
        node.attachChild(g);

        g = new Geometry("Hull Side 2",s);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0f,-0.5f);
        node.attachChild(g);

        c = new Cylinder(samples,samples,0.25f,0.25f,true);
        g = new Geometry("Hull 2 Side 1",c);
        Quaternion q = new Quaternion();
        q.fromAngles(FastMath.HALF_PI,0f,0f);
        g.setLocalRotation(q);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0.12499f,0.5f);
        node.attachChild(g);

        g = new Geometry("Hull 2 Side 1",c);
        g.setLocalRotation(q);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0.12499f,-0.5f);
        node.attachChild(g);

        b = new Box(0.05f,0.4f,0.05f);
        g = new Geometry("Flagpole",b);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,0.65f,0f);
        node.attachChild(g);

        Quad qu = new Quad(0.6f,0.4f);
        g = new Geometry("Flag",qu);
        g.setMaterial(m);
        g.setLocalTranslation(0,0.65f,0f);
        node.attachChild(g);

        g = new Geometry("Flag",qu);
        g.setMaterial(m);
        g.setLocalTranslation(0.6f,0.65f,0f);
        Quaternion q2 = new Quaternion();
        q2.fromAngles(0f,FastMath.PI,0f);
        g.setLocalRotation(q2);
        node.attachChild(g);

        Node pieceNode = new Node();
        for(int i = 0; i < pieces.length; i++){
            Piece piece = pieces[i];
            if(piece == null) continue;

            Node model = piece.getModel();
            model.setLocalScale(0.05f);

            final float x = i < 4 ? -0.125f : 0.125f;
            final float y = 0.25f;
            final float z = (i % 4)*0.25f-0.375f;
            model.setLocalTranslation(x,y,z);

            pieceNode.attachChild(model);
        }
        node.attachChild(pieceNode);

        node.setLocalTranslation(0.5f,0.125f/3f,-0.5f);
        node.setLocalScale(1f/3f);

        Node n = new Node();
        n.attachChild(node);
        return n;
    }

    public static final int CAPACITY = 8;

    private final Piece[] pieces;
    public Transport(Player player) {
        super(player);
        pieces = new LandPiece[CAPACITY];
    }

    @Override
    public Node getModel() {
        return getModel(player.getTexture(),pieces);
    }
    public boolean addPiece(Piece piece){
        for(int i = 0; i < pieces.length; i++){
            if(pieces[i] == null){
                pieces[i] = piece;
                return true;
            }
        }
        return false;
    }
    public int countPieces(){
        int count = 0;
        for (Piece piece : pieces) {
            if (piece != null) count++;
        }
        return count;
    }
    public boolean isFull(){
        return countPieces() >= CAPACITY;
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
    int[][] moveMap(boolean onMove) {
        return new int[0][];
    }
    @Override
    public String getCode(){
        return "Transport";
    }
}
