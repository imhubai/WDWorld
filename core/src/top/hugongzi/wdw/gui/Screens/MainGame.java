package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.Socket;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.core.FreeRoamingMovementListener;
import top.hugongzi.wdw.core.GameMap;
import top.hugongzi.wdw.core.Log;
import top.hugongzi.wdw.core.Player;
import top.hugongzi.wdw.wdwServer.ClientRequest;
import top.hugongzi.wdw.wdwServer.ServerResponse;

import java.io.IOException;

public class MainGame extends AbstractScreen {
    private static final float CAMERA_SPEED = 200.0f;
    public static String CLASSNAME = ScreenLogin.class.getSimpleName();
    private static volatile MainGame instance;
    OrthographicCamera camera;
    GameMap gameMap;
    Socket mainsocket;
    float delta = 0;
    Player player;
    private String serverAddress, serverName, phpserverurl;
    private Vector2 direction;

    private MainGame(String serverAddress, String serverName, String phpserverurl) {
        this.serverAddress = serverAddress;
        Log.i("SERVER AT:" + serverAddress);
        this.serverName = serverName;
        this.phpserverurl = phpserverurl;
        this.create();
        GameEntry.screenSplash.remove();
        GameEntry.screenLogin.remove();
        GameEntry.screenOverlap.remove();
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

        try {
            tryConnect();
        } catch (IOException e) {
            Log.e("Server Exception", new RuntimeException(e));
        }
        gameMap = new GameMap("Maps/map003.tmx", stage);

        camera = (OrthographicCamera) stage.getCamera();
        camera.zoom =0.5f;

        player = new Player(new Vector2(640f, 640f), gameMap.getWorld());
        stage.addActor(player);
        stage.addListener(new FreeRoamingMovementListener(player));
    }

    public void tryConnect() throws IOException {
        String[] server = serverAddress.split(":");
        Client client = new Client();
        Kryo kryo = client.getKryo();
        kryo.register(ClientRequest.class);
        kryo.register(ServerResponse.class);
        client.start();
        client.connect(50000, server[0], Integer.parseInt(server[1]), Integer.parseInt(server[1]) + 1);
        ClientRequest request = new ClientRequest();
        request.text = "Here is the request";
        client.sendTCP(request);
    }

    @Override
    public void draw() {
        gameMap.draw();
        stage.draw();
    }

    private void updateCamera() {
        camera.position.x =player.getX();
        camera.position.y =player.getY();

        TiledMapTileLayer layer = (TiledMapTileLayer) gameMap.getMap().getLayers().get(0);

        float cameraMinX = stage.getViewport().getWorldWidth() * 0f;
        float cameraMinY = stage.getViewport().getWorldHeight() * 0f;
        float cameraMaxX = layer.getWidth() * layer.getTileWidth() - cameraMinX;
        float cameraMaxY = layer.getHeight() * layer.getTileHeight() - cameraMinY;

        camera.position.x = MathUtils.clamp(camera.position.x, cameraMinX, cameraMaxX);
        camera.position.y = MathUtils.clamp(camera.position.y, cameraMinY, cameraMaxY);

        camera.update();
    }


    @Override
    public void act() {
        delta += Gdx.graphics.getDeltaTime();
        stage.act();
        updateCamera();
        player.act(delta);
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
