package top.hugongzi.wdw.gui.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import top.hugongzi.wdw.GameEntry;

/**
 * 快速实例化组件
 *
 * @author Hubai
 */
public class GameGUI {
    public static Texture upTexture = new Texture(Gdx.files.internal("GUISkins/btn_default_up.png"));
    public static Texture downTexture = new Texture(Gdx.files.internal("GUISkins/btn_default_down.png"));
    public static Texture squareUpTexture = new Texture(Gdx.files.internal("GUISkins/btn_square_up.png"));
    public static Texture squareDownTexture = new Texture(Gdx.files.internal("GUISkins/btn_square_down.png"));
    public static Texture backgroundTexture = new Texture(Gdx.files.internal("GUISkins/input_bg_up.png"));
    public static Texture cursorTexture = new Texture(Gdx.files.internal("GUISkins/cursor.png"));

    public static Label label(String label, int pos1, int pos2, String fonts, Color c) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GameEntry.font.get(fonts);
        labelStyle.fontColor = c;
        Label label1 = new Label(label, labelStyle);
        label1.setPosition(pos1, pos2);
        return label1;
    }

    public static TextButton textbtn_default(String label, int pos1, int pos2, String fonts, Color c) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        textButtonStyle.font = GameEntry.font.get(fonts);
        textButtonStyle.fontColor = c;
        TextButton textButton = new TextButton(label, textButtonStyle);
        textButton.setPosition(pos1, pos2);
        return textButton;
    }

    public static TextButton textbtn_square(String label, int pos1, int pos2, String fonts, Color c) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(squareUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(squareDownTexture));
        textButtonStyle.font = GameEntry.font.get(fonts);
        textButtonStyle.fontColor = c;
        TextButton textButton = new TextButton(label, textButtonStyle);
        textButton.setPosition(pos1, pos2);
        return textButton;
    }

    public static Button btn_default(int pos1, int pos2) {
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        Button button = new Button(buttonStyle);
        button.setPosition(pos1, pos2);
        return button;
    }

    public static Button btn_custom(int pos1, int pos2, String upTexture, String downTexture) {
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(upTexture))));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(downTexture))));
        Button button = new Button(buttonStyle);
        button.setPosition(pos1, pos2);
        return button;
    }

    public static TextField tf_default(String text, float w, float h, int pos1, int pos2, String fonts, Color c) {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        textFieldStyle.font = GameEntry.font.get(fonts);
        textFieldStyle.fontColor = c;
        textFieldStyle.cursor = new TextureRegionDrawable(new TextureRegion(cursorTexture));
        TextField textField = new TextField(text, textFieldStyle);
        textField.setBlinkTime(0.5f);
        textField.setAlignment(1);
        textField.setPosition(pos1, pos2);
        textField.setSize(w, h);
        return textField;
    }
}
