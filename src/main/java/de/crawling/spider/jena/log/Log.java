package de.crawling.spider.jena.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Log {
	private static Logger logger = Logger.getRootLogger();
	static {
			SimpleLayout layout = new SimpleLayout();
			ConsoleAppender consoleAppender = new ConsoleAppender( layout );
			logger.addAppender( consoleAppender );
			setLevel(Level.DEBUG);
	}
	
	public static void setLevel(Level level){
		logger.setLevel(level);
	}
	
	public static void debug(Object msg){
		logger.debug(msg);
	}
	
	public static void info(Object msg){
		logger.info(msg);
	}
	
	public static void warn(Object msg){
		logger.warn(msg);
	}
	
	public static void error(Object msg){
		logger.error(msg);
	}
	
	public static void fatal(Object msg){
		logger.fatal(msg);
	}
}
