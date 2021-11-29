package kazumy.plugin.zreport.report.event;

import org.bukkit.event.inventory.InventoryClickEvent;

import kazumy.plugin.zreport.inventories.ReportBuildInventory;
import kazumy.plugin.zreport.report.Report;
import kazumy.plugin.zreport.report.ReportPriority;
import kazumy.plugin.zreport.report.event.base.ReportEvent;
import lombok.val;

public class PriorityEvent extends ReportEvent<InventoryClickEvent> {

	public PriorityEvent(Report report) {
		super(report);
	}

	@Override
	public void event(InventoryClickEvent event) {
		val item = event.getCurrentItem();
		
		if (item.getItemMeta().getDisplayName().contains("BAIXA"))
			this.getReport().getViewer().setPriority(ReportPriority.BAIXA);
		if (item.getItemMeta().getDisplayName().contains("MÉDIA"))
			this.getReport().getViewer().setPriority(ReportPriority.MEDIA);
		if (item.getItemMeta().getDisplayName().contains("ALTA"))
			this.getReport().getViewer().setPriority(ReportPriority.ALTA);
		event.getWhoClicked().openInventory(ReportBuildInventory.of(this.getReport()).buildInventory());
	}
}
