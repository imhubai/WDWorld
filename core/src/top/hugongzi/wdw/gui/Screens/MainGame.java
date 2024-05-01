package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.Log;

public class MainGame extends AbstractScreen {
    public static String CLASSNAME = LoginScreen.class.getSimpleName();
    private static volatile MainGame instance;
    private String serverAddress, serverName, phpserverurl;

    private MainGame(String serverAddress, String serverName, String phpserverurl) {
        this.serverAddress = serverAddress;
        Log.i("SERVER AT:" + serverAddress);
        this.serverName = serverName;
        this.phpserverurl = phpserverurl;
        this.create();
        GameEntry.splashScreen.remove();
        GameEntry.loginScreen.remove();
    }

    public static MainGame getInstance(String serverAddress, String serverName, String phpserverurl) {
        if (instance == null) {
            synchronized (MainGame.class) {
                if (instance == null) {
                    instance = new MainGame(serverAddress, serverName, phpserverurl);
                }
            }
        }
        return instance;
    }

    @Override
    public void create() {
        stage = GameEntry.stage();
        new TmxMapLoader();
    }

    @Override
    public void draw() {

    }

    @Override
    public void act() {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
