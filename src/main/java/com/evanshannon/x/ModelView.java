package com.evanshannon.x;

import com.evanshannon.x.model.Chunk;
import com.evanshannon.x.model.World;
import com.evanshannon.x.model.pieces.*;
import com.evanshannon.x.view.TextureHandler;
import com.evanshannon.x.view.TileRenderer;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class ModelView extends SimpleApplication {
    public static boolean RUNNING_MODEL_VIEW = false;
    private static ModelView instance;

    public static void main(String[] args) {
        RUNNING_MODEL_VIEW = true;

        ModelView app = new ModelView();
        instance = app;

        AppSettings settings = new AppSettings(true);
        settings.setResolution(1920,1070);
        settings.setFullscreen(false);
        app.setSettings(settings);

        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(10f);

        DirectionalLight light = new DirectionalLight();
        light.setColor(ColorRGBA.White.mult(0.85f));
        light.setDirection(new Vector3f(-1,-1,-1));
        light.setEnabled(true);
        rootNode.addLight(light);

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.15f));
        ambientLight.setEnabled(true);
        rootNode.addLight(ambientLight);

        Node rook = Rook.getModel(TextureHandler.RED);
        rook.setLocalTranslation(1,0,0);
        Node pawn = Pawn.getModel(TextureHandler.RED);
        pawn.setLocalTranslation(2,0,0);
        Node bishop = Bishop.getModel(TextureHandler.RED);
        bishop.setLocalTranslation(3,0,0);
        Node knight = Knight.getModel(TextureHandler.RED);
        knight.setLocalTranslation(4,0,0);
        Node queen = Lieutenant.getModel(TextureHandler.RED);
        queen.setLocalTranslation(5,0,0);
        Node king = General.getModel(TextureHandler.RED);
        king.setLocalTranslation(6,0,0);
        Node cannon = Cannon.getModel(TextureHandler.RED);

        rootNode.attachChild(rook);
        rootNode.attachChild(pawn);
        rootNode.attachChild(bishop);
        rootNode.attachChild(knight);
        rootNode.attachChild(queen);
        rootNode.attachChild(king);
        rootNode.attachChild(cannon);
    }
    @Override
    public void simpleUpdate(float tpf) {


    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
    public static ModelView getInstance(){
        return instance;
    }
}
