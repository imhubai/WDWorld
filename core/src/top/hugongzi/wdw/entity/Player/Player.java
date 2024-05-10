package top.hugongzi.wdw.entity.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import top.hugongzi.wdw.core.AnimationManager;
import top.hugongzi.wdw.core.Log;

import java.util.ArrayList;

/**
 * Player类，玩家自身
 */
public class Player extends Actor {

    private final Sprite sprite;
    public float speed = 5 * 32;
    AnimationManager animationManager;
    Animation<TextureRegion> walkAnimation_s, walkAnimation_n, walkAnimation_e, walkAnimation_w;
    Animation<TextureRegion> stand_s, stand_n, stand_e, stand_w;
    TextureRegion currentFrame;
    float stateTime = 0f;
    PlayerState currentState;
    private PlayerData playerData;
    private Body physicalBody;
    private Vector2 currentVelocity = new Vector2(0, 0);

    public Player(Vector2 position, World world) {
        ArrayList<Animation<TextureRegion>> animationPlayer = animationManager.createAnimation_player("Player/Boy/player1_te.png", 4, 4, 0.25f);
        walkAnimation_s = animationPlayer.get(0);
        walkAnimation_w = animationPlayer.get(1);
        walkAnimation_e = animationPlayer.get(2);
        walkAnimation_n = animationPlayer.get(3);
        stand_s = animationPlayer.get(4);
        stand_w = animationPlayer.get(5);
        stand_e = animationPlayer.get(6);
        stand_n = animationPlayer.get(7);

        currentFrame = stand_s.getKeyFrame(0);
        currentState = PlayerState.STANDING_S;
        sprite = new Sprite(stand_s.getKeyFrame(0));

        setBounds(position.x, position.y, animationManager.getRegionWidth(), animationManager.getRegionHeight());

        physicalBody = createBox(world, position.x, position.y, animationManager.getRegionWidth(), animationManager.getRegionHeight(), false);
        Log.i(" " + position.x + " " + position.y + " " + animationManager.getRegionWidth() + " " + animationManager.getRegionHeight());
        physicalBody.setUserData(this);
    }

    public static Body createBox(World world, float x, float y, float width, float height, boolean isStatic) {
        BodyDef def = new BodyDef();
        if (isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }
        def.position.set(x, y);
        def.fixedRotation = true;
        Body pBody = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 4f, height / 4f);
        pBody.createFixture(shape, 1f);
        shape.dispose();
        return pBody;
    }

    public Body getPhysicalBody() {
        return physicalBody;
    }

    public void setPhysicalBody(Body physicalBody) {
        this.physicalBody = physicalBody;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += Gdx.graphics.getDeltaTime();
        // trackMovement(delta);
    }

    public void setTime(float time) {
        stateTime = time;
    }

    public void reset() {
        setTime(0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        get_State();
        sprite.setRegion(currentFrame);
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    public void update(float delta) {
        trackMovement(delta);
    }

    private void get_State() {
        switch (currentState) {
            case WALKING_N:
                currentFrame = walkAnimation_n.getKeyFrame(stateTime, true);
                break;
            case STANDING_N:
                currentFrame = stand_n.getKeyFrame(stateTime, false);
                break;
            case WALKING_S:
                currentFrame = walkAnimation_s.getKeyFrame(stateTime, true);
                break;
            case STANDING_S:
                currentFrame = stand_s.getKeyFrame(stateTime, false);
                break;
            case WALKING_NE:
            case WALKING_SE:
            case WALKING_E:
                currentFrame = walkAnimation_e.getKeyFrame(stateTime, true);
                break;
            case STANDING_E:
                currentFrame = stand_e.getKeyFrame(stateTime, false);
                break;
            case WALKING_NW:
            case WALKING_SW:
            case WALKING_W:
                currentFrame = walkAnimation_w.getKeyFrame(stateTime, true);
                break;
            case STANDING_W:
                currentFrame = stand_w.getKeyFrame(stateTime, false);
                break;
            default:
                Log.e("", new IllegalArgumentException());
        }
    }

    private void trackMovement(float delta) {
        float movement = delta * speed;
        physicalBody.setLinearVelocity(currentVelocity.cpy().scl(movement));
        this.setPosition(physicalBody.getPosition().x - 32 / 2f - 10, physicalBody.getPosition().y - 32 / 2f);
    }

    public void updatePlayerState(PlayerState newState, Vector2 newVelocity) {
        currentVelocity = newVelocity;
        physicalBody.setLinearVelocity(currentVelocity);
        if (newState == null) {
            switch (currentState) {
                case WALKING_N:
                    currentState = PlayerState.STANDING_N;
                    break;
                case WALKING_S:
                    currentState = PlayerState.STANDING_S;
                    break;
                case WALKING_NE:
                case WALKING_SE:
                case WALKING_E:
                    currentState = PlayerState.STANDING_E;
                    break;
                case WALKING_NW:
                case WALKING_SW:
                case WALKING_W:
                    currentState = PlayerState.STANDING_W;
                    break;
            }
            reset();
        } else currentState = newState;
    }
}
