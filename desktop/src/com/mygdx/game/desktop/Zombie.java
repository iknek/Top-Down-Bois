package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Zombie extends Sapien implements Zombies{

    private Sector playerSector;
    private float playerX;
    private float playerY;
    private int damage = 1;
    private List<Sector> path = new CopyOnWriteArrayList<>();

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
        if(path != null){
            Sector nextGoal = path.get(0);

            if(nextGoal == playerSector){
                angle = (int) Math.tan((playerX-getX())/(playerY-getY()));
            }else{
                double xDifference = ((nextGoal.getX()+nextGoal.getWidth()/2) - getX());
                double yDifference = ((nextGoal.getY()+nextGoal.getHeight()/2) - getY());
                angle = (int) Math.tan(xDifference/yDifference);
            }
        }
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public boolean moving(){
        return true;
    }

    @Override
    public void playerLocation(float x, float y) {
        playerSector = SectorGrid.getInstance().getCurrentSector(x,y);
        playerX = x;
        playerY = y;
    }

    public void getHit(int damage){
        health = health - damage;
        if (health <= 0){
            View.getInstance().removeSprite(this);
            MovableSubject.getInstance().detach(this);
            ZombieObserver.getInstance().detach(this);
        }
    }
    ///////////////////////////////////

    private Sector getCurrentSector(){
        return SectorGrid.getInstance().getCurrentSector(getX(), getY());
    }

    public void updatePath(){
        List<Sector> visited = new CopyOnWriteArrayList<>();

        path.add(getCurrentSector());

        path = findPath(getCurrentSector(), path, visited);
    }

    private List<Sector> findPath(Sector current, List<Sector> path, List<Sector> visited){
        List<Sector> neighbours = current.getNeighbours(visited);
        if(neighbours.contains(playerSector)){
            path.add(playerSector);
            return path;
        }
        neighbours = sort(neighbours);
        if(neighbours.isEmpty()){ return null;}

        for(Sector sector : neighbours){
            path.add(sector);
            visited.add(sector);
            if(findPath(sector, path, visited) == null){
                path.remove(sector);
                visited.remove(sector);
            } else{
                return path;
            }
        }
        return null;
    }

    private List<Sector> sort(List<Sector> neighbours){
        boolean sorted = false;
        while(!sorted){
            for (int i = 0; i < neighbours.size() - 1; i++) {
                if (distanceFromPlayer(neighbours.get(i)) > distanceFromPlayer(neighbours.get(i + 1))) {
                    neighbours = swap(neighbours, i);
                    break;
                }
                sorted = true;
            }
        }
        return neighbours;
    }

    private double distanceFromPlayer(Sector sector){
        float x = sector.getX()+ sector.getWidth()/2;
        float y = sector.getY()+ sector.getHeight()/2;

        return Math.sqrt(Math.pow(playerSector.getX()-x, 2) + Math.pow(playerSector.getY()-y, 2));
    }

    private List<Sector> swap(List<Sector> neighbours, int i){
        List<Sector> temp = new ArrayList<>();
        for (int j = 0; j < neighbours.size(); j++) {
            if(j == i){
                temp.add(neighbours.get(i+1));
                temp.add(neighbours.get(i));
                i++;
            }else{
                temp.add(neighbours.get(i));
            }
        }
        return temp;
    }
}