package top.hugongzi.wdw.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

public class Player extends Actor {

    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private final Sprite sprite;
    public float speed = 5 * 32;
    Animation<TextureRegion> walkAnimation_s, walkAnimation_n, walkAnimation_e, walkAnimation_w;
    Animation<TextureRegion> stand_s, stand_n, stand_e, stand_w;
    Texture walkSheet;
    TextureRegion currentFrame;
    float stateTime;
    PlayerState currentState;
    private Body physicalBody;
    private Vector2 currentVelocity = new Vector2(0, 0);
    private float time = 0f;

    public Player(Vector2 startPosition, World world) {
        super();
        walkSheet = new Texture(Gdx.files.internal("Player/Boy/player1_te.png"));
        TextureRegion[][] walkFrames = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);

        walkAnimation_s = new Animation<>(0.25f, walkFrames[0]);
        walkAnimation_w = new Animation<>(0.25f, walkFrames[1]);
        walkAnimation_e = new Animation<>(0.25f, walkFrames[2]);
        walkAnimation_n = new Animation<>(0.25f, walkFrames[3]);
        stand_s = new Animation<>(0.25f, walkFrames[0][0]);
        stand_w = new Animation<>(0.25f, walkFrames[1][0]);
        stand_e = new Animation<>(0.25f, walkFrames[2][0]);
        stand_n = new Animation<>(0.25f, walkFrames[3][0]);
        stateTime = 0f;
        currentFrame = stand_s.getKeyFrame(0);
        currentState = PlayerState.STANDING_S;
        sprite = new Sprite(stand_s.getKeyFrame(0));
        setBounds(startPosition.x, startPosition.y, walkFrames[0][0].getRegionWidth(), walkFrames[0][0].getRegionHeight());

        physicalBody = createBox(world, startPosition.x, startPosition.y, walkFrames[0][0].getRegionWidth(), walkFrames[0][0].getRegionHeight(), false);
        Log.i(" " + startPosition.x + " " + startPosition.y + " " + walkFrames[0][0].getRegionWidth() + " " + walkFrames[0][0].getRegionHeight());
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
        shape.setAsBox(width / 3f, height / 4f);
        pBody.createFixture(shape, 1f);
        shape.dispose();
        return pBody;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += Gdx.graphics.getDeltaTime();
        trackMovement(delta);
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void reset() {
        setTime(0f);
        stateTime = 0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
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
        sprite.setRegion(currentFrame);
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    private void trackMovement(float delta) {
        float movement = delta * speed;
        physicalBody.setLinearVelocity(currentVelocity.cpy().scl(movement));

        this.setPosition(physicalBody.getPosition().x-32 / 2f-5, physicalBody.getPosition().y - 32 / 2f);
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
        Log.i(currentState.toString() + " " + stateTime);
    }
}
