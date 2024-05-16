package top.hugongzi.wdw.entity.Player;

import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import top.hugongzi.wdw.core.AnimationManager;
import top.hugongzi.wdw.core.Log;
import top.hugongzi.wdw.gui.Buttons.GameGUI;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerActor extends Actor {
    private final Sprite sprite;
    public String boy = "Player/Boy/player1_te.png";
    public String girl = "Player/Girl/player2_te.png";
    public float speed;
    Player player;
    AnimationManager animationManager;
    Animation<TextureRegion> walkAnimation_s, walkAnimation_n, walkAnimation_e, walkAnimation_w;
    Animation<TextureRegion> stand_s, stand_n, stand_e, stand_w;
    Label namelabel;
    TextureRegion currentFrame;
    float stateTime = 0f;

    public PlayerActor(Player player, Vector2 position, World world) {
        this.player = player;
        this.player.setX(position.x);
        this.player.setY(position.y);
        this.setPosition(position.x, position.y);
        this.speed = player.getSpeed() * 5 * 32;
        animationManager = new AnimationManager();
        ArrayList<Animation<TextureRegion>> animationPlayer = animationManager.createAnimation_player(player.isMale() ? boy : girl, 4, 4, 0.10f);
        walkAnimation_s = animationPlayer.get(0);
        walkAnimation_w = animationPlayer.get(1);
        walkAnimation_e = animationPlayer.get(2);
        walkAnimation_n = animationPlayer.get(3);
        stand_s = animationPlayer.get(4);
        stand_w = animationPlayer.get(5);
        stand_e = animationPlayer.get(6);
        stand_n = animationPlayer.get(7);

        currentFrame = stand_s.getKeyFrame(0);
        player.currentState = PlayerState.STANDING_S;
        sprite = new Sprite(stand_s.getKeyFrame(0));
        namelabel = GameGUI.label(player.getName(), (int) (getX() + sprite.getWidth() / 2), (int) (getY() + sprite.getHeight() + 10), "cubic16", Color.WHITE);

        setBounds(getX(), getY(), animationManager.getRegionWidth(), animationManager.getRegionHeight());

        player.setBody(createBox(world, getX(), getY(), animationManager.getRegionWidth(), animationManager.getRegionHeight(), false));
        Log.i(" " + getX() + " " + getY() + " " + animationManager.getRegionWidth() + " " + animationManager.getRegionHeight());
        player.getBody().setUserData(this);
    }

    public Actor get() {
        return this;
    }

    public Body createBox(World world, float x, float y, float width, float height, boolean isStatic) {
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

    private void get_State() {
        if (player.currentState == null) {
            currentFrame = stand_s.getKeyFrame(stateTime, false);
            return;
        }
        switch (Objects.requireNonNull(player.currentState)) {
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
        float movement = delta * speed * 200;
        player.getBody().setLinearVelocity(player.getCurrentVelocity().cpy().scl(movement));
        this.setPosition(player.getBody().getPosition().x - 32 / 2f - 10, player.getBody().getPosition().y - 32 / 2f);
        player.setX(getX());
        player.setY(getY());
        namelabel.setPosition(getX() + namelabel.getPrefWidth() / 2 - 32 / 2f + 10, getY() + sprite.getWidth() + namelabel.getPrefHeight() + 32 / 2f);
    }

    public void updatePlayerState(PlayerState newState, Vector2 newVelocity) {
        player.setCurrentVelocity(newVelocity);
        player.getBody().setLinearVelocity(player.getCurrentVelocity());
        if (newState == null) {
            switch (player.currentState) {
                case WALKING_N:
                    player.currentState = PlayerState.STANDING_N;
                    break;
                case WALKING_S:
                    player.currentState = PlayerState.STANDING_S;
                    break;
                case WALKING_NE:
                case WALKING_SE:
                case WALKING_E:
                    player.currentState = PlayerState.STANDING_E;
                    break;
                case WALKING_NW:
                case WALKING_SW:
                case WALKING_W:
                    player.currentState = PlayerState.STANDING_W;
                    break;
            }
            reset();
        } else player.currentState = newState;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        trackMovement(delta);
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
        namelabel.draw(batch, parentAlpha);
        //Log.i("draw at" + getX() + " " + getY());
        sprite.draw(batch);
    }
}