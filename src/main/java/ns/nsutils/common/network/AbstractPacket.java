package ns.nsutils.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractPacket implements IMessage
{
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.readFrom(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		this.writeTo(buf);
	}
	
	public abstract void readFrom(ByteBuf buf);
	
	public abstract void writeTo(ByteBuf buf);
	
	public abstract AbstractPacket receive(Side side, EntityPlayer player);
	
	public abstract Side receivingSide();
	
}