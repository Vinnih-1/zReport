package kazumy.plugin.zreport.spigot.report.menu;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import kazumy.plugin.zreport.spigot.configuration.ReportValue;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.menu.itembuilder.ItemBuilder;
import lombok.val;
import org.bukkit.Material;

import java.util.LinkedList;
import java.util.List;

public class ReasonReportInventory extends PagedInventory {

    private final Report report;

    public ReasonReportInventory(Report report) {
        super("zreport.reason.inventory", "§8Menu: Motivos", 9 * 6);
        this.report = report;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer pagedViewer) {
        val suppliers = new LinkedList<InventoryItemSupplier>();
        ReportValue.get(ReportValue::reasons).forEach(reason -> {
            suppliers.add(() -> InventoryItem.of(
                    new ItemBuilder(Material.PAPER)
                            .name("§a" + reason)
            ).defaultCallback(event -> {
                this.report.getData().setReason(reason);
                new BuildReportInventory(report).openInventory(event.getPlayer());
            }));
        });

        return suppliers;
    }
}
