package kazumy.plugin.zreport.inventories.base;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;


public interface BaseInventory {

	public void onClick(InventoryClickEvent event);
	
	public Inventory buildInventory();	
}
