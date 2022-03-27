package kazumy.plugin.zreport.spigot.report.menu;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.enums.Priority;
import kazumy.plugin.zreport.spigot.report.menu.itembuilder.ItemBuilder;
import org.bukkit.Material;

public class PriorityReportInventory extends SimpleInventory {

    private final Report report;

    public PriorityReportInventory(Report report) {
        super("zreport.priority.inventory", "§8Menu: Prioridade", 9 * 3);
        this.report = report;
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {
        editor.setItem(12, InventoryItem.of(
                new ItemBuilder(Material.getMaterial("STAINED_CLAY"), 1, (short)9)
                        .name(Priority.LOW.getName())
        ).defaultCallback(event -> {
            this.report.getData().setPriority(Priority.LOW);
            new BuildReportInventory(report).openInventory(event.getPlayer());
        }));

        editor.setItem(13, InventoryItem.of(
                new ItemBuilder(Material.getMaterial("STAINED_CLAY"), 1, (short)4)
                        .name(Priority.NORMAL.getName())
        ).defaultCallback(event -> {
            this.report.getData().setPriority(Priority.NORMAL);
            new BuildReportInventory(report).openInventory(event.getPlayer());
        }));

        editor.setItem(14, InventoryItem.of(
                new ItemBuilder(Material.getMaterial("STAINED_CLAY"), 1, (short)14)
                        .name(Priority.HIGHEST.getName())
        ).defaultCallback(event -> {
            this.report.getData().setPriority(Priority.HIGHEST);
            new BuildReportInventory(report).openInventory(event.getPlayer());
        }));
    }
}
