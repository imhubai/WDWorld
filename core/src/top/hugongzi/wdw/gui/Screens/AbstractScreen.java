package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScreen implements InputProcessor, Disposable {
    public Stage stage;
    public boolean isMarkedRemove = false;
    private List<InputProcessor> processors = new ArrayList<>();

    public abstract void create();

    public abstract void draw();

    public abstract void act();

    public boolean removeable() {
        return isMarkedRemove;
    }

    public void removeable(boolean flag) {
        isMarkedRemove = flag;
    }

    public void remove() {
        isMarkedRemove = true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return stage.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return stage.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return stage.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stage.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return stage.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return stage.touchCancelled(i,i1,i2,i3);
    }
    @Override
    public boolean scrolled(float i,float i1) {
        return stage.scrolled(i,i1);
    }
}
