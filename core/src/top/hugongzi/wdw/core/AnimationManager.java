package top.hugongzi.wdw.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * 管理动画的类，提供创建和获取动画功能。
 */
public class AnimationManager {
    Texture Sheet; // 动画帧所在的纹理
    TextureRegion[][] Frames; // 分割后的动画帧

    /**
     * 创建玩家动画。
     *
     * @param loc  图片文件的相对路径
     * @param cols 图片分割的列数
     * @param rows 图片分割的行数
     * @param dur  每帧持续的时间
     * @return 包含玩家动画帧的动画列表
     */
    public ArrayList<Animation<TextureRegion>> createAnimation_player(String loc, int cols, int rows, float dur) {
        Sheet = new Texture(Gdx.files.internal(loc)); // 加载纹理
        Frames = TextureRegion.split(Sheet, Sheet.getWidth() / cols, Sheet.getHeight() / rows); // 分割纹理成动画帧
        ArrayList<Animation<TextureRegion>> list = new ArrayList<>();
        for (TextureRegion[] frame : Frames) {
            list.add(new Animation<>(dur, frame)); // 创建动画并添加到列表
        }
        // 为每个动画帧序列添加一个单帧动画，用于播放动画的每一帧
        for (TextureRegion[] frame : Frames) {
            list.add(new Animation<>(dur, frame[0]));
        }
        return list;
    }

    /**
     * 获取动画帧的宽度。
     * @return 动画帧的宽度
     */
    public float getRegionWidth(){
        return Frames[0][0].getRegionWidth();
    }

    /**
     * 获取动画帧的高度。
     * @return 动画帧的高度
     */
    public float getRegionHeight(){
        return Frames[0][0].getRegionHeight();
    }
}
