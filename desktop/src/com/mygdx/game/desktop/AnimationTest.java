package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

public class AnimationTest extends ApplicationAdapter{
    Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;
    private Sapien player;
    private Zombie zombie;
    private CollisionController collisionController = new CollisionController();

    public void create (Batch batch, String string, Sapien player) {
        this.batch = batch;
        this.player = player;
        textureAtlas = new TextureAtlas(Gdx.files.internal(string));
    }

    public TextureAtlas getTextureAtlas(){
        return new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    public void render (Player player) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(player.moving()){
            switch(player.angle){
                case 0:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Back/running/BackRunningSheet.atlas"));
                    break;
                case 45:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle2/running/Angle2RunningSheet.atlas"));
                    break;
                case 90:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Side/running/SideRunningSheet.atlas"));
                    break;
                case 135:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));
                    break;
                case 180:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Front/running/FrontRunningSheet.atlas"));
                    break;
                case 225:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));
                    break;
                case 270:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Left/running/running.atlas"));
                    break;
                case 315:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Left/running/running.atlas"));
                    break;
            }
        }
        if(collisionController.playerZombieCollision(player)){ // If player is hit
            textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/dead/dead.atlas")); //TODO Controlls should be locked for length of animation!!! Otherwise they dont fully play
        }
        else if(!player.moving() && !collisionController.playerZombieCollision(player)){ //If not hit or moving
            switch (player.angle){
                case 0:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Back/idle/idle.atlas"));
                    break;
                case 45:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle2/idle/idle.atlas"));
                    break;
                case 90:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Side/idle/idle.atlas"));
                    break;
                case 135:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/idle/idle.atlas"));
                    break;
                case 180:
                    textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Front/idle/idle.atlas"));
                    break;
                case 225:
                case 270:
                case 315:
            }
        }
        batch.begin();
        animation = new Animation(1f/10f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),player.getX(),player.getY(),75,75);
        batch.end();
    }
}
