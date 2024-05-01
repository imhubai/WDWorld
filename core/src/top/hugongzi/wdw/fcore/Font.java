package top.hugongzi.wdw.fcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ArrayMap;
import top.hugongzi.wdw.lazyfont.LazyBitmapFont;

/**
 * 字体加载
 *
 * @author Hubai
 */
public class Font {
    private final ArrayMap<String, BitmapFont> map = new ArrayMap<>();

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

    public ArrayMap<String, BitmapFont> getFont() {
        return map;
    }

}
