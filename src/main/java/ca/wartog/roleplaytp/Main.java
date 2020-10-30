package ca.wartog.roleplaytp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import ca.wartog.roleplaytp.commands.RoleplayTpCommand;
import ca.wartog.roleplaytp.commands.WarpCommand;
import ca.wartog.roleplaytp.listener.Events;
import ca.wartog.roleplaytp.listener.InventoryClickEvents;
import ca.wartog.roleplaytp.utils.ItemBuilder;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	/*
	 * PERMISSIONS:
	 * - roleplaytp.give
	 * - roleplaytp.reload
	 * - roleplaytp.setwarp
	 * - roleplaytp.delwarp
	 * - roleplaytp.warp
	 */
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		
		Bukkit.getPluginManager().registerEvents(new InventoryClickEvents(), this);
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		
		getCommand("roleplaytp").setExecutor(new RoleplayTpCommand());
		getCommand("execwarpmenu").setExecutor(new WarpCommand());
	}

	public static Main getInstance() {
		return instance;
	}
	
	public ItemStack getItem(String itemId) {
		ItemBuilder ib = new ItemBuilder(Material.getMaterial(getConfig().getString("items." + itemId + ".type"))).setName(getConfig().getString("items." + itemId + ".name").replace("&", "§")).setLore(getConfig().getString("items." + itemId + ".description").replace("&", "§").split(";")).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		int customModelData = getConfig().getInt("items." + itemId + ".custom-model-data");
		if(customModelData != -1) {
			return ib.setCustomModelData(customModelData).toItemStack();
		}
		return ib.toItemStack();
	}
	
}
