package com.evanshannon.x;

import com.evanshannon.x.model.Chunk;
import com.evanshannon.x.model.Tile;
import com.evanshannon.x.model.World;
import com.evanshannon.x.model.pieces.*;
import com.evanshannon.x.view.TextureHandler;
import com.evanshannon.x.view.TileRenderer;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

import static com.evanshannon.x.MathLib.roll;
import static com.evanshannon.x.model.Chunk.CHUNK_SIZE;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class X extends SimpleApplication {

    private static X instance;
    private World world = new World();
    private int[] oldloc;
    private Node horizon;

    public static void main(String[] args) {
        X app = new X();
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
        TextureHandler.initialize();
        flyCam.setMoveSpeed(15);
        oldloc = new int[]{0,0};

        horizon = TileRenderer.getHorizon();
        rootNode.attachChild(horizon);

        initializeLights();
        initializeHUD();
        initializeInputs();

        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Rook());
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Pawn());
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Knight());
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Bishop());
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new General());
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Lieutenant());
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Cannon());
        TileRenderer.render(rootNode,world,0,0);
    }
    private void initializeInputs(){
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(clickListener,"Left Click");
        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(clickListener,"Right Click");
    }

    private final ActionListener clickListener = new ActionListener(){
        public void onAction(String name, boolean keyPressed, float tpf){
            if(keyPressed){
                Ray r = new Ray(cam.getLocation(),cam.getDirection());
                CollisionResults c = new CollisionResults();
                rootNode.collideWith(r,c);

                if(c.size() == 0) return;

                CollisionResult col = c.getCollision(0);
                Geometry g = col.getGeometry();

                if(name.equals("Left Click")){
                    Piece p = Piece.getPiece(g);
                    if(p != null) p.turn();
                }
                else{
                    IO.print(g.getLocalTranslation().toString());
                    Piece p = Piece.getPiece(g);
                    if(p != null) return;

                    int x = (int)(g.getLocalTranslation().x+g.getParent().getLocalTranslation().x);
                    int y = (int)(g.getLocalTranslation().z+g.getParent().getLocalTranslation().z);

                    int cx = MathLib.divide(x,CHUNK_SIZE);
                    int cy = MathLib.divide(y,CHUNK_SIZE);

                    int dx = MathLib.mod(x,CHUNK_SIZE);
                    int dy = MathLib.mod(y,CHUNK_SIZE);

                    Chunk chunk = world.get(cx,cy,false);
                    Tile tile = chunk.getTile(dx,dy);
                    tile.setPiece(Piece.randomPiece());

                    TileRenderer.rerender(rootNode,chunk);
                }
            }
        }
    };

    private void initializeLights(){
        DirectionalLight light = new DirectionalLight();
        light.setColor(ColorRGBA.White.mult(0.85f));
        light.setDirection(new Vector3f(-1,-1,-1));
        light.setEnabled(true);
        rootNode.addLight(light);

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.15f));
        ambientLight.setEnabled(true);
        rootNode.addLight(ambientLight);
    }
    private void initializeHUD(){
        Picture pic = new Picture("Crosshair");
        pic.setImage(assetManager,"Textures/crosshair.png",true);
        float width = 16;
        pic.setWidth(width);
        pic.setHeight(width);
        pic.setPosition(settings.getWidth()/2f-width/2f,settings.getHeight()/2f-width/2f);
        guiNode.attachChild(pic);
    }

    @Override
    public void simpleUpdate(float tpf) {
        checkChunks();
    }
    private void checkChunks(){
        int x = (int)MathLib.divide(cam.getLocation().x, CHUNK_SIZE);
        int z = (int)MathLib.divide(cam.getLocation().z, CHUNK_SIZE);

        int ox = oldloc[0];
        int oz = oldloc[1];

        if(ox != x || oz != z){
            TileRenderer.clear(rootNode,world,x,z);
            TileRenderer.render(rootNode,world,x,z);
        }
        oldloc[0] = x;
        oldloc[1] = z;

        horizon.setLocalTranslation(cam.getLocation().x,0,cam.getLocation().z);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }

    public static X getInstance(){
        if(instance == null) throw new RuntimeException("X::getInstance() got called early!");
        return instance;
    }
}
