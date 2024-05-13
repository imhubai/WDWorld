package top.hugongzi.wdw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import top.hugongzi.wdw.core.Font;
import top.hugongzi.wdw.core.Log;
import top.hugongzi.wdw.gui.Screens.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 游戏入口,全局事件柄,全局绘制
 *
 * @author Hubai
 * @version 1.0
 */
public class GameEntry extends Game implements InputProcessor {
    public static String CLASSNAME = GameEntry.class.getSimpleName();
    public static String GAMENAME = "wdw";
    public static String GAMEVERSION = "t0.3.6";
    public static int GAMEWIDTH, GAMEHEIGHT;

    public static SpriteBatch batch;
    public static ArrayMap<String, BitmapFont> font;
    public static AbstractScreen maingame;
    public static ScreenLogin screenLogin;
    public static ScreenSplash screenSplash;
    public static ScreenOverlap screenOverlap;

    public static Vector<AbstractScreen> screens = new Vector<>();
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

        //初始化屏幕
        screenSplash = new ScreenSplash(this);
        screenSplash.create();
        addScreen(screenSplash);
    }

    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        prepareScreen();
    }

    /**
     * 屏幕栈操作,删除被标记的屏幕,添加已缓存的屏幕
     */
    private void prepareScreen() {
        //如果屏幕被标记为IsMarkedRemove，在当前屏幕列表中删除
        screens.removeIf(AbstractScreen::removeable);
        screens.addAll(InsertScreens);
        InsertScreens.clear();
        //如果空栈,初始化一个崩溃界面
        if (screens.isEmpty()) {
            Log.e("CRASHED:Stack has 0 screen to render()", new Throwable());
            ScreenCrash screenCrash = new ScreenCrash();
            screenCrash.create();
            screens.add(screenCrash);
        }
        for (AbstractScreen s:screens) {
            Log.d("screen draw << " + s);
            s.act();
            s.draw();
        }
    }

    public void resize(int width, int height) {
        Log.i(CLASSNAME + " -> resize()");
        Log.i("Window Resized to " + width + " x " + height);
        GAMEHEIGHT = height;
        GAMEWIDTH = width;
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
        return screens.lastElement().keyDown(i);
    }

    @Override
    public boolean keyUp(int i) {
        return screens.lastElement().keyUp(i);
    }

    @Override
    public boolean keyTyped(char c) {
        return screens.lastElement().keyTyped(c);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return screens.lastElement().touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return screens.lastElement().touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return screens.lastElement().touchCancelled(i, i1, i2, i3);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return screens.lastElement().touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return screens.lastElement().mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return screens.lastElement().scrolled(v, v1);
    }

}
