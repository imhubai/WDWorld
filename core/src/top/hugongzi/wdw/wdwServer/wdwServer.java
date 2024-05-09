package top.hugongzi.wdw.wdwServer;

import java.io.IOException;

public class wdwServer {
    public static final int TCPport = 22333, UDPport = 22334;
    public final ServerConnection serverConnection;

    public wdwServer() throws IOException {
        this.serverConnection = new ServerConnection(this, TCPport, UDPport);
    }

    public static void main(String[] args) throws IOException {
        wdwServer wdwServer = new wdwServer();
    }
}
