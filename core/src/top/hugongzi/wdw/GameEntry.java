package top.hugongzi.wdw;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import top.hugongzi.wdw.fcore.Log;
import top.hugongzi.wdw.gui.Screens.AbstractScreen;
import top.hugongzi.wdw.gui.Screens.LoginScreen;
import top.hugongzi.wdw.gui.Text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 游戏入口
 *
 * @author Hubai
 * @version 1.0
 */
public class GameEntry implements ApplicationListener, InputProcessor {
    public static String CLASSNAME = GameEntry.class.getSimpleName();
    public static String GAMENAME = "wdw";
    public static String GAMEVERSION = "t0.2.2";

    public static SpriteBatch batch;
    public static Input input;
    public static BitmapFont font;

    private static Stack<AbstractScreen> screens = new Stack<>();
    private static List<AbstractScreen> InsertScreens = new ArrayList<>();

    public static void addScreen(AbstractScreen screen) {
        InsertScreens.add(0, screen);
        screen.removeable(false);
        Log.i("Screens << " + screen);
    }

    public void create() {
        Log.i(CLASSNAME + " -> create()");
        Log.i(" _ _ _ ____  _ _ _         _   _");
        Log.i("| | | |    \\| | | |___ ___| |_| |");
        Log.i("| | | |  |  | | | | . |  _| | . |");
        Log.i("|_____|____/|_____|___|_| |_|___|");
        Log.i(GAMENAME + " - " + GAMEVERSION + " Running!");

        batch = new SpriteBatch();
        font = new Font().getFont();
        Gdx.input.setInputProcessor(this);
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.create();
        addScreen(loginScreen);
    }

    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screens.removeIf(AbstractScreen::removeable);
        for (int i = 0; i < screens.size(); ++i) {
            if (screens.get(i).removeable()) {
                screens.get(i).remove();
                screens.remove(i);
            }
        }
        screens.addAll(InsertScreens);
        InsertScreens.clear();

        for (int i = 0; i < screens.size(); ++i) {
            AbstractScreen s = screens.get(i);
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
        font.dispose();
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
