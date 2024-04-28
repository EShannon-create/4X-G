package com.evanshannon.x.model;

import com.evanshannon.x.MessageParser;
import com.evanshannon.x.model.buildings.Barracks;
import com.evanshannon.x.model.buildings.Farm;
import com.evanshannon.x.model.pieces.Piece;
import com.jme3.scene.Node;

public class Chunk {
    public static final int CHUNK_SIZE = 8;
    private Tile[][] tiles;
    private final int x;
    private final int y;
    private Node node = null;
    public Tile getTile(int x, int y){
        if(x < 0 || x > CHUNK_SIZE) return null;
        if(y < 0 || y > CHUNK_SIZE) return null;
        return tiles[x][y];
    }

    public Chunk(int x, int y){
        this.x = x;
        this.y = y;
        tiles = new Tile[CHUNK_SIZE][CHUNK_SIZE];
        init();
    }

    private void init(){
        for(int i = 0; i < CHUNK_SIZE; i++){
            for(int j = 0; j < CHUNK_SIZE; j++){
                tiles[i][j] = new Tile(x*CHUNK_SIZE+i,y*CHUNK_SIZE+j);
            }
        }
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Node popNode(){
        Node node = this.node;
        this.node = null;
        return node;
    }
    public Node getNode(){
        return node;
    }
    public boolean hasNode(){
        return node != null;
    }
    public void setNode(Node node){
        this.node = node;
    }
    public void clearNode(){
        node = null;
    }
    protected String getInitString(){
        String init = "";
        for(Tile[] tt : tiles) for(Tile t : tt){
            if(t.hasBuilding() && t.getBuilding() instanceof Farm f){
                for(int i = 0; i < f.getFarmRows(); i++){
                    init += MessageParser.build(f,t.getLocation()[0],t.getLocation()[1])+'\n';
                }
            }
            else if(t.hasBuilding()) init += MessageParser.build(t.getBuilding(),t.getLocation()[0],t.getLocation()[1])+'\n';
            if (t.hasBuilding() && t.getBuilding() instanceof Barracks b) init += b.getInitString();
            if(t.hasPiece()) init += MessageParser.build(t.getPiece(),t.getLocation()[0],t.getLocation()[1])+'\n';
        }
        return init;
    }
}
