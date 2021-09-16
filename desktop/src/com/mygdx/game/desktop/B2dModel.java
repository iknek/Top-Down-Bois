package com.mygdx.game.desktop;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


/*
public class B2dModel {
    int objectLayerId = 5;
    TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer)map.getLayers().get(objectLayerId);
    MapObjects objects = collisionObjectLayer.getObjects();
    Player player = new Player(...);
    MapObjects objects = map.getLayers().get("Obstacles").getObjects();

    public void checkCollision(){


// there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, player.getRectangle()) {
                // collision happened
            }
        }
    }
}*/