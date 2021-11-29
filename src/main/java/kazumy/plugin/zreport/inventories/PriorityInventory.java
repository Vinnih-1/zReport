package kazumy.plugin.zreport.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.inventories.base.BaseInventory;
import kazumy.plugin.zreport.inventories.utils.ItemBuilder;
import lombok.Data;
import lombok.val;

@Data(staticConstructor = "of")
public class PriorityInventory implements BaseInventory {
	
	private final MainReport plugin;

	@Override
	public void onClick(InventoryClickEvent event) {
	}

	@Override
	public Inventory buildInventory() {
		val inventory = Bukkit.createInventory(null, 9 * 3, "§8Menu: Prioridade");
		inventory.setItem(12, new ItemBuilder(Material.BOOK).name("§fPrioridade: §7§lBAIXA"));
		inventory.setItem(13, new ItemBuilder(Material.BOOK).name("§fPrioridade: §e§lMÉDIA"));
		inventory.setItem(14, new ItemBuilder(Material.BOOK).name("§fPrioridade: §c§lALTA"));
		
		return inventory;
	}
}
