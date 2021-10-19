package com.mygdx.game.desktop.sapiens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.desktop.sapiens.Player;

public class PlayerController implements InputProcessor{
    /**
     * Instance of {@link Player} which the inputs correspond to
     */
    private Player player;

    /**
     * Constructor
     * @param player = player
     */
    public PlayerController(Player player){
        this.player = player;
        Gdx.input.setInputProcessor(this);

    }

    /**
     * Checks which key is being pressed and returns boolean for corresponding action
     * @param keycode = input from keyboard
     * @return boolean
     */
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
                player.setFirearm(0);
                break;
            case Input.Keys.NUM_2 :
                player.setFirearm(1);
                break;
            case Input.Keys.NUM_3 :
                player.setFirearm(2);
                break;
            case Input.Keys.SHIFT_LEFT :
                player.setSprint(true);
                break;
        }
        return true;
    }

    /**
     *  Checks which key(s) has been released and sets boolean for corresponding action to false.
     * @param keycode = keyboard input
     * @return boolean
     */
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
            case Input.Keys.SHIFT_LEFT :
                player.setSprint(false);
                break;
        }
        return true;
    }

    /**
     * Checks if mouse button is clicked and returns true for firing projectiles.
     * @param screenX = where on screen x plane
     * @param screenY = where on screen y plane
     * @param pointer = mouse pointer
     * @param button = button pressed
     * @return boolean
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.setTriggerPulled(true);
        return true;
    }

    /**
     * LIBGDX default method
     * @param character
     * @return boolean
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Checks if mouse button is let go and returns false for firing projectiles.
     * @param screenX = where on screen x plane
     * @param screenY = where on screen y plane
     * @param pointer = mouse pointer
     * @param button = button pressed
     * @return boolean
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.setTriggerPulled(false);
        return true;
    }

    /**
     * Checks if mouse is being dragged and updates angle projectiles are shot at
     * @param screenX = where on screen the mouse is on X-axis
     * @param screenY = where on screen the mouse is on Y-axis
     * @param pointer = mouse pointer
     * @return boolean
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        player.setAimAngle(findAimAngle(screenX, screenY));
        return true;
    }

    /**
     * Checks if mouse is moved and updates angle projectiles are shot at
     * @param screenX = where on screen the mouse is on X-axis
     * @param screenY = where on screen the mouse is on Y-axis
     * @return boolean
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        player.setAimAngle(findAimAngle(screenX, screenY));
        return true;
    }

    /**
     * Finds what angle the player should aim.
     * @param screenX = where on screen the mouse is on X-axis
     * @param screenY = where on screen the mouse is on Y-axis
     * @return boolean
     */
    private int findAimAngle(int screenX, int screenY){
        float adjustedX;
        float adjustedY;
        if (Gdx.graphics.getHeight() >= Gdx.graphics.getWidth()) {
            adjustedX = screenX * ((float)640 * player.scale/Gdx.graphics.getWidth());
            adjustedY = (screenY - (((float)Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()))/2)) * ((float)640 * player.scale/Gdx.graphics.getWidth());

        } else {
            adjustedY = screenY * ((float)640 * player.scale/Gdx.graphics.getHeight());
            adjustedX = (screenX - (((float)Gdx.graphics.getWidth()-(Gdx.graphics.getHeight()))/2)) * ((float)640 * player.scale/Gdx.graphics.getHeight());
        }
        int aimAngle = (int) -Math.toDegrees(Math.atan2(640 * player.scale - player.getY() - adjustedY, adjustedX-player.getX()));
        aimAngle += 90;
        if(aimAngle < 0){
            aimAngle += 360;
        }
        return aimAngle;
    }

    /**
     * Default LIBGDX method
     * @param amountX
     * @param amountY
     * @return boolean = false
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
