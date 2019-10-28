package mx.tesseract.util;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author Luis Gerardo Jimenez
 */
public class TESSERACTLogger {

    public static final boolean debug = true;
    private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

    public static void error(String source, String message, Throwable t) {
    	TESSERACT_LOGGER.error(source + ":" + message, t);
        if (debug) {
        	TESSERACT_LOGGER.error(TESSERACTLogger.class.getName() + ": " + "index", t);
        }
    }

    public static void info(String source, String message) {
    	TESSERACT_LOGGER.info(source + ":" + message);
    }

    public static void debug(String source, String message) {
    	TESSERACT_LOGGER.debug(source + ":" + message);
    }

    public static void main(String args[]) {
    	TESSERACT_LOGGER.info("Prueba INFO");
    	TESSERACT_LOGGER.debug("Prueba DEBUG");
    	TESSERACT_LOGGER.warn("Prueba WARN");
    	TESSERACT_LOGGER.error("Prueba ERROR", new RuntimeException("Prueba"));
    }
}
