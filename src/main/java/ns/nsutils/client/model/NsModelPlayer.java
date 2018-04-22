package ns.nsutils.client.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ns.nsutils.client.model.action.AbstractDefaultAction;
import ns.nsutils.client.model.action.AbstractModelAction;
import ns.nsutils.client.model.action.ActionBow;
import ns.nsutils.client.model.action.ActionElytraFlying;
import ns.nsutils.client.model.action.ActionGuard;
import ns.nsutils.client.model.action.ActionNormal;
import ns.nsutils.client.model.action.ActionPriority;
import ns.nsutils.client.model.action.ActionRiding;
import ns.nsutils.client.model.action.ActionSneak;
import ns.nsutils.client.model.action.ActionSwing;
import ns.nsutils.util.ArrayManager;

@SideOnly(Side.CLIENT)
public class NsModelPlayer extends ModelPlayer
{
	private ArrayListMultimap<String, AbstractModelAction> actionMap = ArrayListMultimap.create();
	private static Map<String, AbstractDefaultAction> defaultActionMap = Maps.newHashMap();
	private String[] runningActionNames;
	private static String[] defaultSctionNames = {"elytra_flying", "normal", "swing", "bow", "guard", "riding", "sneak"};
	static
	{
		defaultActionMap.put("elytra_flying", new ActionElytraFlying());
		defaultActionMap.put("normal", new ActionNormal());
		defaultActionMap.put("swing", new ActionSwing());
		defaultActionMap.put("bow", new ActionBow());
		defaultActionMap.put("guard", new ActionGuard());
		defaultActionMap.put("riding", new ActionRiding());
		defaultActionMap.put("sneak", new ActionSneak());
	}
	
	public NsModelPlayer(float modelSize, boolean smallArmsIn)
	{
		super(modelSize, smallArmsIn);
		this.actionMap.put("elytra_flying", defaultActionMap.get("elytra_flying"));
		this.actionMap.put("normal", defaultActionMap.get("normal"));
		this.actionMap.put("swing", defaultActionMap.get("swing"));
		this.actionMap.put("bow", defaultActionMap.get("bow"));
		this.actionMap.put("guard", defaultActionMap.get("guard"));
		this.actionMap.put("riding", defaultActionMap.get("riding"));
		this.actionMap.put("sneak", defaultActionMap.get("sneak"));
		this.runningActionNames = defaultSctionNames;
	}
	
	public void registerAction(AbstractModelAction action)
	{
		if (action == null) return;
		String key = action.getActionName();
		List<AbstractModelAction> list = this.actionMap.get(key);
		ActionPriority prio = action.getPriority();
		AbstractDefaultAction defAction = defaultActionMap.get(key);
		int i = list.indexOf(defAction);
		if (prio != null && prio.isDef() && i != -1)
		{
			if (prio == ActionPriority.AFTER_DEF)
			{
				i++;
			}
		}
		else
		{
			if (prio == ActionPriority.FIRST)
			{
				i = 0;
			}
			else
			{
				i = list.size();
			}
		}
		list.add(i, action);
		this.checkRunningNames();
	}
	
	public void restartAction(String modid, String actionName)
	{
		enable(modid, actionName, true);
	}
	
	public void stopAction(String modid, String actionName)
	{
		enable(modid, actionName, false);
	}
	
	public void shiftUp(String modid, String actionName, int count)
	{
		int i = this.getActionIndex(modid, this.actionMap.get(actionName));
		if (i != -1)
		{
			count = count < 0 ? -count : count;
			int j = MathHelper.clamp(i - count, 0, this.actionMap.get(actionName).size());
			shift(i, j, actionName);
		}
	}
	
	public void shiftFirst(String modid, String actionName)
	{
		int i = this.getActionIndex(modid, this.actionMap.get(actionName));
		if (i != -1)
		{
			shift(i, 0, actionName);
		}
	}
	
