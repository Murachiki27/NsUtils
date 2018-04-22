package ns.nsutils.common;

import ns.nsutils.common.network.NsPacketHandler;

public class CommonProxy
{
	
	public void preInit()
	{
		;
	}
	
	public void init()
	{
		NsPacketHandler.init();
	}
	
	public void postInit()
	{
		;
	}
	
	
}