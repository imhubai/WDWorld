package top.hugongzi.wdw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class ServerWork extends Game {
    public static int TCPport = 22333;
    public static int UDPport = 22334;
    public final ServerConnection serverConnection;
    private float time;

    public ServerWork(int tcpport, int udpport) {
        TCPport = tcpport;
        UDPport = udpport;
        this.serverConnection = new ServerConnection(this, TCPport, UDPport);
    }

    @Override
    public void create() {
        Gdx.app.getApplicationListener().create();
        ServerLog.i("Server Start!");
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        System.out.println("nogtrg");
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        if (time >= 1) {
            time = 0;
        }
        serverConnection.update(deltaTime);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
