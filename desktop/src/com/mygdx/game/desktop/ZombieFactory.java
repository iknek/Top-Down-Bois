package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.mygdx.game.desktop.sapiens.Zombie;
import com.mygdx.game.desktop.views.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * A factory class for Zombies
 */
public class ZombieFactory {

    private float scale;

    /**
     * @param scale = scale of spawnpoint rectangle object
     * Constructor for ZombieFactory.
     */
    public ZombieFactory(float scale){
        this.scale = scale;
    }

    /**
     * @param amount = amount of zombies to loop thru
     * Creates zombies by looping thru @param amount, assigning the zombie to a random spawnpoint
     */
    public void createZombie(int amount, int roundNumber, Spawnpoint spawnpoint){
        TextureAtlas atlasEric = new TextureAtlas(Gdx.files.internal("Coffin/Back/running/running"));
        for (int i = 0; i < amount; i++) {
            new Zombie(atlasEric, spawnpoint.getX(), spawnpoint.getY(), scale, roundNumber);
        }
    }
}