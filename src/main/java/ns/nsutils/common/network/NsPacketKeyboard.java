package ns.nsutils.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import ns.nsutils.client.key.NsKeyHandler;

public class NsPacketKeyboard extends AbstractPacket
{
	
	public enum Type { PRESS, DOWN, RELEASE }
	private int keycode;
	private Type type;
	private int listenerIndex;
	
	public NsPacketKeyboard()
	{
	}
	
	public NsPacketKeyboard(int keycode, Type type, int index)
	{
		this.keycode = keycode;
		this.type = type;
		this.listenerIndex = index;
	}
	
	@Override
	public void readFrom(ByteBuf buf)
	{
		this.keycode = buf.readInt();
		this.type = Type.values()[buf.readInt()];
		this.listenerIndex = buf.readInt();
	}
	
	@Override
	public void writeTo(ByteBuf buf)
	{
		buf.writeInt(this.keycode);
		buf.writeInt(this.type.ordinal());
		buf.writeInt(this.listenerIndex);
	}
	
	@Override
	public AbstractPacket receive(Side side, EntityPlayer player)
	{
		switch (this.type)
		{
		case PRESS:
			NsKeyHandler.onServerPressed(this.listenerIndex, this.keycode);
			break;
		case DOWN:
			NsKeyHandler.onServerKeyDown(this.listenerIndex, this.keycode);
			break;
		case RELEASE:
			NsKeyHandler.onServerReleased(this.listenerIndex, this.keycode);
			break;
		default:
		}
		return null;
	}

	@Override
	public Side receivingSide()
	{
		return Side.SERVER;
	}
}
