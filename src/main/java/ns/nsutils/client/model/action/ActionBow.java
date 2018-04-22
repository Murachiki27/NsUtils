package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class ActionBow extends AbstractDefaultAction
{	
	@Override
	public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		if (modelIn.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
		{
			modelIn.bipedRightArm.rotateAngleY = -0.1F + modelIn.bipedHead.rotateAngleY;
			modelIn.bipedLeftArm.rotateAngleY = 0.1F + modelIn.bipedHead.rotateAngleY + 0.4F;
			modelIn.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + modelIn.bipedHead.rotateAngleX;
			modelIn.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + modelIn.bipedHead.rotateAngleX;
		}
		else if (modelIn.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
		{
			modelIn.bipedRightArm.rotateAngleY = -0.1F + modelIn.bipedHead.rotateAngleY - 0.4F;
			modelIn.bipedLeftArm.rotateAngleY = 0.1F + modelIn.bipedHead.rotateAngleY;
			modelIn.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + modelIn.bipedHead.rotateAngleX;
			modelIn.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + modelIn.bipedHead.rotateAngleX;
		}
		return true;
	}
	
	@Override
	public String getActionName()
	{
		return "bow";
	}
	
	@Override
	public boolean allowAction(EntityPlayer playerIn, ModelPlayer modelIn)
	{
		return modelIn.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW || modelIn.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW;
	}
}
