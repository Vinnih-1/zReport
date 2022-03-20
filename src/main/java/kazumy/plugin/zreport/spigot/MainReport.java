package kazumy.plugin.zreport.spigot;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import kazumy.plugin.zreport.spigot.additional.dependencies.manager.DependenciesManager;
import kazumy.plugin.zreport.spigot.additional.discord.DiscordBot;
import kazumy.plugin.zreport.spigot.database.SQLProvider;
import kazumy.plugin.zreport.spigot.license.License;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class MainReport extends JavaPlugin {

    @Getter private static MainReport instance;

    private SQLExecutor executor;
    private DiscordBot discord;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§6███████╗██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗");
        Bukkit.getConsoleSender().sendMessage("§6╚══███╔╝██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝");
        Bukkit.getConsoleSender().sendMessage("§6  ███╔╝ ██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   ");
        Bukkit.getConsoleSender().sendMessage("§6███████╗██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   ");

        instance = this;

        if (!new License(getConfig().getString("license")).check()) {
            Bukkit.shutdown();
            return;
        }
        val dependenciesManager = DependenciesManager.of(this, new File("./plugins/zReport/dependencies"));
        dependenciesManager.missingAny().ifPresent(dependencies -> {
            dependenciesManager.downloadTo(dependencies.getName(), dependencies.getUrl());
        });

        this.executor = SQLProvider.of(this).createDefaults();
        this.discord = DiscordBot.of(this).initialize();


    }
}
