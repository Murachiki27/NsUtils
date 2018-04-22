package ns.nsutils.common.addon;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLModContainer;
import net.minecraftforge.fml.common.MetadataCollection;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ContainerType;
import net.minecraftforge.fml.common.discovery.ModCandidate;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ns.nsutils.common.core.NsUtils;
import ns.nsutils.util.io.NsPropertiesReader;

public class NsAddonManager
{
	private static Map<String, AddonData> addonList = Maps.newHashMap();
	protected static Pattern filePattern = Pattern.compile("(.+).(zip|jar)$");
	public static ClassLoader classloader;
	public static Method addURLMethod;
	
	public static List<File> putAddon(String parentModid)
	{
		List<File> ret = Lists.newArrayList();
		File[] dir = (new File(NsUtils.addonDir, "/" + parentModid + "/")).listFiles();
		for (File addonFile : dir)
		{
			if (addonFile.isDirectory() || filePattern.matcher(addonFile.getName()).matches())
			{
				ret.add(addonFile);
			}
		}
		NsUtils.LOGGER.info("Load complete addons in server side. AddonCount:" + ret.size());
		return ret;
	}
	
	
	@SideOnly(Side.CLIENT)
	public static List<File> putAddonAndURL(Class<?> parentModClass)
	{
		List<File> ret = Lists.newArrayList();
		if (parentModClass.isAnnotationPresent(Mod.class))
		{
			Mod modAnnotaion = parentModClass.getAnnotation(Mod.class);
			File dir = new File(NsUtils.addonDir, "/" + modAnnotaion.modid() + "/");
			if (!dir.exists()) dir.mkdirs();
			File[] addonFiles = dir.listFiles();
			for (File addonFile : addonFiles)
			{
				if (addonFile.isDirectory() || filePattern.matcher(addonFile.getName()).matches())
				{
					addonFile = putAddonAndURL(addonFile, modAnnotaion.modid(), modAnnotaion.name(), parentModClass.getName());
					if (addonFile != null)
					{
						ret.add(addonFile);
					}
				}
			}
			NsUtils.LOGGER.info("Load complete addons in client side. AddonCount:" + ret.size());
		}
		return ret;
	}
	
	private static File putAddonAndURL(File addonFile, String modid, String modName, String classPath)
	{
		boolean err = false;
		if (classloader == null)
		{
			classloader = (net.minecraft.server.MinecraftServer.class).getClassLoader();
		}
		
		if (addURLMethod == null)
		{
			try
			{
				addURLMethod = (java.net.URLClassLoader.class).getDeclaredMethod("addURL", java.net.URL.class);
				addURLMethod.setAccessible(true);
			}
			catch (Exception e)
			{
				NsUtils.LOGGER.error("Failed to get method. cause:" + e.getLocalizedMessage());
				err = true;
			}
		}
		NsPropertiesReader reader = new NsPropertiesReader("=", new File(addonFile, "/package-info.txt"), false);
		String packName = reader.get("packageName", addonFile.getName());
		String version = reader.get("version", "1");
		try
		{
			addURLMethod.invoke(classloader, addonFile.toURI().toURL());
			Map<String, Object> descriptor = Maps.newHashMap();
			descriptor.put("modid", modid);
			descriptor.put("name", modName + ":" + packName);
			descriptor.put("version", version);
			FMLModContainer container = new FMLModContainer(classPath, new ModCandidate(addonFile, addonFile, addonFile.isDirectory() ? ContainerType.DIR : ContainerType.JAR), descriptor);
			container.bindMetadata(MetadataCollection.from(null, ""));
			FMLClientHandler.instance().addModAsResource(container);
		}
		catch (Exception e)
		{
			NsUtils.LOGGER.error("Failed to load for pack : " + addonFile.getName());
			NsUtils.LOGGER.error(e.getLocalizedMessage());
			err = true;
		}
		if (!err)
		{
			addonList.put(modid + ":" + packName, new AddonData(packName, version, addonFile));
			NsUtils.LOGGER.info("Loaded addon. AddonName :" + packName);
		}
		
		return err ? null : addonFile;
	}
	
	@SideOnly(Side.CLIENT)
	public static AddonData getAddon(String modid, String addonName)
	{
		return addonList.get(modid + ":" + addonName);
	}
	
}
