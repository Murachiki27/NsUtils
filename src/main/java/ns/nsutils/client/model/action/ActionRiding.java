package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class ActionRiding extends AbstractDefaultAction
{
	
	@Override
	public String getActionName()
	{
		return "riding";
	}
	
	@Override
	public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		modelIn.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
		modelIn.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
		modelIn.bipedRightLeg.rotateAngleX = -1.4137167F;
		modelIn.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
		modelIn.bipedRightLeg.rotateAngleZ = 0.07853982F;
		modelIn.bipedLeftLeg.rotateAngleX = -1.4137167F;
		modelIn.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
		modelIn.bipedLeftLeg.rotateAngleZ = -0.07853982F;
		return true;
	}
	
	@Override
	public boolean allowAction(EntityPlayer playerIn, ModelPlayer modelIn)
	{
		return modelIn.isRiding;
	}
}
