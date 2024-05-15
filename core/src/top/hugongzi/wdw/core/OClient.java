package top.hugongzi.wdw.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import top.hugongzi.wdw.Messages.*;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.entity.Player.PlayerState;
import top.hugongzi.wdw.gui.Screens.MainGame;

import java.io.IOException;

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
                        //OClient.this.game.logoutReceieved(m);
                    } else if (object instanceof NewbieMessage) {
                        NewbieMessage m = (NewbieMessage) object;
                        OClient.this.game.newbieReceieved(m);
                    }
                });

            }

        });
    }

    /**
     * This function register every class that will be sent back and forth between
     * client and server.
     */
    private void registerClasses() {
        // messages
        this.client.getKryo().register(LoginMessage.class);
        this.client.getKryo().register(LogoutMessage.class);
        this.client.getKryo().register(GameWorldMessage.class);
        this.client.getKryo().register(PositionMessage.class);
        this.client.getKryo().register(PositionMessage.DIRECTION.class);
        this.client.getKryo().register(NewbieMessage.class);
        this.client.getKryo().register(Player.class);
        this.client.getKryo().register(PlayerState.class);
        this.client.getKryo().register(Vector2.class);
        // primitive arrays
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
