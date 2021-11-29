package kazumy.plugin.zreport.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.report.event.ProofEvent;
import kazumy.plugin.zreport.report.event.SubmitEvent;
import lombok.val;

public class EventListener implements Listener {
	
	private final MainReport plugin;
	
	public EventListener(MainReport plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		val player = event.getWhoClicked().getName();
		val pendings = plugin.getStorage().getReportPending();


	}
	
	@EventHandler
	public void onChatUseEvent(AsyncPlayerChatEvent event) {
		val player = event.getPlayer().getName();
		val pendings = plugin.getStorage().getReportPending();
		
		if (pendings.containsKey(player))
			new ProofEvent(pendings.get(player)).event(event);
	}
}
