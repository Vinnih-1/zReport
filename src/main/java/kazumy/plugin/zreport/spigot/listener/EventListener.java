package kazumy.plugin.zreport.spigot.listener;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.report.viewer.entity.Author;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventListener implements Listener {

    private final MainReport plugin;

    public EventListener(MainReport plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatEvent(AsyncPlayerChatEvent event) {
        val author = new Author(event.getPlayer().getName());
        val pendingReport = plugin.getReportManager().getWaitingEvidence().get(author);

        if (pendingReport != null) {
            event.setCancelled(true);
            pendingReport.setEvidenceToReport(event.getMessage());
            plugin.getReportManager().getWaitingEvidence().remove(author);
        }
    }
}
