package com.evanshannon.x;

import com.evanshannon.x.ModelView;
import com.evanshannon.x.X;
import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;

public class TextureHandler {
    private static final AssetManager assetManager = ModelView.RUNNING_MODEL_VIEW ? ModelView.getInstance().getAssetManager() : X.getInstance().getAssetManager();
    public static Texture GRASS_LIGHT = assetManager.loadTexture("Textures/grass_top.png");
    public static Texture GRASS_DARK = assetManager.loadTexture("Textures/grass_top_dark.png");
    public static Texture WATER_LIGHT = assetManager.loadTexture("Textures/water_opaque.png");
    public static Texture WATER_DARK = assetManager.loadTexture("Textures/water_opaque_dark.png");
    public static Texture YELLOW = assetManager.loadTexture("Textures/yellow.png");
    public static Texture RED = assetManager.loadTexture("Textures/red.png");
    public static Texture GREEN = assetManager.loadTexture("Textures/green.png");
    public static Texture CYAN = assetManager.loadTexture("Textures/cyan.png");
    public static Texture BLUE = assetManager.loadTexture("Textures/blue.png");
    public static Texture MAGENTA = assetManager.loadTexture("Textures/magenta.png");
    public static Texture WHITE = assetManager.loadTexture("Textures/white.png");
    public static Texture BLACK = assetManager.loadTexture("Textures/black.png");
    public static Texture FARM = assetManager.loadTexture("Textures/farm.png");
    private static final Texture[] TEXTURES  = {GRASS_LIGHT,GRASS_DARK,WATER_LIGHT,WATER_DARK};
    private static final Texture[] COLORS = {RED,YELLOW,GREEN,CYAN,BLUE,MAGENTA,WHITE,BLACK};

    public static void initialize(){
        for(Texture t : TEXTURES) fix(t);
        for(Texture t : COLORS) change(t);
    }
    public static void change(Texture t){
        t.setWrap(Texture.WrapMode.Repeat);
    }
    public static void fix(Texture t){
        t.setMagFilter(Texture.MagFilter.Nearest);
        t.setWrap(Texture.WrapMode.Repeat);
        t.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
    }
}
