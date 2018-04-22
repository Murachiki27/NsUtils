package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class ActionGuard extends AbstractDefaultAction
{
	
	@Override
	public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		if (modelIn.leftArmPose == ModelBiped.ArmPose.BLOCK)
		{
			modelIn.bipedLeftArm.rotateAngleX = modelIn.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
			modelIn.bipedLeftArm.rotateAngleY = 0.5235988F;
		}
		
		if (modelIn.rightArmPose == ModelBiped.ArmPose.BLOCK)
		{
			modelIn.bipedRightArm.rotateAngleX = modelIn.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
			modelIn.bipedRightArm.rotateAngleY = -0.5235988F;
		}
		return true;
	}
	
	@Override
	public String getActionName()
	{
		return "guard";
	}
	
	@Override
	public boolean allowAction(EntityPlayer playerIn, ModelPlayer modelIn)
	{
		return modelIn.leftArmPose == ModelBiped.ArmPose.BLOCK || modelIn.rightArmPose == ModelBiped.ArmPose.BLOCK;
	}
}
