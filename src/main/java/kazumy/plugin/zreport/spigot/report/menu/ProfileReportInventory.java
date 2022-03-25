package kazumy.plugin.zreport.spigot.report.menu;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import kazumy.plugin.zreport.spigot.report.Report;

public class ProfileReportInventory extends SimpleInventory {

    public ProfileReportInventory() {
        super("zreport.profile.report", "§8Menu: Informações do Report", 9 * 5);
    }

    public ProfileReportInventory(Report report) {
        super("zreport.profile.report", "§8Menu: Informações do Report", 9 * 5);
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {

    }
}
