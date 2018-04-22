package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;

public interface IModelAction
{
	void initAction();
	
	boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor);
	
	void endAction();
	
	String getActionName();
	
	ActionPriority getPriority();
}
