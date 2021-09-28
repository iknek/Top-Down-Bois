package com.mygdx.game.desktop;


import java.util.List;

public interface Sectors {
    public void checkMovable(float scale);
    public void setMovable(boolean movable);
    public boolean getMovable();

    public List<Sector> getNeighbours(List<Sector> visited);
}
