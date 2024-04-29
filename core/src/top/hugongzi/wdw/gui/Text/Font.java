package top.hugongzi.wdw.gui.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import top.hugongzi.wdw.fcore.Log;
import top.hugongzi.wdw.lazyfont.LazyBitmapFont;

import java.util.Hashtable;

/**
 * 字体加载
 *
 * @author Hubai
 */
public class Font {
    private final Hashtable<String, BitmapFont> map = new Hashtable<>();

    public Font() {
        String fontPath = "Fonts/Cubic.ttf";
        Log.i("FontInit << " + fontPath);
        //font = new BitmapFont(Gdx.files.internal(FontPath));
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        map.put("cubic16", new LazyBitmapFont(freeTypeFontGenerator, 16));
        map.put("cubic18", new LazyBitmapFont(freeTypeFontGenerator, 18));
        map.put("cubic20", new LazyBitmapFont(freeTypeFontGenerator, 20));
        map.put("cubic22", new LazyBitmapFont(freeTypeFontGenerator, 22));
        map.put("cubic32", new LazyBitmapFont(freeTypeFontGenerator, 32));
    }

    public Hashtable<String, BitmapFont> getFont() {
        return map;
    }

}
