package top.hugongzi.wdw.fcore;

import com.badlogic.gdx.utils.Logger;
import top.hugongzi.wdw.GameEntry;

/**
 * Log信息输出
 *
 * @author Hubai
 */
public class Log {
    private static String NullMsg = "NULL LOG FOUND! PLEASE CHECK LOG DETAIL.";
    private static Logger ilogger = new Logger(GameEntry.GAMENAME + "][I", Logger.INFO);
    private static Logger dlogger = new Logger(GameEntry.GAMENAME + "][D", Logger.DEBUG);
    private static Logger elogger = new Logger(GameEntry.GAMENAME + "][E", Logger.ERROR);

    /**
     * 添加一条 <b>消息</b> 等级的日志
     */
    public static void i(Object obj) {
        ilogger.info(obj == null ? NullMsg : obj.toString());
    }

    /**
     * 添加一条 <b>调试</b> 等级的日志
     */
    public static void d(Object obj) {
        dlogger.debug(obj == null ? NullMsg : obj.toString());
    }

    /**
     * 添加一条 <b>错误</b> 等级的日志
     */
    public static void e(Object obj) {
        elogger.error(obj == null ? NullMsg : obj.toString());
    }

    /**
     * 添加一条 <b>错误</b> 等级的日志
     */
    public static void e(Object obj, Throwable error) {
        elogger.error(obj == null ? NullMsg : obj.toString(), error);
        StringBuilder text = new StringBuilder(obj == null ? NullMsg : obj.toString());
        text.append("\n");
        text.append("[#ff00ff]").append(error.getLocalizedMessage()).append("[]");
        for (StackTraceElement ele : error.getStackTrace())
            text.append("\n\t[#fc2929]at ").append(ele.getClassName()).append(".").append(ele.getMethodName()).append(":[][#8493ff]").append(ele.getLineNumber()).append("[]");
    }
}
