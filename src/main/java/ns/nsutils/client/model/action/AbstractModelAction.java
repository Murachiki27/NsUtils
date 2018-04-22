package ns.nsutils.client.model.action;

import javax.annotation.Nonnull;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractModelAction implements IModelAction
{
	protected boolean enable;
	
	protected final String modId;
	
	public AbstractModelAction(@Nonnull String modId)
	{
		this.modId = modId;
		this.enable = true;
	}
	
	public String getModId()
	{
		return this.modId;
	}
	
	/**
	 * Updating player model actions.</p>
	 * 
	 * If you try to avoid interference with other actions,
	 * you need to set the return value to false. For example:
	 * <blockquote><pre>
	 *     if (!playerIn.isSneaking())
	 *     {
	 *         return false;
	 *     }
	 *     
	 *     // Your codes
	 *     
	 *     return true;
	 * </pre></blockquote><p>
	 */
	@Override
	public abstract boolean updateAction(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor);
	
	public boolean notAllowedUpdate(EntityPlayer playerIn, ModelPlayer modelIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		return true;
	}
	
	public final void setEnable(boolean enable)
	{
		this.enable = enable;
	}
	
	public final boolean isEnable()
	{
		return this.enable;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractModelAction other = (AbstractModelAction) obj;
		if (enable != other.enable)
			return false;
		if (modId == null)
		{
			if (other.modId != null)
				return false;
		}
		else if (!modId.equals(other.modId))
			return false;
		return true;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (enable ? 1231 : 1237);
		result = prime * result + ((modId == null) ? 0 : modId.hashCode());
		return result;
	}
}
