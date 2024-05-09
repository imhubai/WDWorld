package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.gui.Buttons.GameGUI;

public class ScreenOverlap extends AbstractScreen {
    public static String CLASSNAME = ScreenLogin.class.getSimpleName();
    Label info;
    float delta;

    @Override
    public void create() {
        stage = GameEntry.stage();
        Group overlap = new Group();
        info = GameGUI.label_Default("", 0, GameEntry.GAMEHEIGHT - 300);
        overlap.addActor(info);
        stage.addActor(overlap);
    }

    @Override
    public void draw() {

    }

    @Override
    public void act() {
        delta += Gdx.graphics.getDeltaTime();
        info.setText("FPS:" + Gdx.graphics.getFramesPerSecond() + "\n" +
                "Delta:" + delta + "\n" +
                "UncompressedMem:" + Gdx.app.getJavaHeap() / 80000 + " MB" + "\n");
        stage.act();
        stage.draw();
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
