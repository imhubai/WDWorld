package top.hugongzi.wdw.gui.Screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.Messages.LoginMessage;
import top.hugongzi.wdw.Messages.NewbieMessage;
import top.hugongzi.wdw.core.GameMap;
import top.hugongzi.wdw.core.Log;
import top.hugongzi.wdw.core.OClient;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.gui.Buttons.GameGUI;
import top.hugongzi.wdw.listener.CameraListener;
import top.hugongzi.wdw.listener.FreeRoamingMovementListener;

import java.io.IOException;

public class MainGame extends AbstractScreen {
    private static volatile MainGame instance;
    private final String serverAddress;
    private final String serverName;
    private final String phpserverurl;
    private final String username;
    private final String name;
    OrthographicCamera camera;
    GameMap gameMap;
    Label info;
    RayHandler rayHandler;
    OClient client;
    float delta = 0;
    Player player;

    private MainGame(String serverAddress, String serverName, String phpserverurl, String username, String name) {
        this.serverAddress = serverAddress;
        Log.i("SERVER AT:" + serverAddress);
        this.serverName = serverName;
        this.phpserverurl = phpserverurl;
        this.username = username;
        this.name = name;
        this.create();
        GameEntry.screenSplash.remove();
        GameEntry.screenLogin.remove();
        GameEntry.screenOverlap.remove();
    }

    public static MainGame getInstance(String serverAddress, String serverName, String phpserverurl, String username, String name) {
        if (instance == null) {
            synchronized (MainGame.class) {
                if (instance == null) {
                    instance = new MainGame(serverAddress, serverName, phpserverurl, username, name);
                }
            }
        }
        return instance;
    }

    @Override
    public void create() {
        stage = GameEntry.stage();

        info = GameGUI.label("", 1000, 1000, "cubic20", Color.WHITE);
        stage.addActor(info);
        try {
            tryConnect();
        } catch (IOException e) {
            Log.e(new RuntimeException(e));
        }
        camera = (OrthographicCamera) stage.getCamera();
        camera.zoom = 0.5f;

        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setId(username);
        client.sendUDP(loginMessage);
        //loadGameMap("Maps/map003.tmx", this.stage);

        //player = new Player(new Vector2(640f, 640f), gameMap.getWorld());
        //stage.addActor(player);
        //stage.addListener(new FreeRoamingMovementListener(player));
        //stage.addListener(new CameraListener(camera));
    }

    private void loadGameMap(String map, Stage stage) {
        gameMap = new GameMap(map, stage);
        rayHandler = new RayHandler(gameMap.getWorld());
        rayHandler.setAmbientLight(1f, 1f, 1f, 1f);
        rayHandler.setBlurNum(1);
        rayHandler.setShadows(true);
    }

    public void tryConnect() throws IOException {
        String[] server = serverAddress.split(":");
        client = new OClient(server[0], Integer.parseInt(server[1]), Integer.parseInt(server[1]) + 1, this);
        client.connect();
    }

    @Override
    public void draw() {
        //gameMap.draw();
        stage.draw();
        //rayHandler.prepareRender();
        //rayHandler.updateAndRender();
    }

    private void updateCamera() {
        camera.position.x = player.getX();
        camera.position.y = player.getY();

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
        //updateCamera();
        //rayHandler.setCombinedMatrix(camera);
        //player.act(delta);
        info.setText("FPS:" + Gdx.graphics.getFramesPerSecond() + "\n" +
                "Delta:" + delta + "\n" +
                "UncompressedMem:" + Gdx.app.getJavaHeap() / 80000 + " MB" + "\n");
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
        //rayHandler.dispose();
    }

    public void registerPlayer(Player player) {
        NewbieMessage newbieMessage = new NewbieMessage();
        newbieMessage.setId(username);
        newbieMessage.setPlayer(player);
        client.sendUDP(newbieMessage);
    }

    public void loginReceieved(LoginMessage m) {
        Log.i("login?");
    }

    public void newbieReceieved(NewbieMessage m) {
        Log.i("MainGame - There is no playerdata, jump to create character.");
        ScreenCreateCharacter screenCreateCharacter = new ScreenCreateCharacter(this);
        screenCreateCharacter.create();
        GameEntry.addScreen(screenCreateCharacter);
        this.remove();
    }
}
