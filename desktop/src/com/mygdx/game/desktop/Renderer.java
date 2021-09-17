package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

// Kan vi byta namn?? Kanske till Renderer?

public class Renderer extends OrthogonalTiledMapRenderer {

    // Singleton mönster
    private static Renderer single_instance = null;
    public static Renderer getInstance() {
        if (single_instance == null)
            single_instance = new Renderer(new TmxMapLoader().load("proto.tmx"), 2);
        return single_instance;
    }

    private List<Sprite> sprites;
    private int drawSpritesAfterLayer = 1;

    public Renderer(TiledMap map, int scale) {
        super(map, scale);
        sprites = new ArrayList<Sprite>();
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
                    if(currentLayer == drawSpritesAfterLayer){

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
}
