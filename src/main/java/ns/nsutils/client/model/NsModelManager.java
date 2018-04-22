package ns.nsutils.client.model;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ns.nsutils.client.model.action.AbstractModelAction;
import ns.nsutils.common.core.NsUtils;

@SideOnly(Side.CLIENT)
public class NsModelManager
{
	
	private static Class<? extends ModelPlayer> playerModelClass;
	
	public static Map<String, RenderPlayer> skinMap;
	
	public static void initModel()
	{
		playerModelClass = NsModelPlayer.class;
		setupPlayerModel();
	}
	
	private static void setupPlayerModel()
	{
		if (NsUtils.allowPlayerModelChange)
		{
			if (skinMap == null) skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
			for (String key : skinMap.keySet())
			{
				ObfuscationReflectionHelper.setPrivateValue(RenderLivingBase.class, skinMap.get(key), newModelPlayer(playerModelClass, 0.0F, key.equals("slim")), "mainModel");
			}
		}
	}
	
	public static <T extends ModelPlayer> T newModelPlayer(Class<T> clazz, float modelSize, boolean slimArms)
	{
		try
		{
			return clazz.getConstructor(float.class, boolean.class).newInstance(modelSize, slimArms);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * <p>Set up the player's main model.</p>
	 * 
	 * This intentionally changes {@link net.minecraft.client.renderer.entity.RenderLivingBase#mainModel}
	 * which is recommended to be used by default.
	 * When using it, you need to be careful not to conflict with other mods.
	 * If player render classes are rewritten by {@link ns.client.renderer.NsRenderManager#setPlayerRender()},
	 * this operation may not work properly (when the model getter does not return {@link net.minecraft.client.renderer.entity.RenderLivingBase#mainModel})
	 */
	public static void setPlayerModel(Class<? extends ModelPlayer> model)
	{
		if (model == null) return;
		playerModelClass = model;
		setupPlayerModel();
	}
	
	/**
	 * <p>Associates the player action with the specified ModId.</p>
	 * 
	 * @param modid
	 * @param actionPartName
	 */
	public static void registerPlayerAction(AbstractModelAction action)
	{
		if (action == null || !Loader.isModLoaded(action.getModId())) return;
		
		for (String key : skinMap.keySet())
		{
			ModelPlayer model = skinMap.get(key).getMainModel();
			if (model instanceof NsModelPlayer)
			{
				((NsModelPlayer)model).registerAction(action);
			}
		}
	}
	
	public static void restartPlayerAction(String modid, String actionName)
	{
		for (String key : skinMap.keySet())
		{
			ModelPlayer model = skinMap.get(key).getMainModel();
			if (model instanceof NsModelPlayer)
			{
				((NsModelPlayer)model).restartAction(modid, actionName);
			}
		}
	}
	
	public static void stopPlayerAction(String modid, String actionName)
	{
		for (String key : skinMap.keySet())
		{
			ModelPlayer model = skinMap.get(key).getMainModel();
			if (model instanceof NsModelPlayer)
			{
				((NsModelPlayer)model).stopAction(modid, actionName);
			}
		}
	}
	
	public static void actionShiftFirst(String modid, String actionName)
	{
		for (String key : skinMap.keySet())
		{
			ModelPlayer model = skinMap.get(key).getMainModel();
			if (model instanceof NsModelPlayer)
			{
				((NsModelPlayer)model).shiftFirst(modid, actionName);
			}
		}
	}
	
	public static void actionShiftLast(String modid, String actionName)
	{
		for (String key : skinMap.keySet())
		{
			ModelPlayer model = skinMap.get(key).getMainModel();
			if (model instanceof NsModelPlayer)
			{
				((NsModelPlayer)model).shiftLast(modid, actionName);
			}
		}
	}
	
	public static void actionShiftUp(String modid, String actionName, int shiftSteps)
	{
		for (String key : skinMap.keySet())
		{
			ModelPlayer model = skinMap.get(key).getMainModel();
			if (model instanceof NsModelPlayer)
			{
				((NsModelPlayer)model).shiftUp(modid, actionName, shiftSteps);
			}
		}
	}
	
	public static void actionShiftDown(String modid, String actionName, int shiftSteps)
	{
		for (String key : skinMap.keySet())
		{
			ModelPlayer model = skinMap.get(key).getMainModel();
			if (model instanceof NsModelPlayer)
			{
				((NsModelPlayer)model).shiftDown(modid, actionName, shiftSteps);
			}
		}
	}
}