	public void shiftDown(String modid, String actionName, int count)
	{
		int i = this.getActionIndex(modid, this.actionMap.get(actionName));
		if (i != -1)
		{
			count = count < 0 ? -count : count;
			int j = MathHelper.clamp(i + count, 0, this.actionMap.get(actionName).size());
			shift(i, j, actionName);
		}
	}
	
	public void shiftLast(String modid, String actionName)
	{
		List<AbstractModelAction> list = this.actionMap.get(actionName);
		int i = this.getActionIndex(modid, list);
		if (i != -1)
		{
			shift(i, list.size(), actionName);
		}
	}
	
	void shift(int index, int dirIndex, String actionName)
	{
		List<AbstractModelAction> list = this.actionMap.get(actionName);
		AbstractModelAction action = list.remove(index);
		list.add(dirIndex, action);
	}
	
	void sort(int index, int dirIndex, String actionName)
	{
		List<AbstractModelAction> list = this.actionMap.get(actionName);
		AbstractModelAction a1 = list.get(index);
		AbstractModelAction a2 = list.get(dirIndex);
		list.set(dirIndex, a1);
		list.set(index, a2);
	}
	
	void enable(String modid, String actionName, boolean enable)
	{
		AbstractModelAction target = getModelAction(modid, this.actionMap.get(actionName));
		if (target != null)
		{
			target.setEnable(enable);
		}
	}
	
	@Deprecated
	public void startAction(String modid, String actionName)
	{
		restartAction(modid, actionName);
	}
	
	@Deprecated
	public void endAction(String modid, String actionName)
	{
		stopAction(modid, actionName);
	}
	
	private void checkRunningNames()
	{
		Set<String> set = this.actionMap.keySet();
		String[] newArray = set.toArray(new String[0]);
		List<String> l1 = Lists.newLinkedList();
		List<String> l2 = Lists.newLinkedList(set);
		this.runningActionNames = null;
		for (String s : defaultSctionNames)
		{
			if (ArrayManager.indexOf(s, newArray) != -1)
			{
				l1.add(s);
				l2.remove(s);
			}
		}
		if (!l2.isEmpty())
		{
			l1.addAll(l2);
		}
		this.runningActionNames = l1.toArray(new String[l1.size()]);
	}
	
	protected AbstractModelAction getModelAction(String modid, List<AbstractModelAction> list)
	{
		if (modid == null || list == null) return null;
		for (int i = 0; i < list.size(); i++)
		{
			AbstractModelAction action = list.get(i);
			if (action.getModId().equals(modid))
			{
				return action;
			}
		}
		return null;
	}
	
	protected int getActionIndex(String modid, List<AbstractModelAction> list)
	{
		if (modid == null || list == null) return -1;
		for (int i = 0; i < list.size(); i++)
		{
			AbstractModelAction action = list.get(i);
			if (action.getModId().equals(modid))
			{
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		String[] array = this.runningActionNames;
		for (int i = 0; i < array.length; i++)
		{
			AbstractDefaultAction defAction = defaultActionMap.get(array[i]);
			List<AbstractModelAction> l = this.actionMap.get(array[i]);
			if (l.isEmpty() && defAction != null)
			{
				l.add(defAction);
			}
			for (int j = 0; j < l.size(); j++)
			{
				if (!l.get(j).isEnable()) continue;
				if (defAction != null)
				{
					if (defAction.allowAction((EntityPlayer)entityIn, (ModelPlayer)this))
					{
						if (!l.get(j).updateAction((EntityPlayer)entityIn, (ModelPlayer)this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor))
						{
							break;
						}
					}
					else
					{
						if (!l.get(j).notAllowedUpdate((EntityPlayer)entityIn, (ModelPlayer)this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor))
						{
							break;
						}
					}
				}
				else
				{
					if (!l.get(j).updateAction((EntityPlayer)entityIn, (ModelPlayer)this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor))
					{
						break;
					}
				}
			}
		}
		copyModelAngles(this.bipedHead, this.bipedHeadwear);
	}
}
