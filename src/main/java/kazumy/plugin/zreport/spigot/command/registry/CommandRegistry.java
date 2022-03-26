package kazumy.plugin.zreport.spigot.command.registry;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.command.ReportCMD;
import kazumy.plugin.zreport.spigot.command.ReportInfoCMD;
import lombok.Data;
import lombok.val;
import me.saiintbrisson.bukkit.command.BukkitFrame;

@Data(staticConstructor = "of")
public class CommandRegistry {

    private final MainReport plugin;

    public void register() {
        val frame = new BukkitFrame(plugin);
        frame.registerCommands(new ReportCMD(), new ReportInfoCMD());
    }
}
