package ns.nsutils.util;

public class StringManager
{
	
	public static String[] toStringArray(Object[] o)
	{
		return toStringArray(o, true);
	}
	
	public static String[] toStringArray(Object[] o, boolean nullToString)
	{
		if (o == null) return null;
		String[] ret = new String[o.length];
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = o == null ? (nullToString ? "null" : null) : o.toString();
		}
		return ret;
	}
}
