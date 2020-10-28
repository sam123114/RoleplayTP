package ca.wartog.roleplaytp;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import ca.wartog.roleplaytp.commands.RoleplayTpCommand;
import ca.wartog.roleplaytp.utils.ItemBuilder;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		
		getCommand("roleplaytp").setExecutor(new RoleplayTpCommand());
	}

	public static Main getInstance() {
		return instance;
	}
	
	public ItemStack getItem() {
		return new ItemBuilder(Material.getMaterial(getConfig().getString("item.type"))).setName(getConfig().getString("item.name").replace("&", "§")).setLore(getConfig().getString("item.description").replace("&", "§").split(";")).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS).toItemStack();
	}
	
}
