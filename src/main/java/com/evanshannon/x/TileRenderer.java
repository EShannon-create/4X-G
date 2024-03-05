package com.evanshannon.x;

import com.evanshannon.x.model.Chunk;
import com.evanshannon.x.model.Tile;
import com.evanshannon.x.model.World;
import com.evanshannon.x.model.pieces.Piece;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

public class TileRenderer {
    public static final int RENDER_DISTANCE = 10;
    public static void clear(Node rootnode, World world, int x, int y){
        for(int i = x-(RENDER_DISTANCE+1); i <= x+(RENDER_DISTANCE+1); i++){
            for(int j = y-(RENDER_DISTANCE+1); j <= y+(RENDER_DISTANCE+1); j++){
                Chunk chunk = world.get(i,j,false);
                if(chunk == null) continue;
                Node node = chunk.popNode();
                if(node == null) continue;
                rootnode.detachChild(node);
            }
        }
    }
    public static void render(Node rootnode, World world, int x, int y){
        for(int i = x-RENDER_DISTANCE; i <= x+RENDER_DISTANCE; i++){
            for(int j = y-RENDER_DISTANCE; j <= y+RENDER_DISTANCE; j++){
                if(!MathLib.checkDistance(x,y,i,j,RENDER_DISTANCE)) continue;
                Chunk chunk = world.get(i,j,true);

                if(chunk.hasNode()) continue;

                Node node = render(chunk);

                chunk.setNode(node);
                rootnode.attachChild(node);
            }
        }
    }
    private static Node render(Chunk chunk){
        Node node = new Node();

        for(int i = 0; i < Chunk.CHUNK_SIZE; i++){
            for(int j = 0; j < Chunk.CHUNK_SIZE; j++){
                Quad q = new Quad(1,1);
                Geometry g = new Geometry(i+" "+j,q);

                Material m = new Material(X.getInstance().getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");

                Texture t;
                Tile tile = chunk.getTile(i,j);
                if(tile.isLand()){
                    if((i+j)%2==0) t = TextureHandler.GRASS_LIGHT;
                    else t = TextureHandler.GRASS_DARK;
                }
                else{
                    if((i+j)%2==0) t = TextureHandler.WATER_LIGHT;
                    else t = TextureHandler.WATER_DARK;
                }

                m.setTexture("ColorMap",t);
                g.setMaterial(m);

                g.setLocalTranslation(i,0,j);
                Quaternion r = new Quaternion();
                r.fromAngles(-FastMath.HALF_PI, 0,0);
                g.setLocalRotation(r);

                node.attachChild(g);
                //System.out.println("\tAttached " + i + " " + j + " to chunk.");
                if(tile.hasPiece()){
                    Piece p = tile.getPiece();
                    Node model = p.getModel();
                    model.setLocalTranslation(i+0.5f,0,j-0.5f);
                    node.attachChild(model);
                }
            }
        }

        node.setLocalTranslation(chunk.getX()*Chunk.CHUNK_SIZE,0,chunk.getY()*Chunk.CHUNK_SIZE);

        return node;
    }
    public static void rerender(Node rootnode, Chunk chunk){
        rootnode.detachChild(chunk.popNode());
        Node node = render(chunk);
        chunk.setNode(node);
        rootnode.attachChild(node);
    }
    public static Node getHorizon(){
        Cylinder cylinder = new Cylinder(8,32,Chunk.CHUNK_SIZE*RENDER_DISTANCE-1.25f*Chunk.CHUNK_SIZE,70f,false,true);
        Geometry g = new Geometry("Cylinder",cylinder);
        Material m = new Material(X.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", ColorRGBA.Blue);
        g.setMaterial(m);

        Quaternion r = new Quaternion();
        r.fromAngles(-FastMath.HALF_PI, 0,0);
        g.setLocalRotation(r);
        g.setLocalTranslation(RENDER_DISTANCE/2f,0,RENDER_DISTANCE/2f);

        Node n = new Node();
        n.attachChild(g);
        return n;
    }
}
