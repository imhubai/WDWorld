package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
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
    Label msglabel;
    String version = "1.0";
    String serverurl = "http://127.0.0.1/wdwgame/";
    String username, password;

    @Override
    public void create() {
        Log.i(CLASSNAME + " -> create()");
        stage = GameEntry.stage();
        page.put("login", new Group());
        page.put("register", new Group());
        page.put("change", new Group());
        page.put("server", new Group());
        page.put("msg", new Group());
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
            case "msg":
                page.get(pagename).setVisible(false);
                List<Actor> list_msg = new ArrayList<>();
                msglabel = GameGUI.label_Default("", basewidth - 300, baseheight + 400);
                list_msg.add(msglabel);

                Button btn_msg_ok = GameGUI.TextBtn_Default("OK", basewidth - 300, baseheight + 300);
                btn_msg_ok.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        page.get("msg").setVisible(false);
                    }
                });
                list_msg.add(btn_msg_ok);
                for (Actor a : list_msg) page.get(pagename).addActor(a);
                return;
            case "login":
                page.get(pagename).setVisible(true);
                List<Actor> list_login = new ArrayList<>();

                Label label_login_title = GameGUI.label_Big("用户登入", basewidth + 10, baseheight + 360);
                list_login.add(label_login_title);

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

                Button btn_login_login = GameGUI.TextBtn_Default("登录", basewidth + 10, baseheight + 100);
                btn_login_login.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String login_username = tf_login_username.getText();
                        String login_password = tf_login_password.getText();
                        String[] res = post("login.php?username=" + login_username + "&" + "password=" + login_password + "&" + "version=" + version);
                        if (res[0].equals("fail")) {
                            msgwindow(res[1]);
                        } else {
                            page.get("server").setVisible(true);
                            page.get(pagename).setVisible(false);
                        }
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

                for (Actor a : list_login) page.get(pagename).addActor(a);
                return;

            case "register":
                page.get(pagename).setVisible(false);
                List<Actor> list_register = new ArrayList<>();

                Label label_register_title = GameGUI.label_Big("用户注册", basewidth + 10, baseheight + 620);
                list_register.add(label_register_title);

                TextField tf_register_name = GameGUI.Tf_Default("", 320, 48, basewidth + 80, baseheight + 500);
                tf_register_name.setMessageText("请输入昵称(无法修改!)");
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

                Label btn_register_back = GameGUI.label_Default("返回", basewidth + 80, baseheight + 90);
                btn_register_back.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        page.get("login").setVisible(true);
                        page.get(pagename).setVisible(false);
                    }
                });
                list_register.add(btn_register_back);

                Button btn_register = GameGUI.TextBtn_Default("注册", basewidth + 220, baseheight + 80);
                btn_register.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String register_name = tf_register_name.getText();
                        String register_username = tf_register_username.getText();
                        String register_password = tf_register_password.getText();
                        String register_password2 = tf_register_password2.getText();
                        String register_email = label_register_email.getText().toString();
                        if (!register_password.equals(register_password2)) {
                            msgwindow("密码不一致!");
                        } else {
                            String[] res = post("reg.php?username=" + register_username + "&" + "name=" + register_name + "&" + "password=" + register_password + "&" + "email=" + register_email + "&" + "version=" + version);
                            if (res[0].equals("fail")) {
                                msgwindow(res[1]);
                            } else {
                                msgwindow(res[1]);
                                page.get("login").setVisible(true);
                                page.get(pagename).setVisible(false);
                            }
                        }
                    }
                });
                list_register.add(btn_register);

                for (Actor a : list_register) page.get(pagename).addActor(a);
                return;

            case "server":
                page.get(pagename).setVisible(false);
                List<Actor> list_server = new ArrayList<>();

                for (Actor a : list_server) page.get(pagename).addActor(a);
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

    public String[] post(String cmd) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(serverurl + cmd).build();
        final String[] back = {"", ""};
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                HttpStatus httpStatus = httpResponse.getStatus();
                if (httpStatus.getStatusCode() == 200) {
                    String[] temp = httpResponse.getResultAsString().split("\\|");
                    back[0] = temp[0];
                    back[1] = temp[1];
                    Log.i(CLASSNAME + " -> post() => " + httpStatus.getStatusCode());
                }
            }

            @Override
            public void failed(Throwable throwable) {
                msgwindow("服务器异常\n" + throwable.toString());
            }

            @Override
            public void cancelled() {
                msgwindow("取消操作");
            }

        });
        return back;
    }

    public void msgwindow(String msg) {
        msglabel.setText(msg);
        page.get("msg").setVisible(true);
    }
}
