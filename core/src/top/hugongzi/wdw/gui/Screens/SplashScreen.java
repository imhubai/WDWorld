package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.Log;

/**
 * 开始屏幕
 *
 * @author Hubai
 */
public class SplashScreen extends AbstractScreen {
    Texture texture;
    Image image;
    float delta = 0;
    /**
     * game为游戏入口
     */
    Game game;

    public SplashScreen(Game game) {
        this.game = game;
    }

    @Override
    public void create() {
        stage = GameEntry.stage();
        texture = new Texture(Gdx.files.internal("Images/640_pixel_logo.png"));
        image = new Image(new TextureRegion(texture));
        image.setPosition((float) GameEntry.GAMEWIDTH / 2 - (float) texture.getWidth() / 2, (float) GameEntry.GAMEHEIGHT / 2 - (float) texture.getHeight() / 2);
        stage.addActor(image);
        image.getColor().a = 0F;
        AlphaAction action = Actions.alpha(1F, 1F);
        image.addAction(action);
        GameEntry.loginScreen = new LoginScreen();
        GameEntry.loginScreen.create();
        GameEntry.overlapScreen = new OverlapScreen();
        GameEntry.overlapScreen.create();
    }

    @Override
    public void draw() {
        stage.act();
        stage.draw();
    }

    @Override
    public void act() {
        render(delta+=Gdx.graphics.getDeltaTime());
        Log.d("Splash time:"+delta);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        if (v >= 2f && v < 4f) {
            AlphaAction action2 = Actions.alpha(0F, 0.7F);
            image.addAction(action2);
        } else if (v >= 4f) {
            GameEntry.addScreen(GameEntry.loginScreen);
            GameEntry.addScreen(GameEntry.overlapScreen);
            this.dispose();
            remove();
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
