package ns.nsutils.client.gui;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import ns.nsutils.common.core.NsUtils;

public class ModGuiConfig extends GuiConfig
{

	public ModGuiConfig(GuiScreen parentScreen)
	{
		super(parentScreen, makeConfigElements(), NsUtils.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(NsUtils.configData.toString()));
	}
	
	private static List<IConfigElement> makeConfigElements()
	{
		List<IConfigElement> elements = Lists.newArrayList();
		elements.addAll((new ConfigElement(NsUtils.getConfigCategoryClient())).getChildElements());
		//elements.addAll((new ConfigElement(NSUtils.getConfigCategoryServer())).getChildElements());
		//elements.addAll((new ConfigElement(NSUtils.getConfigCategoryCompability())).getChildElements());
		return elements;
	}
	
}
