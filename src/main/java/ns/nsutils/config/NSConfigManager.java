package ns.nsutils.config;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import ns.nsutils.util.ClassTypeManager;
import ns.nsutils.util.StringManager;

public class NSConfigManager
{
	
	public static Configuration loadConfig(File configDir, Object target)
	{
		if (target == null)
			throw new IllegalArgumentException("argument is null.");
		if (target == Class.class)
			throw new IllegalArgumentException("argument has not supported \" Class \" type.");
		
		Configuration config = new Configuration(configDir);
		config.load();
		for (Field field : target.getClass().getFields())
		{
			if (field.isAnnotationPresent(ConfigInfo.class))
			{
				ConfigInfo ano = field.getAnnotation(ConfigInfo.class);
				if (ano.category() == null || ano.key() == null)
					continue;
				Side side = FMLCommonHandler.instance().getEffectiveSide();
				if ((ano.clientOnly() && side == Side.CLIENT) || (ano.serverOnly() && side == Side.SERVER) || (!ano.clientOnly() && !ano.serverOnly()))
				{
					try
					{
						field.setAccessible(true);
						Object defObj = field.get(target);
						Class<?> fieldType = field.getType();
						Property prop = getProperty(config, ano.category(), ano.key(), defObj, fieldType);
						Method real;
						if (fieldType == String.class)
						{
							real = fieldType.getMethod("valueOf", Object.class);
						}
						else if (ClassUtils.isPrimitiveOrWrapper(fieldType))
						{
							fieldType = ClassUtils.primitiveToWrapper(fieldType);
							real = fieldType.getMethod("valueOf", String.class);
						}
						else
							throw new RuntimeException(fieldType + ":" + field.getName() + " is unsupported class type.");
						field.set(target, real.invoke(defObj, prop.getString()));
						if (!StringUtils.isEmpty(ano.info()))
						{
							prop.setComment(ano.info());
						}
					}
					catch (Exception e)
					{
						FMLLog.info("error in config loading.");
						FMLLog.warning(e.getLocalizedMessage());
					}
				}
			}
		}
		if (config.hasChanged())
		{
			config.save();
		}
		return config;
	}
	
	private static Property getProperty(Configuration config, String category, String key, Object value, Class<?> type)
	{
		if (value == null) return config.get(category, key, "null");
		ClassTypeManager.Type classType = ClassTypeManager.getType(type);
		switch (classType)
		{
		case ARRAY:
			ClassTypeManager.Type arrayType = ClassTypeManager.getTypeOutsideArray(type);
			switch (arrayType)
			{
			case BOOLEAN: return config.get(category, key, (boolean[])value);
			case DOUBLE:
			case FLOAT: return config.get(category, key, (double[])value);
			case SHORT:
			case LONG:
			case INTEGER: return config.get(category, key, (int[])value);
			case CHARACTER: return config.get(category, key, String.valueOf((char[])value));
			case STRING: return config.get(category, key, (String[])value);
			case OBJECT:
			default:
				return config.get(category, key, StringManager.toStringArray((Object[])value));
			}
		case BOOLEAN: return config.get(category, key, (boolean)value);
		case DOUBLE:
		case FLOAT: return config.get(category, key, (double)value);
		case SHORT:
		case LONG:
		case INTEGER: return config.get(category, key, (int)value);
		case CHARACTER: return config.get(category, key, String.valueOf((char)value));
		case STRING: return config.get(category, key, (String)value);
		case OBJECT:
		default:
			return config.get(category, key, String.valueOf(value));
		}
	}
}
