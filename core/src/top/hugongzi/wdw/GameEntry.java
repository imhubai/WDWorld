package top.hugongzi.wdw;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import top.hugongzi.wdw.fcore.Log;
import top.hugongzi.wdw.gui.Screens.AbstractScreen;
import top.hugongzi.wdw.gui.Screens.LoginScreen;
import top.hugongzi.wdw.gui.Text.Font;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

/**
 * 游戏入口,全局事件柄,全局绘制
 *
 * @author Hubai
 * @version 1.0
 */
public class GameEntry implements ApplicationListener, InputProcessor {
    public static String CLASSNAME = GameEntry.class.getSimpleName();
    public static String GAMENAME = "wdw";
    public static String GAMEVERSION = "t0.2.7";
    public static int GAMEWIDTH, GAMEHEIGHT;

    public static SpriteBatch batch;
    public static Input input;
    public static Hashtable<String,BitmapFont> font;

    private static Stack<AbstractScreen> screens = new Stack<>();
    private static List<AbstractScreen> InsertScreens = new ArrayList<>();

    /**
     * 在待渲染列表中加入新屏幕，等待下一帧渲染
     *
     * @param screen 加入渲染列表的屏幕
     */
    public static void addScreen(AbstractScreen screen) {
        InsertScreens.add(0, screen);
        screen.removeable(false);
        Log.i("Screens << " + screen);
    }

    /**
     * 为继承绝对屏幕的屏幕提供初始化stage
     */
    public static Stage stage() {
        return new Stage(viewport(), GameEntry.batch);
    }

    /**
     * 为继承绝对屏幕的屏幕提供初始化viewport
     */
    public static ScalingViewport viewport() {
        /*return new ScalingViewport(Game.setting.fitScaling ? Scaling.fit : Scaling.stretch, Game.STAGE_WIDTH, Game.STAGE_HEIGHT, new OrthographicCamera());*/
        return new ScalingViewport(Scaling.fit, GAMEWIDTH, GAMEHEIGHT, new OrthographicCamera());
    }

    public void create() {
        //wdw,启动!
        Log.i(CLASSNAME + " -> create()");
        Log.i(" _ _ _ ____  _ _ _         _   _");
        Log.i("| | | |    \\| | | |___ ___| |_| |");
        Log.i("| | | |  |  | | | | . |  _| | . |");
        Log.i("|_____|____/|_____|___|_| |_|___|");
        Log.i(GAMENAME + " - " + GAMEVERSION + " Running!");

        batch = new SpriteBatch();
        font = new Font().getFont();
        GAMEWIDTH = Gdx.graphics.getWidth();
        GAMEHEIGHT = Gdx.graphics.getHeight();
        Log.i("GAME WIDTH:" + GAMEWIDTH + " GAME HEIGHT:" + GAMEHEIGHT);
        Gdx.input.setInputProcessor(this);

        LoginScreen loginScreen = new LoginScreen();
        loginScreen.create();
        addScreen(loginScreen);
    }

    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //如果屏幕被标记为IsMarkedRemove，在当前屏幕列表中删除
        screens.removeIf(AbstractScreen::removeable);
        screens.addAll(InsertScreens);
        InsertScreens.clear();

        for (AbstractScreen s : screens) {
            Log.d("screen draw << " + s);
            s.act();
            s.draw();
        }
    }

    public void resize(int width, int height) {
        Log.i(CLASSNAME + " -> resize()");
        Log.i("Window Resized to " + width + " x " + height);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int i) {
        return screens.peek().keyDown(i);
    }

    @Override
    public boolean keyUp(int i) {
        return screens.peek().keyUp(i);
    }

    @Override
    public boolean keyTyped(char c) {
        return screens.peek().keyTyped(c);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return screens.peek().touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return screens.peek().touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return screens.peek().touchCancelled(i, i1, i2, i3);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return screens.peek().touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return screens.peek().mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return screens.peek().scrolled(v, v1);
    }

}
