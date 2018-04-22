package ns.nsutils.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class AbstractItemRenderer implements IItemRenderer
{
	@Override
	public void renderItem(TransformType cameraType, EntityLivingBase livingBase, ItemStack stack, TextureManager manager)
	{
		GlStateManager.pushMatrix();
		this.renderPre(cameraType, livingBase, stack);
		if (!this.bindTexture(livingBase, stack, manager))
		{
			return;
		}
		this.renderItem(cameraType, livingBase, stack);
		
		this.renderPost(cameraType, livingBase, stack);
		GlStateManager.popMatrix();
	}
	
	protected abstract void renderItem(TransformType cameraType, EntityLivingBase livingBase, ItemStack stack);
	
	protected void renderPre(TransformType cameraType, EntityLivingBase livingBase, ItemStack stack)
	{
		;
	}
	
	protected void renderPost(TransformType cameraType, EntityLivingBase livingBase, ItemStack stack)
	{
		;
	}
	
	protected boolean bindTexture(EntityLivingBase livingBaseIn, ItemStack stackIn, TextureManager manager)
	{
		ResourceLocation location = this.getTextureLocation(livingBaseIn, stackIn);
		if (location == null)
		{
			return false;
		}
		else
		{
			this.bindTexture(location, manager);
			return true;
		}
	}
	
	public void bindTexture(ResourceLocation location, TextureManager manager)
	{
		manager.bindTexture(location);
	}
	
	protected abstract ResourceLocation getTextureLocation(EntityLivingBase livingBaseIn, ItemStack stackIn);
	
	@Override
	public ItemCameraTransforms getCameraTransforms()
	{
		return ItemCameraTransforms.DEFAULT;
	}
	
	public static boolean isFirstPersonSide(TransformType type)
	{
		return type == TransformType.FIRST_PERSON_LEFT_HAND || type == TransformType.FIRST_PERSON_RIGHT_HAND;
	}
	
	public static boolean isThirdPersonSide(TransformType type)
	{
		return type == TransformType.THIRD_PERSON_LEFT_HAND || type == TransformType.THIRD_PERSON_RIGHT_HAND;
	}
}
