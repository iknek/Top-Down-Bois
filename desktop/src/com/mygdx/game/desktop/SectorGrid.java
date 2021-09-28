package com.mygdx.game.desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class SectorGrid {
    ArrayList<ArrayList<Sector>> sectorMatrix = new ArrayList<>();

    private static SectorGrid single_instance = null;

    public static SectorGrid getInstance() {
        if (single_instance == null)
            single_instance = new SectorGrid();
        return single_instance;
    }

    public SectorGrid(){

    }

    public SectorGrid(float w, float h, float scale){
        for (int i = 0; i < h; i+=16) {
            ArrayList<Sector> row = new ArrayList<>();

            for (int j = 0; j < w; j+=16) {
                Sector sector = new Sector(j, i, 16, 16);
                sector.checkMovable(scale);
                row.add(sector);
            }
            sectorMatrix.add(row);
        }
    }

    public ArrayList<ArrayList<Sector>> getMatrix(){
        return sectorMatrix;
    }

    public Sector getCurrentSector(float x, float y){
        for (List<Sector> row : getMatrix()) {
            for (Sector sector : row) {
                if(x > sector.getX() && x < sector.getX()+sector.getWidth()){
                    if(y > sector.getY() && y < sector.getY()+sector.getHeight()){
                        return sector;
                    }
                }
            }
        }
        return null;
    }
}
