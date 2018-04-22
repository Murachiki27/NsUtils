package ns.nsutils.common.addon;

import java.io.File;

public class AddonData
{
	public String addonName;
	public String addonVersion;
	
	public File parentDir;
	
	public AddonData(String addonName, String addonVersion, File parentDir)
	{
		this.addonName = addonName;
		this.addonVersion = addonVersion;
		this.parentDir = parentDir;
	}
	
}
