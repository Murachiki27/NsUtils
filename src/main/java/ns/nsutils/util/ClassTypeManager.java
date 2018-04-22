package ns.nsutils.util;

public class ClassTypeManager
{
	public static enum Type
	{
		STRING(), CHARACTER(), BOOLEAN(),
		INTEGER(), LONG(), SHORT(),
		FLOAT(), DOUBLE(), OBJECT(),
		ARRAY();
	}
	
	public static Type getType(Class<?> type)
	{
		if (type == null) throw new NullPointerException("argument invalid null.");
		else if(type.isArray()) return Type.ARRAY;
		else if (type == String.class) return Type.STRING;
		else if (equalsOr(type, Integer.class, int.class)) return Type.INTEGER;
		else if (equalsOr(type, Double.class, double.class)) return Type.DOUBLE;
		else if (equalsOr(type, Float.class, float.class)) return Type.FLOAT;
		else if (equalsOr(type, Long.class, long.class)) return Type.LONG;
		else if (equalsOr(type, Short.class, short.class)) return Type.SHORT;
		else if (equalsOr(type, Boolean.class, boolean.class)) return Type.BOOLEAN;
		else if (equalsOr(type, Character.class, char.class)) return Type.CHARACTER;
		else return Type.OBJECT;
	}
	
	public static Type getTypeOutsideArray(Class<?> type)
	{
		if (type == null) throw new NullPointerException("argument invalid null.");
		else if (type == String.class) return Type.STRING;
		else if (equalsOr(type, Integer.class, int.class)) return Type.INTEGER;
		else if (equalsOr(type, Double.class, double.class)) return Type.DOUBLE;
		else if (equalsOr(type, Float.class, float.class)) return Type.FLOAT;
		else if (equalsOr(type, Long.class, long.class)) return Type.LONG;
		else if (equalsOr(type, Short.class, short.class)) return Type.SHORT;
		else if (equalsOr(type, Boolean.class, boolean.class)) return Type.BOOLEAN;
		else if (equalsOr(type, Character.class, char.class)) return Type.CHARACTER;
		else return Type.OBJECT;
	}
	
	
	static boolean equalsOr(Class<?> typeA, Class<?> typeB1, Class<?> typeB2)
	{
		return typeA == typeB1 || typeA == typeB2;
	}
}
