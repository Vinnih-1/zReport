package kazumy.plugin.zreport.report.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.inventories.PriorityInventory;
import kazumy.plugin.zreport.inventories.ReasonInventory;
import kazumy.plugin.zreport.report.Report;
import kazumy.plugin.zreport.report.event.base.ReportEvent;
import lombok.Setter;
import lombok.val;

public class SubmitEvent extends ReportEvent<InventoryClickEvent> {

	@Setter private MainReport plugin;
	
	public SubmitEvent(Report report) {
		super(report);
	}

	@Override
	public void event(InventoryClickEvent event) {
		event.setCancelled(true);
		val player = (Player) event.getWhoClicked();
		val item = event.getCurrentItem();

		if (item.getType() == Material.ANVIL) {
			plugin.getStorage().getReportPending().put(player.getName(), this.getReport());
			val message = new StringBuilder();
			plugin.getConfig().getStringList("report.messages.evidence")
				.forEach(e -> message.append(e.replace('&', '§')).append("\n"));
			player.sendMessage(message.toString());
			player.closeInventory();
		}
		
		if (item.getType() == Material.BEACON) {
			player.openInventory(ReasonInventory.of(plugin).build());
		}
		
		if (item.getType() == Material.BOOK) {
			player.openInventory(PriorityInventory.of(plugin).build());
		}
		
		if (item.getType() == Material.SKULL_ITEM) {
		}
	}
}
