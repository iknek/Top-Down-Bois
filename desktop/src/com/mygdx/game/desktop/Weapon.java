package com.mygdx.game.desktop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.ArrayList;

public class Weapon {
    private ArrayList<Projectile> projectileList;

    //add weapon characteristics

    public Weapon()
    {
        this.projectileList = new ArrayList<>();
    }

    public void fireWeapon(float angle, float x, float y){
        Projectile p = new Projectile(200, angle, x, y, new TextureAtlas(Gdx.files.internal("sprites.atlas")));
        projectileList.add(p);
    }
}
