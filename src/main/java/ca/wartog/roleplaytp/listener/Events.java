package ca.wartog.roleplaytp.listener;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ca.wartog.roleplaytp.Main;
import ca.wartog.roleplaytp.PlayerInteraction;

public class Events implements Listener{
	
	private static Main main = Main.getInstance();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Action action = e.getAction();
		Player p = e.getPlayer();
		ItemStack itemInHand = p.getInventory().getItemInMainHand();
		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if(itemInHand != null) {
				ConfigurationSection itemInfo = getItemInfo(itemInHand);
				if(itemInfo == null) return;
				e.setCancelled(true);
				PlayerInteraction.teleportToWarp(p, itemInfo.getString("warp"), itemInHand);
			}
		}
	}
	
	private ConfigurationSection getItemInfo(ItemStack item) {
		for(String key : main.getConfig().getConfigurationSection("items").getKeys(false)) {
			if(item.getItemMeta().getDisplayName().equals(main.getConfig().getString("items." + key + ".name").replace("&", "§"))) {
				return main.getConfig().getConfigurationSection("items." + key);
			}
		}
		return null;
	}
}
