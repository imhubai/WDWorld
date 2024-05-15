package top.hugongzi.wdw.core;

import com.badlogic.gdx.utils.Logger;

import java.util.Date;

/**
 * 提供日志记录功能的类，用于输出不同等级的日志信息。
 *
 * @author Hubai
 */
public class Log {
    // 默认日志消息，用于当输入参数为null时
    private static final String NullMsg = "NULL LOG FOUND! PLEASE CHECK LOG DETAIL.";
    // 信息等级日志记录器，以当前时间初始化
    private static final Logger ilogger = new Logger(String.format("%tT", new Date()) + "][I", Logger.INFO);
    // 调试等级日志记录器，以当前时间初始化
    private static final Logger dlogger = new Logger(String.format("%tT", new Date()) + "][D", Logger.DEBUG);
    // 错误等级日志记录器，以当前时间初始化
    private static final Logger elogger = new Logger(String.format("%tT", new Date()) + "][E", Logger.ERROR);

    /**
     * 记录一条信息等级的日志。
     *
     * @param obj 要记录的信息对象，可以为null，为null时将记录默认信息。
     */
    public static void i(Object obj) {
        ilogger.info(obj == null ? NullMsg : obj.toString());
    }

    /**
     * 记录一条调试等级的日志。
     *
     * @param obj 要记录的调试信息对象，可以为null，为null时将记录默认信息。
     */
    public static void d(Object obj) {
        dlogger.debug(obj == null ? NullMsg : obj.toString());
    }

    /**
     * 记录一条错误等级的日志。
     *
     * @param obj 要记录的错误信息对象，可以为null，为null时将记录默认信息。
     */
    public static void e(Object obj) {
        elogger.error(obj == null ? NullMsg : obj.toString());
    }

    /**
     * 记录一条错误等级的日志，并附带异常信息。
     *
     * @param obj   要记录的错误信息对象，可以为null，为null时将记录默认信息。
     * @param error 与日志相关联的异常信息。
     */
    public static void e(Object obj, Throwable error) {
        elogger.error(obj == null ? NullMsg : obj.toString(), error);
    }
}
