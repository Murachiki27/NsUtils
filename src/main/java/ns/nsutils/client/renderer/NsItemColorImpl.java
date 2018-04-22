package ns.nsutils.client.renderer;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class NsItemColorImpl implements IItemColor
{
	private final int[] colorLayers;
	
	public NsItemColorImpl(int... colorLayers)
	{
		this.colorLayers = colorLayers;
	}
	
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		if (tintIndex < 0 || tintIndex >= this.colorLayers.length) throw new IndexOutOfBoundsException("tintIndex is out of range :" + tintIndex);
		return this.colorLayers[tintIndex];
	}
}
