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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class Wall extends Building {

    public enum Direction{NORTH,SOUTH,EAST,WEST};
    public static Node getStraight(Texture texture, Direction direction){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", texture == TextureHandler.BLACK ? TextureHandler.WHITE : TextureHandler.BLACK);

        Box box = new Box(1f,2f/3f,1f/4f);
        Geometry g = new Geometry("Wall",box);
        g.setMaterial(m);
        node.attachChild(g);

        Cylinder c = new Cylinder(4,24,2f/7f,2f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        Quaternion q = new Quaternion();
        q.fromAngles(0,FastMath.HALF_PI, 0);
        g.setLocalRotation(q);
        g.setLocalTranslation(0f,2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,2f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalRotation(q);
        g.setLocalTranslation(0f,-2f/3f,0f);
        node.attachChild(g);

        node.setLocalScale(1f/2f);
        node.setLocalTranslation(0.5f,2/6f,-0.5f);

        if(direction == Direction.NORTH || direction == Direction.SOUTH){
            node.setLocalRotation(q);
        }

        Node n = new Node();
        n.attachChild(node);

        return n;
    }
    public static Node getCorner(Texture texture, Direction direction){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", texture == TextureHandler.BLACK ? TextureHandler.WHITE : TextureHandler.BLACK);

        Box box = new Box(0.5f,2f/3f,1f/4f);
        Geometry g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0.5f,0,0);
        node.attachChild(g);

        box = new Box(0.25f,2f/3f,0.5f);
        g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0,0.5f);
        node.attachChild(g);

        Cylinder c = new Cylinder(4,24,0.4f,4f/3f);
        g = new Geometry("Tower",c);
        g.setMaterial(m);
        Quaternion q = new Quaternion();
        q.fromAngles(FastMath.HALF_PI,0,0);
        g.setLocalRotation(q);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        Quaternion q2 = new Quaternion();
        q2.fromAngles(0,FastMath.HALF_PI, 0);
        g.setLocalRotation(q2);
        g.setLocalTranslation(0.5f,2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,0.5f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalRotation(q2);
        g.setLocalTranslation(0.5f,-2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,-2f/3f,0.5f);
        node.attachChild(g);

        Sphere s = new Sphere(24,24,0.5f);
        g = new Geometry("Top",s);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,0f);
        node.attachChild(g);

        s = new Sphere(24,24,0.5f);
        g = new Geometry("Top",s);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,-2f/3f,0f);
        node.attachChild(g);

        node.setLocalScale(1f/2f);
        node.setLocalTranslation(0.5f,2/6f,-0.5f);

        if(direction != null){
            Quaternion q3 = new Quaternion();
            switch(direction){
                case NORTH -> q3.fromAngles(0,-FastMath.HALF_PI,0);
                case SOUTH -> q3.fromAngles(0,FastMath.HALF_PI,0);
                case EAST -> q3.fromAngles(0,0,0);
                case WEST -> q3.fromAngles(0,FastMath.PI,0);
            }
            node.setLocalRotation(q3);
        }

        Node n = new Node();
        n.attachChild(node);

        return n;
    }
    public static Node getTriple(Texture texture, Direction direction){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", texture == TextureHandler.BLACK ? TextureHandler.WHITE : TextureHandler.BLACK);

        Box box = new Box(0.5f,2f/3f,1f/4f);
        Geometry g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0.5f,0,0);
        node.attachChild(g);

        box = new Box(0.25f,2f/3f,0.5f);
        g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0,0.5f);
        node.attachChild(g);

        box = new Box(0.25f,2f/3f,0.5f);
        g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0,-0.5f);
        node.attachChild(g);

        Cylinder c = new Cylinder(4,24,0.4f,4f/3f);
        g = new Geometry("Tower",c);
        g.setMaterial(m);
        Quaternion q = new Quaternion();
        q.fromAngles(FastMath.HALF_PI,0,0);
        g.setLocalRotation(q);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        Quaternion q2 = new Quaternion();
        q2.fromAngles(0,FastMath.HALF_PI, 0);
        g.setLocalRotation(q2);
        g.setLocalTranslation(0.5f,2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,0.5f);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,-0.5f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalRotation(q2);
        g.setLocalTranslation(0.5f,-2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,-2f/3f,0.5f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,-2f/3f,-0.5f);
        node.attachChild(g);

        Sphere s = new Sphere(24,24,0.5f);
        g = new Geometry("Top",s);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,0f);
        node.attachChild(g);

        node.setLocalScale(1f/2f);
        node.setLocalTranslation(0.5f,2/6f,-0.5f);

        if(direction != null){
            Quaternion q3 = new Quaternion();
            switch(direction){
                case NORTH -> q3.fromAngles(0,FastMath.HALF_PI,0);
                case SOUTH -> q3.fromAngles(0,-FastMath.HALF_PI,0);
                case EAST -> q3.fromAngles(0,FastMath.PI,0);
                case WEST -> q3.fromAngles(0,0,0);
            }
            node.setLocalRotation(q3);
        }

        Node n = new Node();
        n.attachChild(node);

        return n;
    }
    public static Node getFour(Texture texture){

        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", texture == TextureHandler.BLACK ? TextureHandler.WHITE : TextureHandler.BLACK);

        Box box = new Box(0.5f,2f/3f,1f/4f);
        Geometry g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0.5f,0,0);
        node.attachChild(g);

        box = new Box(0.25f,2f/3f,0.5f);
        g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0,0.5f);
        node.attachChild(g);

        box = new Box(0.25f,2f/3f,0.5f);
        g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0f,0,-0.5f);
        node.attachChild(g);

        box = new Box(0.5f,2f/3f,1f/4f);
        g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(-0.5f,0,0);
        node.attachChild(g);

        Cylinder c = new Cylinder(4,24,0.4f,4f/3f);
        g = new Geometry("Tower",c);
        g.setMaterial(m);
        Quaternion q = new Quaternion();
        q.fromAngles(FastMath.HALF_PI,0,0);
        g.setLocalRotation(q);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        Quaternion q2 = new Quaternion();
        q2.fromAngles(0,FastMath.HALF_PI, 0);
        g.setLocalRotation(q2);
        g.setLocalTranslation(0.5f,2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalRotation(q2);
        g.setLocalTranslation(-0.5f,2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,0.5f);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,-0.5f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalRotation(q2);
        g.setLocalTranslation(0.5f,-2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalRotation(q2);
        g.setLocalTranslation(-0.5f,-2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,-2f/3f,0.5f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,-2f/3f,-0.5f);
        node.attachChild(g);

        Sphere s = new Sphere(24,24,0.5f);
        g = new Geometry("Top",s);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,0f);
        node.attachChild(g);

        node.setLocalScale(1f/2f);
        node.setLocalTranslation(0.5f,2/6f,-0.5f);

        Node n = new Node();
        n.attachChild(node);

        return n;
    }
    public static Node getTower(Texture texture){

        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", texture == TextureHandler.BLACK ? TextureHandler.WHITE : TextureHandler.BLACK);

        Cylinder c = new Cylinder(4,24,0.4f,4f/3f);
        Geometry g = new Geometry("Tower",c);
        g.setMaterial(m);
        Quaternion q = new Quaternion();
        q.fromAngles(FastMath.HALF_PI,0,0);
        g.setLocalRotation(q);
        node.attachChild(g);

        Sphere s = new Sphere(24,24,0.5f);
        g = new Geometry("Top",s);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,0f);
        node.attachChild(g);

        s = new Sphere(24,24,0.5f);
        g = new Geometry("Top",s);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,-2f/3f,0f);
        node.attachChild(g);

        node.setLocalScale(1f/2f);
        node.setLocalTranslation(0.5f,2/6f,-0.5f);

        Node n = new Node();
        n.attachChild(node);

        return n;
    }
    public static Node getEnd(Texture texture, Direction direction){
        AssetManager assetManager;
        if(ModelView.RUNNING_MODEL_VIEW) assetManager = ModelView.getInstance().getAssetManager();
        else assetManager = X.getInstance().getAssetManager();
        Node node = new Node();

        Material m = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", texture);
        Material m2 = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        m2.setTexture("DiffuseMap", texture == TextureHandler.BLACK ? TextureHandler.WHITE : TextureHandler.BLACK);

        Box box = new Box(0.5f,2f/3f,1f/4f);
        Geometry g = new Geometry("Wall",box);
        g.setMaterial(m);
        g.setLocalTranslation(0.5f,0,0);
        node.attachChild(g);

        Cylinder c = new Cylinder(4,24,0.4f,4f/3f);
        g = new Geometry("Tower",c);
        g.setMaterial(m);
        Quaternion q = new Quaternion();
        q.fromAngles(FastMath.HALF_PI,0,0);
        g.setLocalRotation(q);
        node.attachChild(g);

        c = new Cylinder(4,24,2f/7f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        Quaternion q2 = new Quaternion();
        q2.fromAngles(0,FastMath.HALF_PI, 0);
        g.setLocalRotation(q2);
        g.setLocalTranslation(0.5f,2f/3f,0f);
        node.attachChild(g);

        c = new Cylinder(4,24,1f/2f,1f,false);
        g = new Geometry("Top",c);
        g.setMaterial(m2);
        g.setLocalRotation(q2);
        g.setLocalTranslation(0.5f,-2f/3f,0f);
        node.attachChild(g);

        Sphere s = new Sphere(24,24,0.5f);
        g = new Geometry("Top",s);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,2f/3f,0f);
        node.attachChild(g);

        s = new Sphere(24,24,0.5f);
        g = new Geometry("Top",s);
        g.setMaterial(m2);
        g.setLocalTranslation(0f,-2f/3f,0f);
        node.attachChild(g);

        node.setLocalScale(1f/2f);
        node.setLocalTranslation(0.5f,2/6f,-0.5f);

        if(direction != null){
            Quaternion q3 = new Quaternion();
            switch(direction){
                case NORTH -> q3.fromAngles(0,-FastMath.HALF_PI,0);
                case SOUTH -> q3.fromAngles(0,FastMath.HALF_PI,0);
                case EAST -> q3.fromAngles(0,0,0);
                case WEST -> q3.fromAngles(0,FastMath.PI,0);
            }
            node.setLocalRotation(q3);
        }

        Node n = new Node();
        n.attachChild(node);

        return n;
    }

    public Wall(Tile tile){
        super(tile);
    }
    public Node getModel(){
        Texture texture = getTexture();

        final boolean east; final boolean west; final boolean north; final boolean south;
        int count = 0;
        if(tile.getEast().hasWall() && tile.getEast().getOwner() == getOwner()){
            count++;
            east = true;
        } else east = false;
        if(tile.getWest().hasWall() && tile.getWest().getOwner() == getOwner()){
            count++;
            west = true;
        } else west = false;
        if(tile.getNorth().hasWall() && tile.getNorth().getOwner() == getOwner()){
            count++;
            north = true;
        } else north = false;
        if(tile.getSouth().hasWall() && tile.getSouth().getOwner() == getOwner()){
            count++;
            south = true;
        } else south = false;

        switch(count){
            default -> {
                return getTower(texture);
            }
            case 1 -> {
                if(east) return getEnd(texture,Direction.EAST);
                if(west) return getEnd(texture,Direction.WEST);
                if(north) return getEnd(texture,Direction.NORTH);
                if(south) return getEnd(texture,Direction.SOUTH);
                return getTower(texture);
            }
            case 2 ->{
                if(east && west) return getStraight(texture,Direction.EAST);
                if(north && south) return getStraight(texture,Direction.NORTH);
                if(east && north) return getCorner(texture,Direction.EAST);
                if(east && south) return getCorner(texture,Direction.SOUTH);
                if(west && north) return getCorner(texture,Direction.NORTH);
                if(west && south) return getCorner(texture,Direction.WEST);
                return getTower(texture);
            }
            case 3 -> {
                if(!east) return getTriple(texture,Direction.EAST);
                if(!west) return getTriple(texture,Direction.WEST);
                if(!north) return getTriple(texture,Direction.NORTH);
                if(!south) return getTriple(texture,Direction.SOUTH);
                return getTower(texture);
            }
            case 4 -> {
                return getFour(texture);
            }
        }
    }
}
