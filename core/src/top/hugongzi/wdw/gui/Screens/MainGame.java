package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.GameMap;
import top.hugongzi.wdw.fcore.Log;

public class MainGame extends AbstractScreen {
    private static final float CAMERA_SPEED = 200.0f;
    public static String CLASSNAME = LoginScreen.class.getSimpleName();
    private static volatile MainGame instance;
    OrthographicCamera camera;
    GameMap gameMap;
    private String serverAddress, serverName, phpserverurl;
    private Vector2 direction;

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
        gameMap = new GameMap("Maps/map003.tmx", stage);
        camera = (OrthographicCamera) stage.getCamera();
        camera.zoom = 0.5f;
        direction = new Vector2();
    }

    @Override
    public void draw() {
        //updateCamera();
        gameMap.draw();
    }

    private void updateCamera() {
        direction.set(0.0f, 0.0f);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction.x = -1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction.x = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction.y = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction.y = -1;
        }

        direction.nor().scl(CAMERA_SPEED * Gdx.graphics.getDeltaTime());

        camera.position.x += direction.x;
        camera.position.y += direction.y;

        TiledMapTileLayer layer = (TiledMapTileLayer) gameMap.getMap().getLayers().get(0);

        float cameraMinX = stage.getViewport().getWorldWidth() * 0.5f;
        float cameraMinY = stage.getViewport().getWorldHeight() * 0.5f;
        float cameraMaxX = layer.getWidth() * layer.getTileWidth() - cameraMinX;
        float cameraMaxY = layer.getHeight() * layer.getTileHeight() - cameraMinY;

        camera.position.x = MathUtils.clamp(camera.position.x, cameraMinX, cameraMaxX);
        camera.position.y = MathUtils.clamp(camera.position.y, cameraMinY, cameraMaxY);

        camera.update();
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
