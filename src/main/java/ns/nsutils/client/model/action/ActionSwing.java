package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ActionSwing extends AbstractDefaultAction
{
	public ActionSwing()
	{
		super();
	}
	@Override
	public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		EnumHandSide enumhandside = this.getMainHand(playerIn);
		ModelRenderer modelrenderer = this.getArmForSide(enumhandside, modelIn);
		float f1 = modelIn.swingProgress;
		modelIn.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;
		if (enumhandside == EnumHandSide.LEFT)
		{
			modelIn.bipedBody.rotateAngleY *= -1.0F;
		}
		modelIn.bipedRightArm.rotationPointZ = MathHelper.sin(modelIn.bipedBody.rotateAngleY) * 5.0F;
		modelIn.bipedRightArm.rotationPointX = -MathHelper.cos(modelIn.bipedBody.rotateAngleY) * 5.0F;
		modelIn.bipedLeftArm.rotationPointZ = -MathHelper.sin(modelIn.bipedBody.rotateAngleY) * 5.0F;
		modelIn.bipedLeftArm.rotationPointX = MathHelper.cos(modelIn.bipedBody.rotateAngleY) * 5.0F;
		modelIn.bipedRightArm.rotateAngleY += modelIn.bipedBody.rotateAngleY;
		modelIn.bipedLeftArm.rotateAngleY += modelIn.bipedBody.rotateAngleY;
		modelIn.bipedLeftArm.rotateAngleX += modelIn.bipedBody.rotateAngleY;
		f1 = 1.0F - modelIn.swingProgress;
		f1 = f1 * f1;
		f1 = f1 * f1;
		f1 = 1.0F - f1;
		float f2 = MathHelper.sin(f1 * (float)Math.PI);
		float f3 = MathHelper.sin(modelIn.swingProgress * (float)Math.PI) * -(modelIn.bipedHead.rotateAngleX - 0.7F) * 0.75F;
		modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
		modelrenderer.rotateAngleY += modelIn.bipedBody.rotateAngleY * 2.0F;
		modelrenderer.rotateAngleZ += MathHelper.sin(modelIn.swingProgress * (float)Math.PI) * -0.4F;
		return true;
	}
	
	@Override
	public String getActionName()
	{
		return "swing";
	}
	
	protected EnumHandSide getMainHand(EntityPlayer playerIn)
	{
		EnumHandSide enumhandside = playerIn.getPrimaryHand();
		return playerIn.swingingHand == EnumHand.MAIN_HAND ? enumhandside : enumhandside.opposite();
	}
	
	protected ModelRenderer getArmForSide(EnumHandSide side, ModelPlayer modelIn)
	{
		return side == EnumHandSide.LEFT ? modelIn.bipedLeftArm : modelIn.bipedRightArm;
	}
	
	@Override
	public boolean allowAction(EntityPlayer playerIn, ModelPlayer modelIn)
	{
		return modelIn.swingProgress > 0.0F;
	}
}
