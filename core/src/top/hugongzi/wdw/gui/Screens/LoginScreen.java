package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.gui.Buttons.GameButton;

/**
 * 登录页面,继承来自AbstractScreen
 * 用于处理所有关于登录注册相关事件
 *
 * @author Hubai
 */
public class LoginScreen extends AbstractScreen {
    @Override
    public void create() {
        stage = GameEntry.stage();
        Group LoginPage = new Group();
        Group RegisterPage = new Group();
        Group ChangePwdPage = new Group();
        Group BanPage = new Group();
        Group Page = new Group();
        LoginPage.setVisible(true);
        RegisterPage.setVisible(false);
        ChangePwdPage.setVisible(false);
        BanPage.setVisible(false);

        Button btn1 = GameButton.TextBtn_Default("显示注册页面", 100, 100);
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LoginPage.setVisible(false);
                RegisterPage.setVisible(true);
            }
        });
        LoginPage.addActor(btn1);
        Label label = new Label("这是注册也面", new Label.LabelStyle(GameEntry.font, Color.CYAN));
        label.setPosition(200, 200);
        RegisterPage.addActor(label);
        Page.addActor(LoginPage);
        Page.addActor(RegisterPage);
        Page.addActor(ChangePwdPage);
        Page.addActor(BanPage);
        stage.setRoot(Page);
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
