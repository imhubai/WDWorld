package top.hugongzi.wdw.wdwServer;

import com.badlogic.gdx.Input;

public class ConnectedClient {
    //private final Input input;
    private final String username;
    private final int id;

    public ConnectedClient(String username, int id) {
        //this.input = new Input();
        this.username = username;
        this.id = id;
    }

//    public Input getInput() {
//        return input;
//    }

    public String getUsername() {
        return username;
    }

    public int getID() {
        return id;
    }
}
