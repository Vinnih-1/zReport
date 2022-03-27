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

public class BuildReportInventory extends SimpleInventory {

    private final Report report;

    public BuildReportInventory(Report report) {
        super("zreport.build.report", "§8Menu: Denunciar", 9 * 5);
        this.report = report;
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {
        editor.setItem(4, InventoryItem.of(
                new ItemBuilder(Material.SKULL_ITEM, 1, (short)3)
                        .name(String.format("§aPerfil de %s", report.getUser().getName()))
                        .owner(this.report.getUser().getName())
                        .lore("", "§eClique para mais Informações")
        ).defaultCallback(event -> {
            new PlayerReportInventory(report).openInventory(event.getPlayer());
        }));

        editor.setItem(19, InventoryItem.of(
                new ItemBuilder(Material.BOOK)
                        .name("§aSelecionar motivo da Denúncia")
        ).defaultCallback(event  -> {
            new ReasonReportInventory(report).openInventory(event.getPlayer());
        }));

        editor.setItem(20, InventoryItem.of(
                new ItemBuilder(Material.FEATHER)
                        .name("§aSelecionar prioridade da Denúncia")
        ).defaultCallback(event  -> {
            new PriorityReportInventory(report).openInventory(event.getPlayer());
        }));

        editor.setItem(22, InventoryItem.of(
                new ItemBuilder(Material.ENDER_PORTAL_FRAME)
                        .name("§eInformações da Denúncia")
                        .lore(
                                "§fID: §7" + report.getData().getId(),
                                "§fNome: §7" + report.getUser().getName(),
                                "§fAutor: §7" + report.getAuthor().getName(),
                                "",
                                "§fMotivo: §7" + (report.getData().getReason() == null ? "Não Especificado" : report.getData().getReason()),
                                "",
                                "§fPrioridade: " + report.getData().getPriority().getName(),
                                "§fServidor: §7" + report.getData().getServer(),
                                "",
                                "§eSua denúncia ficará assim para nós"
                        )
        ));

        if (this.report.getData().getReason() != null) {
            editor.setItem(24, InventoryItem.of(
                    new ItemBuilder(Material.getMaterial("STAINED_CLAY"), 1, (short)5)
                            .name("§aEnviar sua Denúncia")
            ).defaultCallback(event  -> {
                event.getPlayer().closeInventory();
                this.report.waitEvidence(MainReport.getInstance().getReportManager());
                MessageValue.get(MessageValue::evidenceMessage).forEach(message -> event.getPlayer().sendMessage(message));
            }));
        }

        editor.setItem(25, InventoryItem.of(
                new ItemBuilder(Material.getMaterial("STAINED_CLAY"), 1, (short)14)
                        .name("§cClique para Cancelar")
        ).defaultCallback(event  -> event.getPlayer().closeInventory()));
    }
}
