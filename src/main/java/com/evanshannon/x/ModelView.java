package com.evanshannon.x;

import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.pieces.*;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
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

        Player p = new Player("",TextureHandler.RED);
        LandPiece[] pieces = {new Pawn(p),new Bishop(p),new Rook(p), new Cannon(p), new General(p), new Lieutenant(p), new Knight(p), new Pawn(p)};

        Node node;
        node = Amphibian.getModel(TextureHandler.RED,pieces);

        Quad q = new Quad(1f,1f);
        Material m = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        m.setTexture("ColorMap",TextureHandler.GRASS_LIGHT);
        Geometry g = new Geometry("G",q);
        g.setMaterial(m);
        Quaternion r = new Quaternion();
        r.fromAngles(-FastMath.HALF_PI, 0,0);
        g.setLocalRotation(r);

        Quad q2 = new Quad(1f,1f);
        Geometry g2 = new Geometry("G",q2);
        g2.setMaterial(m);
        Quaternion r2 = new Quaternion();
        r2.fromAngles(FastMath.HALF_PI, 0,0);
        g2.setLocalRotation(r2);
        g2.setLocalTranslation(0,0,-1);

        rootNode.attachChild(node);
        rootNode.attachChild(g);
        rootNode.attachChild(g2);
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
