package ca.wartog.roleplaytp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ca.wartog.roleplaytp.manager.WarpManager;
import ca.wartog.roleplaytp.utils.CountdownTitle;

public class PlayerInteraction {
	
	private static Main main = Main.getInstance();
	
	public static boolean teleportToWarp(Player p, String warpName, ItemStack item) {
		WarpManager wm = new WarpManager();
		if(!wm.isWarpSet(warpName)) {
			p.sendMessage("§cThere is no warp linked with this item");
			return false;
		}
		ConfigurationSection section = wm.getWarpInformation(warpName);
		
		World world = Bukkit.getWorld((String) section.getString("world"));
		double x = section.getDouble("x");
		double y = section.getDouble("y");
		double z = section.getDouble("z");
		float pitch = section.getInt("pitch");
		float yaw = section.getInt("yaw");
		
		int timer = section.getInt("teleport-timer-value");
		
		Location tpLocation = new Location(world, x, y, z, yaw, pitch);
		
		if(item != null) {
			new CountdownTitle(timer, p, tpLocation, warpName, item).runTaskTimer(Main.getInstance(), 0, 20);
			return true;
		}
		
		p.teleport(tpLocation);
		p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 0.1f, 1);
		p.sendMessage(main.getConfig().getString("messages.teleport-to-warp").replace("&", "§").replace("{warpName}", warpName));
		return true;
	}
	
	public static void removeItemFromInventory(Player p, ItemStack item) {
		int amount = item.getAmount();
		if(amount > 1) {
			item.setAmount(amount - 1);
			return;
		}
		item.setAmount(0);
	}
	
	public static void sendTitleToPlayer(Player p, String title, String subTitle) {
		p.sendTitle(title, subTitle, 1, 20, 1);
	}
}
