package top.hugongzi.wdw.listener;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.entity.Player.PlayerState;

import java.util.HashSet;
import java.util.Set;

public class FreeRoamingMovementListener extends InputListener {
    private final Player player;
    private final Set<Integer> pressedKeyCodes = new HashSet<>();

    public FreeRoamingMovementListener(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        pressedKeyCodes.add(keycode);
        PlayerState state = getPlayerStateBasedOnCurrentlyPressedKeys();
        if (state == null) {
            pressedKeyCodes.remove(keycode);
            return false;
        }
        Vector2 newVelocity = state.calculateDirectionVector();
        //player.updatePlayerState(state, newVelocity);
        return true;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        pressedKeyCodes.remove(keycode);
        PlayerState state = getPlayerStateBasedOnCurrentlyPressedKeys();
        Vector2 newVelocity = Vector2.Zero;
        if (state != null) {
            newVelocity = state.calculateDirectionVector();
        }
        //player.updatePlayerState(state, newVelocity);
        return true;
    }

    private PlayerState getPlayerStateBasedOnCurrentlyPressedKeys() {
        if (pressedKeyCodes.contains(Input.Keys.UP)) {
            if (pressedKeyCodes.contains(Input.Keys.RIGHT)) {
                return PlayerState.WALKING_NE;
            } else if (pressedKeyCodes.contains(Input.Keys.LEFT)) {
                return PlayerState.WALKING_NW;
            } else {
                return PlayerState.WALKING_N;
            }
        } else if (pressedKeyCodes.contains(Input.Keys.DOWN)) {
            if (pressedKeyCodes.contains(Input.Keys.RIGHT)) {
                return PlayerState.WALKING_SE;
            } else if (pressedKeyCodes.contains(Input.Keys.LEFT)) {
                return PlayerState.WALKING_SW;
            } else {
                return PlayerState.WALKING_S;
            }
        } else if (pressedKeyCodes.contains(Input.Keys.RIGHT)) {
            return PlayerState.WALKING_E;
        } else if (pressedKeyCodes.contains(Input.Keys.LEFT)) {
            return PlayerState.WALKING_W;
        } else {
            return null;
        }
    }
}