package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class ActionSneak extends AbstractDefaultAction
{
	@Override
	public String getActionName()
	{
		return "sneak";
	}
	
	@Override
	public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		modelIn.bipedBody.rotateAngleX = 0.5F;
		modelIn.bipedRightArm.rotateAngleX += 0.4F;
		modelIn.bipedLeftArm.rotateAngleX += 0.4F;
		modelIn.bipedRightLeg.rotationPointZ = 4.0F;
		modelIn.bipedLeftLeg.rotationPointZ = 4.0F;
		modelIn.bipedRightLeg.rotationPointY = 9.0F;
		modelIn.bipedLeftLeg.rotationPointY = 9.0F;
		modelIn.bipedHead.rotationPointY = 1.0F;
		return true;
	}
	
	@Override
	public boolean allowAction(EntityPlayer playerIn, ModelPlayer modelIn)
	{
		return modelIn.isSneak;
	}
	
	@Override
	public boolean notAllowedUpdate(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		modelIn.bipedBody.rotateAngleX = 0.0F;
		modelIn.bipedRightLeg.rotationPointZ = 0.1F;
		modelIn.bipedLeftLeg.rotationPointZ = 0.1F;
		modelIn.bipedRightLeg.rotationPointY = 12.0F;
		modelIn.bipedLeftLeg.rotationPointY = 12.0F;
		modelIn.bipedHead.rotationPointY = 0.0F;
		return true;
	}
}
