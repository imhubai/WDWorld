package top.hugongzi.wdw.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class AnimationManager {
    Texture Sheet;
    TextureRegion[][] Frames;

    public ArrayList<Animation<TextureRegion>> createAnimation_player(String loc, int cols, int rows, float dur) {
        Sheet = new Texture(Gdx.files.internal("Player/Boy/player1_te.png"));
        Frames = TextureRegion.split(Sheet, Sheet.getWidth() / cols, Sheet.getHeight() / rows);
        ArrayList<Animation<TextureRegion>> list = new ArrayList<>();
        for (int i = 0; i < Frames.length; i++) {
            list.add(new Animation<>(dur, Frames[i]));
        }
        for (int i = 0; i < Frames.length; i++) {
            list.add(new Animation<>(dur, Frames[i][0]));
        }
        return list;
    }
    public float getRegionWidth(){
        return Frames[0][0].getRegionWidth();
    }
    public float getRegionHeight(){
        return Frames[0][0].getRegionHeight();
    }
}
