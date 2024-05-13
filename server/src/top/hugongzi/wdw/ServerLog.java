package top.hugongzi.wdw;

import com.badlogic.gdx.utils.Logger;

import java.util.Date;

/**
 *
 * @author Hubai
 */
public class ServerLog {
    private static final String NullMsg = "NULL LOG FOUND! PLEASE CHECK LOG DETAIL.";
    private static final Logger ilogger = new Logger(String.format("%tT",new Date()) + "][I", Logger.INFO);
    private static final Logger dlogger = new Logger(String.format("%tT",new Date()) + "][D", Logger.DEBUG);
    private static final Logger elogger = new Logger(String.format("%tT",new Date()) + "][E", Logger.ERROR);

    public static void i(Object obj) {
        ilogger.info(obj == null ? NullMsg : obj.toString());
    }
    public static void d(Object obj) {
        dlogger.debug(obj == null ? NullMsg : obj.toString());
    }
    public static void e(Object obj) {
        elogger.error(obj == null ? NullMsg : obj.toString());
    }
    public static void e(Object obj, Throwable error) {
        elogger.error(obj == null ? NullMsg : obj.toString(), error);
    }
}
