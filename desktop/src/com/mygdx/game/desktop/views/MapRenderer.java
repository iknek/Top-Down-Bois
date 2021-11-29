package com.mygdx.game.desktop.views;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.List;

public class MapRenderer extends OrthogonalTiledMapRenderer {
    List<Sprite> sprites;

    public MapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public void renderMap(List<Sprite> sprites){
        this.sprites = sprites;
        render();
    }

    @Override
    public void render(){
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

    @Override
    public Batch getBatch(){
        return batch;
    }
}
