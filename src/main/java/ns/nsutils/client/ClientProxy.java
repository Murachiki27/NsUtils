package ns.nsutils.client;

import ns.nsutils.client.key.NsKeyHandler;
import ns.nsutils.client.model.NsModelManager;
import ns.nsutils.client.renderer.NsRenderManager;
import ns.nsutils.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
	
	@Override
	public void preInit()
	{
		super.preInit();
		
		NsRenderManager.init();
	}
	
	@Override
	public void init()
	{
		super.init();
		NsModelManager.initModel();
		
		NsKeyHandler.init();
		
		//NsModelManager.registerPlayerAction(new DebugActionSwing());
		//NsModelManager.registerPlayerAction(new ZombieActions.ZombieNormalAction());
		//NsModelManager.registerPlayerAction(new ZombieActions.ZombieGunAction());
	}
	
	@Override
	public void postInit()
	{
		super.postInit();
	}
	
}
