package kazumy.plugin.zreport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.executor.SQLExecutor;

import kazumy.plugin.zreport.commands.GameCommand;
import kazumy.plugin.zreport.database.SQLProvider;
import kazumy.plugin.zreport.discord.Discord;
import kazumy.plugin.zreport.listener.EventListener;
import kazumy.plugin.zreport.report.storage.ReportStorage;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;

@Getter
public class MainReport extends JavaPlugin {

	private SQLConnector sqlConnector;
	private SQLExecutor sqlExecutor;
	
	private ReportStorage storage;
	private Discord discord;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		sqlConnector = SQLProvider.of(this).connect();
		sqlExecutor = new SQLExecutor(sqlConnector);
		SQLProvider.setup(sqlExecutor);
		
		new EventListener(this);
		new GameCommand(this).registerCommand(new BukkitFrame(this));
		
		storage = new ReportStorage(this).runReportTask();
		discord = Discord.of(this).start();
	}
	
	public void log(String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}
}
