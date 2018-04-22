package ns.nsutils.common.network;

import com.google.common.base.Throwables;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ns.nsutils.common.core.NsUtils;

public class NsPacketHandler
{
	private static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(NsUtils.CHANNEL_ID);
	
	public static void init()
	{
		register(NsPacketKeyboard.class, 0, Side.SERVER);
	}
	
	public static void sendToServer(IMessage message)
	{
		NETWORK_WRAPPER.sendToServer(message);
	}
	
	public static void sendTo(IMessage message, net.minecraft.entity.player.EntityPlayerMP player)
	{
		NETWORK_WRAPPER.sendTo(message, player);
	}
	
	public static void sendToAll(IMessage message)
	{
		NETWORK_WRAPPER.sendToAll(message);
	}
	
	public static void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point)
	{
		NETWORK_WRAPPER.sendToAllAround(message, point);
	}
	
	public static <REQ extends AbstractPacket> void register(Class<REQ> packetClass, int discriminator, Side side)
	{
		NETWORK_WRAPPER.registerMessage(new NsSimpleMessageHandler(), packetClass, discriminator, side);
	}
	
	public static SimpleNetworkWrapper newSimpleChannel(String channelName)
	{
		return NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
	}
	
	public static SimpleNetworkWrapper newSimpleChannel(String channelName, Class<? extends AbstractPacket>... packets)
	{
		SimpleNetworkWrapper ret = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		
		for (int i = 0; i < packets.length; i++)
		{
			Side side;
			try
			{
				side = packets[i].newInstance().receivingSide();
			}
			catch (Exception e)
			{
				NsUtils.LOGGER.warn("Could not create packet for class " + packets[i].getName() + " for channel " + channelName + ". Is there a default constructor in that class?");
				throw Throwables.propagate(e);
			}
			ret.registerMessage(new NsSimpleMessageHandler(), packets[i], i, side);
		}
		return ret;
	}
	
	
	static class NsSimpleMessageHandler implements IMessageHandler<AbstractPacket, AbstractPacket>
	{
		public NsSimpleMessageHandler()
		{
		}
		
		@Override
		public AbstractPacket onMessage(AbstractPacket message, MessageContext ctx)
		{
			EntityPlayer player;
			if(ctx.side.isServer())
			{
				player = ctx.getServerHandler().playerEntity;
			}
			else
			{
				player = this.getClientPlayer();
			}
			return message.receive(ctx.side, player);
		}
		
		@SideOnly(Side.CLIENT)
		EntityPlayer getClientPlayer()
		{
			return Minecraft.getMinecraft().player;
		}
	}
}
