package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.core.AnimationManager;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.gui.Buttons.GameGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ScreenCreateCharacter extends AbstractScreen {
    Group root, msg;
    Label msglabel, label_skillpoints, label_point_hp, label_point_power, label_point_magic, label_point_brain, label_point_speed;
    boolean ismale = true;
    String bornplace = "yangzhou";
    String name;
    int points = 25;
    int hppoints = 20, powerpoints = 20, magicpoints = 20, brainpoints = 20, speedpoints = 20;
    MainGame mainGame;

    public ScreenCreateCharacter(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    public void create() {
        stage = GameEntry.stage();
        root = new Group();
        msg = new Group();

        List<Actor> list_msg = new ArrayList<>();
        msglabel = GameGUI.label("", GameEntry.GAMEWIDTH / 2 - 150, GameEntry.GAMEHEIGHT - 200, "cubic24", Color.WHITE);
        list_msg.add(msglabel);

        Button btn_msg_ok = GameGUI.textbtn_default("OK", GameEntry.GAMEWIDTH / 2 - 140, GameEntry.GAMEHEIGHT - 300, "cubic24", Color.WHITE);
        btn_msg_ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                msg.setVisible(false);
            }
        });
        list_msg.add(btn_msg_ok);
        for (Actor a : list_msg) msg.addActor(a);
        msg.setVisible(false);
        root.addActor(msg);

        List<Actor> actorlist = new ArrayList<>();
        PlayerCreateActor playerCreateActor = new PlayerCreateActor();
        playerCreateActor.setPosition((float) GameEntry.GAMEWIDTH / 2 + playerCreateActor.getWidth() - 100, 100);
        playerCreateActor.setSex(true);
        actorlist.add(playerCreateActor);
        Label label_title = GameGUI.label("Create your character", 100, GameEntry.GAMEHEIGHT - 200, "cubic32", Color.WHITE);
        actorlist.add(label_title);
        Label label_sex = GameGUI.label("Sex:", 150, GameEntry.GAMEHEIGHT - 250, "cubic24", Color.WHITE);
        actorlist.add(label_sex);
        Label label_sex_male = GameGUI.label("Male", 150 + 50, GameEntry.GAMEHEIGHT - 280, "cubic24", Color.WHITE);
        label_sex_male.setColor(Color.CYAN);
        actorlist.add(label_sex_male);
        Label label_sex_female = GameGUI.label("Female", 150 + 110, GameEntry.GAMEHEIGHT - 280, "cubic24", Color.WHITE);
        actorlist.add(label_sex_female);
        label_sex_male.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ismale = true;
                playerCreateActor.setSex(true);
                label_sex_male.setColor(Color.CYAN);
                label_sex_female.setColor(Color.LIGHT_GRAY);
            }
        });
        label_sex_female.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ismale = false;
                playerCreateActor.setSex(false);
                label_sex_male.setColor(Color.LIGHT_GRAY);
                label_sex_female.setColor(Color.CYAN);
            }
        });
        Label label_birthplace = GameGUI.label("Born place:", 150, GameEntry.GAMEHEIGHT - 340, "cubic24", Color.WHITE);
        actorlist.add(label_birthplace);

        Label label_birthplace_display = GameGUI.label(bornplace, 150 + 120, GameEntry.GAMEHEIGHT - 340, "cubic24", Color.WHITE);
        actorlist.add(label_birthplace_display);

        Button btn_born_yangzhou = GameGUI.textbtn_default("扬州城", 150, GameEntry.GAMEHEIGHT - 420, "cubic24", Color.WHITE);
        btn_born_yangzhou.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bornplace = "yangzhou";
                label_birthplace_display.setText(bornplace);
            }
        });
        actorlist.add(btn_born_yangzhou);

        Button btn_born_suzhou = GameGUI.textbtn_default("苏州城", 150, GameEntry.GAMEHEIGHT - 480, "cubic24", Color.WHITE);
        btn_born_suzhou.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bornplace = "suzhou";
                label_birthplace_display.setText(bornplace);
            }
        });
        actorlist.add(btn_born_suzhou);

        Button btn_born_slot1 = GameGUI.textbtn_default("未添加", 150, GameEntry.GAMEHEIGHT - 540, "cubic24", Color.WHITE);
        actorlist.add(btn_born_slot1);
        Button btn_born_slot2 = GameGUI.textbtn_default("未添加", 150, GameEntry.GAMEHEIGHT - 600, "cubic24", Color.WHITE);
        actorlist.add(btn_born_slot2);
        Button btn_born_slot3 = GameGUI.textbtn_default("未添加", 150, GameEntry.GAMEHEIGHT - 660, "cubic24", Color.WHITE);
        actorlist.add(btn_born_slot3);

        Button btn_exit = GameGUI.textbtn_default("退出", 150, 100, "cubic24", Color.WHITE);
        btn_exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameEntry.addScreen(GameEntry.screenLogin);
                remove();
            }
        });
        actorlist.add(btn_exit);

        label_skillpoints = GameGUI.label("Points: 25", GameEntry.GAMEWIDTH - 450, GameEntry.GAMEHEIGHT - 260, "cubic24", Color.YELLOW);
        actorlist.add(label_skillpoints);

        Label label_title_hp = GameGUI.label("Health:", GameEntry.GAMEWIDTH - 400, GameEntry.GAMEHEIGHT - 300, "cubic24", Color.WHITE);
        actorlist.add(label_title_hp);
        label_point_hp = GameGUI.label("20", GameEntry.GAMEWIDTH - 400 + 140, GameEntry.GAMEHEIGHT - 300, "cubic24", Color.WHITE);
        actorlist.add(label_point_hp);
        Button btn_hp_reduce = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 100, GameEntry.GAMEHEIGHT - 300, "GUISkins/btn_reduce.png", "GUISkins/btn_reduce.png");
        actorlist.add(btn_hp_reduce);
        Button btn_hp_add = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 180, GameEntry.GAMEHEIGHT - 300, "GUISkins/btn_add.png", "GUISkins/btn_add.png");
        actorlist.add(btn_hp_add);
        btn_hp_add.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hppoints = cal_point(hppoints, true);
                label_point_hp.setText(hppoints);
            }
        });
        btn_hp_reduce.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hppoints = cal_point(hppoints, false);
                label_point_hp.setText(hppoints);
            }
        });


        Label label_title_power = GameGUI.label("Power:", GameEntry.GAMEWIDTH - 400, GameEntry.GAMEHEIGHT - 300 - 40, "cubic24", Color.WHITE);
        actorlist.add(label_title_power);
        label_point_power = GameGUI.label("20", GameEntry.GAMEWIDTH - 400 + 140, GameEntry.GAMEHEIGHT - 300 - 40, "cubic24", Color.WHITE);
        actorlist.add(label_point_power);
        Button btn_power_reduce = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 100, GameEntry.GAMEHEIGHT - 300 - 40, "GUISkins/btn_reduce.png", "GUISkins/btn_reduce.png");
        actorlist.add(btn_power_reduce);
        Button btn_power_add = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 180, GameEntry.GAMEHEIGHT - 300 - 40, "GUISkins/btn_add.png", "GUISkins/btn_add.png");
        actorlist.add(btn_power_add);
        btn_power_add.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                powerpoints = cal_point(powerpoints, true);
                label_point_power.setText(powerpoints);
            }
        });
        btn_power_reduce.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                powerpoints = cal_point(powerpoints, false);
                label_point_power.setText(powerpoints);
            }
        });

        Label label_title_magic = GameGUI.label("Magic:", GameEntry.GAMEWIDTH - 400, GameEntry.GAMEHEIGHT - 300 - 80, "cubic24", Color.WHITE);
        actorlist.add(label_title_magic);
        label_point_magic = GameGUI.label("20", GameEntry.GAMEWIDTH - 400 + 140, GameEntry.GAMEHEIGHT - 300 - 80, "cubic24", Color.WHITE);
        actorlist.add(label_point_magic);
        Button btn_magic_reduce = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 100, GameEntry.GAMEHEIGHT - 300 - 80, "GUISkins/btn_reduce.png", "GUISkins/btn_reduce.png");
        actorlist.add(btn_magic_reduce);
        Button btn_magic_add = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 180, GameEntry.GAMEHEIGHT - 300 - 80, "GUISkins/btn_add.png", "GUISkins/btn_add.png");
        actorlist.add(btn_magic_add);
        btn_magic_add.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                magicpoints = cal_point(magicpoints, true);
                label_point_magic.setText(magicpoints);
            }
        });
        btn_magic_reduce.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                magicpoints = cal_point(magicpoints, false);
                label_point_magic.setText(magicpoints);
            }
        });


        Label label_title_brain = GameGUI.label("Brain:", GameEntry.GAMEWIDTH - 400, GameEntry.GAMEHEIGHT - 300 - 120, "cubic24", Color.WHITE);
        actorlist.add(label_title_brain);
        label_point_brain = GameGUI.label("20", GameEntry.GAMEWIDTH - 400 + 140, GameEntry.GAMEHEIGHT - 300 - 120, "cubic24", Color.WHITE);
        actorlist.add(label_point_brain);
        Button btn_brain_reduce = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 100, GameEntry.GAMEHEIGHT - 300 - 120, "GUISkins/btn_reduce.png", "GUISkins/btn_reduce.png");
        actorlist.add(btn_brain_reduce);
        Button btn_brain_add = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 180, GameEntry.GAMEHEIGHT - 300 - 120, "GUISkins/btn_add.png", "GUISkins/btn_add.png");
        actorlist.add(btn_brain_add);
        btn_brain_add.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                brainpoints = cal_point(brainpoints, true);
                label_point_brain.setText(brainpoints);
            }
        });
        btn_brain_reduce.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                brainpoints = cal_point(brainpoints, false);
                label_point_brain.setText(brainpoints);
            }
        });
        Label label_title_speed = GameGUI.label("Speed:", GameEntry.GAMEWIDTH - 400, GameEntry.GAMEHEIGHT - 300 - 160, "cubic24", Color.WHITE);
        actorlist.add(label_title_speed);
        label_point_speed = GameGUI.label("20", GameEntry.GAMEWIDTH - 400 + 140, GameEntry.GAMEHEIGHT - 300 - 160, "cubic24", Color.WHITE);
        actorlist.add(label_point_speed);
        Button btn_speed_reduce = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 100, GameEntry.GAMEHEIGHT - 300 - 160, "GUISkins/btn_reduce.png", "GUISkins/btn_reduce.png");
        actorlist.add(btn_speed_reduce);
        Button btn_speed_add = GameGUI.btn_custom(GameEntry.GAMEWIDTH - 400 + 180, GameEntry.GAMEHEIGHT - 300 - 160, "GUISkins/btn_add.png", "GUISkins/btn_add.png");
        actorlist.add(btn_speed_add);
        btn_speed_add.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                speedpoints = cal_point(speedpoints, true);
                label_point_speed.setText(speedpoints);
            }
        });
        btn_speed_reduce.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                speedpoints = cal_point(speedpoints, false);
                label_point_speed.setText(speedpoints);
            }
        });

        Label label_name = GameGUI.label("Name:", GameEntry.GAMEWIDTH - 450, GameEntry.GAMEHEIGHT - 500, "cubic24", Color.WHITE);
        actorlist.add(label_name);

        TextField textField = GameGUI.tf_default("", 200, 50, GameEntry.GAMEWIDTH - 450, GameEntry.GAMEHEIGHT - 560, "cubic24", Color.WHITE);
        actorlist.add(textField);

        Button btn_enter = GameGUI.textbtn_default("Enter Game", GameEntry.GAMEWIDTH - 450, 100, "cubic24", Color.WHITE);
        btn_enter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (textField.getText().isEmpty()) {
                    msgwindow("name cannot be empty!");
                } else if (Pattern.matches("^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", textField.getText())) {
                    name = textField.getText();
                    createplayer();
                } else {
                    msgwindow("name cannot include symbol expect'_' !");
                }
            }
        });
        actorlist.add(btn_enter);

        for (Actor a : actorlist) root.addActor(a);
        root.setVisible(true);
        stage.addActor(root);
    }

    private void createplayer() {
        Player player = new Player();
        player.setName(name);
        player.setPurname(name);
        player.setPoint_hp(hppoints);
        player.setPoint_power(powerpoints);
        player.setPoint_magic(magicpoints);
        player.setPoint_brain(brainpoints);
        player.setPoint_speed(speedpoints);
        player.setMale(ismale);
        player.setBornplace(bornplace);
        mainGame.registerPlayer(player);
        GameEntry.addScreen(mainGame);
        remove();
    }

    public int cal_point(int p, boolean isadd) {
        if (isadd && p < 30) {
            if (points > 0) {
                points--;
                label_skillpoints.setText("Points:" + points);
                return p + 1;
            }
        } else if (isadd && p == 30) {
            msgwindow("points cannot greater than 30!");
        } else if (!isadd && p > 20) {
            points++;
            label_skillpoints.setText("Points:" + points);
            return p - 1;
        } else if (!isadd && p == 20) {
            msgwindow("points cannot lower than 20!");
        }
        return p;
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

    public void msgwindow(String msg_text) {
        msglabel.setText(msg_text);
        msg.setVisible(true);
    }

    public static class PlayerCreateActor extends Actor {
        TextureRegion IdleAnimation_boy, IdleAnimation_girl;
        private TextureRegion region;

        public PlayerCreateActor() {
            AnimationManager animationManager = new AnimationManager();
            ArrayList<Animation<TextureRegion>> animationPlayer = animationManager.createAnimation_player("Player/Boy/player1_te.png", 4, 4, 0.25f);
            IdleAnimation_boy = animationPlayer.get(0).getKeyFrame(0);
            ArrayList<Animation<TextureRegion>> animationPlayer2a = animationManager.createAnimation_player("Player/Girl/player2_te.png", 4, 4, 0.25f);
            IdleAnimation_girl = animationPlayer2a.get(0).getKeyFrame(0);
        }

        public void setRegion(TextureRegion region) {
            this.region = region;
            this.setScale(5);
            setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
        }

        public void setSex(boolean ismale) {
            if (ismale) {
                setRegion(IdleAnimation_boy);
            } else {
                setRegion(IdleAnimation_girl);
            }
        }

        public void act(float delta) {
            super.act(delta);
        }

        public void draw(Batch batch, float parentAlpha) {
            batch.draw(
                    region,
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(),
                    getRotation()
            );
        }
    }
}
