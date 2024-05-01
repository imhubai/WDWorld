package top.hugongzi.wdw.gui.Screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import top.hugongzi.wdw.fcore.Log;

/**
 * 所有屏幕的继承类
 * 每个屏幕都包含一个未初始化的stage用于存放组件
 *
 * @author Hubai
 */
public abstract class AbstractScreen implements InputProcessor, Disposable {
    public static String CLASSNAME = AbstractScreen.class.getSimpleName();
    public Stage stage;
    /**
     * 标记当前屏幕是否即将移除
     * 除了类本身,不要在外部直接更改isMarkedRemove,请使用remove()方法
     */
    public boolean isMarkedRemove = false;
    //private List<InputProcessor> processors = new ArrayList<>();

    public abstract void create();

    public abstract void draw();

    public abstract void act();

    public boolean removeable() {
        return isMarkedRemove;
    }

    public void removeable(boolean flag) {
        isMarkedRemove = flag;
    }

    public void remove() {
        isMarkedRemove = true;
        //this.dispose();
        Log.i(CLASSNAME + " -> remove()");
    }

    @Override
    public boolean keyDown(int keycode) {
        return stage.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return stage.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return stage.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stage.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return stage.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return stage.touchCancelled(i, i1, i2, i3);
    }

    @Override
    public boolean scrolled(float i, float i1) {
        return stage.scrolled(i, i1);
    }
}
