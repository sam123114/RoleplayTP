package ca.wartog.roleplaytp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import ca.wartog.roleplaytp.Main;
import ca.wartog.roleplaytp.manager.WarpManager;

public class WarpCommand implements CommandExecutor {
	
	private static Main main = Main.getInstance();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player p = (Player) sender;
		if(!p.hasPermission("roleplaytp.warp")) {
			p.sendMessage(main.getConfig().getString("messages.permission").replace("&", "§"));
			return false;
		}
		
		Inventory inv = Bukkit.createInventory(null, main.getConfig().getInt("inventory.number-of-rows")*9, main.getConfig().getString("inventory.title").replace("&", "§"));
		
		WarpManager wm = new WarpManager();
		wm.addWarpToInventory(inv);
		
		p.openInventory(inv);
		
		return false;
	}

}
