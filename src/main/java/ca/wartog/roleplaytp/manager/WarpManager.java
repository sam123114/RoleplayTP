package ca.wartog.roleplaytp.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import ca.wartog.roleplaytp.Main;

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
		fileConfig.set("warps." + name + ".item", item.getType().toString());
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
		return fileConfig.isSet(name);
	}

}
