package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;

public class DebugActionSwing extends ActionSwing
{
	
	public DebugActionSwing()
	{
		super();
	}
	
	@Override
	public boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		super.updateAction(playerIn, modelIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		return true;
	}
	
	@Override
	protected EnumHandSide getMainHand(EntityPlayer playerIn)
	{
		return super.getMainHand(playerIn).opposite();
	}
	
	@Override
	public String getModId()
	{
		return "nsutils";
	}
}
