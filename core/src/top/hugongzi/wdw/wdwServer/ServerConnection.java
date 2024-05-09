package top.hugongzi.wdw.wdwServer;

import com.badlogic.gdx.utils.ObjectMap;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import top.hugongzi.wdw.core.Log;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerConnection extends Listener {

    private final Server connection;
    private final wdwServer wdwServer;
    private final LinkedBlockingQueue<Runnable> queue;
    private final ObjectMap<Integer, ConnectedClient> connectedClients;

    public ServerConnection(wdwServer wdwServer, int tcpPort, int udpPort) throws IOException {
        this.wdwServer = wdwServer;
        this.connection = new Server();
        this.queue = new LinkedBlockingQueue<>();
        this.connectedClients = new ObjectMap<>();
        connection.bind(tcpPort, udpPort);
        connection.start();
        Kryo kryo = connection.getKryo();
        Register.registerAll(kryo);
        connection.addListener(new QueuedListener(this) {
            @Override
            protected void queue(Runnable runnable) {
                queue.add(runnable);
            }
        });
    }

    @Override
    public void connected(Connection connection) {
        Log.i("[SERVER]: Client " + connection.getRemoteAddressTCP() + " connected.");
        connectedClients.put(connection.getID(), new ConnectedClient("TestClient", connection.getID()));
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }
}
