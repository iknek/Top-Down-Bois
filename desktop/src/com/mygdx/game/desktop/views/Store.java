package com.mygdx.game.desktop.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.desktop.coins.CoinSubject;
import com.mygdx.game.desktop.sapiens.Player;
import com.mygdx.game.desktop.weapons.*;

import java.util.ArrayList;

public class Store implements Disposable{

    //Scene2D Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Scene2D widgets
    private Image storeImage;

    private Image buttonImageUp;

    private Image buttonImageDown;
    private Image buttonImageDown2;
    private Image buttonImageDown3;
    private Image buttonImageDown4;
    private Image buttonImageDown5;
    private Image buttonImageDown6;
    private Image buttonImageDown7;
    private Image buttonImageDown8;
    private Image buttonImageDown9;
    private Image exitImage;

    private float scale;
    private ArrayList<Image> buttonList = new ArrayList<>();

    private Player player;
    final private Table table;


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

        //Defines image for store.
        storeImage = new Image(new Texture(Gdx.files.internal("Store/store3.png")));

        //Creates table for all the images ("buttons").
        table = new Table();

        table.setFillParent(true);

        //Method calls for image logic.
        createButtons();
        setButtonPos(table);
        addButtonListeners(table);

        //Adds store and table to stage
        storeImage.setPosition(50*scale,50*scale);
        storeImage.setSize(540*scale,540*scale);
        stage.addActor(storeImage);
        stage.addActor(table);
    }

    /**
     * Loops thru all the listeners and adds a listener to them, so that we can check if a player clicks on a "button".
     * if touch up/down, the image is temporarily replaced with a 10% bigger one to give the user feedback.
     * @param table = table that the actors are added/removed from.
     * Note: Method also calls "pressButton" so that the code knows which button has been pressed.
     */
    private void addButtonListeners(final Table table){
        for(int i = 0; i < 10; i++){
            final int finalI = i;
            buttonList.get(i).addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    table.removeActor(buttonImageUp);
                    table.addActor(buttonList.get(finalI));
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    table.removeActor(buttonList.get(finalI));
                    buttonImageUp.setPosition(buttonList.get(finalI).getX()-9, buttonList.get(finalI).getY()-2);
                    table.addActor(buttonImageUp);
                    pressButton(finalI);
                    return true;
                }
            });
        }
    }

    /**
     * Creates all the instances of images (buttons) needed for shop, with an image for both when pressed and not pressed.
     * These are added to a list.
     */
    private void createButtons(){
        buttonImageUp = new Image(new Texture(Gdx.files.internal("Store/buttonUp.png")));

        buttonImageDown = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        buttonImageDown2 = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        buttonImageDown3 = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        buttonImageDown4 = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        buttonImageDown5 = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        buttonImageDown6 = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        buttonImageDown7 = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        buttonImageDown8 = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));
        buttonImageDown9 = new Image(new Texture(Gdx.files.internal("Store/buttonDown.png")));

        exitImage = new Image(new Texture(Gdx.files.internal("Store/exitSign.png")));

        buttonList.add(buttonImageDown);
        buttonList.add(buttonImageDown2);
        buttonList.add(buttonImageDown3);
        buttonList.add(buttonImageDown4);
        buttonList.add(buttonImageDown5);
        buttonList.add(buttonImageDown6);
        buttonList.add(buttonImageDown7);
        buttonList.add(buttonImageDown8);
        buttonList.add(buttonImageDown9);
        buttonList.add(exitImage);
    }

    /**
     * Sets buttonImage position in table and adds image to table as an actor
     * @param table = Table containing all the images of buttons.
     */
    private void setButtonPos(Table table){
        buttonImageUp.setPosition(50*scale,50*scale);
        buttonImageUp.setSize(201*scale/2,46*scale/2);

        buttonImageDown.setPosition(91*scale,87*scale);
        buttonImageDown2.setPosition(91*scale,208*scale);
        buttonImageDown3.setPosition(91*scale,329*scale);

        buttonImageDown4.setPosition(274*scale,87*scale);
        buttonImageDown5.setPosition(274*scale,208*scale);
        buttonImageDown6.setPosition(274*scale,329*scale);

        buttonImageDown7.setPosition((274+183)*scale,87*scale);
        buttonImageDown8.setPosition((274+183)*scale,208*scale);
        buttonImageDown9.setPosition((274+183)*scale,329*scale);

        exitImage.setPosition(533*scale,601*scale);

        for(Image button: buttonList){
            button.setSize(183*scale/2, 42*scale/2);
            table.addActor(button);
        }
        exitImage.setSize(213*scale/2,80*scale/2);
    }

    /**
     * Disposes of stage after update for memory/performance
     */
    @Override
    public void dispose(){
        stage.dispose();
    }

    /**
     * Sets input proccessor to the stage when the shop is opened after a round, and draws the stage.
     * @param player = player
     */
    public void open(Player player){
        this.player = player;
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Logic for what each button does. In all cases, they call a method in player that corresponds to the purchase, also passing a price so that it can be removed
     * from the players' money.
     * @param index
     */
    private void pressButton(int index){
        System.out.println(index);
        switch(index){
            case 0:
                player.giveWeapon(new AutoRifle(scale),100);
                break;
            case 1:
                player.giveWeapon(new Shotgun(scale),50);
                break;
            case 2:
                player.giveWeapon(new Revolver(scale),25);
                break;
            case 3:
                player.giveFullAmmoAutorifle(new AutoRifle(scale), 75);
                break;
            case 4:
                player.giveFullAmmoShotgun(new Shotgun(scale), 35);
                break;
            case 5:
                player.giveFullAmmoRevolver(new Revolver(scale), 15);
                break;
            case 6:
                player.setDouble(true, 200);
                break;
            case 7:
                player.giveFasterSprint(100);
                break;
            case 8:
                player.strongerMagnet(50);
                break;
            case 9:
                table.addActor(exitImage);
                View.getInstance().closeShop(player);
                break;
            default:
                break;
        }
    }

}