package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.gui.Buttons.GameGUI;

public class ScreenCrash extends AbstractScreen {
    Label crashLabel;

    @Override
    public void create() {
        stage = GameEntry.stage();
        crashLabel = GameGUI.label(" :(\n崩溃啦，请检查业务逻辑", 0, 0, "cubic20", Color.WHITE);
        stage.addActor(crashLabel);
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
