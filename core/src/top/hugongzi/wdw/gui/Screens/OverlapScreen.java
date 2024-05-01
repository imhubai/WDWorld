package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.gui.Buttons.GameGUI;

public class OverlapScreen extends AbstractScreen {
    public static String CLASSNAME = LoginScreen.class.getSimpleName();
    Label info;

    @Override
    public void create() {
        stage = GameEntry.stage();
        Group overlap = new Group();
        info = GameGUI.label_Default("", 0,GameEntry.GAMEHEIGHT - 300);
        overlap.addActor(info);
        stage.addActor(overlap);
    }

    @Override
    public void draw() {

    }

    @Override
    public void act() {
//        info.setText("FPS:" + Gdx.graphics.getFramesPerSecond() + "\n" +
//                "Delta:" + Gdx.graphics.getDeltaTime() + "\n" +
//                Gdx.graphics.getGLVersion().getDebugVersionString() + "\n" +
//                "UncompressedMem:" + Gdx.app.getJavaHeap()/80000+" MB"+"\n");
        info.setText("UncompressedMem:" + Gdx.app.getJavaHeap()/80000+" MB"+"\n");
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
