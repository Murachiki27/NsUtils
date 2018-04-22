package ns.nsutils.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.ImmutableMap;

import ns.nsutils.common.core.NsUtils;


public class NsPropertiesReader
{
	
	private final String regex;
	private File targetFile;
	private boolean printLog;
	
	private ImmutableMap<String, String> propertyTable;
	
	public NsPropertiesReader(String regex, String path)
	{
		this(regex, new File(path), true);
	}
	
	public NsPropertiesReader(String regex, File file)
	{
		this(regex, file, true);
	}
	
	public NsPropertiesReader(String regex, String path, boolean printLog)
	{
		this(regex, new File(path), printLog);
	}
	
	public NsPropertiesReader(String regex, File file, boolean printLog)
	{
		this.regex = regex;
		this.targetFile = file;
		this.printLog = printLog;
		this.read();
	}
	
	private void read()
	{
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		BufferedReader reader = null;
		int lineNo = 1;
		try
		{
			reader = new BufferedReader(new FileReader(this.targetFile));
			String line;
			while ((line = reader.readLine()) != null)
			{
				if (line.equals("") || line.startsWith("//")) continue;
				String[] split = line.split("//", 2);
				line = split[0].trim();
				split = line.split(this.regex);
				if (split.length != 2) continue;
				builder.put(split[0].trim(), split[1].trim());
				++lineNo;
			}
		}
		catch (Exception e)
		{
			if (this.printLog)
			{
				NsUtils.LOGGER.error("Reading Error, Cause :" + e.getLocalizedMessage() + " #line:" + lineNo);
			}
		}
		finally
		{
			IOUtils.closeQuietly(reader);
		}
		this.propertyTable = builder.build();
	}
	
	public String get(String key)
	{
		return this.propertyTable.get(key);
	}
	
	public String get(String key, String defaultValue)
	{
		String value = this.propertyTable.get(key);
		return value == null ? defaultValue : value;
	}
	
	public int getInt(String key)
	{
		return this.getInt(key, 0);
	}
	
	public int getInt(String key, int defaultValue)
	{
		String value = this.propertyTable.get(key);
		return value == null ? defaultValue : Integer.parseInt(value);
	}
	
	public float getFloat(String key)
	{
		return this.getFloat(key, 0F);
	}
	
	public float getFloat(String key, float defaultValue)
	{
		String value = this.propertyTable.get(key);
		return value == null ? defaultValue : Float.parseFloat(value);
	}
	
	public boolean getBoolean(String key)
	{
		return this.getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defaultValue)
	{
		String value = this.propertyTable.get(key);
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}
	
}
