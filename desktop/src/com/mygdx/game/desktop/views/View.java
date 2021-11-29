package com.mygdx.game.desktop.views;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.desktop.Model;
import com.mygdx.game.desktop.sapiens.Player;
import com.mygdx.game.desktop.weapons.Firearm;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The View class renders the whole program. This goes for {@link Sprite} and {@link Hud} and {@link Store}
 * This class is created and used by Model.
 * This class uses {@link Sprite}, {@link Hud} and {@link Store}.
 * @author david
 * @author imad
 */
public class View extends ApplicationAdapter {
    private static View single_instance = null;
    private List<Sprite> sprites;

    private Hud hud;
    private Store store;
    private SpriteBatch spriteBatch;

    private boolean shopOpen;

    private OrthographicCamera camera;
    private FitViewport viewport;

    private MapRenderer mapRenderer;
    private TiledMap map;

    private float scale;
    private Model model;

    /**
     * Constructor for the View class.
     * @param scale if the scale of everything on screen
     */
    public View(float scale) {
        sprites = new CopyOnWriteArrayList<>();
        single_instance = this;
        this.scale = scale;
    }

    @Override
    public void create(){
        mapRenderer = new MapRenderer(new TmxMapLoader().load("textures/wildwest.tmx"), scale);
        this.map = new TmxMapLoader().load("textures/wildwest.tmx");

        model = new Model(scale);

        spriteBatch = new SpriteBatch();
        hud = new Hud(spriteBatch, (int) scale);
        store = new Store(spriteBatch,(int) scale);

        store.setPlayer(model.getPlayer());

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = createCamera(w, h);
        viewport = new FitViewport(w, h, camera);

    }

    /**
     * Either creates a new instance of View or returns the instance that already exists.
     * @param scale is the scale for the whole program and so also the map
     * @return the single instance which either is created or already exists
     */
    public static View createInstance(float scale) {
        if (single_instance == null)
            single_instance = new View(scale);
        return single_instance;
    }

    /**
     * Adds a sprite to a list which is to be rendered every cycle
     * @param sprite the sprite which will be rendered
     */
    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }

    /**
     * Removes a sprite from the list to be rendered
     * @param sprite is the sprite which we want removed
     */
    public void removeSprite(Sprite sprite){
        sprites.remove(sprite);
    }

    /**
     * Render is called from TiledTestTwo every cycle
     * It goes through each layer in the map and renders them
     * As well as rendering the sprites when needed, and updated animations
     */
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        mapRenderer.setView(camera);

        mapRenderer.renderMap(sprites);

        if(!getShopOpen()){
            model.render();
        }

        spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        spriteBatch.setProjectionMatrix(store.stage.getCamera().combined);

        Player player = model.getPlayer();
        Firearm firearm = player.getWeapon();

        updateHud(model.getRoundNo(), player.getHealth(), player.getMoney(), player.getWeapon().getName(),
                firearm.getAmmoInMagazine(), firearm.getTotalAmmo());

        hud.stage.draw();

        if(model.getRoundInfo() || getShopOpen()){
            openShop();
        }
    }

    /**
     * "Opens" store, i.e. opens shop UI
     */
    public void openShop(){
        shopOpen = true;
        store.open();
    }

    /**
     * "Closes" store, i.e sets shopOpen to false.
     */
    public void closeShop(Player player){
        player.regainControls();
        shopOpen = false;
    }

    /**
     * Creates a camera for the program to use
     * @param w the width of the program
     * @param h the height of the program
     * @return the camera which is created
     */
    public OrthographicCamera createCamera(float w, float h){

        OrthographicCamera camera = new OrthographicCamera();
        camera.position.set(w/2,0,0);
        camera.setToOrtho(false,w,h);
        camera.update();

        return camera;
    }

    /**
     * LibGDX dispose method.
     */
    @Override
    public void dispose() {
        hud.dispose();
        store.dispose();
        spriteBatch.dispose();
        super.dispose();
    }

    /**
     * When the window gets resized it configures the viewport so that the program does not glitch out.
     * @param width the width of the window
     * @param height the height of the window
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    /**
     * Method to update all relevant UI fields. Called in update
     * @param round = current round
     * @param live = player lives left
     * @param money = player money
     * @param firearm = current player weapon
     * @param mag = Ammo in "magazine"
     * @param totalAmmo = Total weapon ammo left.
     */
    public void updateHud(int round, int live, int money, String firearm, int mag, int totalAmmo){
        hud.update(round, live, money, firearm, mag, totalAmmo);
    }

    /**
     * Returns the list of sprites
     * @return list of sprites which are currently being rendered
     */
    public List<Sprite> getSprites(){
        return new CopyOnWriteArrayList<>(sprites);
    }

    /**
     * returns the batch which the View uses
     * @return the batch
     */
    public Batch getBatch(){
        return mapRenderer.getBatch();
    }

    /**
     * Returns the instance of View
     * @return the single instance of View
     */
    public static View getInstance() {
        return single_instance;
    }

    /**
     * returns the map
     * @return The map of the program
     */
    public TiledMap getMap() {
        return map;
    }

    public boolean getShopOpen(){
        return shopOpen;
    }
}