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

        final Stack stack = new Stack();

        //Defines images
        storeImage = new Image(new Texture(Gdx.files.internal("Store/store2.png")));
        stack.add(storeImage);

        //Button skins for when clicked and not clicked
        /*TextureRegion button_down = new TextureRegion(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        TextureRegion button_up = new TextureRegion(new Texture(Gdx.files.internal("Store/buttonUp.png")));
        skin = new Skin();
        skin.setScale((scale*9)/20);

        skin.add("buttonDown", button_down);
        skin.add("buttonUp", button_up);

        //Sets skins for up/down
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = skin.getDrawable("buttonUp");
        buttonStyle.down = skin.getDrawable("buttonDown");

        Table buttonTable = new Table();
        buttonTable.left();
        buttonTable.padLeft(42*scale);
        buttonTable.top();
        buttonTable.padTop(240*scale);
        //Creates and adds button to table
        button = new Button(buttonStyle);
        buttonTable.add(button);

        stack.add(buttonTable);*/

        final Table table = new Table();
        table.center();
        table.setWidth(640*scale);
        //table.setHeight(640*scale);
        table.scaleBy(scale);

        // Pos of table, size, and alignment
        table.setPosition(0,320);
        table.align(Align.center);

        table.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                int row = -1;
                int col = -1;
                if (y>5*scale && y<15*scale){
                    row=0;
                } else if(y<-46.5*scale && y>-56.5*scale){
                    row=1;
                } else if(y<-106.5*scale && y>-116.5*scale){
                    row=2;
                }
                if (x>42*scale && x<(42 + 50)*scale){
                    col=0;
                } else if(x>138*scale && x<183*scale){
                    col=1;
                } else if(x>230*scale && x<275*scale){
                    col=2;
                }
                System.out.println(row + " " + col);
                buy(row*3 + col);
            }
        });

        table.add(storeImage).size(540,540);
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

    private void buy(int index){
        System.out.println(index);
        switch(index){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                break;
        }
    }
}