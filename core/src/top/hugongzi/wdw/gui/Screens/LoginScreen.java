package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.Log;
import top.hugongzi.wdw.gui.Buttons.GameGUI;

/**
 * 登录页面,继承来自AbstractScreen
 * 用于处理所有关于登录注册相关事件
 *
 * @author Hubai
 */
public class LoginScreen extends AbstractScreen {
    public static String CLASSNAME = LoginScreen.class.getSimpleName();
    ObjectMap<String, Group> page = new ObjectMap<>();
    Group root;

    @Override
    public void create() {
        Log.i(CLASSNAME + " -> create()");
        stage = GameEntry.stage();
        page.put("login", new Group());
        page.put("register", new Group());
        page.put("change", new Group());
        page.put("loading", new Group());
        page.put("update", new Group());
        root = new Group();
        root.setVisible(true);
        for (ObjectMap.Entry<String, Group> entry : page.entries()) {
            fun_page(entry.key);
            root.addActor(entry.value);
        }
        stage.setRoot(root);
    }

    public void fun_page(String pagename) {
        Log.i(CLASSNAME + " -> funpage() => " + pagename);
        switch (pagename) {
            case "login":
                page.get(pagename).setVisible(true);
                int basewidth = GameEntry.GAMEWIDTH / 3 * 2;
                int baseheight = 0;
                Button btn1 = GameGUI.TextBtn_Default("登录", basewidth + 10, baseheight + 100);
                btn1.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //isMarkedRemove=true;
                        SplashScreen splashScreen = new SplashScreen();
                        splashScreen.create();
                        GameEntry.addScreen(splashScreen);
                        //LoginPage.setVisible(false);
                        //RegisterPage.setVisible(true);
                    }
                });
                Button btn2 = GameGUI.TextBtn_Default("注册", basewidth + 220, baseheight + 100);
                btn2.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        isMarkedRemove = true;
                        //LoginPage.setVisible(false);
                        //RegisterPage.setVisible(true);
                    }
                });
                TextField tf_password = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 200);
                tf_password.setPasswordMode(true);
                tf_password.setPasswordCharacter((char) 61440);
                tf_password.setMessageText("请输入密码");
                TextField tf_username = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 280);
                tf_username.setMessageText("请输入账号");
                Label label_username = GameGUI.label_Default("账号", basewidth + 10, baseheight + 290);
                Label label_password = GameGUI.label_Default("密码", basewidth + 10, baseheight + 210);
                page.get(pagename).addActor(label_password);
                page.get(pagename).addActor(label_username);
                page.get(pagename).addActor(tf_password);
                page.get(pagename).addActor(tf_username);
                //page.get(pagename).addActor(image);
                page.get(pagename).addActor(btn1);
                page.get(pagename).addActor(btn2);
                return;
            case "register":
                return;
        }
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
