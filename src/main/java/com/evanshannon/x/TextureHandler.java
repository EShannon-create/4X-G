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
    private static final Texture[] TEXTURES  = {GRASS_LIGHT,GRASS_DARK,WATER_LIGHT,WATER_DARK};

    public static void initialize(){
        for(Texture t : TEXTURES){
            fix(t);
        }
    }
    public static void fix(Texture t){
        t.setMagFilter(Texture.MagFilter.Nearest);
        t.setWrap(Texture.WrapMode.Repeat);
        t.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
    }
}
