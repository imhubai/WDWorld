package top.hugongzi.wdw.gui.Screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.Messages.*;
import top.hugongzi.wdw.core.GameMap;
import top.hugongzi.wdw.core.Log;
import top.hugongzi.wdw.core.OClient;
import top.hugongzi.wdw.core.Util;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.listener.CameraListener;
import top.hugongzi.wdw.listener.FreeRoamingMovementListener;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class MainGame extends AbstractScreen {
    private static volatile MainGame instance;
    private final String serverAddress;
    private final String serverName;
    private final String phpserverurl;
    private final String username;
    private final String name;
    Hashtable<String, Player> serverplayers;
    GameMap gameMap;
    RayHandler rayHandler;
    OClient client;
    float delta = 0;
    Player player;
    boolean ismaprender = false;
    private
    OrthographicCamera camera;

    private MainGame(String serverAddress, String serverName, String phpserverurl, String username, String name) {
        this.serverAddress = serverAddress;
        Log.i("SERVER AT:" + serverAddress);
        this.serverName = serverName;
        this.phpserverurl = phpserverurl;
        this.username = username;
        this.name = name;
        serverplayers = new Hashtable<>();
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
    }

    private void loadGameMap(String map, Stage stage) {
        Log.i("Loading game map " + map);
        gameMap = new GameMap(map, stage);
        rayHandler = new RayHandler(gameMap.getWorld());
        rayHandler.setAmbientLight(1f, 1f, 1f, 1f);
        rayHandler.setBlurNum(1);
        rayHandler.setShadows(true);
        addClientPlayer();
        ismaprender = true;
    }

    public void tryConnect() throws IOException {
        String[] server = serverAddress.split(":");
        client = new OClient(server[0], Integer.parseInt(server[1]), Integer.parseInt(server[1]) + 1, this);
        client.connect();
    }

    @Override
    public void draw() {
        if (ismaprender) {
            gameMap.draw();
            rayHandler.prepareRender();
            rayHandler.updateAndRender();
        }
        stage.draw();
    }

    private void updateCamera() {
        camera.position.x = player.getX() + player.getPlayerActor().getWidth() / 2;
        camera.position.y = player.getY() + player.getPlayerActor().getHeight() / 2;

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
        if (ismaprender) {
            updateCamera();
            rayHandler.setCombinedMatrix(camera);
        }
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

    public void moved() {
        PlayerMovedMessage playerMovedMessage = new PlayerMovedMessage();
        playerMovedMessage.setPlayerid(username);
        playerMovedMessage.setMap(player.getMap());
        playerMovedMessage.setCurrentState(player.getCurrentState());
        playerMovedMessage.setCurrentVelocity(player.getCurrentVelocity());
        playerMovedMessage.setX(player.getX());
        playerMovedMessage.setY(player.getY());
        client.sendTCP(playerMovedMessage);
    }

    public void stoped() {
        PlayerMovedMessage playerMovedMessage = new PlayerMovedMessage();
        playerMovedMessage.setPlayerid(username);
        playerMovedMessage.setMap(player.getMap());
        playerMovedMessage.setCurrentState(player.getCurrentState());
        playerMovedMessage.setCurrentVelocity(player.getCurrentVelocity());
        playerMovedMessage.setX(player.getX());
        playerMovedMessage.setY(player.getY());
        client.sendTCP(playerMovedMessage);
    }

    @Override
    public void dispose() {
        if (ismaprender) {
            rayHandler.dispose();
        }
        LogoutMessage logoutMessage = new LogoutMessage();
        logoutMessage.setId(username);
        client.sendUDP(logoutMessage);
    }

    public void registerPlayer(Player player) {
        NewbieMessage newbieMessage = new NewbieMessage();
        newbieMessage.setId(username);
        newbieMessage.setPlayer(player);
        client.sendUDP(newbieMessage);
    }

    public void loginReceieved(LoginMessage m) {
        player = m.getPlayer();
        loadGameMap(player.getMap(), stage);
    }

    public void addClientPlayer() {
        player.setPlayerActor(new Vector2(player.getX(), player.getY()), gameMap.getWorld());
        stage.addActor(player.getPlayerActor().get());
        stage.addListener(new FreeRoamingMovementListener(player));
        stage.addListener(new CameraListener(camera));
    }

    public void addPlayer(Player OnlineServerPlayer) {
        OnlineServerPlayer.setPlayerActor(new Vector2(OnlineServerPlayer.getX(), OnlineServerPlayer.getY()), gameMap.getWorld());
        stage.addActor(OnlineServerPlayer.getPlayerActor().get());
    }

    public void newbieReceieved(NewbieMessage m) {
        Log.i("MainGame - There is no playerdata, jump to create character.");
        ScreenCreateCharacter screenCreateCharacter = new ScreenCreateCharacter(this);
        screenCreateCharacter.create();
        GameEntry.addScreen(screenCreateCharacter);
        this.remove();
    }

    public void chatReceieved(ChatMessage m) {
        Log.i("received " + m.getMsg());
    }

    public void logoutReceieved(LogoutMessage m) {
        Log.i("logouted!!");
    }

    public void worldMessageReceieved(WorldMessage m) {
        List<Player> playermap = m.getPlayermap();
        for (Player i : playermap) {
            if (i != null && player != null) {
                if (!Objects.equals(i.getId(), player.getId()) && !serverplayers.containsKey(i.getId()) && Objects.equals(player.getMap(), i.getMap())) {
                    serverplayers.put(i.getId(), i);
                    addPlayer(i);
                    Log.i(i.getId() + " entered!");
                } else if (!Objects.equals(i.getId(), player.getId()) && serverplayers.containsKey(i.getId()) && Objects.equals(player.getMap(), i.getMap())) {
                    serverplayers.get(i.getId()).setX(i.getX());
                    serverplayers.get(i.getId()).setY(i.getY());
                    serverplayers.get(i.getId()).getPlayerActor().updatePlayerState(i.getCurrentState(), i.getCurrentState().calculateDirectionVector());
                    serverplayers.get(i.getId()).getPlayerActor().setPosition(i.getX(), i.getY());
                } else if (Objects.equals(i.getId(), player.getId())) {
                    if (!Util.equalsEpsilon(i.getX(), player.getX(), 0.01f)) {
                        moved();
                    }
                }
            }
        }
    }
}
