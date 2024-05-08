package top.hugongzi.wdw.wdwServer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class ServerStart {
    public static int TCPport = 22333,UDPport=22334;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
        server.bind(TCPport, UDPport);
        Kryo kryo = server.getKryo();
        kryo.register(ClientRequest.class);
        kryo.register(ServerResponse.class);
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof ClientRequest) {
                    ClientRequest request = (ClientRequest)object;
                    System.out.println(request.text);

                    ServerResponse response = new ServerResponse();
                    response.text = "Hello";
                    connection.sendTCP(response);
                }
            }
        });
    }
}
