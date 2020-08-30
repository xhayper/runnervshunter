package io.github.hayper.rvsh.Utility;

import io.github.hayper.rvsh.Main;
import org.slf4j.LoggerFactory;

public class Logger {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.modName);

    public static void stackTrace(Exception exception) {
        logger.info("Oops! Something went wrong!");
        exception.printStackTrace();
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void debug(String msg) {
        logger.debug(msg);
    }

}