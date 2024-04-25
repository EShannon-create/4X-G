package com.evanshannon.x.model.pieces;

import com.evanshannon.x.MathLib;
import com.evanshannon.x.Menu;
import com.evanshannon.x.MessageParser;
import com.evanshannon.x.X;
import com.evanshannon.x.model.Chunk;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.evanshannon.x.model.buildings.Barracks;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import java.util.HashSet;

public abstract class Piece {

    public static final int NONE = 0;
    public static final int MOVE = 1;
    public static final int ATCK = 2;
    public static final int GOTO = MOVE+ ATCK;
    public static final int FIRE = 4;
    public static final int JUMP = 8;
    public static final int CHCK = 16;

    public enum Direction{NORTH,SOUTH,EAST,WEST}

    private static HashSet<Piece> allPieces = new HashSet<>();
    private HashSet<Tile> possessing = new HashSet<>();

    private int x = Integer.MIN_VALUE;
    private int y = Integer.MIN_VALUE;
    private Commander commander = null;

    static final int samples = 24;
    public abstract Node getModel();

    final Player player;


    public Piece(Player player){
        allPieces.add(this);
        this.player = player;
        player.registerPiece(this);
        updatePossession();
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
        if(commander == null) findCommander();
    }
    public Vector3f getLocation(){
        return new Vector3f(x+0.5f,0.125f,y-0.5f);
    }
    public void face(Direction direction){
        Node n = getModel();
        Quaternion q = new Quaternion();
        switch(direction){
            case NORTH -> q.fromAngles(0,0*FastMath.HALF_PI,0);
            case SOUTH -> q.fromAngles(0,2*FastMath.HALF_PI,0);
            case EAST -> q.fromAngles(0,1*FastMath.HALF_PI,0);
            case WEST -> q.fromAngles(0,3*FastMath.HALF_PI,0);
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
    public static Piece randomPiece(Player player){
        int i = MathLib.roll(0,7);
        switch(i){
            default: case 0: return new Pawn(player);
            case 1: return new Rook(player);
            case 2: return new Bishop(player);
            case 3: return new Knight(player);
            case 4: return new Lieutenant(player);
            case 5: return new General(player);
            case 6: return new Cannon(player);
        }
    }

    public boolean canMove(){
        return player.canMove(this);
    }
    public int[][] moveMap(){
        return moveMap(true);
    }
    abstract int[][] moveMap(boolean onMove);
    public int[][] getMoves(){
        return getMoves(true);
    }
    public int[][] getMoves(boolean onMove){
        try{
            X.getInstance();
        }
        catch(RuntimeException e){
            return new int[][]{{0}};
        }
        int[][] canMove = getTile().hasBuilding() && getTile().getBuilding() instanceof Barracks ? getDeployment(onMove) : moveMap(onMove);

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
    public boolean hasAnyMove(){
        int[][] moves = getMoves();
        for(int i = 0; i < moves.length; i++){
            for(int j = 0; j < moves.length; j++){
                if(moves[i][j] != 0) return true;
            }
        }
        return false;
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
    public void face(int x, int y){
        final int dx = x-this.x;
        final int dy = y-this.y;
        if(dx > 0 && dx > MathLib.abs(dy)) face(Direction.EAST);
        else if(dx <= 0 && -dx > MathLib.abs(dy)) face(Direction.WEST);
        else if(dy > 0) face(Direction.NORTH);
        else face(Direction.SOUTH);
    }

    public void move(int x, int y){
        move(x,y,true);
    }
    public void move(int x, int y, boolean broadcast){
        if(!player.canMove(this)) return;

        Tile tile = X.getInstance().world.getAt(this.x,this.y,true);
        if(broadcast){
            if(tile.hasBuilding() && tile.getBuilding() instanceof Barracks b){
                final int index = b.getIndex(this);
                X.getInstance().broadcast(MessageParser.deploy(this.x,this.y,x,y,index));
            }
            else X.getInstance().broadcast(MessageParser.move(this.x,this.y,x,y));
        }

        tile.clearPiece();

        tile = X.getInstance().world.getAt(x,y,true);
        this.x = x;
        this.y = y;
        face(x,y);

        tile.setPiece(this);

        if(this instanceof Commander c){
            c.updateBounds();
            if(!tile.hasBuilding()) player.onMove(c);
            updatePossession();
            return;
        }
        if(!commander.inBounds(x,y)) {
            Commander oldCommander = commander;
            commander.remove(this);
            oldCommander.updateBounds();
            findCommander();
        }
        commander.updateBounds();
        updatePossession();
    }
    public void jump(int x, int y){
        jump(x,y,true);
    }
    public void jump(int x, int y, boolean broadcast){
        if(!player.canMove(this)) return;

        if(broadcast) X.getInstance().broadcast(MessageParser.jump(this.x,this.y,x,y));

        final int jumpX = (x-getX())/2+getX();
        final int jumpY = (y-getY())/2+getY();

        Tile tile = X.getInstance().world.getAt(jumpX,jumpY,true);
        tile.clearPiece();
        tile.setBuilding(null);
        move(x,y,false);
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
        player.unregisterPiece(this);
        if(this instanceof Commander c){
            Piece[] connected = c.getConnected();
            for(Piece piece : connected){
                if(piece != null) piece.removeCommander();
            }
            X.getInstance().world.removeCommander(c);
        }
    }
    public boolean findCommander(){
        for(Piece commander : X.getInstance().world.getCommanders()){
            if(commander instanceof Commander c && commander.getPlayer() == getPlayer() && c.inBounds(getX(),getY())){
                c.register(this);
                System.out.println("New commander!");
                return true;
            }
        }
        noCommander();
        return false;
    }
    public Commander getCommander(){
        return commander;
    }
    void noCommander(){
        kill();
    }
    public void updatePossession(){
        clearPossession();
        int[][] moves = getMoves(false);
        for(int i = 0; i < moves.length; i++){
            for(int j = 0; j < moves[i].length; j++){
                if(moves[i][j] == NONE) continue;

                final int x = i+this.x-moves.length/2;
                final int y = j+this.y-moves.length/2;
                final Tile t = X.getInstance().world.getAt(x,y,true);
                if(moves[i][j] == CHCK && t.hasBuilding()) continue;
                t.addControl(this);
                possessing.add(t);
            }
        }
    }
    public void clearPossession(){
        for(Tile i : possessing){
            i.removeControl(this);
        }
        possessing = new HashSet<>();
    }
    public void kill(){
        X.getInstance().world.getAt(x,y,false).clearPiece();
        onDestroy();
    }
    public Tile getTile(){
        return X.getInstance().world.getAt(x,y,true);
    }
    public int[][] getDeployment(boolean onMove){
        final int SIZE = 15;
        int[][] moves = new int[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                final int x = getX()+i-moves.length/2;
                final int y = getY()+j-moves[i].length/2;
                Tile t = X.getInstance().world.getAt(x,y,true);
                if(t.isWater()) continue;
                if(t.hasWall()) continue;
                if(t.hasBuilding() && !(t.getBuilding() instanceof Barracks) && onMove) continue;
                if(!t.hasPiece() || t.getPiece().getPlayer() != getPlayer()) moves[i][j] = GOTO;
                if(t.hasPiece()) moves[i][i] = CHCK;
            }
        }
        return moves;
    }
    public abstract String getCode();
}
