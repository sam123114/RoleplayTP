package ca.wartog.roleplaytp.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import ca.wartog.roleplaytp.Main;
import ca.wartog.roleplaytp.PlayerInteraction;

public class CountdownTitle extends BukkitRunnable{
	
	int time;
	Player p;
	Location loc;
	Location lastLocation;
	String warpName;
	ItemStack item;
	
	public CountdownTitle(int time, Player p, Location loc, String warpName, ItemStack item) {
		this.time = time;
		this.p = p;
		this.loc = loc;
		this.lastLocation = p.getLocation();
		this.warpName = warpName;
		this.item = item;
	}

	public void run() {
		execute();
	}
	
	private void execute() {
		if(playerMoved()) {
			PlayerInteraction.sendTitleToPlayer(p, "", Main.getInstance().getConfig().getString("messages.teleport.canceled").replace("&", "§").replace("{timeLeft}", "" + this.time).replace("{warpName}", this.warpName));
			this.cancel();
			return;
		}
		if(time == 0) {
			PlayerInteraction.sendTitleToPlayer(p, "",  Main.getInstance().getConfig().getString("messages.teleport.notification").replace("&", "§").replace("{timeLeft}", "" + this.time).replace("{warpName}", this.warpName));
			p.teleport(this.loc);
			p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 0.1f, 1);
			PlayerInteraction.removeItemFromInventory(p, item);
			this.cancel();
			return;
		} else {
			PlayerInteraction.sendTitleToPlayer(p, "", Main.getInstance().getConfig().getString("messages.teleport.time-left-notification").replace("&", "§").replace("{timeLeft}", "" + this.time).replace("{warpName}", this.warpName));
		}
		this.time--;
	}
	
	private boolean playerMoved() {
		if(lastLocation.getX() == p.getLocation().getX() && lastLocation.getY() == p.getLocation().getY() && lastLocation.getZ() == p.getLocation().getZ()) return false;
		else return true;
	}

}
