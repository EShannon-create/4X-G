package com.evanshannon.x;

import com.evanshannon.x.model.Chunk;
import com.evanshannon.x.model.Player;
import com.evanshannon.x.model.Tile;
import com.evanshannon.x.model.World;
import com.evanshannon.x.model.pieces.*;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.shadow.DirectionalLightShadowRenderer;
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
    public World world = new World();
    private int[] oldloc;
    private Node horizon;
    private Node moveSpheres = null;
    private Piece selectedPiece = null;
    private BitmapText location;
    public Player POV;
    private Node bounds = null;

    private static final int SHADOWMAP_SIZE = 256;

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

        POV = new Player("POV",TextureHandler.YELLOW);

        addExamplePieces5();
        TileRenderer.render(rootNode,world,0,0);
    }
    private void addExamplePieces1(){
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Rook(POV));
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Pawn(POV));
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Knight(POV));
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Bishop(POV));
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new General(POV));
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Lieutenant(POV));
        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Cannon(POV));

        Player enemy = new Player("Enemy",TextureHandler.RED);
        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Rook(enemy));
        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Pawn(enemy));
        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Knight(enemy));
        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Bishop(enemy));
        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new General(enemy));
        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Lieutenant(enemy));
        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(new Cannon(enemy));
    }
    private void addExamplePieces3(){
        Piece rook = new Rook(POV);
        Piece pawn = new Pawn(POV);
        General general = new General(POV);

        world.get(0,0,true).getTile(6,4).setPiece(general);
        world.get(0,0,true).getTile(2,6).setPiece(rook);
        world.get(0,0,true).getTile(5,1).setPiece(pawn);
        general.register(rook);
        general.register(pawn);
    }
    private void addExamplePieces5(){
        Piece rook = new Rook(POV);
        Piece pawn = new Pawn(POV);
        Piece knight = new Knight(POV);
        Piece bishop = new Bishop(POV);
        General general = new General(POV);
        Piece lieutenant = new Lieutenant(POV);
        Piece cannon = new Cannon(POV);

        Piece[] pieces = {rook,pawn,knight,bishop,lieutenant,cannon};

        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(general);
        for(Piece piece : pieces) world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(piece);
        for(Piece piece : pieces){
            if(!general.register(piece)) piece.move(-5,-5);
        }

        Player p = new Player("Red",TextureHandler.RED);
        Piece rook2 = new Rook(p);
        Piece pawn2 = new Pawn(p);
        Piece knight2 = new Knight(p);
        Piece bishop2 = new Bishop(p);
        General general2 = new General(p);
        Piece lieutenant2 = new Lieutenant(p);
        Piece cannon2 = new Cannon(p);

        Piece[] pieces2 = {rook2,pawn2,knight2,bishop2,lieutenant2,cannon2};

        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(general2);
        for(Piece piece : pieces2) world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(piece);
        for(Piece piece : pieces2){
            if(!general2.register(piece)) piece.move(-5,-5);
        }
    }
    private void addExamplePieces4(){
        Piece rook = new Rook(POV);
        Piece pawn = new Pawn(POV);
        Piece knight = new Knight(POV);
        Piece bishop = new Bishop(POV);
        General general = new General(POV);
        Piece lieutenant = new Lieutenant(POV);
        Piece cannon = new Cannon(POV);

        Piece[] pieces = {rook,pawn,knight,bishop,lieutenant,cannon};

        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(general);
        for(Piece piece : pieces) world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(piece);
        for(Piece piece : pieces){
            if(!general.register(piece)) piece.move(-5,-5);
        }

        Piece rook2 = new Rook(POV);
        Piece pawn2 = new Pawn(POV);
        Piece knight2 = new Knight(POV);
        Piece bishop2 = new Bishop(POV);
        General general2 = new General(POV);
        Piece lieutenant2 = new Lieutenant(POV);
        Piece cannon2 = new Cannon(POV);

        Piece[] pieces2 = {rook2,pawn2,knight2,bishop2,lieutenant2,cannon2};

        world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(general2);
        for(Piece piece : pieces2) world.get(1,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(piece);
        for(Piece piece : pieces2){
            if(!general2.register(piece)) piece.move(-5,-5);
        }
    }
    private void addExamplePieces2(){
        Piece rook = new Rook(POV);
        Piece pawn = new Pawn(POV);
        Piece knight = new Knight(POV);
        Piece bishop = new Bishop(POV);
        General general = new General(POV);
        Piece lieutenant = new Lieutenant(POV);
        Piece cannon = new Cannon(POV);

        Piece[] pieces = {rook,pawn,knight,bishop,lieutenant,cannon};

        world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(general);
        for(Piece piece : pieces) world.get(0,0,true).getTile(roll(0,CHUNK_SIZE),roll(0,CHUNK_SIZE)).setPiece(piece);
        for(Piece piece : pieces){
            if(!general.register(piece)) piece.move(-5,-5);
        }
        System.out.println("Done!");
    }
    private void initializeInputs(){
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(clickListener,"Left Click");
        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(clickListener,"Right Click");

        inputManager.addMapping("X",new KeyTrigger(KeyInput.KEY_X));
        inputManager.addListener(keyListener,"X");
    }

    private final ActionListener keyListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tps) {
            switch(name){
                case "X" -> {
                    if(keyPressed){
                        for(Piece piece : world.getCommanders()){
                            showBounds(piece);
                        }
                    }
                    else{
                        clearBounds();
                    }
                }
            }
        }
    };

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
                    if(selectedPiece != null && g.getName().charAt(0) == '^'){//This is a little silly I have to admit
                        handleClickMoveSphere(g);
                        return;
                    }
                    else if(selectedPiece != null){
                        if(handleMoveToPiece(g)) return;
                    }

                    handleSelectPiece(g);
                }
                else{
                    IO.print(g.getLocalTranslation().toString());
                    if(g.getName().charAt(0) != 'T') return;

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
                    clearMoveSpheres();
                }
            }
        }
    };
    private void showBounds(Piece piece){
        if(!(piece instanceof Commander c)) return;

        if(bounds == null){
            bounds = new Node();
            rootNode.attachChild(bounds);
        }
        Sphere sphere = new Sphere(8,8,0.125f);

        Node n = new Node();

        for(int i = -7; i <= 7; i++){
            for(int j = -7; j <= 7; j++){
                if(c.inBounds(i+piece.getX(),j+piece.getY())){
                    Geometry g = new Geometry("%"+i+" "+j,sphere);
                    Material m = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
                    m.setColor("Color",ColorRGBA.fromRGBA255(70,30,30,255));
                    g.setMaterial(m);

                    g.setLocalTranslation(i,0.125f,j);
                    g.setShadowMode(RenderQueue.ShadowMode.Off);
                    n.attachChild(g);
                }
            }
        }

        n.setLocalTranslation(piece.getLocation());
        bounds.attachChild(n);
    }
    private void clearBounds(){
        if(bounds == null) return;
        rootNode.detachChild(bounds);
        bounds = null;
    }

    private void handleSelectPiece(Geometry g){
        Piece p = Piece.getPiece(g);
        if(p == null){
            clearMoveSpheres();
            return;
        }

        selectedPiece = p;
        makeMoveSpheres(p);
    }
    private void handleClickMoveSphere(Geometry g){
        Chunk oldC = selectedPiece.getChunk();

        String[] split = g.getName().split(" ");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);//Convenient albeit silly

        selectedPiece.move(x,y);
        TileRenderer.rerender(rootNode, oldC);
        Chunk newC = selectedPiece.getChunk();
        TileRenderer.rerender(rootNode, newC);

        clearMoveSpheres();
        selectedPiece = null;
    }
    private boolean handleMoveToPiece(Geometry g){
        Piece p = Piece.getPiece(g);
        if(p == null){
            clearMoveSpheres();
            return false;
        }
        if(p.getPlayer() == selectedPiece.getPlayer()){
            selectedPiece = p;
            makeMoveSpheres(p);
        }

        System.out.println("Moving");
        Chunk oldC = selectedPiece.getChunk();
        int x = p.getX();
        int y = p.getY();

        //Boiler plate code
        selectedPiece.move(x,y);
        TileRenderer.rerender(rootNode, oldC);
        Chunk newC = selectedPiece.getChunk();
        TileRenderer.rerender(rootNode, newC);
        System.out.println(oldC.getX()+" "+oldC.getY());
        System.out.println(newC.getX()+" "+newC.getY());

        clearMoveSpheres();
        selectedPiece = null;
        return true;
    }

    private void makeMoveSpheres(Piece piece){
        clearMoveSpheres();

        int[][] moves = piece.getMoves();
        Sphere s = new Sphere(8,8,0.125f);
        for(int i = 0; i < moves.length; i++){
            for(int j = 0; j < moves[i].length; j++){
                if(moves[i][j] != 0){
                    int x = i+piece.getX()-moves.length/2;
                    int y = j+piece.getY()-moves.length/2;

                    Geometry g = new Geometry("^ "+x+" "+y,s);
                    Material m = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
                    m.setColor("Color",ColorRGBA.fromRGBA255(200,200,200,255));
                    g.setMaterial(m);

                    g.setLocalTranslation(i-moves.length/2,0,j-moves.length/2);
                    g.setShadowMode(RenderQueue.ShadowMode.Off);
                    moveSpheres.attachChild(g);
                }
            }
        }
        moveSpheres.setLocalTranslation(piece.getLocation());
        rootNode.attachChild(moveSpheres);
    }
    private void clearMoveSpheres(){
        if(moveSpheres != null) rootNode.detachChild(moveSpheres);
        moveSpheres = new Node();
    }

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

        DirectionalLightShadowRenderer r = new DirectionalLightShadowRenderer(assetManager,SHADOWMAP_SIZE,3);
        r.setRenderBackFacesShadows(false);
        r.setLambda(0.65f);
        r.setLight(light);

        viewPort.addProcessor(r);
    }
    private void initializeHUD(){
        Picture pic = new Picture("Crosshair");
        pic.setImage(assetManager,"Textures/crosshair.png",true);
        float width = 16;
        pic.setWidth(width);
        pic.setHeight(width);
        pic.setPosition(settings.getWidth()/2f-width/2f,settings.getHeight()/2f-width/2f);
        guiNode.attachChild(pic);

        location = new BitmapText(guiFont,false);
        location.setSize(guiFont.getCharSet().getRenderedSize());
        location.setColor(ColorRGBA.White);
        guiNode.attachChild(location);
    }
    private void updateText(){
        location.setText(cam.getLocation().toString());
        location.setLocalTranslation(settings.getWidth()/2f-location.getLineWidth()/2f,settings.getHeight(),0);
    }

    @Override
    public void simpleUpdate(float tpf) {
        checkChunks();
        updateText();
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
