package top.hugongzi.wdw;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import top.hugongzi.wdw.fcore.Log;
import top.hugongzi.wdw.gui.Screens.AbstractScreen;
import top.hugongzi.wdw.gui.Text.Font;

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
    public static String GAMEVERSION = "t0.2.1";

    public static SpriteBatch batch;
    public static Input input;
    public static BitmapFont font;

    private Stack<AbstractScreen> screenStack;

    public void create() {
        Log.i(CLASSNAME + " -> create()");
        Log.i(" _ _ _ ____  _ _ _         _   _");
        Log.i("| | | |    \\| | | |___ ___| |_| |");
        Log.i("| | | |  |  | | | | . |  _| | . |");
        Log.i("|_____|____/|_____|___|_| |_|___|");
        Log.i(GAMENAME + " - " + GAMEVERSION + " Running!");
        batch=new SpriteBatch();
        font=new Font().getFont();
    }

    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.setColor(Color.valueOf("#ffffff"));
        font.draw(batch,"中文字体测试1a",20,400);
        batch.end();
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
    }

}
