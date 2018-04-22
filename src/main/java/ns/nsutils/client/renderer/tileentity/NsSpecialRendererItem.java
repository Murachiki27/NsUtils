package ns.nsutils.client.renderer.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import ns.nsutils.client.model.NsModelItemBase;
import ns.nsutils.client.renderer.NsRenderManager;
import ns.nsutils.client.tileentity.NsTileEntityItem;

public class NsSpecialRendererItem extends TileEntitySpecialRenderer<NsTileEntityItem>
{
	@Override
	public void renderTileEntityAt(NsTileEntityItem te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		//super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);
		if (te != null || NsModelItemBase.currentOwner == null || NsModelItemBase.currentStack.isEmpty()) return;
		
		
		NsRenderManager.renderItem(NsModelItemBase.currentStack, NsModelItemBase.currentOwner, NsModelItemBase.currentTransType);
	}
}
