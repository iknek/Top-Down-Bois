package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.desktop.weapons.AutoRifle;
import com.mygdx.game.desktop.weapons.Revolver;
import com.mygdx.game.desktop.weapons.Shotgun;

public class PlayerController implements InputProcessor{
    private Player player;

    public PlayerController(Player player){
        this.player = player;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                player.setLeft(true);
                break;
            case Input.Keys.D:
                player.setRight(true);
                break;
            case Input.Keys.W:
                player.setUp(true);
                break;
            case Input.Keys.S:
                player.setDown(true);
                break;
            //Behövs detta forfarande? \/
            case Input.Keys.G:
                player.setTriggerPulled(true);
                break;
            case Input.Keys.R:
                player.reload();
                break;
            case Input.Keys.NUM_1 :
                player.setFirearm(new Revolver(player.scale));
                break;
            case Input.Keys.NUM_2 :
                player.setFirearm(new Shotgun(player.scale));
                break;
            case Input.Keys.NUM_3 :
                player.setFirearm(new AutoRifle(player.scale));
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                player.setLeft(false);
                break;
            case Input.Keys.D:
                player.setRight(false);
                break;
            case Input.Keys.W:
                player.setUp(false);
                break;
            case Input.Keys.S:
                player.setDown(false);
                break;
            case Input.Keys.G:
                player.setTriggerPulled(false);
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.setTriggerPulled(true);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.setTriggerPulled(false);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int adjustedX = (int) player.getX()*(Gdx.graphics.getWidth()/640);
        int adjustedY = (int) player.getY()*(Gdx.graphics.getHeight()/640);

        int aimAngle = (int) -Math.toDegrees(Math.atan2(Gdx.graphics.getHeight()-adjustedY-screenY, screenX-adjustedX));
        aimAngle += 90;
        if(aimAngle < 0){
            aimAngle += 360;
        }
        player.setAimAngle(aimAngle);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        int adjustedX = (int) player.getX()*(Gdx.graphics.getWidth()/640);
        int adjustedY = (int) player.getY()*(Gdx.graphics.getHeight()/640);

        int aimAngle = (int) -Math.toDegrees(Math.atan2(Gdx.graphics.getHeight()-adjustedY-screenY, screenX-adjustedX));
        aimAngle += 90;
        if(aimAngle < 0){
            aimAngle += 360;
        }
        player.setAimAngle(aimAngle);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
