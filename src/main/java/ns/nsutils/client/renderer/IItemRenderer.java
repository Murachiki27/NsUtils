package ns.nsutils.client.renderer;

import java.util.List;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(value = Side.CLIENT)
public interface IItemRenderer
{
	
	void renderItem(TransformType cameraType, EntityLivingBase player, ItemStack stack, TextureManager manager);
	
	boolean isGui3d();
	
	boolean isAmbientOcclusion();
	
	List<ItemOverride> getOverride();
	
	ItemCameraTransforms getCameraTransforms();
	
}
