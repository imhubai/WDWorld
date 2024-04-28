package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.Log;

public class SplashScreen extends AbstractScreen {
    @Override
    public void create() {
        stage = GameEntry.stage();
        Texture texture = new Texture(Gdx.files.internal("Images/bg_gal.png"));

        Image image = new Image(new TextureRegion(texture));

        image.setPosition(200, 200);
        image.setScale(1.0F, 1.0F);
        image.setRotation(0);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Log.i("Image clicked");
                isMarkedRemove = true;
            }
        });
        stage.addActor(image);
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
