package ns.nsutils.client.model.action;

public enum ActionPriority
{
	FIRST(false), BEFORE_DEF(true), AFTER_DEF(true), LAST(false);
	
	private final boolean def;
	
	private ActionPriority(boolean def)
	{
		this.def = def;
	}
	
	public boolean isDef()
	{
		return this.def;
	}
}