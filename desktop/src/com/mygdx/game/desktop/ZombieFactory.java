package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

import java.util.ArrayList;
import java.util.Random;

public class ZombieFactory {
    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();

    public ZombieFactory(int scale){
        MapLayer spawnPointLayer = View.getInstance().getMap().getLayers().get("spawntiles");
        MapObjects spawnPointObjects = spawnPointLayer.getObjects();

        for (RectangleMapObject spawnPointObject : spawnPointObjects.getByType(RectangleMapObject.class)) {
            spawnpoints.add(new Spawnpoint(spawnPointObject, scale));
        }
    }

    public ArrayList<Zombie> createZombie(int amount, float W, float H, int scale){
        TextureAtlas atlasEric = new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas"));

        ArrayList<Zombie> zombies = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Random random = new Random();
            int randomInt = random.nextInt(4);

            Spawnpoint spawnpoint = spawnpoints.get(randomInt);

            zombies.add(new Zombie(atlasEric, spawnpoint.getX(), spawnpoint.getY(), scale));
        }
        return zombies;
    }
}
