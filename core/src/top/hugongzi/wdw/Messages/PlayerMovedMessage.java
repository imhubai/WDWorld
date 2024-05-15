package top.hugongzi.wdw.Messages;

import com.badlogic.gdx.math.Vector2;
import top.hugongzi.wdw.entity.Player.PlayerState;

public class PlayerMovedMessage {
    private String playerid;
    private float x, y;
    private PlayerState currentState;
    private Vector2 CurrentVelocity;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(PlayerState currentState) {
        this.currentState = currentState;
    }

    public Vector2 getCurrentVelocity() {
        return CurrentVelocity;
    }

    public void setCurrentVelocity(Vector2 currentVelocity) {
        CurrentVelocity = currentVelocity;
    }

    private String map;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }
}
