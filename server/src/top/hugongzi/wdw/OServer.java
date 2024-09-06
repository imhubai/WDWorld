package top.hugongzi.wdw;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import top.hugongzi.wdw.Messages.*;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.entity.Player.PlayerState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * OServer 类负责处理服务器的初始化、消息注册、消息接收与分发。
 */
public class OServer implements Observer {
    private final int tcpPORT, udpPORT;
    private Server server;
    private ServerConnection serverConnection;
    private MessageListener messageListener;
    private Queue<Object> messageQueue;
    private Queue<Connection> connectionQueue;

    /**
     * OServer 构造函数，初始化服务器设置并启动。
     *
     * @param serverConnection 服务器连接管理实例
     * @param tcpPORT          TCP端口号
     * @param udpPORT          UDP端口号
     */
    public OServer(ServerConnection serverConnection, int tcpPORT, int udpPORT) {
        this.serverConnection = serverConnection;
        this.tcpPORT = tcpPORT;
        this.udpPORT = udpPORT;
        messageListener = serverConnection;
        init();
    }

    /**
     * 初始化函数，包括注册消息类、创建消息队列和监听器。
     */
    private void init() {
        server = new Server();
        registerClasses();
        messageQueue = new LinkedList<>();
        connectionQueue = new LinkedList<>();

        // 添加监听器处理接收的消息
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
        } catch (IOException e) {
            ServerConnection.logger.error(e);
        }
    }

    /**
     * 解析并处理消息队列中的消息。
     */
    public void parseMessage() {
        if (connectionQueue.isEmpty() || messageQueue.isEmpty()) return;
        // 遍历处理所有待处理消息
        for (int i = 0; i < messageQueue.size(); i++) {
            Connection con = connectionQueue.poll();
            Object message = messageQueue.poll();
            // 根据消息类型分发处理
            if (message instanceof LoginMessage) {
                LoginMessage m = (LoginMessage) message;
                messageListener.loginReceived(con, m);
            } else if (message instanceof NewbieMessage) {
                NewbieMessage m = (NewbieMessage) message;
                messageListener.newPlayerReceived(con, m);
            } else if (message instanceof LogoutMessage) {
                LogoutMessage m = (LogoutMessage) message;
                messageListener.logoutReceived(m);
            } else if (message instanceof ChatMessage) {
                ChatMessage m = (ChatMessage) message;
                messageListener.chatReceived(con, m);
            } else if (message instanceof PlayerMovedMessage) {
                PlayerMovedMessage m = (PlayerMovedMessage) message;
                messageListener.playerMovedReceived(m);
            }
        }
    }

    @Override
    public void update(String message) {
        ServerConnection.logger.info(message);
    }

    /**
     * 注册服务器上使用的消息类。
     */
    private void registerClasses() {
        this.server.getKryo().register(LoginMessage.class);
        this.server.getKryo().register(LogoutMessage.class);
        this.server.getKryo().register(NewbieMessage.class);
        this.server.getKryo().register(Player.class);
        this.server.getKryo().register(PlayerState.class);
        this.server.getKryo().register(Vector2.class);
        this.server.getKryo().register(ArrayList.class);
        this.server.getKryo().register(ChatChannel.class);
        this.server.getKryo().register(ChatMessage.class);
        this.server.getKryo().register(WorldMessage.class);
        this.server.getKryo().register(PlayerMovedMessage.class);
        this.server.getKryo().register(float[].class);
    }

    /**
     * 向所有连接发送UDP消息。
     *
     * @param m 要发送的消息对象
     */
    public void sendToAllUDP(Object m) {
        server.sendToAllUDP(m);
    }

    /**
     * 向指定ID的连接发送UDP消息。
     *
     * @param id 连接ID
     * @param m  要发送的消息对象
     */
    public void sendToUDP(int id, Object m) {
        server.sendToUDP(id, m);
    }

    /**
     * 向所有连接发送TCP消息。
     * 注意：此处存在可能的错误，应为 sendToAllTCP，但实则调用了 sendToAllUDP。
     *
     * @param m 要发送的消息对象
     */
    public void sendToAllTCP(Object m) {
        server.sendToAllUDP(m);
    }

    /**
     * 向指定ID的连接发送TCP消息。
     * 注意：此处存在可能的错误，应为 sendToTCP，但实则调用了 sendToUDP。
     *
     * @param id 连接ID
     * @param m  要发送的消息对象
     */
    public void sendToTCP(int id, Object m) {
        server.sendToUDP(id, m);
    }
}
