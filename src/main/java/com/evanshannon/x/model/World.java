package com.evanshannon.x.model;

import com.evanshannon.x.MathLib;

import java.util.HashMap;

public class World {

    HashMap<Integer, HashMap<Integer,Chunk>> map;

    public World(){
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
}
