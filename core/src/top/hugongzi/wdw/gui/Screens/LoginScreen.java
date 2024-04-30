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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.fcore.Log;
import top.hugongzi.wdw.gui.Buttons.GameGUI;

import java.util.ArrayList;
import java.util.Hashtable;
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
    Label msglabel, label_server_title;
    String version = "1.0";
    String serverurl = "http://127.0.0.1/wdwgame/";
    Hashtable<String, String> gameurl = new Hashtable<>();
    String username, password, name, email;
    int basewidth, baseheight;
    List<TextButton> serverbtnlist = new ArrayList<>();

    @Override
    public void create() {
        Log.i(CLASSNAME + " -> create()");
        stage = GameEntry.stage();
        basewidth = GameEntry.GAMEWIDTH / 3 * 2;
        baseheight = 0;
        page.put("login", new Group());
        page.put("register", new Group());
        page.put("change", new Group());
        page.put("server", new Group());
        page.put("msg", new Group());
        root = new Group();
        root.setVisible(true);
        for (ObjectMap.Entry<String, Group> entry : page.entries()) {
            fun_page(entry.key);
            entry.value.setPosition(basewidth, baseheight);
            root.addActor(entry.value);
        }
        stage.addActor(root);
    }

    public void fun_page(String pagename) {
        Log.i(CLASSNAME + " -> funpage() => " + pagename);
        switch (pagename) {
            case "msg":
                page.get(pagename).setVisible(false);
                List<Actor> list_msg = new ArrayList<>();
                msglabel = GameGUI.label_Default("", GameEntry.GAMEWIDTH/2-basewidth, GameEntry.GAMEHEIGHT / 2);
                list_msg.add(msglabel);

                Button btn_msg_ok = GameGUI.TextBtn_Default("OK", GameEntry.GAMEWIDTH/2-basewidth, GameEntry.GAMEHEIGHT / 2-100);
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

                Label label_login_title = GameGUI.label_Big("用户登入", 10, 360);
                list_login.add(label_login_title);

                TextField tf_login_password = GameGUI.Tf_Default("", 320, 48, 80, 200);
                tf_login_password.setPasswordMode(true);
                tf_login_password.setPasswordCharacter((char) 61440);
                tf_login_password.setMessageText("请输入密码");
                list_login.add(tf_login_password);

                TextField tf_login_username = GameGUI.Tf_Default("", 320, 48, 80, 280);
                tf_login_username.setMessageText("请输入账号");
                list_login.add(tf_login_username);

                Label label_login_username = GameGUI.label_Default("账号", 10, 290);
                list_login.add(label_login_username);

                Label label_login_password = GameGUI.label_Default("密码", 10, 210);
                list_login.add(label_login_password);

                Button btn_login_login = GameGUI.TextBtn_Default("登录", 10, 100);
                btn_login_login.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String login_username = tf_login_username.getText();
                        String login_password = tf_login_password.getText();
                        username = login_username;
                        password = login_password;
                        post("login.php?username=" + login_username + "&" + "password=" + login_password + "&" + "version=" + version, "login");
                    }
                });
                list_login.add(btn_login_login);

                Button btn_login_register = GameGUI.TextBtn_Default("注册", 220, 100);
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

                Label label_register_title = GameGUI.label_Big("用户注册", 10, 620);
                list_register.add(label_register_title);

                TextField tf_register_name = GameGUI.Tf_Default("", 320, 48, 80, 500);
                tf_register_name.setMessageText("请输入昵称(无法修改!)");
                list_register.add(tf_register_name);

                Label label_register_name = GameGUI.label_Default("昵称", 10, 510);
                list_register.add(label_register_name);

                TextField tf_register_username = GameGUI.Tf_Default("", 320, 48, 80, 420);
                tf_register_username.setMessageText("小写字母开头,4-18个字节");
                list_register.add(tf_register_username);

                Label label_register_username = GameGUI.label_Default("账号", 10, 430);
                list_register.add(label_register_username);

                TextField tf_register_password = GameGUI.Tf_Default("", 320, 48, 80, 340);
                tf_register_password.setPasswordMode(true);
                tf_register_password.setPasswordCharacter((char) 61440);
                tf_register_password.setMessageText("请输入密码");
                list_register.add(tf_register_password);

                Label label_register_password = GameGUI.label_Default("密码", 10, 350);
                list_register.add(label_register_password);

                TextField tf_register_password2 = GameGUI.Tf_Default("", 320, 48, 80, 260);
                tf_register_password2.setPasswordMode(true);
                tf_register_password2.setPasswordCharacter((char) 61440);
                tf_register_password2.setMessageText("请再输入一次密码");
                list_register.add(tf_register_password2);

                Label label_register_password2 = GameGUI.label_Default("验证", 10, 270);
                list_register.add(label_register_password2);

                TextField tf_register_email = GameGUI.Tf_Default("", 320, 48, 80, 180);
                tf_register_email.setMessageText("请输入邮箱");
                list_register.add(tf_register_email);

                Label label_register_email = GameGUI.label_Default("邮箱", 10, 190);
                list_register.add(label_register_email);

                Label btn_register_back = GameGUI.label_Default("返回", 80, 90);
                btn_register_back.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        page.get("login").setVisible(true);
                        page.get(pagename).setVisible(false);
                    }
                });
                list_register.add(btn_register_back);

                Button btn_register = GameGUI.TextBtn_Default("注册", 220, 80);
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
                            post("reg.php?username=" + register_username + "&" + "name=" + register_name + "&" + "password=" + register_password + "&" + "email=" + register_email + "&" + "version=" + version, "reg");
                        }
                    }
                });
                list_register.add(btn_register);

                for (Actor a : list_register) page.get(pagename).addActor(a);
                return;

            case "server":
                page.get(pagename).setVisible(false);
                List<Actor> list_server = new ArrayList<>();
                label_server_title = GameGUI.label_Big("welcome", 10, 620);
                list_server.add(label_server_title);
                Label label_server_change = GameGUI.label_Default("修改信息", 10, 580);
                label_server_change.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        page.get(pagename).setVisible(false);
                        page.get("change").setVisible(true);
                    }
                });
                list_server.add(label_server_change);

                Label label_server_off = GameGUI.label_Default("退出登录", 150, 580);

                label_server_off.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        page.get(pagename).setVisible(false);
                        page.get("login").setVisible(true);
                    }
                });
                list_server.add(label_server_off);

                Label label_server_listtitle = GameGUI.label_Default("服务器列表", 10, 520);
                list_server.add(label_server_listtitle);
                serverbtnlist.add(GameGUI.TextBtn_Default("", 10, 500 - 50));
                serverbtnlist.add(GameGUI.TextBtn_Default("", 10, 500 - 110));
                serverbtnlist.add(GameGUI.TextBtn_Default("", 10, 500 - 170));
                serverbtnlist.add(GameGUI.TextBtn_Default("", 10, 500 - 230));
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

    public void post(String cmd, String operation) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(serverurl + cmd).build();
        //httpRequest.setTimeOut(3);
        Log.d(CLASSNAME + " -> post()");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                HttpStatus httpStatus = httpResponse.getStatus();
                if (httpStatus.getStatusCode() == 200) {
                    String[] temp = httpResponse.getResultAsString().split("\\|");
                    Log.d(CLASSNAME + " -> post() => " + temp[0]);
                    switch (operation) {
                        case "login":
                            funlogin(temp);
                            break;
                        case "reg":
                            funregister(temp);
                            break;
                        case "server":
                            fungetserver(temp);
                            break;
                        case "getinfo":
                            fungetinfo(temp);
                            break;
                    }
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
    }

    private void fungetinfo(String[] res) {
        if (res[0].equals("success")) {
            String[] obj = res[1].split("&");
            name = obj[0];
            email = obj[2];
            label_server_title.setText("欢迎你," + name + "!");
        } else {
            msgwindow(res[1]);
        }
    }

    public void msgwindow(String msg) {
        msglabel.setText(msg);
        page.get("msg").setVisible(true);
    }

    public void funlogin(String[] res) {
        if (res[0].equals("success")) {
            page.get("server").setVisible(true);
            page.get("login").setVisible(false);
            post("server.php?username=" + username + "&" + "password=" + password, "server");
        } else {
            msgwindow(res[1]);
        }
    }

    public void funregister(String[] res) {
        if (res[0].equals("success")) {
            msgwindow(res[1]);
            page.get("login").setVisible(true);
            page.get("register").setVisible(false);
        } else {
            msgwindow(res[1]);
        }
    }

    public void fungetserver(String[] res) {
        if (res[0].equals("success")) {
            String[] temp = res[1].split("#");
            for (int i = 0; i < (Math.min(temp.length, 4)); ++i) {
                String[] obj = temp[i].split("&");
                gameurl.put(obj[0], obj[1]);
                TextButton btn = serverbtnlist.get(i);
                btn.setText(obj[0]);
                btn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Log.i(btn.getText().toString() + "clicked!");
                    }
                });
                page.get("server").addActor(btn);
            }
            post("getuserinfo.php?username=" + username + "&" + "password=" + password + "&" + "version=" + version, "getinfo");
        } else {
            msgwindow(res[1]);
        }
    }
}
