package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ZombieFactory {

    private ArrayList<Zombie> zombies;
    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();

    /**
     * @param scale = scale of spawnpoint rectangle object
     * Constructor for ZombieFactory.
     */
    public ZombieFactory(float scale){
        MapLayer spawnPointLayer = View.getInstance().getMap().getLayers().get("spawntiles");
        MapObjects spawnPointObjects = spawnPointLayer.getObjects();

        for (RectangleMapObject spawnPointObject : spawnPointObjects.getByType(RectangleMapObject.class)) {
            spawnpoints.add(new Spawnpoint(spawnPointObject, scale));
        }
    }

    /**
     * @param scale = scale of zombies
     * @param amount = amount of zombies to loop thru
     * Creates zombies by looping thru @param amount, assigning the zombie to a random spawnpoint
     */
    public ArrayList<Zombie> createZombie(int amount, float scale){
        TextureAtlas atlasEric = new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas"));

        zombies = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Random random = new Random();

            int randomInt = random.nextInt(4);

            Spawnpoint spawnpoint = spawnpoints.get(randomInt);

            zombies.add(new Zombie(atlasEric, spawnpoint.getX(), spawnpoint.getY(), scale));
        }
        return zombies;
    }
}