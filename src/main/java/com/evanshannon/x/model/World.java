package com.evanshannon.x.model;

import com.evanshannon.x.MathLib;
import com.evanshannon.x.model.pieces.Commander;
import com.evanshannon.x.model.pieces.Piece;

import java.util.HashMap;
import java.util.HashSet;

public class World {

    HashMap<Integer, HashMap<Integer,Chunk>> map;
    private HashSet<Commander> commanders;

    public World(){
        commanders = new HashSet<>();
        map = new HashMap<>();
    }

    public Chunk get(int x, int y, boolean load){
        HashMap<Integer, Chunk> temp = map.get(x);
        if(temp == null){
            temp = new HashMap<>();
            map.put(x,temp);
        }
        Chunk chunk = temp.get(y);
        if(load && chunk == null){
            chunk = new Chunk(x,y);
            temp.put(y,chunk);
        }
        return chunk;
    }
    public Tile getAt(int x, int y, boolean load){
        int chunkX = MathLib.divide(x,Chunk.CHUNK_SIZE);
        int chunkY = MathLib.divide(y,Chunk.CHUNK_SIZE);
        int tileX = MathLib.mod(x,Chunk.CHUNK_SIZE);
        int tileY = MathLib.mod(y,Chunk.CHUNK_SIZE);

        Chunk chunk = get(chunkX,chunkY,load);
        if(chunk == null) return null;
        else return chunk.getTile(tileX,tileY);
    }
    public void registerCommander(Commander commander){
        commanders.add(commander);
    }
    public void removeCommander(Commander commander){
        commanders.remove(commander);
    }
    public Piece[] getCommanders(){
        Piece[] pieces = new Piece[commanders.size()];
        int i = 0;
        for(Commander c : commanders){
            if(c instanceof Piece p){
                pieces[i] = p;
                i++;
            }
        }
        return pieces;
    }
}
