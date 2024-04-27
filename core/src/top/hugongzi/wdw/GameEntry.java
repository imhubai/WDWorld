package top.hugongzi.wdw;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fengine.Log;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏入口
 *
 * @author Hubai
 * @version 1.0
 */
public class GameEntry implements ApplicationListener {
    public static String GAMENAME = "wdw";
    public static String GAMEVERSION = "t0.2";

    public static SpriteBatch batch;
    public static Input input;
    public static List<View> views = new ArrayList<>();
    private static List<View> insertViews = new ArrayList<>();

    public void create() {
        Log.i(" _ _ _ ____  _ _ _         _   _");
        Log.i("| | | |    \\| | | |___ ___| |_| |");
        Log.i("| | | |  |  | | | | . |  _| | . |");
        Log.i("|_____|____/|_____|___|_| |_|___|");
        Log.i(GAMENAME + " - " + GAMEVERSION + " Running!");
    }

    public void render() {
    }

    public void resize(int width, int height) {
        Log.i("Window Resized to "+width+" x "+height);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }

}
