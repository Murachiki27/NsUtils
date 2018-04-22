package ns.nsutils.client.model.action;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractDefaultAction extends AbstractModelAction
{
	public AbstractDefaultAction()
	{
		super("minecraft");
	}
	
	@Override
	public void initAction()
	{
	}
	
	@Override
	public void endAction()
	{
	}
	
	@Override
	public ActionPriority getPriority()
	{
		return ActionPriority.LAST;
	}
	
	public abstract boolean allowAction(EntityPlayer playerIn, ModelPlayer modelIn);
}
