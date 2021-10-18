package com.mygdx.game.desktop.views;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud implements Disposable{

    //Scene2D Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Scene2D widgets
    private Label roundLabel;
    private Label liveLabel;
    private Label moneyLabel;
    private Label currentWeaponLabel;
    private float scale;

    /**
     * Constructor that does everything associated with the table and labels. See sub-comments.
     * @param sb spritebatch for every object in table
     * @param progScale scale of program
     */
    public Hud(SpriteBatch sb, int progScale){

        scale = progScale;

        BitmapFont font = new BitmapFont(Gdx.files.internal("Font/font.fnt"), Gdx.files.internal("Font/font.png"),false);

        viewport = new FitViewport(640*scale, 640*scale, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        //Top-Aligns table
        table.bottom();
        table.padBottom(20);
        table.scaleBy(scale);
        //Makes the table fill the entire stage
        table.setFillParent(true);

        //defines labels using the String, and a Label style consisting of a font and null color (font has just one). All values set to 1 since they get updated anyways, thus reducing amount of var. declerations needed.
        roundLabel = new Label(String.format("%03d", 1), new Label.LabelStyle(font, null));
        liveLabel = new Label(String.format("%03d", 1), new Label.LabelStyle(font, null));
        moneyLabel = new Label(String.format("%03d", 1), new Label.LabelStyle(font, null));
        currentWeaponLabel = new Label((1 + "/" + 1), new Label.LabelStyle(font, null));

        // Pos of table
        liveLabel.setPosition(50,100);

        // Scale of labels.
        liveLabel.setFontScale(scale);
        roundLabel.setFontScale(scale);
        moneyLabel.setFontScale(scale);
        currentWeaponLabel.setFontScale(scale);

        //Adds labels to table, padding the bottom, and giving them all equal width with expandX
        table.add(roundLabel).expandX().padBottom(10);
        table.add(liveLabel).expandX().padBottom(10);

        //Adds a second row to table
        table.row();
        table.add(moneyLabel).expandX();
        table.add(currentWeaponLabel).expandX();
        //table.add(liveLabel).expandX();

        //Adds table to stage
        stage.addActor(table);
    }

    /**
     * Update method that, when called, updates all the labels.
     * @param round current game round
     * @param live player lives left
     * @param money player money
     * @param firearm current player weapon
     * @param mag current amount of ammo in player mag
     * @param totalAmmo total ammo player has for one weapon (excluding amount in mag)
     */
    public void update(int round, int live, int money, String firearm, int mag, int totalAmmo){
        roundLabel.setText("ROUND " + round);
        liveLabel.setText("LIVES " + live);
        moneyLabel.setText("MONEY " + money + "$");

        if(firearm.equals("REVOLVER")){
            currentWeaponLabel.setText("REVOLVER -" + mag + "/" + totalAmmo);
        }
        if(firearm.equals("AUTORIFLE")){
            currentWeaponLabel.setText("AUTO RIFLE -" + mag + "/" + totalAmmo);
        }
        if(firearm.equals("SHOTGUN")){
            currentWeaponLabel.setText("SHOTGUN -" + mag + "/" + totalAmmo);
        }
    }

    /**
     * Disposes of stage after update for memory/performance
     */
    @Override
    public void dispose(){
        stage.dispose();
    }
}