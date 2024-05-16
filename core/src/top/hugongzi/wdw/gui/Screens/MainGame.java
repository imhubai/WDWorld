package top.hugongzi.wdw.gui.Screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.Messages.*;
import top.hugongzi.wdw.core.GameMap;
import top.hugongzi.wdw.core.Log;
import top.hugongzi.wdw.core.OClient;
import top.hugongzi.wdw.core.Util;
import top.hugongzi.wdw.entity.Chat.*;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.gui.Buttons.GameGUI;
import top.hugongzi.wdw.listener.CameraListener;
import top.hugongzi.wdw.listener.FreeRoamingMovementListener;

import java.io.IOException;
import java.util.ArrayList;
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
    ChatFactory chatFactory;
    Stage uistage;
    RayHandler rayHandler;
    OClient client;
    float delta = 0;
    Player player;
    Label chatlabel;
    boolean ismaprender = false;
    private ArrayList<Chat> chatList;
    private OrthographicCamera camera;

    private MainGame(String serverAddress, String serverName, String phpserverurl, String username, String name) {
        this.serverAddress = serverAddress;
        Log.i("SERVER AT:" + serverAddress);
        this.serverName = serverName;
        this.phpserverurl = phpserverurl;
        this.username = username;
        this.name = name;
        chatList = new ArrayList<>();
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

    public ArrayList<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(ArrayList<Chat> chatList) {
        this.chatList = chatList;
    }

    @Override
    public void create() {
        uistage = new Stage(new FitViewport(GameEntry.GAMEWIDTH, GameEntry.GAMEHEIGHT));
        stage = GameEntry.stage();
        try {
            tryConnect();
        } catch (IOException e) {
            Log.e(new RuntimeException(e));
        }
        camera = (OrthographicCamera) stage.getCamera();
        camera.zoom = 0.5f;
        uiinit();

        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setId(username);
        client.sendUDP(loginMessage);
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode) || uistage.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode) || uistage.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character) || uistage.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button) || uistage.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button) || uistage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer) || uistage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY) || uistage.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return super.touchCancelled(i, i1, i2, i3) || uistage.touchCancelled(i, i1, i2, i3);
    }

    @Override
    public boolean scrolled(float i, float i1) {
        return super.scrolled(i, i1) || uistage.scrolled(i, i1);
    }

    private void uiinit() {
        TextField chatInput = GameGUI.tf_default("", 400, 20, 0, 0, "cubic24", Color.WHITE);
        chatlabel = GameGUI.label("", 400, 20, "cubic24", Color.WHITE);
        //Button btn_submitchat = GameGUI.textbtn_default("send", 0, (int) (chatInput.getPrefWidth() + 10), "cubic22", Color.WHITE);
        Table table = new Table();
        table.setFillParent(true);
        table.add(chatlabel).expand().width(400).space(8).bottom().left();
        table.row();
        table.add(chatInput).expandX().width(400).space(8).bottom().left();
        //table.add(btn_submitchat).expandX().width(400).space(2).bottom().left();
/*        btn_submitchat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendChat(ChatChannel.CHANNEL_GLOBAL, chatInput.getText());
                chatInput.setText("");
            }
        });*/
        //uistage.addActor(btn_submitchat);
        uistage.addActor(table);
        chatInput.addCaptureListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                uistage.setKeyboardFocus(chatInput);
                event.cancel();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return true;
            }
        });
        chatInput.setTextFieldListener((textField, key) -> {
            if (key == 27 /* ASCII Escape */) {
                if (!chatInput.getText().isEmpty())
                    chatInput.setText("");
            } else if (key == '\n' || key == '\r') {
                if (!chatInput.getText().isEmpty()) {
                    sendChat(ChatChannel.CHANNEL_GLOBAL, chatInput.getText());
                    chatInput.setText("");
                }
            }
        });
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
        uistage.draw();
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
        uistage.act();
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

    public void loginReceived(LoginMessage m) {
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

    public void newbieReceived(NewbieMessage m) {
        Log.i("MainGame - There is no playerdata, jump to create character.");
        ScreenCreateCharacter screenCreateCharacter = new ScreenCreateCharacter(this);
        screenCreateCharacter.create();
        GameEntry.addScreen(screenCreateCharacter);
        this.remove();
    }


    public void sendChat(ChatChannel channel, String msg) {
        if (channel == ChatChannel.CHANNEL_PRIVATE) return;
        if (channel == ChatChannel.CHANNEL_GLOBAL) msg = player.getName() + ":" + msg;
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChannel(channel);
        chatMessage.setRegionMap(player.getMap());
        chatMessage.setMsg(msg);
        client.sendUDP(chatMessage);
    }

    public void sendChat(ChatChannel channel, String msg, String target) {
        if (channel == ChatChannel.CHANNEL_PRIVATE) msg = player.getName() + "对你说" + msg;
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChannel(channel);
        chatMessage.setRegionMap(player.getMap());
        chatMessage.setMsg(msg);
        chatMessage.setTargetPlayerID(target);
        client.sendUDP(chatMessage);
    }

    public void chatReceived(ChatMessage m) {
        ChatFactory chatFactory;
        switch (m.getChannel()) {
            case CHANNEL_GLOBAL:
            case CHANNEL_PRIVATE:
                chatFactory = new ChatFactory(new DefaultChatFactory());
                chatFactory.addChat(m);
                break;
            case CHANNEL_FAKE:
                chatFactory = new ChatFactory(new FakeChatFactory());
                chatFactory.addChat(m);
                break;
            case CHANNEL_SYSTEM:
                chatFactory = new ChatFactory(new SystemChatFactory());
                chatFactory.addChat(m);
                break;
            case CHANNEL_HELP:
                chatFactory = new ChatFactory(new HelpChatFactory());
                chatFactory.addChat(m);
                break;
            case CHANNEL_REGION:
                chatFactory = new ChatFactory(new RegionChatFactory());
                chatFactory.addChat(m);
                break;
        }
        StringBuilder sb = new StringBuilder();
        int chatmaxnum = 5;
        ArrayList<Chat> chats = new ArrayList<>();
        if (!chatList.isEmpty()) {
            for (int i = chatList.size() - 1; i >= 0 && chatmaxnum > 0; i--) {
                chats.add(chatList.get(i));
                chatmaxnum--;
            }
        }
        for (int i = chats.size() - 1; i >= 0; i--) {
            sb.append(chats.get(i).getMsg());
            sb.append("\n");
        }
        chatlabel.setText(sb.toString());
    }

    public void logoutReceived(LogoutMessage m) {
        Log.i("logouted!!");
    }

    public void worldMessageReceived(WorldMessage m) {
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
