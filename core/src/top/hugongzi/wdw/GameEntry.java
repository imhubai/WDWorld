package top.hugongzi.wdw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import top.hugongzi.wdw.core.Log;
import top.hugongzi.wdw.gui.Screens.*;
import top.hugongzi.wdw.lazyfont.Font;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏入口，全局事件处理器，全局绘制
 *
 * @author Hubai
 * @version 1.0
 */
public class GameEntry extends Game implements InputProcessor {
    public static String CLASSNAME = GameEntry.class.getSimpleName();
    public static String GAMENAME = "wdw";
    public static String GAMEVERSION = "0.4.2";
    public static int GAMEWIDTH, GAMEHEIGHT;

    public static SpriteBatch batch;
    public static ArrayMap<String, BitmapFont> font;
    public static MainGame maingame;
    public static ScreenLogin screenLogin;
    public static ScreenSplash screenSplash;
    public static ScreenOverlap screenOverlap;
    private static InputMultiplexer inputMultiplexer;

    private static List<AbstractScreen> screens = new ArrayList<>();

    /**
     * 将新屏幕加入待渲染列表，等待下一帧渲染
     *
     * @param screen 加入渲染列表的屏幕
     */
    public static void addScreen(AbstractScreen screen) {
        screens.add(screen);
        screen.removeable(false);
        Log.i("Screens << " + screen);
        inputMultiplexer.addProcessor(screen);
    }

    /**
     * 为继承自AbstractScreen的屏幕提供初始化Stage的方法
     *
     * @return 初始化后的Stage对象
     */
    public static Stage stage() {
        return new Stage(viewport(), GameEntry.batch);
    }

    /**
     * 为继承自AbstractScreen的屏幕提供初始化Viewport的方法
     *
     * @return 初始化后的ScalingViewport对象
     */
    public static ScalingViewport viewport() {
        return new ScalingViewport(Scaling.fit, GAMEWIDTH, GAMEHEIGHT, new OrthographicCamera());
    }

    /**
     * 游戏创建时调用，初始化游戏资源和屏幕
     */
    public void create() {
        // 初始化日志和显示信息
        Log.i(CLASSNAME + " -> create()");
        Log.i(" _ _ _ ____  _ _ _         _   _");
        Log.i("| | | |    \\| | | |___ ___| |_| |");
        Log.i("| | | |  |  | | | | . |  _| | . |");
        Log.i("|_____|____/|_____|___|_| |_|___|");
        Log.i(GAMENAME + " - " + GAMEVERSION + " Running!");

        // 初始化图形和字体资源
        batch = new SpriteBatch();
        font = new Font().getFontList();
        GAMEWIDTH = Gdx.graphics.getWidth();
        GAMEHEIGHT = Gdx.graphics.getHeight();
        Log.i("GAME WIDTH:" + GAMEWIDTH + " GAME HEIGHT:" + GAMEHEIGHT);
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        screenSplash = new ScreenSplash(this);
        screenSplash.create();
        addScreen(screenSplash);
    }

    /**
     * 每帧渲染时调用，准备屏幕栈并渲染当前屏幕
     */
    public void render() {
        // 清理并准备渲染
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        prepareScreen();
    }

    /**
     * 处理屏幕栈的更新，删除待移除的屏幕，添加待加入的屏幕
     */
    private void prepareScreen() {
        // 删除标记为可移除的屏幕
        screens.removeIf(AbstractScreen::removeable);
        // 如果屏幕栈为空，初始化一个错误界面
        if (screens.isEmpty()) {
            Log.e("CRASHED:Stack has 0 screen to render()", new Throwable());
            ScreenCrash screenCrash = new ScreenCrash();
            screenCrash.create();
            addScreen(screenCrash);
        }
        // 渲染每个屏幕
        for (int i = 0; i < screens.size(); i++) {
            screens.get(i).act();
            screens.get(i).draw();
            Log.d("screen draw << " + screens.get(i));
        }
    }

    /**
     * 窗口大小改变时调用，更新游戏窗口大小
     *
     * @param width  新窗口宽度
     * @param height 新窗口高度
     */
    public void resize(int width, int height) {
        Log.i(CLASSNAME + " -> resize()");
        Log.i("Window Resized to " + width + " x " + height);
        GAMEHEIGHT = height;
        GAMEWIDTH = width;
        screens.forEach(screen -> screen.resize(width, height));
    }

    // 游戏暂停和恢复时不进行处理
    public void pause() {
    }

    public void resume() {
    }

    /**
     * 游戏资源释放时调用，释放SpriteBatch资源
     */
    public void dispose() {
        screens.forEach(AbstractScreen::dispose);
        batch.dispose();
        font.values().forEach(BitmapFont::dispose);
    }

    // 输入处理器方法，转发给当前栈顶屏幕处理
    @Override
    public boolean keyDown(int i) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).keyDown(i);
    }

    @Override
    public boolean keyUp(int i) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).keyUp(i);
    }

    @Override
    public boolean keyTyped(char c) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).keyTyped(c);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).touchCancelled(i, i1, i2, i3);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return !screens.isEmpty() && screens.get(screens.size() - 1).scrolled(v, v1);
    }
}