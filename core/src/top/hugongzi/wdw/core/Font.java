package top.hugongzi.wdw.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ArrayMap;
import top.hugongzi.wdw.lazyfont.LazyBitmapFont;

/**
 * 字体加载类，用于初始化和管理游戏中的字体资源。
 *
 * @author Hubai
 */
public class Font {
    // 字体资源映射，通过字符串标识访问不同大小的字体
    private final ArrayMap<String, BitmapFont> map = new ArrayMap<>();

    /**
     * 构造函数，初始化字体资源。
     * 此处加载了基于Cubic.ttf字体文件的多种大小的字体，供后续使用。
     */
    public Font() {
        String fontPath = "Fonts/Cubic.ttf";
        Log.i("FontInit << " + fontPath);
        // 使用FreeTypeFontGenerator从字体文件生成字体
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        // 添加不同大小的字体到映射中
        map.put("cubic16", new LazyBitmapFont(freeTypeFontGenerator, 16));
        map.put("cubic18", new LazyBitmapFont(freeTypeFontGenerator, 18));
        map.put("cubic20", new LazyBitmapFont(freeTypeFontGenerator, 20));
        map.put("cubic22", new LazyBitmapFont(freeTypeFontGenerator, 22));
        map.put("cubic32", new LazyBitmapFont(freeTypeFontGenerator, 32));
    }

    /**
     * 获取字体资源映射。
     *
     * @return 包含已初始化字体的映射。
     */
    public ArrayMap<String, BitmapFont> getFont() {
        return map;
    }

}
