package ca.wartog.roleplaytp.manager;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import ca.wartog.roleplaytp.Main;
import ca.wartog.roleplaytp.utils.ItemBuilder;

public class WarpManager {
	
	private static final String WARP_FILE_NAME = "warps.yml";
	
	private File file;
	private FileConfiguration fileConfig;
	
	public WarpManager() {
		file = new File(Main.getInstance().getDataFolder() + File.separator + WARP_FILE_NAME);
		fileConfig = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean setWarp(Location loc, String name, ItemStack item) {
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		fileConfig.set("warps." + name + ".item", item.getType().toString());
		fileConfig.set("warps." + name + ".player-uuid", meta.getOwningPlayer().getUniqueId().toString());
		fileConfig.set("warps." + name + ".teleport-timer-value", Main.getInstance().getConfig().getInt("teleport-timer-default-value"));
		fileConfig.set("warps." + name + ".world", loc.getWorld().getName());
		fileConfig.set("warps." + name + ".x", loc.getX());
		fileConfig.set("warps." + name + ".y", loc.getY());
		fileConfig.set("warps." + name + ".z", loc.getZ());
		fileConfig.set("warps." + name + ".yaw", loc.getYaw());
		fileConfig.set("warps." + name + ".pitch", loc.getPitch());
		try {
			fileConfig.save(file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delWarp(String name) {
		fileConfig.set("warps." + name, null);
		try {
			fileConfig.save(file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isWarpSet(String name) {
		return fileConfig.isSet("warps." + name);
	}
	
	public boolean isWarpListEmpty() {
		if(fileConfig.getConfigurationSection("warps") == null)
			return true;
		return false;
	}
	
	public int numberOfWarps() {
		if(isWarpListEmpty())
			return 0;
		return fileConfig.getConfigurationSection("warps").getKeys(false).size();
	}
	
	public ConfigurationSection getWarpInformation(String name) {
		return fileConfig.getConfigurationSection("warps." + name);
	}
	
	public void addWarpToInventory(Inventory inv) {
		if(isWarpListEmpty()) return;
		for(String name : fileConfig.getConfigurationSection("warps").getKeys(false)) {
			inv.addItem(new ItemBuilder(Material.getMaterial(fileConfig.getString("warps." + name + ".item"))).setName(Main.getInstance().getConfig().getString("inventory.warp-name-color").replace("&", "§") + name).setSkullOwner((Player) Bukkit.getOfflinePlayer(UUID.fromString(fileConfig.getString("warps." + name + ".player-uuid")))).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS).toItemStack());
		}
	}

}
