package kazumy.plugin.zreport.inventories.utils;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import lombok.val;

public class ItemBuilder extends ItemStack {

	public ItemBuilder(Material material) {
		super(material);
	}
	
	public ItemBuilder(Material material, int amount, short damage) {
		super(material, amount, damage);
	}
	
	public ItemBuilder name(String name) {
		val meta = this.getItemMeta();
		meta.setDisplayName(name);
		this.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder owner(String owner) {
		val meta = (SkullMeta) this.getItemMeta();
		meta.setOwner(owner);
		this.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder lore(String... lore) {
		val meta = this.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		this.setItemMeta(meta);
		return this;
	}
}
