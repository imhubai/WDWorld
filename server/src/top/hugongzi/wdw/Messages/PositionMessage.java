package top.hugongzi.wdw.Messages;

/***
 * Whenever player press a button from a keyboard this message should be send.
 */
public class PositionMessage {

    /**
     * Player ID
     */
    private String id;
    /**
     * The direction that player wants to move. Server will check the direction and
     * let player move if its possible.
     */
    private DIRECTION direction;

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public enum DIRECTION {
        LEFT, RIGHT, DOWN, UP
    }
}
