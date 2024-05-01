package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import top.hugongzi.wdw.GameEntry;

public class SplashScreen extends AbstractScreen {
    LoginScreen loginScreen;
    Texture texture;
    Image image;

    @Override
    public void create() {
        stage = GameEntry.stage();
        texture = new Texture(Gdx.files.internal("Images/640_pixel_logo.png"));
        image = new Image(new TextureRegion(texture));
        image.setPosition((float) GameEntry.GAMEWIDTH / 2 - (float) texture.getWidth() / 2, (float) GameEntry.GAMEHEIGHT / 2 - (float) texture.getHeight() / 2);
        stage.addActor(image);
        image.addAction(Actions.fadeOut(1f));
        loginScreen = new LoginScreen();
        loginScreen.create();
        GameEntry.addScreen(loginScreen);
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
        texture.dispose();
    }
}
