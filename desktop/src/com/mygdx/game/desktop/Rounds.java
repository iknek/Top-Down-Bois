package com.mygdx.game.desktop;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.mygdx.game.desktop.sapiens.Player;
import com.mygdx.game.desktop.sapiens.Zombie;
import com.mygdx.game.desktop.views.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Rounds class represents every group of {@link Zombie} objects spawning in. Once all zombies are killed, a new round starts
 * with new zombies spawning in with increased health, for the sake of increased difficulty.
 */
public class Rounds {
    /**
     * {@link ZombieFactory} object, called for creating new {@link Zombie} objects each round.
     */
    private ZombieFactory zombiefactory;
    /**
     * Counter for keeping track of which round the player has progressed to.
     */
    private int roundNumber;

    private int zombiesLeftToSpawn;

    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();

    /**
     * Constructor for the {@link Rounds} class.
     * @param scale scale of zombie factory
     */
    public Rounds (float scale){
        MapLayer spawnPointLayer = View.getInstance().getMap().getLayers().get("spawntiles");
        MapObjects spawnPointObjects = spawnPointLayer.getObjects();

        this.roundNumber = 0;
        zombiefactory = new ZombieFactory(scale);

        for (RectangleMapObject spawnPointObject : spawnPointObjects.getByType(RectangleMapObject.class)) {
            spawnpoints.add(new Spawnpoint(spawnPointObject, scale));
        }
    }

    /**
     * Checks if any {@link Zombie} objects remain on the map. If there aren't while the player isn't dead, it starts a new round.
     * @param player current {@link Player} object
     */
    public boolean checkNewRound(Player player){
        boolean newRound = false;
        Random random = new Random();
        int randomInt = random.nextInt(4);
        Spawnpoint spawnpoint = spawnpoints.get(randomInt);
        if(zombiesLeftToSpawn > 0){
            zombiefactory.createZombie(1, roundNumber, spawnpoint);
            zombiesLeftToSpawn--;
        }else{
            int zombiesLeft = 0;
            for (Movable o : MovableSubject.getInstance().getObservers()) {
                if(o instanceof Zombie){
                    zombiesLeft++;
                }
            }
            if(zombiesLeft == 0 && player.getHealth() != 0){
                startNewRound(player);
                newRound = true;
            }
        }
        return newRound;
    }

    public int getRound(){
        return roundNumber;
    }

    /**
     * Starts new round by increasing <code>roundnumber</code>, adding 1 to player health, and calling {@link ZombieFactory} to create new {@link Zombie} objects.
     * @param player current {@link Player} object
     */
    private void startNewRound(Player player){
        roundNumber++;
        if(roundNumber % 5 == 0){ player.addHealth(1); }
        zombiesLeftToSpawn = roundNumber*5;
    }
}