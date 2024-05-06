package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.GameMap;
import top.hugongzi.wdw.fcore.Log;

public class MainGame extends AbstractScreen {
    private static final float CAMERA_SPEED = 200.0f;
    public static String CLASSNAME = LoginScreen.class.getSimpleName();
    private static volatile MainGame instance;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    OrthographicCamera camera;
    Vector2 dir;
    TiledMap map;
    GameMap gameMap;
    private String serverAddress, serverName, phpserverurl;

    private MainGame(String serverAddress, String serverName, String phpserverurl) {
        this.serverAddress = serverAddress;
        Log.i("SERVER AT:" + serverAddress);
        this.serverName = serverName;
        this.phpserverurl = phpserverurl;
        this.create();
        GameEntry.splashScreen.remove();
        GameEntry.loginScreen.remove();
        GameEntry.overlapScreen.remove();
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
        gameMap = new GameMap("Maps/test/map003.tmx", stage);
        camera = (OrthographicCamera) stage.getCamera();
        camera.zoom = 0.5f;
    }

    @Override
    public void draw() {
        updateCamera();
        gameMap.draw();
    }

    private void updateCamera() {

    }

    @Override
    public void act() {
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
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

    }
}
