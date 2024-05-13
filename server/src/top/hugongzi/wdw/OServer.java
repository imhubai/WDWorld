package top.hugongzi.wdw;

import com.badlogic.gdx.math.Interpolation;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import top.hugongzi.wdw.Messages.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class OServer {

    private final int tcpPORT, udpPORT;
    private Server server;
    private ServerConnection serverConnection;
    private MessageListener messageListener;
    private Queue<Object> messageQueue;
    private Queue<Connection> connectionQueue;

    public OServer(ServerConnection serverConnection, int tcpPORT, int udpPORT) {
        this.serverConnection = serverConnection;
        this.tcpPORT = tcpPORT;
        this.udpPORT = udpPORT;

        init();
    }

    private void init() {
        server = new Server();
        registerClasses();
        messageQueue = new LinkedList<>();
        connectionQueue = new LinkedList<>();
        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                messageQueue.add(object);
                connectionQueue.add(connection);
            }
        });
        server.start();
        try {
            server.bind(tcpPORT, udpPORT);
            ServerLog.i("Server has ben started on TCP_PORT: " + tcpPORT + " UDP_PORT: " + udpPORT);
        } catch (IOException e) {
            ServerLog.e(e);
        }

    }

    public void parseMessage() {
        if (connectionQueue.isEmpty() || messageQueue.isEmpty())
            return;

        for (int i = 0; i < messageQueue.size(); i++) {
            Connection con = connectionQueue.poll();
            Object message = messageQueue.poll();

            if (message instanceof LoginMessage) {
                LoginMessage m = (LoginMessage) message;
                messageListener.loginReceived(con, m);
                System.out.println("it worked?");
            } else if (message instanceof LogoutMessage) {
                LogoutMessage m = (LogoutMessage) message;
                messageListener.logoutReceived(m);
            } else if (message instanceof PositionMessage) {
                PositionMessage m = (PositionMessage) message;
                messageListener.playerMovedReceived(m);
            }
        }
    }

    private void registerClasses() {
        // messages
        this.server.getKryo().register(LoginMessage.class);
        this.server.getKryo().register(LogoutMessage.class);
        this.server.getKryo().register(GameWorldMessage.class);
        this.server.getKryo().register(PositionMessage.class);
        this.server.getKryo().register(PositionMessage.DIRECTION.class);
        // primitive arrays
        this.server.getKryo().register(float[].class);
    }

    public void sendToAllUDP(Object m) {
        server.sendToAllUDP(m);
    }

    public void sendToUDP(int id, Object m) {
        server.sendToUDP(id, m);
    }
    public void sendToAllTCP(Object m) {
        server.sendToAllUDP(m);
    }

    public void sendToTCP(int id, Object m) {
        server.sendToUDP(id, m);
    }
}
