package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Zombie extends Sapien implements Zombies{

    private Sector playerSector;
    private int damage = 1;

    public Zombie(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas, posX, posY, scale);
        this.name = "Eric";

        textureAtlas = atlas;
        setRegion(atlas.findRegion(name + "_back"));

        Random random = new Random();
        int randomInt = random.nextInt(10);

        this.speed = (25 + randomInt*2)*scale;

        health = 2;

        ZombieObserver.getInstance().attach(this);
    }

    //Change so that it will follow the path (( ie. find next sector; ))
    protected void updateAngle() {
        /*angle = (int) Math.toDegrees(Math.atan2(playerY - getY(), getX()-playerX));
        angle -= 90;
        if(angle < 0){
            angle += 360;
        }*/
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public boolean moving(){
        return true;
    }

    @Override
    public void playerLocation(Sector sector) {
        playerSector = sector;
    }

    public void getHit(int damage){
        health = health - damage;
        if (health <= 0){
            View.getInstance().removeSprite(this);
            MovableSubject.getInstance().detach(this);
            ZombieObserver.getInstance().detach(this);
        }
    }

    private List<Sector> path = new CopyOnWriteArrayList<>();

    private Sector getCurrentSector(){
        return SectorGrid.getInstance().getCurrentSector(getX(), getY());
    }

    //instead maybe get next sector; where it finds the closest movable sector which is closer to player
    public void findPath(){
        boolean found = false;
        List<Sector> visited = new CopyOnWriteArrayList<>();

        path.add(getCurrentSector());

        for (Sector sector : path) {
            if (!found) {
                for (Sector neighbour : sector.getNeighbours()) {
                    if (neighbour == playerSector) {
                        found = true;
                    }
                    if (neighbour.getMovable() && !visited.contains(neighbour)) {
                        path.add(neighbour);
                    }
                }
                visited.add(sector);
            }
        }
    }
}