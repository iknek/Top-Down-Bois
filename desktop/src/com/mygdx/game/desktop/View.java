package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

// Kan vi byta namn?? Kanske till Renderer?

public class View extends OrthogonalTiledMapRenderer {
    // Singleton mönster
    private static View single_instance = null;
    private ArrayList<Sprite> sprites;
    private int drawSpritesAfterLayer = 3;
    private float scale;

    //SpriteBatch batch;
    public AnimationTest animationTest = new AnimationTest();

    public static View getInstance() {
        if (single_instance == null)
            single_instance = new View(new TmxMapLoader().load("outside.tmx"), 2);
        return single_instance;
    }

    public static View createInstance(float scale) {
        if (single_instance == null)
            single_instance = new View(new TmxMapLoader().load("outside.tmx"), scale);

        return single_instance;
    }

    public View(TiledMap map, float scale) {
        super(map, scale);
        sprites = new ArrayList<>();
    }

    @Override
    public TiledMap getMap() {
        return super.getMap();
    }

    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }
    public void removeSprite(Sprite sprite){ sprites.remove(sprite); }

    public List<Sprite> getSprites(){
        return sprites;
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == 2){
                        for(Sprite sprite : sprites) {
                            sprite.draw(this.batch);
                        }
                        animationTest.createAnimation();
                        animationTest.render();
                        //elapsedTime += Gdx.graphics.getDeltaTime();
                        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                        //batch.begin();
                        //batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),400,400);
                        //batch.end();
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        animationTest.dispose();
        endRender();
    }

    public OrthographicCamera createCamera(float w, float h){

        OrthographicCamera camera = new OrthographicCamera();
        camera.position.set(w/2,0,0);
        camera.setToOrtho(false,w,h);
        camera.update();

        return camera;
    }
}