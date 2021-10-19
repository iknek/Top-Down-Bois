package com.mygdx.game.desktop.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Store implements Disposable{

    //Scene2D Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Scene2D widgets
    private Image storeImage;
    private Button button;

    private float scale;

    private Skin skin;

    /**
     * Constructor that does everything associated with the table and labels. See sub-comments.
     * @param sb spritebatch for every object in table
     * @param progScale scale of program
     */
    public Store(SpriteBatch sb, int progScale){
        scale = progScale;

        viewport = new FitViewport(640*scale, 640*scale, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.center();
        table.setWidth(640*scale);
        //table.setHeight(640*scale);
        table.scaleBy(scale);

        // Pos of table, size, and alignment
        table.setPosition(0,320);
        table.align(Align.center);

        //Defines images
        storeImage = new Image(new Texture(Gdx.files.internal("Store/store2.png")));
        table.add(storeImage).size(540,540);

        //Button skins for when clicked and not clicked
        TextureRegion button_down = new TextureRegion(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        TextureRegion button_up = new TextureRegion(new Texture(Gdx.files.internal("Store/buttonUp.png")));
        skin = new Skin();

        skin.add("buttonDown", button_down);
        skin.add("buttonUp", button_up);

        //Sets skins for up/down
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = skin.getDrawable("buttonUp");
        buttonStyle.down = skin.getDrawable("buttonDown");

        //Creates and adds button to table
        button = new Button(buttonStyle);

        //Listener for click
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.row();
        table.add(button);
        //Adds table to stage
        stage.addActor(table);

    }

    public void update(){

    }

    /**
     * Disposes of stage after update for memory/performance
     */
    @Override
    public void dispose(){
        stage.dispose();
    }
}