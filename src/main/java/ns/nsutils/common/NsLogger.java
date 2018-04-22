package ns.nsutils.common;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NsLogger
{
	private final Logger logger;
	private final String logName;
	
	private NsLogger(String logName)
	{
		this.logName = logName;
		this.logger = LogManager.getLogger(this.logName);
	}
	
	public void log(Level logLevel, String format, Object... msg)
	{
		this.logger.log(logLevel, String.format(format, msg));
	}
	
	public void log(Level logLevel, Object msg)
	{
		log(logLevel, "%s", msg);
	}
	
	public void warn(Object msg)
	{
		log(Level.WARN, msg);
	}
	
	public void info(Object msg)
	{
		log(Level.INFO, msg);
	}
	
	public void error(Object msg)
	{
		log(Level.ERROR, msg);
	}
	
	public static NsLogger create(String modid)
	{
		return new NsLogger(modid);
	}
	
}
