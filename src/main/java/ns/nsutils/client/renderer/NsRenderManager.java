package ns.nsutils.client.renderer;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ns.nsutils.client.model.NsModelItemBase;
import ns.nsutils.client.renderer.tileentity.NsSpecialRendererItem;
import ns.nsutils.client.tileentity.NsTileEntityItem;
import ns.nsutils.common.core.NsUtils;

@SideOnly(Side.CLIENT)
public class NsRenderManager
{
	//static final ModelResourceLocation MODEL_INTERNAL_LOCATION = new ModelResourceLocation(NsUtils.MOD_ID + ":models/ns/");
	private static Map<Pair<Item, Integer>, IItemRenderer> itemRendererMap = Maps.newHashMap();
	private static TextureManager textureManager;
	
	private NsRenderManager()
	{
	}
	
	public static ModelResourceLocation getLocation(Item item, int meta)
	{
		return new ModelResourceLocation(NsUtils.MOD_ID, "models/ns/" + item.getRegistryName().getResourcePath() + meta);
	}
	
	public static void init()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(NsTileEntityItem.class, new NsSpecialRendererItem());
		MinecraftForge.EVENT_BUS.register(NsRenderManager.class);
	}
	
	public static void setPlayerRender(RenderPlayer typeNormal, RenderPlayer typeSlim)
	{
		
	}
	
	public static void registerItemRenderer(Item targetItem, int metadata, IItemRenderer targetHandler)
	{
		if (NsUtils.allowItemRendererChange && itemRendererMap.containsKey(Pair.of(targetItem, metadata))) return;
		ModelLoader.setCustomModelResourceLocation(targetItem, metadata, getLocation(targetItem, metadata));
		ForgeHooksClient.registerTESRItemStack(targetItem, metadata, NsTileEntityItem.class);
		itemRendererMap.put(Pair.of(targetItem, metadata), targetHandler);
	}
	
	public static void registerItemModelCustom(Item item, int metadata, ResourceLocation location, String variant)
	{
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(location, variant));
	}
	
	public static void registerItemModelCustom(Item item, int metadata, String modid, String path, String variant)
	{
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(new ResourceLocation(modid, path), variant));
	}
	
	public static void registerItemCustomModelAndColor(Item item, int metadata, int[] colorLayers, ResourceLocation location, String variant)
	{
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new NsItemColorImpl(colorLayers), item);
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(location, variant));
	}
	
	public static void renderItem(ItemStack stack, EntityLivingBase living, TransformType transType)
	{
		IItemRenderer ir = itemRendererMap.get(Pair.of(stack.getItem(), stack.getMetadata()));
		if (ir != null)
		{
			if (textureManager == null)
				textureManager = Minecraft.getMinecraft().renderEngine;
			ir.renderItem(transType, living, stack, textureManager);
		}
	}
	
	@SubscribeEvent
	public static void modelBakeEvent(ModelBakeEvent event)
	{
		for (Map.Entry<Pair<Item, Integer>, IItemRenderer> entry : itemRendererMap.entrySet())
		{
			event.getModelRegistry().putObject(getLocation(entry.getKey().getLeft(), entry.getKey().getRight()), new NsModelItemBase(entry.getValue()));
		}
	}
	
}
