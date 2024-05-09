package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.core.Log;
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
public class ScreenLogin extends AbstractScreen {
    public static String CLASSNAME = ScreenLogin.class.getSimpleName();
    ObjectMap<String, Group> page = new ObjectMap<>();
    Group root;
    Label msglabel, label_server_title;
    String version = "1.0";
    String phpserverurl = "https://hugongzi.top/wdwgame/";
    Hashtable<String, String> gameurl = new Hashtable<>();
    String username, password, name, email;
    int basewidth = GameEntry.GAMEWIDTH / 5 * 3, baseheight = 0;
    List<TextButton> serverbtnlist = new ArrayList<>();
    boolean drawheroimg = true, singlegamelock =true;

    @Override
    public void create() {
        Log.i(CLASSNAME + " -> create()");
        if (GameEntry.GAMEWIDTH <= 800) {
            Log.e("Doesn't support 800*n or lower des.");
            Gdx.graphics.setWindowedMode(1024, 720);
            basewidth = 1024 / 4;
            drawheroimg = false;
        } else if (GameEntry.GAMEWIDTH <= 1024) {
            basewidth = GameEntry.GAMEWIDTH / 5 * 3;
            drawheroimg = false;
        } else if (GameEntry.GAMEWIDTH >= 1920) {
            basewidth = GameEntry.GAMEWIDTH / 4 * 3;
        }
        stage = GameEntry.stage();
        page.put("login", new Group());
        page.put("register", new Group());
        page.put("change", new Group());
        page.put("server", new Group());
        page.put("other", new Group());
        page.put("msg", new Group());
        root = new Group();
        for (ObjectMap.Entry<String, Group> entry : page.entries()) {
            fun_page(entry.key);
            entry.value.setPosition(basewidth, baseheight);
            root.addActor(entry.value);
        }
        root.setVisible(true);
        stage.addActor(root);
    }

