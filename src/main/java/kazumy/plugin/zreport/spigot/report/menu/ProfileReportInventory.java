package kazumy.plugin.zreport.spigot.report.menu;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.menu.itembuilder.ItemBuilder;
import org.bukkit.Material;

public class ProfileReportInventory extends SimpleInventory {

    private final Report report;

    public ProfileReportInventory(Report report) {
        super("zreport.profile.report", "§8Menu: Informações do Report", 9 * 5);
        this.report = report;
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {
        editor.setItem(4, InventoryItem.of(
                new ItemBuilder(Material.SKULL_ITEM, 1, (short)3)
                        .name(String.format("§aPerfil de %s", report.getUser().getName()))
                        .lore("", "§eClique para mais Informações")
        ).defaultCallback(event -> {

        }));

        editor.setItem(20, InventoryItem.of(
                new ItemBuilder(Material.getMaterial("STAINED_CLAY"), 1, (short)5)
                        .name("§aClique para Teleportar")
        ).defaultCallback(event  -> {

        }));

        editor.setItem(22, InventoryItem.of(
                new ItemBuilder(Material.ENDER_PORTAL_FRAME)
                        .name("§eInformações da Denúncia")
                        .lore(
                                "§fID: §7" + report.getData().getId(),
                                "§fNome: §7" + report.getUser().getName(),
                                "§fAutor: §7" + report.getAuthor().getName(),
                                "",
                                "§fMotivo: §7" + report.getData().getReason(),
                                "§fPrioridade: " + report.getData().getPriority().getName(),
                                "",
                                "§fVezes: §e" + MainReport.getInstance().getReportManager().getAllReportsByName(this.report.getUser().getName()).size(),
                                "§fServidor: §7" + report.getData().getServer()
                        )
        ));

        editor.setItem(24, InventoryItem.of(
                new ItemBuilder(Material.getMaterial("STAINED_CLAY"), 1, (short)14)
                        .name("§cClique para Cancelar")
        ).defaultCallback(event  -> {
            report.invalidateReport(MainReport.getInstance().getReportManager());
            event.getPlayer().sendMessage(MessageValue.get(MessageValue::deniedReport));
        }));
    }
}
