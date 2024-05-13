package top.hugongzi.wdw.gui.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import top.hugongzi.wdw.GameEntry;

/**
 * 快速实例化组件
 * @author Hubai
 */
public class GameGUI {
    public static Texture upTexture = new Texture(Gdx.files.internal("GUISkins/btn_default_up.png"));
    public static Texture downTexture = new Texture(Gdx.files.internal("GUISkins/btn_default_down.png"));
    public static Texture squareUpTexture = new Texture(Gdx.files.internal("GUISkins/btn_square_up.png"));
    public static Texture squareDownTexture = new Texture(Gdx.files.internal("GUISkins/btn_square_down.png"));
    public static Texture backgroundTexture = new Texture(Gdx.files.internal("GUISkins/input_bg_up.png"));
    public static Texture cursorTexture = new Texture(Gdx.files.internal("GUISkins/cursor.png"));

    public static Label label_Default(String label,int pos1,int pos2){
        Label.LabelStyle labelStyle=new Label.LabelStyle();
        labelStyle.font=GameEntry.font.get("cubic20");
        labelStyle.fontColor=Color.WHITE;
        Label label1 = new Label(label,labelStyle);
        label1.setPosition(pos1,pos2);
        return label1;
    }
    public static Label label_Big(String label,int pos1,int pos2){
        Label.LabelStyle labelStyle=new Label.LabelStyle();
        labelStyle.font=GameEntry.font.get("cubic32");
        labelStyle.fontColor=Color.WHITE;
        Label label1 = new Label(label,labelStyle);
        label1.setPosition(pos1,pos2);
        return label1;
    }

    public static TextButton TextBtn_Default(String label, int pos1, int pos2) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        textButtonStyle.font = GameEntry.font.get("cubic20");
        TextButton textButton = new TextButton(label, textButtonStyle);
        textButton.setPosition(pos1, pos2);
        return textButton;
    }
    public static TextButton TextBtn_Square(String label, int pos1, int pos2) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(squareUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(squareDownTexture));
        textButtonStyle.font = GameEntry.font.get("cubic20");
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
    public static TextField Tf_Default(String text,float w,float h,int pos1, int pos2) {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background=new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        textFieldStyle.font=GameEntry.font.get("cubic20");
        textFieldStyle.fontColor= Color.WHITE;
        textFieldStyle.cursor=new TextureRegionDrawable(new TextureRegion(cursorTexture));
        TextField textField = new TextField(text,textFieldStyle);
        textField.setBlinkTime(0.5f);
        textField.setAlignment(1);
        textField.setPosition(pos1, pos2);
        textField.setSize(w,h);
        return textField;
    }
}
