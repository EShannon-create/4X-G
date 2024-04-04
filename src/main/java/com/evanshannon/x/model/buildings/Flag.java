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
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

public class Flag extends Building{
    private static final boolean TWO_FLAG = true;
    private Player player;
    private Flag(Tile tile) {
        super(tile);
    }
    public Flag(Tile tile, Player player){
        this(tile);
        this.player = player;
        player.registerFlag(this);
    }

    public static Node getModel(Texture texture){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap",texture);

        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", TextureHandler.WOOD);

        Box box = new Box(0.1f,1f,0.1f);
        Geometry g = new Geometry("Pole",box);
        g.setMaterial(m2);
        g.setLocalTranslation(0,1f,0);
        node.attachChild(g);

        if(TWO_FLAG) {
            Quad q = new Quad(1f, 2 * 0.875f);
            g = new Geometry("Flag FF", q);
            g.setMaterial(m);
            g.setLocalTranslation(-1f / 2f, 1f - 0.875f, 0.1001f);
            node.attachChild(g);

            g = new Geometry("Flag BF", q);
            g.setMaterial(m);
            g.setLocalTranslation(1f / 2f, 1f - 0.875f, 0.1001f);
            Quaternion r = new Quaternion();
            r.fromAngles(0, FastMath.PI, 0);
            g.setLocalRotation(r);
            node.attachChild(g);

            g = new Geometry("Flag FB", q);
            g.setMaterial(m);
            g.setLocalTranslation(-1f / 2f, 1f - 0.875f, -0.1001f);
            node.attachChild(g);

            g = new Geometry("Flag BB", q);
            g.setMaterial(m);
            g.setLocalTranslation(1f / 2f, 1f - 0.875f, -0.1001f);
            g.setLocalRotation(r);
            node.attachChild(g);

        }
        else{


            Quad q = new Quad(1f,2*0.875f);
            g = new Geometry("Flag F",q);
            g.setMaterial(m);
            g.setLocalTranslation(-1f/2f,1f-0.875f,0);
            node.attachChild(g);

            g = new Geometry("Flag B",q);
            g.setMaterial(m);
            g.setLocalTranslation(1f/2f,1f-0.875f,0);
            Quaternion r = new Quaternion();
            r.fromAngles(0, FastMath.PI,0);
            g.setLocalRotation(r);
            node.attachChild(g);
        }

        node.setLocalTranslation(0.5f,0f,-0.5f);
        node.setLocalScale(1f/4f);

        Node n = new Node();
        n.attachChild(node);

        return n;
    }
    public Node getModel(){
        return getModel(player.getTexture());
    }
    public Vector3f getLocation(){
        int[] loc = tile.getLocation();
        return new Vector3f(loc[0],10f,loc[1]);
    }

    @Override
    public Player getOwner() {
        return player;
    }
}
