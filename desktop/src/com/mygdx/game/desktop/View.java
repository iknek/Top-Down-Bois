package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class View extends OrthogonalTiledMapRenderer {
    private static View single_instance = null;
    private List<Sprite> sprites;

    /**
     * Constructor for the View class.
     * @param map is the map which is going to be shown
     * @param scale if the scale of everything on screen
     */
    public View(TiledMap map, float scale) {
        super(map, scale);
        sprites = new CopyOnWriteArrayList<>();
    }

    /**
     * Returns the instance of View
     * @return the single instance of View
     */
    public static View getInstance() {
        return single_instance;
    }

    /**
     * Either creates a new instance of View or returns the instance that already exists.
     * @param scale is the scale for the whole program and so also the map
     * @return the single instance which either is created or already exists
     */
    public static View createInstance(float scale) {
        if (single_instance == null)
            single_instance = new View(new TmxMapLoader().load("textures/wildwest.tmx"), scale);
        return single_instance;
    }

    /**
     * returns the map
     * @return The map of the program
     */
    @Override
    public TiledMap getMap() {
        return super.getMap();
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
     * Returns the list of sprites
     * @return list of sprites which are currently being rendered
     */
    public List<Sprite> getSprites(){
        return sprites;
    }

    /**
     * returns the batch which the View uses
     * @return the batch
     */
    public Batch getBatch(){
        return batch;
    }

    /**
     * Render is called from TiledTestTwo every cycle
     * It goes through each layer in the map and renders them
     * As well as rendering the sprites when needed, and updated animations
     */
    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == 9){
                        for(Sprite sprite : sprites) {
                            sprite.draw(this.batch);
                        }
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        endRender();
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
}