package com.mygdx.game.desktop;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
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

    private int rounds;
    private int lives;
    private int money;

    //Scene2D widgets
    private Label roundLabel;
    private Label liveLabel;
    private Label moneyLabel;
    private float scale;

    public Hud(SpriteBatch sb, int progScale){

        scale = progScale;


        BitmapFont font = new BitmapFont(Gdx.files.internal("Font/font.fnt"), Gdx.files.internal("Font/font.png"),false);

        viewport = new FitViewport(640*scale, 640*scale, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        //Top-Align table
        table.bottom();
        table.padBottom(50);
        table.scaleBy(scale);
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        roundLabel = new Label(String.format("%03d", rounds), new Label.LabelStyle(font, Color.WHITE));
        liveLabel = new Label(String.format("%03d", lives), new Label.LabelStyle(font, Color.WHITE));
        moneyLabel = new Label(String.format("%03d", money), new Label.LabelStyle(font, Color.WHITE));

        // Pos
        liveLabel.setPosition(50,100);

        // Scale
        liveLabel.setFontScale(scale);
        roundLabel.setFontScale(scale);
        moneyLabel.setFontScale(scale);

        //Adds labels to table, padding the bottom, and giving them all equal width with expandX
        table.add(roundLabel).expandX().padBottom(10);
        table.add(liveLabel).expandX().padBottom(10);

        //add a second row to our table
        table.row();
        table.add(moneyLabel).expandX();
        //table.add(roundLabel).expandX();
        //table.add(liveLabel).expandX();

        //add our table to the stage
        stage.addActor(table);
    }

    public void update(int round, int live, int money){
        roundLabel.setText("ROUND " + round);
        liveLabel.setText("LIVES " + live);
        moneyLabel.setText("MONEY " + money + "$");
    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}