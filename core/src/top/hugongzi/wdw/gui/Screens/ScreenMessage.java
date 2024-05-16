package top.hugongzi.wdw.gui.Screens;

import top.hugongzi.wdw.GameEntry;

import java.util.ArrayList;

public class ScreenMessage extends AbstractScreen {
    ArrayList<String> msg;

    @Override
    public void create() {
        stage = GameEntry.stage();
    }

    @Override
    public void draw() {
        stage.draw();
        stage.act();
    }

    @Override
    public void act() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
