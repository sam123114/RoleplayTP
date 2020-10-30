package ca.wartog.roleplaytp.utils;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

	private ItemStack is;
	   
    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }
   
    @SuppressWarnings("deprecation")
	public ItemBuilder(Material m, int amount, short meta){
        is = new ItemStack(m, amount, meta);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    @SuppressWarnings("deprecation")
	public ItemBuilder setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setSkullOwner(Player owner) {
        try {
            SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setOwningPlayer(owner);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    @SuppressWarnings("deprecation")
	public ItemBuilder setInfinityDurability() {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder addItemFlags(ItemFlag... flags) {
    	ItemMeta im = is.getItemMeta();
    	im.addItemFlags(flags);
    	is.setItemMeta(im);
    	return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }
    
    public ItemBuilder setCustomModelData(int data) {
    	ItemMeta im = is.getItemMeta();
    	im.setCustomModelData(data);
    	is.setItemMeta(im);
    	return this;
    }
    
    public int getCustomModelData() {
    	ItemMeta im = is.getItemMeta();
    	return im.getCustomModelData();
    }

    public ItemStack toItemStack() {
        return is;
    }
	
}
