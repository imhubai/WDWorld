package top.hugongzi.wdw.entity.Player;

import com.badlogic.gdx.math.Vector2;
import top.hugongzi.wdw.core.Log;

public enum PlayerState {
    STANDING_S, STANDING_N, STANDING_E, STANDING_W,
    WALKING_S, WALKING_N, WALKING_E, WALKING_W, WALKING_NE, WALKING_SE, WALKING_SW, WALKING_NW,
    SITTING_W,
    PICKUP_S, PICKUP_N, PICKUP_E, PICKUP_W,
    HOLD_S,
    ;

    private static final float ONE_ON_ROOT_TWO = (float) (1.0 / Math.sqrt(2));

    public Vector2 calculateDirectionVector() {
        switch (this) {
            case WALKING_N:
            case STANDING_N:
                return new Vector2(0, 1);
            case WALKING_S:
            case STANDING_S:
                return new Vector2(0, -1);
            case WALKING_E:
            case STANDING_E:
                return new Vector2(1, 0);
            case WALKING_W:
            case STANDING_W:
                return new Vector2(-1, 0);
            case WALKING_NE:
                return new Vector2(ONE_ON_ROOT_TWO, ONE_ON_ROOT_TWO);
            case WALKING_NW:
                return new Vector2(-ONE_ON_ROOT_TWO, ONE_ON_ROOT_TWO);
            case WALKING_SE:
                return new Vector2(ONE_ON_ROOT_TWO, -ONE_ON_ROOT_TWO);
            case WALKING_SW:
                return new Vector2(-ONE_ON_ROOT_TWO, -ONE_ON_ROOT_TWO);
            case SITTING_W:
            case PICKUP_S:
            case PICKUP_E:
            case PICKUP_W:
            case PICKUP_N:
            case HOLD_S:
                return new Vector2(0, 0);
            default:
                Log.e("", new IllegalArgumentException());
        }
        return new Vector2(0, 0);
    }
}