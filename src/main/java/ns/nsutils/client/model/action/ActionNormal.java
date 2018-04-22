package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class ActionNormal extends AbstractDefaultAction
{
	
	@Override
	public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		modelIn.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
		modelIn.bipedBody.rotateAngleY = 0.0F;
		modelIn.bipedRightArm.rotationPointZ = 0.0F;
		modelIn.bipedRightArm.rotationPointX = -5.0F;
		modelIn.bipedLeftArm.rotationPointZ = 0.0F;
		modelIn.bipedLeftArm.rotationPointX = 5.0F;
		modelIn.bipedRightArm.rotateAngleZ = 0.0F;
		modelIn.bipedLeftArm.rotateAngleZ = 0.0F;
		modelIn.bipedRightLeg.rotateAngleY = 0.0F;
		modelIn.bipedLeftLeg.rotateAngleY = 0.0F;
		modelIn.bipedRightLeg.rotateAngleZ = 0.0F;
		modelIn.bipedLeftLeg.rotateAngleZ = 0.0F;
		
		modelIn.bipedRightArm.rotateAngleY = 0.0F;
		modelIn.bipedRightArm.rotateAngleZ = 0.0F;
		
		switch (modelIn.leftArmPose)
		{
			case EMPTY:
				modelIn.bipedLeftArm.rotateAngleY = 0.0F;
				break;
			case ITEM:
				modelIn.bipedLeftArm.rotateAngleX = modelIn.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
				modelIn.bipedLeftArm.rotateAngleY = 0.0F;
				break;
			default:
		}
		switch (modelIn.rightArmPose)
		{
			case EMPTY:
				modelIn.bipedRightArm.rotateAngleY = 0.0F;
				break;
			case ITEM:
				modelIn.bipedRightArm.rotateAngleX = modelIn.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
				modelIn.bipedRightArm.rotateAngleY = 0.0F;
				break;
			default:
		}
		modelIn.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		modelIn.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		modelIn.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		modelIn.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		return true;
	}
	
	@Override
	public String getActionName()
	{
		return "normal";
	}
	
	@Override
	public boolean allowAction(EntityPlayer playerIn, ModelPlayer modelIn)
	{
		return true;
	}
}
