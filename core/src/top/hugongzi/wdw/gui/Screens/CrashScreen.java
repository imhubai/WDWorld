package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.gui.Buttons.GameGUI;

public class CrashScreen extends AbstractScreen {
    Label crashLabel;

    @Override
    public void create() {
        stage = GameEntry.stage();
        crashLabel = GameGUI.label_Big(" :(\n崩溃啦，请检查业务逻辑", 0, 0);
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
    public void dispose() {
    }
}
