package ca.wartog.roleplaytp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import ca.wartog.roleplaytp.Main;
import ca.wartog.roleplaytp.PlayerInteraction;

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
			
			PlayerInteraction.teleportToWarp(p, itemName, null);
		}
	}
}
