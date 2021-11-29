package kazumy.plugin.zreport.report.event;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.report.Report;
import kazumy.plugin.zreport.report.event.base.ReportEvent;
import lombok.Setter;

public class ProofEvent extends ReportEvent<AsyncPlayerChatEvent> {

	@Setter private MainReport plugin;
	
	public ProofEvent(Report report) {
		super(report);
	}

	@Override
	public void event(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		if (event.getMessage().equalsIgnoreCase("nenhuma")) this.getReport().getViewer().setEvidence("Nenhuma");
		else this.getReport().getViewer().setEvidence(event.getMessage());
		
		this.getReport().run(Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("zreport.view")));
		plugin.getStorage().getReportPending().remove(event.getPlayer().getName());
	}
}
