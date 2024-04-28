package top.hugongzi.wdw.gui.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import top.hugongzi.wdw.GameEntry;

public class GameButton {
    public static Texture upTexture = new Texture(Gdx.files.internal("ButtonSkins/btn_default_up.png"));
    public static Texture downTexture = new Texture(Gdx.files.internal("ButtonSkins/btn_default_down.png"));

    public static TextButton TextBtn_Default(String label, int pos1, int pos2) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        textButtonStyle.font = GameEntry.font;
        TextButton textButton = new TextButton(label, textButtonStyle);
        textButton.setPosition(pos1, pos2);
        return textButton;
    }

    public static Button Btn_Default(int pos1, int pos2) {
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        Button button = new Button(buttonStyle);
        button.setPosition(pos1, pos2);
        return button;
    }
}
