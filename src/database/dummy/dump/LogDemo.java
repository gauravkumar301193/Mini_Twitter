package database.dummy.dump;

import org.apache.log4j.Logger;

public class LogDemo {
	static Logger log = Logger.getLogger(LogDemo.class);
	//BasicConfigurator.configure();
	
	public static void main(String[] ar) {
		if(log.isDebugEnabled()) {
			log.debug("Running Log");
		}
	}
}
