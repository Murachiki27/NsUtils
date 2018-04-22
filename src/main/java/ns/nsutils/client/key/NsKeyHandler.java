package ns.nsutils.client.key;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NsKeyHandler
{
	
	private static final List<IKeyListener> keyList = Lists.<IKeyListener>newArrayList();
	private static final List<Boolean> keyFlagList = Lists.<Boolean>newArrayList();
	
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(NsKeyHandler.class);
	}
	
	public static KeyBinding register(String description, int keycode, String category)
	{
		KeyBinding keybinding = new KeyBinding(description, keycode, category);
		ClientRegistry.registerKeyBinding(keybinding);
		return keybinding;
	}
	
	public static void register(KeyBinding keybinding)
	{
		ClientRegistry.registerKeyBinding(keybinding);
	}
	
	public static void addKeyListener(IKeyListener listener)
	{
		keyList.add(listener);
		keyFlagList.add(false);
	}
	
	@SubscribeEvent
	public static void onKeyInputEvent(InputEvent.KeyInputEvent event)
	{
		for (int i = 0; i < keyList.size(); ++i)
		{
			IKeyListener listener = keyList.get(i);
			int keycode = listener.getKeyCode();
			if (Keyboard.isKeyDown(keycode))
			{
				if (!keyFlagList.get(i))
				{
					keyFlagList.set(i, true);
					listener.keyPressed(Side.CLIENT, keycode);
				}
				listener.keyDown(Side.CLIENT, keycode);
			}
			else if (keyFlagList.get(i))
			{
				listener.keyReleased(Side.CLIENT, keycode);
				keyFlagList.set(i, false);
			}
		}
	}
	
	public static void onServerPressed(int listenerIndex, int keycode)
	{
		keyList.get(listenerIndex).keyPressed(Side.SERVER, keycode);
	}
	
	public static void onServerKeyDown(int listenerIndex, int keycode)
	{
		keyList.get(listenerIndex).keyDown(Side.SERVER, keycode);
	}
	
	public static void onServerReleased(int listenerIndex, int keycode)
	{
		keyList.get(listenerIndex).keyReleased(Side.SERVER, keycode);
	}
	
}
