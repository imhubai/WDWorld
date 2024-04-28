package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import top.hugongzi.wdw.Game;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.Log;

public class LoginScreen extends AbstractScreen {
    @Override
    public void create() {
        stage = Game.stage();
        Gdx.input.setInputProcessor(stage);
        Texture upTexture = new Texture(Gdx.files.internal("ButtonSkins/btn_default_up.png"));
        Texture downTexture = new Texture(Gdx.files.internal("ButtonSkins/btn_default_down.png"));
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(downTexture));

        Button btn1 = new Button(buttonStyle);
        btn1.setPosition(100, 200);
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Log.i("Button clicked");
                isMarkedRemove = true;
                SplashScreen splashScreen=new SplashScreen();
                splashScreen.create();
                GameEntry.addScreen(splashScreen);
            }
        });
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
}
