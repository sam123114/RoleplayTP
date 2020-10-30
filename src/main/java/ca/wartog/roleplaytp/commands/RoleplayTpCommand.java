package ca.wartog.roleplaytp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import ca.wartog.roleplaytp.Main;
import ca.wartog.roleplaytp.manager.WarpManager;

public class RoleplayTpCommand implements CommandExecutor{
	
	private static Main main = Main.getInstance();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		
		if(args.length > 0) {
			// COMMAND GIVE
			if(args[0].equalsIgnoreCase("give")) {
				if(!p.hasPermission("roleplaytp.give")) {
					p.sendMessage(main.getConfig().getString("messages.permission").replace("&", "§"));
					return false;
				}
				ItemStack item;
				if(args.length == 2) {
					if(!itemExists(args[1])) {
						p.sendMessage(main.getConfig().getString("messages.item-does-not-exist").replace("&", "§"));
						return false;
					}
					item = main.getItem(args[1]);
					giveItemToPlayer(p, item);
					p.sendMessage(main.getConfig().getString("messages.item-received").replace("&", "§").replace("{item}", item.getItemMeta().getDisplayName()));
					return true;
				} else if(args.length == 3) {
					if(!itemExists(args[2])) {
						p.sendMessage(main.getConfig().getString("messages.item-does-not-exist").replace("&", "§"));
						return false;
					}
					item = main.getItem(args[2]);
					Player target = Bukkit.getPlayer(args[1]);
					if(target == null){
						p.sendMessage(main.getConfig().getString("messages.player-not-online").replace("&", "§"));
						return false;
					}
					giveItemToPlayer(target, item);
					p.sendMessage(main.getConfig().getString("messages.item-given").replace("&", "§").replace("{item}", item.getItemMeta().getDisplayName()).replace("{playerName}", target.getDisplayName()));
					target.sendMessage(main.getConfig().getString("messages.item-received").replace("&", "§").replace("{item}", item.getItemMeta().getDisplayName()));
					return true;
				} else {
					displayMenu(p);
					return true;
				}
			}
			//COMMAND RELOAD
			else if(args[0].equalsIgnoreCase("reload")) {
				if(!p.hasPermission("roleplaytp.reload")) {
					p.sendMessage(main.getConfig().getString("messages.permission").replace("&", "§"));
					return false;
				}
				if(args.length == 1) {
					main.reloadConfig();
					p.sendMessage("§aConfig reloaded");
				} else {
					displayMenu(p);
					return true;
				}
			} 
			//COMMAND SETWARP
			else if(args[0].equalsIgnoreCase("setwarp")){
				if(!p.hasPermission("roleplaytp.setwarp")) {
					p.sendMessage(main.getConfig().getString("messages.permission").replace("&", "§"));
					return false;
				}
				if(args.length == 2) {
					ItemStack itemInHand = p.getInventory().getItemInMainHand();
					if(itemInHand == null || !itemInHand.getType().equals(Material.PLAYER_HEAD)) {
						p.sendMessage(main.getConfig().getString("messages.no-head-in-hand").replace("&", "§"));
						return false;
					}
					WarpManager wm = new WarpManager();
					if(wm.numberOfWarps() >= (main.getConfig().getInt("inventory.number-of-rows") * 9)) {
						p.sendMessage(main.getConfig().getString("messages.maximum-amount-warp").replace("&", "§"));
						return false;
					}
					if(wm.isWarpSet(args[1])) {
						p.sendMessage(main.getConfig().getString("messages.warp-already-exists").replace("&", "§"));
						return false;
					}
					SkullMeta sm = (SkullMeta) itemInHand.getItemMeta();
					if(sm == null || !sm.hasOwner()) {
						p.sendMessage(main.getConfig().getString("messages.no-head-in-hand").replace("&", "§"));
						return false;
					}
					wm.setWarp(p.getLocation(), args[1], itemInHand);
					p.sendMessage(main.getConfig().getString("messages.warp-set").replace("&", "§").replace("{warpName}", args[1]));
					return true;
				} else {
					displayMenu(p);
					return true;
				}
			}
			//COMMAND DELWARP
			else if(args[0].equalsIgnoreCase("delwarp")) {
				if(!p.hasPermission("roleplaytp.delwarp")) {
					p.sendMessage(main.getConfig().getString("messages.permission").replace("&", "§"));
					return false;
				}
				if(args.length == 2) {
					WarpManager wm = new WarpManager();
					if(!wm.isWarpSet(args[1])) {
						p.sendMessage(main.getConfig().getString("messages.warp-does-not-exists").replace("&", "§"));
						return false;
					}
					wm.delWarp(args[1]);
					p.sendMessage(main.getConfig().getString("messages.warp-delete").replace("&", "§").replace("{warpName}", args[1]));
					return true;
				} else {
					displayMenu(p);
					return true;
				}
			}
			else {
				displayMenu(p);
				return true;
			}
		} else {
			displayMenu(p);
		}
		
		return false;
	}
	
	private void giveItemToPlayer(Player p, ItemStack item) {
		p.getInventory().addItem(item);
	}
	
	private boolean itemExists(String itemId) {
		if(main.getConfig().isSet("items." + itemId)) return true;
		else return false;
	}
	
	private void displayMenu(Player p) {
		p.sendMessage("§8-=-=-=-=-[§aRoleplayTP§8]-=-=-=-=-");
		p.sendMessage("");
		p.sendMessage("§e /rptp §f- §7Command list");
		p.sendMessage("§e /rptp reload §f- §7Reload plugin");
		p.sendMessage("§e /rptp give [<player>] <itemId> §f- §7give item to player");
		p.sendMessage("§e /rptp setwarp <name> §f- §7set warp to your location");
		p.sendMessage("§e /rptp delwarp <name> §f- §7delete a warp");
		p.sendMessage("");
		p.sendMessage("§8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	}

}
