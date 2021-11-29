package kazumy.plugin.zreport.inventories;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.inventories.base.BaseInventory;
import kazumy.plugin.zreport.inventories.utils.ItemBuilder;
import lombok.Data;
import lombok.val;

@SuppressWarnings("all")
@Data(staticConstructor = "of")
public class ReasonInventory implements BaseInventory {
	
	private final MainReport plugin;

	@Override
	public void onClick(InventoryClickEvent event) {	
	}

	@Override
	public Inventory buildInventory() {
		val inventory = Bukkit.createInventory(null, 9 * 6, "§8Menu: Motivos");
		val reason = plugin.getConfig().getStringList("report.messages.reason"); 
		val allowed = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53,54);
		
		reason.stream().map(r -> r.replace('&', '§'))
			.forEach(r -> {
				val slot = inventory.first(Material.AIR.getId()); 
				if (!allowed.contains(slot))
					inventory.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (short)3).name("§7" + r));
			});
		return inventory;
	}
}
