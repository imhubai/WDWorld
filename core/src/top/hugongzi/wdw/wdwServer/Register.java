package top.hugongzi.wdw.wdwServer;

import com.esotericsoftware.kryo.Kryo;

public final class Register {

    private Register() {
    }

    public static void registerAll(Kryo kryo) {
        kryo.register(ClientRequest.class);
        kryo.register(ServerResponse.class);
    }
}
