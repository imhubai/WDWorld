package top.hugongzi.wdw.gui.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import top.hugongzi.wdw.fcore.Log;
import top.hugongzi.wdw.lazyfont.LazyBitmapFont;

/**
 * 字体加载
 *
 * @author Hubai
 */
public class Font implements Disposable {
    private String FontPath = "CubicFont/Cubic.ttf";
    private String Symbol_cn = "CharTXT/CN_3500.txt";
    private BitmapFont font;

    public Font() {
        Log.i("FontInit - " + FontPath);
        //font = new BitmapFont(Gdx.files.internal(FontPath));
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FontPath));
        font = new LazyBitmapFont(freeTypeFontGenerator, 16);
    }

    public BitmapFont getFont() {
        return font;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
