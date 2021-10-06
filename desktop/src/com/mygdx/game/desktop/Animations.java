package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

public class Animations extends ApplicationAdapter{
    private Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;
    private Player player;
    private CollisionController collisionController = new CollisionController();

    public Animations (Batch batch, Player player) {
        this.batch = batch;
        this.player = player;
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    private void renderRunning(){
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
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle3/running/running.atlas"));
                break;
            case 270:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Left/running/running.atlas"));
                break;
            case 315:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle4/running/running.atlas"));
                break;
        }
    }

    private void renderIdle(){
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
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle3/idle/idle.atlas"));
                break;
            case 270:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Left/idle/idle.atlas"));
                break;
            case 315:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle4/idle/idle.atlas"));
                break;
        }
    }

    public void render () {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(player.moving()){
            renderRunning();
        }
        /* Shooting
        if(player.isShooting() && !collisionController.playerZombieCollision(player)){ //TODO doesn't render shooting if standing still?? maybe
            renderShooting();
        }*/

        /** Hit */
        if(collisionController.playerZombieCollision(player)){
            textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/dead/dead.atlas")); //TODO Controlls should be locked for length of animation? Otherwise they dont fully play.
        }

        /** Idle */
        else if(!player.moving() && !collisionController.playerZombieCollision(player)){
            renderIdle();
        }

        //batch.begin();
        animation = new Animation(1f/10f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),player.getX(),player.getY(),75/2,75/2);
        //batch.end();
    }

    /*
    private void renderShooting(){
        switch(player.angle){
            case 0:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Back/shooting/shooting.atlas"));
                break;
            case 45:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle2/shooting/shooting.atlas"));
                break;
            case 90:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Side/shooting/shooting.atlas"));
                break;
            case 135:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/shooting/shooting.atlas"));
                break;
            case 180:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Front/shooting/shooting.atlas"));
                break;
            case 225:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/shooting/shooting.atlas"));
                break;
            case 270:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Left/shooting/shooting.atlas"));
                break;
            case 315:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle2/shooting/shooting.atlas"));
                break;
        }
    }*/

}
