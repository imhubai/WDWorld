package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScreen implements InputProcessor {
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
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
