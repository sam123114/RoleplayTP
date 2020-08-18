package ca.wartog.roleplaytp;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
	}

	public Main getInstance() {
		return instance;
	}
	
}
