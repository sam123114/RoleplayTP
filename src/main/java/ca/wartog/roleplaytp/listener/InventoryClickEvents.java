package ca.wartog.roleplaytp.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import ca.wartog.roleplaytp.Main;
import ca.wartog.roleplaytp.manager.WarpManager;

public class InventoryClickEvents implements Listener{
	
	private static Main main = Main.getInstance();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		String invName = e.getView().getTitle();
		Player p = (Player) e.getWhoClicked();
		if(invName.equals(main.getConfig().getString("inventory.title").replace("&", "§")) && !e.getInventory().getType().equals(InventoryType.PLAYER)) {
			e.setCancelled(true);
			
			ItemStack clickedItem = e.getCurrentItem();
			if(clickedItem == null) return;
			if(!clickedItem.getItemMeta().hasDisplayName()) return;
			String itemName = clickedItem.getItemMeta().getDisplayName().substring(2);
			WarpManager wm = new WarpManager();
			if(!wm.isWarpSet(itemName)) return;
			ConfigurationSection section = wm.getWarpInformation(itemName);
			
			World world = Bukkit.getWorld((String) section.getString("world"));
			double x = section.getDouble("x");
			double y = section.getDouble("y");
			double z = section.getDouble("z");
			float pitch = section.getInt("pitch");
			float yaw = section.getInt("yaw");
			
			Location tpLocation = new Location(world, x, y, z, yaw, pitch);
			
			p.teleport(tpLocation);
			p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 0.1f, 1);
			p.sendMessage(main.getConfig().getString("messages.teleport-to-warp").replace("&", "§").replace("{warpName}", itemName));
			
		}
	}
}
