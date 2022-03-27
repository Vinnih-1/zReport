package kazumy.plugin.zreport.spigot.report.menu;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.report.manager.ReportManager;
import kazumy.plugin.zreport.spigot.report.menu.itembuilder.ItemBuilder;
import lombok.val;
import org.bukkit.Material;

import java.util.LinkedList;
import java.util.List;

public class ListReportInventory extends PagedInventory {

    public ListReportInventory() {
        super("zreport.reports.inventory", "§8Menu: Lista de Denúncias", 9 * 6);
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer pagedViewer) {
        val suppliers = new LinkedList<InventoryItemSupplier>();
        MainReport.getInstance().getReportManager().getCurrentReport().forEach(report -> {
            suppliers.add(() -> InventoryItem.of(
                    new ItemBuilder(Material.ENDER_PORTAL_FRAME)
                            .name("§eInformações da Denúncia")
                            .lore(
                                    "§fID: §7" + report.getData().getId(),
                                    "§fNome: §7" + report.getUser().getName(),
                                    "§fAutor: §7" + report.getAuthor().getName(),
                                    "",
                                    "§fMotivo: §7" + report.getData().getReason(),
                                    "§fProva: §7" + report.getData().getEvidence(),
                                    "",
                                    "§fPrioridade: " + report.getData().getPriority().getName(),
                                    "§fData: §e" + ReportManager.formattedDate(report.getData().getDate()),
                                    "",
                                    "§fVezes: §e" + report.getUser().getAllReportsEnabled(MainReport.getInstance().getReportManager()).size(),
                                    "§fServidor: §7" + report.getData().getServer(),
                                    "",
                                    "§aClique para Teleportar"
                            )
            ).defaultCallback(event -> {
                if (report.getUser().toPlayer() == null) {
                    event.getPlayer().sendMessage(MessageValue.get(MessageValue::offlinePlayer));
                    return;
                }
                report.getAuthor().toPlayer().teleport(report.getUser().toPlayer());
            }));
        });

        return suppliers;
    }
}