    public void fun_page(String pagename) {
        switch (pagename) {
            case "msg":
                page.get(pagename).setVisible(false);
                List<Actor> list_msg = new ArrayList<>();
                msglabel = GameGUI.label_Default("", GameEntry.GAMEWIDTH / 2 - basewidth - 100, GameEntry.GAMEHEIGHT / 2);
                list_msg.add(msglabel);

                Button btn_msg_ok = GameGUI.TextBtn_Default("OK", GameEntry.GAMEWIDTH / 2 - basewidth - 100, GameEntry.GAMEHEIGHT / 2 - 100);
                btn_msg_ok.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        page.get("msg").setVisible(false);
                    }
                });
                list_msg.add(btn_msg_ok);

                for (Actor a : list_msg) page.get(pagename).addActor(a);
                return;
            case "other":
                page.get(pagename).setVisible(true);
                List<Actor> list_other = new ArrayList<>();
                String verlabel_s = "ver:" + GameEntry.GAMEVERSION + " Test Version Visit www.hugongzi.top";
                Label verLabel = GameGUI.label_Default(verlabel_s, 0, 0);
                list_other.add(verLabel);

                Texture hero = new Texture(Gdx.files.internal("Images/hero_index_login.png"));
                Image image_hero = new Image(new TextureRegion(hero));
                image_hero.setPosition((float) 10 - basewidth, 0);
                image_hero.getColor().a=0;
                AlphaAction action_alpha = Actions.alpha(1F, 0.7F);
                image_hero.addAction(action_alpha);
                if (drawheroimg) list_other.add(image_hero);

                for (Actor a : list_other) page.get(pagename).addActor(a);
                return;
            case "login":
                page.get(pagename).setVisible(true);
                List<Actor> list_login = new ArrayList<>();

                Label label_login_title = GameGUI.label_Big("User Login", 10, 360);
                list_login.add(label_login_title);

                TextField tf_login_password = GameGUI.Tf_Default("", 350, 48, 80, 200);
                tf_login_password.setPasswordMode(true);
                tf_login_password.setPasswordCharacter((char) 61440);
                tf_login_password.setMessageText("Input password");
                tf_login_password.setText("123456");
                list_login.add(tf_login_password);

                TextField tf_login_username = GameGUI.Tf_Default("", 350, 48, 80, 280);
                tf_login_username.setMessageText("Input userid");
                tf_login_username.setText("admin");
                list_login.add(tf_login_username);

                Label label_login_username = GameGUI.label_Default("account", 10, 290);
                list_login.add(label_login_username);

                Label label_login_password = GameGUI.label_Default("password", 10, 210);
                list_login.add(label_login_password);

                Button btn_login_login = GameGUI.TextBtn_Default("Login", 10, 100);
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

                Button btn_login_register = GameGUI.TextBtn_Default("Register", 220, 100);
                btn_login_register.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        pageto(pagename, "register");
                    }
                });
                list_login.add(btn_login_register);

                for (Actor a : list_login) page.get(pagename).addActor(a);
                return;

            case "register":
                page.get(pagename).setVisible(false);
                List<Actor> list_register = new ArrayList<>();

                Label label_register_title = GameGUI.label_Big("User Register", 10, 620);
                list_register.add(label_register_title);

                TextField tf_register_name = GameGUI.Tf_Default("", 320, 48, 80, 500);
                tf_register_name.setMessageText("Please input name");
                list_register.add(tf_register_name);

                Label label_register_name = GameGUI.label_Default("name", 10, 510);
                list_register.add(label_register_name);

                TextField tf_register_username = GameGUI.Tf_Default("", 320, 48, 80, 420);
                tf_register_username.setMessageText("locase alpha,4-18 chars");
                list_register.add(tf_register_username);

                Label label_register_username = GameGUI.label_Default("account", 10, 430);
                list_register.add(label_register_username);

                TextField tf_register_password = GameGUI.Tf_Default("", 320, 48, 80, 340);
                tf_register_password.setPasswordMode(true);
                tf_register_password.setPasswordCharacter((char) 61440);
                tf_register_password.setMessageText("Input password");
                list_register.add(tf_register_password);

                Label label_register_password = GameGUI.label_Default("password", 10, 350);
                list_register.add(label_register_password);

                TextField tf_register_password2 = GameGUI.Tf_Default("", 320, 48, 80, 260);
                tf_register_password2.setPasswordMode(true);
                tf_register_password2.setPasswordCharacter((char) 61440);
                tf_register_password2.setMessageText("Input password again");
                list_register.add(tf_register_password2);

                Label label_register_password2 = GameGUI.label_Default("verify", 10, 270);
                list_register.add(label_register_password2);

                TextField tf_register_email = GameGUI.Tf_Default("", 320, 48, 80, 180);
                tf_register_email.setMessageText("input email");
                list_register.add(tf_register_email);

                Label label_register_email = GameGUI.label_Default("email", 10, 190);
                list_register.add(label_register_email);

                Label btn_register_back = GameGUI.label_Default("back", 80, 90);
                btn_register_back.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        pageto(pagename, "login");
                    }
                });
                list_register.add(btn_register_back);

                Button btn_register = GameGUI.TextBtn_Default("Register", 220, 80);
                btn_register.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String register_name = tf_register_name.getText();
                        String register_username = tf_register_username.getText();
                        String register_password = tf_register_password.getText();
                        String register_password2 = tf_register_password2.getText();
                        String register_email = label_register_email.getText().toString();
                        if (!register_password.equals(register_password2)) {
                            msgwindow("password isn't same!");
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
                Label label_server_change = GameGUI.label_Default("Change info", 10, 580);
                label_server_change.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        pageto(pagename, "change");
                    }
                });
                list_server.add(label_server_change);

                Label label_server_off = GameGUI.label_Default("Exit account", 150, 580);

                label_server_off.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        pageto(pagename, "login");
                    }
                });
                list_server.add(label_server_off);

                Label label_server_listtitle = GameGUI.label_Default("Server List", 10, 520);
                list_server.add(label_server_listtitle);
                serverbtnlist.add(GameGUI.TextBtn_Default("", 10, 500 - 50));
                serverbtnlist.add(GameGUI.TextBtn_Default("", 10, 500 - 110));
                serverbtnlist.add(GameGUI.TextBtn_Default("", 10, 500 - 170));
                serverbtnlist.add(GameGUI.TextBtn_Default("", 10, 500 - 230));
                for (Actor a : list_server) page.get(pagename).addActor(a);
                return;
            case "change":
                page.get(pagename).setVisible(false);
                List<Actor> list_change = new ArrayList<>();

                Label label_change_title = GameGUI.label_Big("Change", 10, 620);
                list_change.add(label_change_title);

                TextField tf_change_newpwd = GameGUI.Tf_Default("", 320, 48, 80, 500);
                tf_change_newpwd.setMessageText("new password(not change without modification)");
                list_change.add(tf_change_newpwd);

                Label label_change_newpwd = GameGUI.label_Default("password", 10, 510);
                list_change.add(label_change_newpwd);

                TextField tf_change_newemail = GameGUI.Tf_Default("", 320, 48, 80, 420);
                tf_change_newemail.setMessageText("new email(not change without modification)");
                list_change.add(tf_change_newemail);

                Label label_change_newemail = GameGUI.label_Default("email", 10, 430);
                list_change.add(label_change_newemail);

                Label btn_change_back = GameGUI.label_Default("back", 80, 90);
                btn_change_back.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        pageto(pagename, "server");
                    }
                });
                list_change.add(btn_change_back);

                Button btn_change = GameGUI.TextBtn_Default("提交", 220, 80);
                btn_change.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String newpwd = tf_change_newpwd.getText();
                        String newemail = tf_change_newemail.getText();
                        String cmd = "updateuser.php?username=" + username + "&" + "password=" + password + "&" + "version=" + version;
                        if (newpwd.isEmpty() && newemail.isEmpty()) {
                            msgwindow("new password or email leave blank");
                            return;
                        } else if (!newpwd.isEmpty()) {
                            cmd += "&newpwd=" + newpwd;
                        } else {
                            cmd += "&newemail=" + newemail;
                        }
                        post(cmd, "change");
                    }
                });
                list_change.add(btn_change);
                for (Actor a : list_change) page.get(pagename).addActor(a);
        }
    }

    private void pageto(String thispage, String pageto) {
        Log.i(CLASSNAME + " -> " + thispage + " => " + pageto);
        page.get(thispage).clearActions();
        page.get(thispage).addAction(Actions.fadeOut(0.2f));
        page.get(thispage).setVisible(false);
        page.get(pageto).setVisible(true);
        page.get(pageto).clearActions();
        page.get(pageto).addAction(Actions.fadeIn(0.2f));
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
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        root.remove();
    }

    /**
     * post执行php操作
     *
     * @param cmd       命令,在url的?之后
     * @param operation 操作方式
     */
    public void post(String cmd, String operation) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url(phpserverurl + cmd).build();
        httpRequest.setTimeOut(30000);
        Log.d(CLASSNAME + " -> post():" + operation);
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
                        case "change":
                            funchange(temp);
                            break;
                        default:
                            Log.e("Unexpected value: " + operation, new IllegalStateException());
                    }
                }
            }

            @Override
            public void failed(Throwable throwable) {
                msgwindow("Server error\n" + throwable.toString());
            }

            @Override
            public void cancelled() {
                msgwindow("Cancel");
            }

        });
    }

    private void funchange(String[] res) {
        if (res[0].equals("success")) {
            msgwindow(res[1]);
            pageto("change", "server");
        } else {
            msgwindow(res[1]);
        }
    }

    private void fungetinfo(String[] res) {
        if (res[0].equals("success")) {
            String[] obj = res[1].split("&");
            name = obj[0];
            email = obj[2];
            label_server_title.setText("Welcome," + name + "!");
        } else {
            msgwindow(res[1]);
        }
    }

    /**
     * 提示框
     *
     * @param msg 消息
     */
    public void msgwindow(String msg) {
        msglabel.setText(msg);
        page.get("msg").setVisible(true);
    }

    public void funlogin(String[] res) {
        if (res[0].equals("success")) {
            pageto("login", "server");
            post("server.php?username=" + username + "&" + "password=" + password, "server");
        } else {
            msgwindow(res[1]);
        }
    }

    public void funregister(String[] res) {
        if (res[0].equals("success")) {
            msgwindow(res[1]);
            pageto("register", "login");
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
                        startGame(btn.getText().toString());
                    }
                });
                page.get("server").addActor(btn);
            }
            if (temp.length == 0) {
                TextButton btn = serverbtnlist.get(0);
                btn.setText("Server is under maintenance");
                page.get("server").addActor(btn);
            }
            post("getuserinfo.php?username=" + username + "&" + "password=" + password + "&" + "version=" + version, "getinfo");
        } else {
            msgwindow(res[1]);
        }
    }

    private void startGame(String serverName) {
        GameEntry.maingame = MainGame.getInstance(gameurl.get(serverName), serverName, phpserverurl);
        if(singlegamelock){
            GameEntry.addScreen(GameEntry.maingame);
            //Collections.reverse(GameEntry.screens);
            //Log.i(GameEntry.screens);
            singlegamelock =false;
        }
    }
}
