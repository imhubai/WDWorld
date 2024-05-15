package top.hugongzi.wdw.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import top.hugongzi.wdw.Messages.*;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.entity.Player.PlayerState;
import top.hugongzi.wdw.gui.Screens.MainGame;

import java.io.IOException;
import java.util.ArrayList;

public class OClient {

    private Client client;
    private MainGame game;
    private String ip;
    private int tcpPort, udpPort;

    public OClient(String ip, int tcpPort, int udpPort, MainGame game) {
        this.game = game;
        this.ip = ip;
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        client = new Client();
        registerClasses();
        addListeners();
    }

    public void connect() {
        client.start();
        try {
            Log.i("Attempting to connect args[0]: " + ip);
            client.connect(5000, ip, tcpPort, udpPort);
        } catch (IOException e) {
            Log.e(e);
        }
    }

    private void addListeners() {
        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                Gdx.app.postRunnable(() -> {
                    if (object instanceof LoginMessage) {
                        LoginMessage m = (LoginMessage) object;
                        OClient.this.game.loginReceieved(m);
                    } else if (object instanceof LogoutMessage) {
                        LogoutMessage m = (LogoutMessage) object;
                        OClient.this.game.logoutReceieved(m);
                    } else if (object instanceof NewbieMessage) {
                        NewbieMessage m = (NewbieMessage) object;
                        OClient.this.game.newbieReceieved(m);
                    } else if (object instanceof ChatMessage) {
                        ChatMessage m = (ChatMessage) object;
                        OClient.this.game.chatReceieved(m);
                    } else if (object instanceof WorldMessage) {
                        WorldMessage m = (WorldMessage) object;
                        OClient.this.game.worldMessageReceieved(m);
                    }
                });

            }

        });
    }

    private void registerClasses() {
        this.client.getKryo().register(LoginMessage.class);
        this.client.getKryo().register(LogoutMessage.class);
        this.client.getKryo().register(NewbieMessage.class);
        this.client.getKryo().register(Player.class);
        this.client.getKryo().register(PlayerState.class);
        this.client.getKryo().register(Vector2.class);
        this.client.getKryo().register(ArrayList.class);
        this.client.getKryo().register(ChatChannel.class);
        this.client.getKryo().register(ChatMessage.class);
        this.client.getKryo().register(WorldMessage.class);
        this.client.getKryo().register(PlayerMovedMessage.class);
        this.client.getKryo().register(Body.class);
        this.client.getKryo().register(Array.class);
        this.client.getKryo().register(Object[].class);
        this.client.getKryo().register(Fixture.class);
        this.client.getKryo().register(float[].class);

    }

    public void close() {
        client.close();
    }

    public void sendTCP(Object m) {
        client.sendTCP(m);
    }

    public void sendUDP(Object m) {
        client.sendUDP(m);
    }

}
