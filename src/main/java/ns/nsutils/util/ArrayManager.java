package ns.nsutils.util;

public class ArrayManager
{
	
	public static <T> int indexOf(T o, T[] from, boolean allCheck, boolean reverse)
	{
		if (from == null) return -1;
		int ret = -1;
		boolean flag;
		int i = reverse ? from.length - 1 : 0;
		int j = reverse ? -1 : 1;
		for (int k = i; k >= 0 && k < from.length ; k += j)
		{
			flag = o == null ? from[k] == null : o.equals(from[k]);
			if (flag)
			{
				ret = k;
				if (!allCheck) break;
			}
		}
		return ret;
	}
	
	public static <T> int indexOf(T o, T[] from)
	{
		return indexOf(o, from, false, false);
	}
	
	public static <T> int indexOfAll(T o, T[] from)
	{
		return indexOf(o, from, true, false);
	}
}
