package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class ActionElytraFlying extends AbstractDefaultAction
{
	
	@Override
	public String getActionName()
	{
		return "elytra_flying";
	}
	
	@Override
	public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		modelIn.bipedHead.rotateAngleX = -((float)Math.PI / 4F);
		float f = (float)(playerIn.motionX * playerIn.motionX + playerIn.motionY * playerIn.motionY + playerIn.motionZ * playerIn.motionZ);
		f = f / 0.2F;
		f = f * f * f;
		if (f < 1.0F)
		{
			f = 1.0F;
		}
		modelIn.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		modelIn.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		modelIn.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		modelIn.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
		return true;
	}
	
	@Override
	public boolean allowAction(EntityPlayer playerIn, ModelPlayer modelIn)
	{
		return playerIn.getTicksElytraFlying() > 4;
	}
	
	@Override
	public boolean notAllowedUpdate(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		modelIn.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		modelIn.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		modelIn.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		modelIn.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		modelIn.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		return true;
	}
}
