package kazumy.plugin.zreport.spigot.configuration.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.DiscordValue;
import kazumy.plugin.zreport.spigot.configuration.EmbedValue;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.configuration.ReportValue;
import lombok.Data;

@Data(staticConstructor = "of")
public class ConfigurationRegistry {

    private final MainReport plugin;

    public void register() {
        new BukkitConfigurationInjector(plugin).injectConfiguration(
                MessageValue.instance(),
                ReportValue.instance(),
                DiscordValue.instance(),
                EmbedValue.instance()
        );
    }
}
