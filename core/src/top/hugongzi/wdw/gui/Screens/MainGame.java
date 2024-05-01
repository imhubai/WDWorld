package top.hugongzi.wdw.gui.Screens;

import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.gui.Screens.AbstractScreen;

public class MainGame extends AbstractScreen {
    @Override
    public void create() {
        stage = GameEntry.stage();
    }

    @Override
    public void draw() {

    }

    @Override
    public void act() {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
