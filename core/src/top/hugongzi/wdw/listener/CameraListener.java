package top.hugongzi.wdw.listener;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import top.hugongzi.wdw.core.Log;

public class CameraListener extends InputListener {
    OrthographicCamera camera;

    public CameraListener(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
        Log.i(amountY+"ca"+camera.zoom);
        if (amountY > 0f) {
            if (camera.zoom < 0.5f) {
                camera.zoom += 0.05f;
            }
        } else {
            if (camera.zoom > 0.2f) {
                camera.zoom -= 0.05f;
            }
        }
        return true;
    }

}
