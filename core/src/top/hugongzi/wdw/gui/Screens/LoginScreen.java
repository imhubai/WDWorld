package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import top.hugongzi.wdw.Game;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.Log;
import top.hugongzi.wdw.gui.Buttons.GameButton;

public class LoginScreen extends AbstractScreen {
    @Override
    public void create() {
        stage = Game.stage();
        Button btn1= GameButton.TextBtn_Default("你好世界",100,100);
        stage.addActor(btn1);
    }

    @Override
    public void draw() {
        stage.act();
        stage.draw();
    }

    @Override
    public void act() {

    }

    @Override
    public void dispose() {

    }
}
