package ns.nsutils.util;

public class NumberFormatter
{
	public static Integer phaseInt(String s, int err)
	{
		try {return Integer.valueOf(s);}
		catch (NumberFormatException e) {return new Integer(err);}
	}
	
	public static Integer phaseInt(String s)
	{
		return phaseInt(s, 0);
	}
	
	public static Float phaseFloat(String s, float err)
	{
		try {return Float.valueOf(s);}
		catch (NumberFormatException e) {return new Float(err);}
	}
	
	public static Float phaseFloat(String s)
	{
		return phaseFloat(s, 0F);
	}
	
	public static Double phaseDouble(String s, double err)
	{
		try {return Double.valueOf(s);}
		catch (NumberFormatException e) {return new Double(err);}
	}
	
	public static Double phaseDouble(String s)
	{
		return phaseDouble(s, 0D);
	}
	
	public static Short phaseShort(String s, short err)
	{
		try {return Short.valueOf(s);}
		catch (NumberFormatException e) {return new Short(err);}
	}
	
	public static Short phaseShort(String s)
	{
		return phaseShort(s, (short) 0);
	}
	
	public static Long phaseLong(String s, long err)
	{
		try {return Long.valueOf(s);}
		catch (NumberFormatException e) {return new Long(err);}
	}
	
	public static Long phaseLong(String s)
	{
		return phaseLong(s, 0L);
	}
}
