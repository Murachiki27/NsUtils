package ns.nsutils.common.core;

import java.io.File;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import ns.nsutils.common.CommonProxy;
import ns.nsutils.common.NsLogger;
import ns.nsutils.config.ConfigInfo;
import ns.nsutils.config.NSConfigManager;

@Mod(
		modid = NsUtils.MOD_ID,
		name = NsUtils.MOD_NAME,
		version = NsUtils.MOD_VERSION,
		dependencies = "required-after:forge@[" + NsUtils.REQ_FOREGE_VERSION + ")",
		acceptableRemoteVersions = NsUtils.MOD_VERSION_RANGE,
		acceptedMinecraftVersions = NsUtils.MC_VERSION_RANGE,
		guiFactory = "ns.nsutils.client.gui.ModGuiFactory"
	)
public class NsUtils
{
	public static final String MOD_ID = "nsutils";
	public static final String MOD_NAME = "Ns Utils";
	
	public static final int MAJOR_VERSION = 1;
	public static final int MINOR_VERSION = 2;
	public static final int REVISION_VERSION = 9;
	public static final String MOD_VERSION =
			MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION;
	
	public static final int FORGE_MAJOR = 13;
	public static final int FORGE_MINOR = ForgeVersion.minorVersion;
	public static final int FORGE_REVISION = 0;
	public static final int FORGE_BUILD = 2255;
	public static final String REQ_FOREGE_VERSION =
			FORGE_MAJOR + "." + FORGE_MINOR + "." + FORGE_REVISION + "." + FORGE_BUILD +
			",99999" + "." + (FORGE_MINOR + 1) + ".0.0";
	public static final String MOD_VERSION_RANGE = "[" + MAJOR_VERSION + "." + MINOR_VERSION + ".0," + MAJOR_VERSION + "." + (MINOR_VERSION + 1) + ".0)";
	
	public static final String MC_VERSION_RANGE = "[1.11,1.12)";
	
	public static final String GUI_FACTORY_CLASS = "";
	public static final String CHANNEL_ID = "ch.NsUtils";
	
	
	@Mod.Instance(MOD_ID)
	public static NsUtils instance;
	
	@SidedProxy(clientSide = "ns.nsutils.client.ClientProxy", serverSide = "ns.nsutils.common.CommonProxy")
	public static CommonProxy proxyData;
	
	public static File addonDir;
	
	public static MinecraftServer mcServer;
	
	public static final NsLogger LOGGER = NsLogger.create(MOD_NAME);
	
	
	// Config Data
	public static final String CATEGORY_CLIENT = "client";
	public static final String CATEGORY_SERVER = "server";
	public static final String CATEGORY_COMPABILITY = "compatibility";
	
	public static Configuration configData;
	
	@ConfigInfo(category = CATEGORY_CLIENT, key = "allowPlayerModelChange", info = "", clientOnly = true)
	public static boolean allowPlayerModelChange = true;
	@ConfigInfo(category = CATEGORY_CLIENT, key = "allowItemRendererChange", info = "", clientOnly = true)
	public static boolean allowItemRendererChange = true;
	
	public static ConfigCategory getConfigCategoryClient()
	{
		return configData.getCategory(CATEGORY_CLIENT);
	}
	
	public static ConfigCategory getConfigCategoryServer()
	{
		return configData.getCategory(CATEGORY_SERVER);
	}
	
	public static ConfigCategory getConfigCategoryCompability()
	{
		return configData.getCategory(CATEGORY_COMPABILITY);
	}
	
	public static ConfigCategory getConfigCategory(String categoryName)
	{
		return configData.getCategory(categoryName);
	}
	
	public NsUtils()
	{
		;
	}
	
	
	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		configData = NSConfigManager.loadConfig(event.getSuggestedConfigurationFile(), this);
		addonDir = new File(new File(event.getModConfigurationDirectory(), ".."), "nsaddons");
		if (!addonDir.exists())
		{
			addonDir.mkdirs();
		}
		proxyData.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxyData.init();
	}
	
	@Mod.EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		proxyData.postInit();
	}
	
	@Mod.EventHandler
	public void serverStartup(FMLServerAboutToStartEvent event)
	{
		mcServer = event.getServer();
	}
	
	public void serverStart(FMLServerStartingEvent event)
	{
		
	}
	
	public void serverStopping(FMLServerStoppingEvent event)
	{
		
	}
	
	
	public static File makeAddonDir(String modid)
	{
		File dir = new File(addonDir, modid);
		if (!dir.exists()) dir.mkdirs();
		return dir;
	}
	
}
