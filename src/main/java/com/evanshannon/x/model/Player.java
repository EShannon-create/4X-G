package com.evanshannon.x.model;

import com.jme3.texture.Texture;

public class Player {
    private final Texture texture;
    private final String name;
    public Player(String name, Texture texture){
        this.name = name;
        this.texture = texture;
    }
    public String getName(){
        return name;
    }
    public Texture getTexture(){
        return texture;
    }
}
