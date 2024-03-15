package com.evanshannon.x.model.pieces;

import com.evanshannon.x.MathLib;
import com.evanshannon.x.X;
import com.evanshannon.x.model.Chunk;
import com.evanshannon.x.model.Player;
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
    private Commander commander = null;

    static final int samples = 24;
    public abstract Node getModel();

    final Player player;


    public Piece(Player player){
        allPieces.add(this);
        this.player = player;
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
            default: case 0: return new Pawn(X.getInstance().POV);
            case 1: return new Rook(X.getInstance().POV);
            case 2: return new Bishop(X.getInstance().POV);
            case 3: return new Knight(X.getInstance().POV);
            case 4: return new Lieutenant(X.getInstance().POV);
            case 5: return new General(X.getInstance().POV);
            case 6: return new Cannon(X.getInstance().POV);
        }
    }

    public abstract int[][] canMove();
    public int[][] getMoves(){
        int[][] canMove = canMove();

        HashSet<int[][]> moves = new HashSet<>();
        for(Piece commander : X.getInstance().world.getCommanders()){
            if(commander.getPlayer() != getPlayer()) continue;

            int[][] movesConsideringCommander = new int[canMove.length][canMove[0].length];

            for(int i = 0; i < canMove.length; i++){
                for(int j = 0; j < canMove[i].length; j++){
                    final int x = i - canMove.length/2;
                    final int y = j - canMove[i].length/2;

                    boolean in = ((Commander) commander).inBounds(x+getX(),y+getY());
                    movesConsideringCommander[i][j] = in ? canMove[i][j] : NONE;
                }
            }
            moves.add(movesConsideringCommander);
        }

        int[][] finalMoves = new int[canMove.length][canMove[0].length];
        for(int[][] i : moves){
            finalMoves = combineMoveSets(finalMoves,i);
        }

        return finalMoves;
    }

    public static int[][] combineMoveSets(int[][] a, int[][] b){
        if(a.length == 0) throw new IllegalArgumentException("Arrays may not be empty!");
        if(a.length != b.length || a[0].length != b[0].length) throw new IllegalArgumentException("Arrays must be of the same length!");

        int[][] c = new int[a.length][a[0].length];

        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a[i].length; j++){
                c[i][j] = a[i][j] | b[i][j];
            }
        }

        return c;
    }

    public void move(int x, int y){
        Tile tile = X.getInstance().world.getAt(this.x,this.y,true);
        tile.clearPiece();

        tile = X.getInstance().world.getAt(x,y,true);
        tile.setPiece(this);

        if(this instanceof Commander c){
            c.updateBounds();
            return;
        }
        if(!commander.inBounds(x,y)) {
            Commander oldCommander = commander;
            commander.remove(this);
            oldCommander.updateBounds();
            getCommander();
        }
        commander.updateBounds();
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Chunk getChunk(){
        return X.getInstance().world.get(MathLib.divide(x,Chunk.CHUNK_SIZE),MathLib.divide(y,Chunk.CHUNK_SIZE),true);
    }

    public Player getPlayer() {
        return player;
    }
    public void setCommander(Commander commander){
        this.commander = commander;
    }
    public void removeCommander(){
        this.commander = null;
    }
    public void onDestroy(){
        if(commander != null) commander.remove(this);
    }
    public void getCommander(){
        for(Piece commander : X.getInstance().world.getCommanders()){
            if(commander instanceof Commander c && commander.getPlayer() == getPlayer() && c.inBounds(getX(),getY())){
                c.register(this);
                System.out.println("New commander!");
                return;
            }
        }
        noCommander();
    }
    void noCommander(){
        System.out.println("No commander!");
    }
}
