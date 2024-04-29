package top.hugongzi.wdw;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Files;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.HdpiMode;

/**
 * Desktop入口
 *
 * @author Hubai
 */
public class DesktopLauncher {
    public static void main(String[] arg) {
        //BOX2DLights
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        //Lwjgl3 Config
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setIdleFPS(0);
        config.setWindowedMode(1280, 720);
        config.useVsync(true);
        config.setTitle("WDWorld");

        //Hdpi
        config.setHdpiMode(HdpiMode.Pixels);
        config.setInitialVisible(true);
        config.setInitialBackgroundColor(Color.valueOf("#2e2e2e"));
        config.setWindowIcon("Icon/icon.png");
        config.setResizable(false);

        config.disableAudio(false);

        Gdx.files = new Lwjgl3Files();
        //Entry
        new Lwjgl3Application(new GameEntry(), config);
    }
}
