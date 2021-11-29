package kazumy.plugin.zreport.report.event;

import org.bukkit.event.inventory.InventoryClickEvent;

import kazumy.plugin.zreport.inventories.ReportBuildInventory;
import kazumy.plugin.zreport.report.Report;
import kazumy.plugin.zreport.report.event.base.ReportEvent;
import lombok.val;

public class ReasonEvent extends ReportEvent<InventoryClickEvent> {

	public ReasonEvent(Report report) {
		super(report);
	}

	@Override
	public void event(InventoryClickEvent event) {
		val item = event.getCurrentItem();
		this.getReport().getViewer().setReason(item.getItemMeta().getDisplayName().replaceFirst("§7", ""));
		event.getWhoClicked().openInventory(ReportBuildInventory.of(this.getReport()).buildInventory());
	}
}
