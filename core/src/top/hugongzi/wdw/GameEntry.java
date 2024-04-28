package top.hugongzi.wdw;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
public class GameEntry implements ApplicationListener {
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
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.create();
        addScreen(loginScreen);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screens.removeIf(AbstractScreen::removeable);
        screens.addAll(InsertScreens);
        InsertScreens.clear();
        for (int i = screens.size() - 1; i >= 0; --i) {
            AbstractScreen s = screens.elementAt(i);
            Log.d("screen draw << "+s);
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
}
