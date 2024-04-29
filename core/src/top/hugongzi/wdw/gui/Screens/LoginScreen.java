package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
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

import java.util.ArrayList;
import java.util.List;

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
        int basewidth = GameEntry.GAMEWIDTH / 3 * 2;
        int baseheight = 0;
        switch (pagename) {
            case "login":
                page.get(pagename).setVisible(true);
                List<Actor> list_login = new ArrayList<>();

                Label label_login_title = GameGUI.label_Big("用户登入", basewidth + 10, baseheight + 360);
                list_login.add(label_login_title);

                Button btn_login_login = GameGUI.TextBtn_Default("登录", basewidth + 10, baseheight + 100);
                btn_login_login.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }
                });
                list_login.add(btn_login_login);

                Button btn_login_register = GameGUI.TextBtn_Default("注册", basewidth + 220, baseheight + 100);
                btn_login_register.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        page.get("register").setVisible(true);
                        page.get(pagename).setVisible(false);
                    }
                });
                list_login.add(btn_login_register);

                TextField tf_login_password = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 200);
                tf_login_password.setPasswordMode(true);
                tf_login_password.setPasswordCharacter((char) 61440);
                tf_login_password.setMessageText("请输入密码");
                list_login.add(tf_login_password);

                TextField tf_login_username = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 280);
                tf_login_username.setMessageText("请输入账号");
                list_login.add(tf_login_username);

                Label label_login_username = GameGUI.label_Default("账号", basewidth + 10, baseheight + 290);
                list_login.add(label_login_username);

                Label label_login_password = GameGUI.label_Default("密码", basewidth + 10, baseheight + 210);
                list_login.add(label_login_password);
                for (Actor a : list_login) page.get(pagename).addActor(a);
                return;

            case "register":
                page.get(pagename).setVisible(false);
                List<Actor> list_register = new ArrayList<>();

                Label label_register_title = GameGUI.label_Big("用户注册", basewidth + 10, baseheight + 620);
                list_register.add(label_register_title);

                Label btn_register_back = GameGUI.label_Default("返回", basewidth + 80, baseheight + 90);
                btn_register_back.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Log.i("clicked");
                    }
                });
                list_register.add(btn_register_back);

                Button btn_register = GameGUI.TextBtn_Default("注册", basewidth + 220, baseheight + 80);
                btn_register.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }
                });
                list_register.add(btn_register);
                TextField tf_register_name = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 500);
                tf_register_name.setMessageText("请输入昵称");
                list_register.add(tf_register_name);

                Label label_register_name = GameGUI.label_Default("昵称", basewidth + 10, baseheight + 510);
                list_register.add(label_register_name);

                TextField tf_register_username = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 420);
                tf_register_username.setMessageText("小写字母开头,4-18个字节");
                list_register.add(tf_register_username);

                Label label_register_username = GameGUI.label_Default("账号", basewidth + 10, baseheight + 430);
                list_register.add(label_register_username);

                TextField tf_register_password = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 340);
                tf_register_password.setPasswordMode(true);
                tf_register_password.setPasswordCharacter((char) 61440);
                tf_register_password.setMessageText("请输入密码");
                list_register.add(tf_register_password);

                Label label_register_password = GameGUI.label_Default("密码", basewidth + 10, baseheight + 350);
                list_register.add(label_register_password);

                TextField tf_register_password2 = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 260);
                tf_register_password2.setPasswordMode(true);
                tf_register_password2.setPasswordCharacter((char) 61440);
                tf_register_password2.setMessageText("请再输入一次密码");
                list_register.add(tf_register_password2);

                Label label_register_password2 = GameGUI.label_Default("验证", basewidth + 10, baseheight + 270);
                list_register.add(label_register_password2);

                TextField tf_register_email = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 180);
                tf_register_email.setMessageText("请输入邮箱");
                list_register.add(tf_register_email);

                Label label_register_email = GameGUI.label_Default("邮箱", basewidth + 10, baseheight + 190);
                list_register.add(label_register_email);

                for (Actor a : list_register) page.get(pagename).addActor(a);
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
