package com.evanshannon.x.model.pieces;

import com.evanshannon.x.MathLib;
import com.evanshannon.x.X;
import com.evanshannon.x.model.Chunk;
import com.evanshannon.x.model.Tile;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import java.util.HashSet;

public abstract class Piece {

    static final int NONE = 0;
    static final int MOVE = 1;
    static final int ATCK = 2;
    static final int GOTO = MOVE+ ATCK;
    static final int FIRE = 4;
    static final int JUMP = 8;

    public enum Direction{NORTH,SOUTH,EAST,WEST}

    private static HashSet<Piece> allPieces = new HashSet<>();

    private int x = Integer.MIN_VALUE;
    private int y = Integer.MIN_VALUE;

    static final int samples = 24;
    public abstract Node getModel();

    public Piece(){
        allPieces.add(this);
    }

    public static Piece getPiece(Geometry geometry){
        for(Piece piece : allPieces){
            if(piece.containsGeom(geometry)) return piece;
        }
        return null;
    }
    private boolean containsGeom(Geometry geometry){
        Node model = getModel();

        return model.hasChild(geometry);
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Vector3f getLocation(){
        return new Vector3f(x+0.5f,0.125f,y-0.5f);
    }
    public void face(Direction direction){
        Node n = getModel();
        Quaternion q = new Quaternion();
        switch(direction){
            case NORTH -> q.fromAngles(0,FastMath.HALF_PI,0);
            case SOUTH -> q.fromAngles(0,3*FastMath.HALF_PI,0);
            case EAST -> q.fromAngles(0,FastMath.PI,0);
            case WEST -> q.fromAngles(0,0,0);
        }
        n.setLocalRotation(q);
    }
    public void turn(){
        int i = MathLib.roll(0,4);
        switch(i){
            case 0 -> face(Direction.NORTH);
            case 1 -> face(Direction.SOUTH);
            case 2 -> face(Direction.EAST);
            case 3 -> face(Direction.WEST);
        }
    }
    public static Piece randomPiece(){
        int i = MathLib.roll(0,7);
        switch(i){
            default: case 0: return new Pawn();
            case 1: return new Rook();
            case 2: return new Bishop();
            case 3: return new Knight();
            case 4: return new Lieutenant();
            case 5: return new General();
            case 6: return new Cannon();
        }
    }

    public abstract int[][] canMove();

    public void move(int x, int y){
        Tile tile = X.getInstance().world.getAt(this.x,this.y,true);
        tile.clearPiece();

        tile = X.getInstance().world.getAt(x,y,true);
        tile.setPiece(this);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Chunk getChunk(){
        return X.getInstance().world.get(x/Chunk.CHUNK_SIZE,y/Chunk.CHUNK_SIZE,true);
    }
}
