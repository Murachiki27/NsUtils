package ns.nsutils.client.key;

import net.minecraftforge.fml.relauncher.Side;

public interface IKeyListener
{
	void keyPressed(Side side, int keycode);
	
	void keyReleased(Side side, int keycode);
	
	void keyDown(Side side, int keycode);
	
	int getKeyCode();
}
