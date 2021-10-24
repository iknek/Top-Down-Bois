package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.desktop.sapiens.Zombie;

/**
 * A factory class for Zombies
 * This class is created and used by {@link Rounds} but does not use any other classes.
 * @author david
 * @author imad
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