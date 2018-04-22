package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ZombieActions
{
	
	public static class ZombieNormalAction extends ActionNormal
	{
		
		public ZombieNormalAction()
		{
			super();
		}

		@Override
		public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
		{
			//super.updateAction(playerIn, modelIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			ItemStack stack = playerIn.getHeldItemMainhand();
			if (stack.isEmpty() || stack.getItem() != Items.ROTTEN_FLESH) return true;
			
			float f = MathHelper.sin(modelIn.swingProgress * (float)Math.PI);
			float f1 = MathHelper.sin((1.0F - (1.0F - modelIn.swingProgress) * (1.0F - modelIn.swingProgress)) * (float)Math.PI);
			modelIn.bipedRightArm.rotateAngleZ = 0.0F;
			modelIn.bipedLeftArm.rotateAngleZ = 0.0F;
			modelIn.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
			modelIn.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
			float f2 = -(float)Math.PI / 1.5F;//2.25F;
			modelIn.bipedRightArm.rotateAngleX = f2;
			modelIn.bipedLeftArm.rotateAngleX = f2;
			modelIn.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
			modelIn.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
			modelIn.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			modelIn.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			modelIn.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
			modelIn.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
			return false;
		}
		
		@Override
		public String getModId()
		{
			return "nsutils";
		}
	}
	
	public static class ZombieGunAction extends ActionNormal
	{
		public ZombieGunAction()
		{
			super();
		}
		
		@Override
		public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
		{
			//super.updateAction(playerIn, modelIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			ItemStack stack = playerIn.getHeldItemMainhand();
			if (stack.isEmpty() || stack.getItem() != Items.BANNER) return true;
			
			modelIn.bipedRightArm.rotateAngleY = -0.1F + modelIn.bipedHead.rotateAngleY;
			modelIn.bipedLeftArm.rotateAngleY = 0.1F + modelIn.bipedHead.rotateAngleY + 0.4F;
			modelIn.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + modelIn.bipedHead.rotateAngleX;
			modelIn.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + modelIn.bipedHead.rotateAngleX;
			return false;
		}
		
		@Override
		public String getModId()
		{
			return "nsutils";
		}
	}
}
