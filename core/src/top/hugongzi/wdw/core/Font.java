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
    String fontPath = "Fonts/Cubic.ttf";

    /**
     * 构造函数，初始化字体资源。
     * 此处加载了基于Cubic.ttf字体文件的多种大小的字体，供后续使用。
     */
    public Font() {
        Log.i("FontInit << " + fontPath);
        int[] sizes = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 48, 64, 128};
        for (int s : sizes) putFont(s);
    }

    public void putFont(int size) {
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        map.put("cubic" + size, new LazyBitmapFont(freeTypeFontGenerator, size));
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
