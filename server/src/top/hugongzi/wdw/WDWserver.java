package top.hugongzi.wdw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Files;

public class WDWserver {
    public static int TCPport = 22333;
    public static int UDPport = 22334;

    public static void main(String[] args) {
        if(args.length>=2){
            TCPport= Integer.parseInt(args[0]);
            UDPport= Integer.parseInt(args[1]);
        }
        Gdx.files = new Lwjgl3Files();
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        conf.updatesPerSecond = 60;
        new HeadlessApplication(new ServerWork(TCPport,UDPport), conf);
    }
}
