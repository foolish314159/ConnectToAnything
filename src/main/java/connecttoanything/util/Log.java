package connecttoanything.util;

import java.util.logging.Logger;

public class Log {

	private static Logger logger = Logger.getLogger(Log.class.getSimpleName());

	public static void info(String msg) {
		logger.info(msg);
	}

	public static void severe(String msg) {
		logger.severe(msg);
	}

}
