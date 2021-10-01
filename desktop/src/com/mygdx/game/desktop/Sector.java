package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Sector implements Sectors{
    private int X;
    private int Y;

    private int width;
    private int height;

    private boolean movable;

    public Sector(int x, int y, int w, int h){
        this.X = x;
        this.Y = y;

        this.width = w;
        this.height = h;

        this.movable = true;
    }

    public void checkMovable(float scale){
        Rectangle boundingRectangle = new Rectangle(X,Y,width,height);

        MapLayer collisionObjectLayer = View.getInstance().getMap().getLayers().get("collision");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            com.badlogic.gdx.math.Rectangle rectangle = rectangleObject.getRectangle();
            rectangle = CollisionController.scaleRectangle(rectangle, scale);

            if(Intersector.overlaps(boundingRectangle, rectangle)){
                setMovable(false);
            }

            rectangle = CollisionController.scaleBackRectangle(rectangle, scale);
        }
    }

    public void setMovable(boolean movable){
        this.movable = movable;
    }

    public boolean getMovable(){
        return movable;
    }


    @Override
    public List<Sector> getNeighbours(List<Sector> visited) {
        int indexX=0;
        int indexY=0;
        List<Sector> neighbours = new ArrayList<>();
        for (ArrayList<Sector> row: SectorGrid.getInstance().getMatrix()) {
            if(row.contains(this)){
                indexY = SectorGrid.getInstance().getMatrix().indexOf(row);
                indexX = row.indexOf(this);
            }
        }

        for (int i = indexX-1; i <= indexX+1; i++) {
            for (int j = indexY-1; j <= indexY+1; j++) {
                Sector neighbour = SectorGrid.getInstance().getMatrix().get(j).get(i);
                if(neighbour.getMovable() && !visited.contains(neighbour) && !neighbour.equals(this)){
                    neighbours.add(neighbour);
                }else{
                    neighbours.add(null);
                }
            }
        }
        return neighbours;
    }

    public List<Sector> getNeighbours() {
        int indexX=0;
        int indexY=0;
        List<Sector> neighbours = new ArrayList<>();
        for (ArrayList<Sector> row: SectorGrid.getInstance().getMatrix()) {
            if(row.contains(this)){
                indexY = SectorGrid.getInstance().getMatrix().indexOf(row);
                indexX = row.indexOf(this);
            }
        }

        for (int i = indexX-1; i <= indexX+1; i++) {
            for (int j = indexY-1; j <= indexY+1; j++) {
                if(j < SectorGrid.getInstance().getMatrix().size() && j >= 0){
                    if(i < SectorGrid.getInstance().getMatrix().get(j).size() && i >= 0){
                        Sector neighbour = SectorGrid.getInstance().getMatrix().get(j).get(i);
                        if(!neighbour.equals(this)){
                            neighbours.add(neighbour);
                        }
                    }
                }
            }
        }
        return neighbours;
    }

    public int getX(){
        return X;
    }

    public int getY(){
        return Y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
