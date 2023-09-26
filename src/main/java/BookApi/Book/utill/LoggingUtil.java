package BookApi.Book.utill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;

public class LoggingUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggingUtil.class);

    public static void DebugInfo(String msg) {
        System.out.println(new Date() + " " + msg);
        logger.info(new Date() + " " + msg);

    }

    public static void WarningInfo(String msg) {
        System.out.println(new Date() + " " + msg);
        logger.warn(new Date() + " " + msg);

    }

    public static void ExceptionInfo(Exception ex) {
        System.out.println(ex.getMessage());
        ex.printStackTrace();
        logger.info(Arrays.toString(ex.getStackTrace()).replaceAll(",","\n"));

    }

}
