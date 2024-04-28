package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import top.hugongzi.wdw.Game;

public class SplashScreen extends AbstractScreen {
    @Override
    public void create() {
        stage = Game.stage();
        Texture texture = new Texture(Gdx.files.internal("Images/bg_gal.png"));

        Image image = new Image(new TextureRegion(texture));

        image.setPosition(0, 0);
        image.setScale(1.0F, 1.0F);
        image.setRotation(0);

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
}
