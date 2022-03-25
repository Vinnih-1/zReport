package kazumy.plugin.zreport.spigot.configuration.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import lombok.Data;

@Data(staticConstructor = "of")
public class ConfigurationRegistry {

    private final MainReport plugin;

    public void register() {
        new BukkitConfigurationInjector(plugin).injectConfiguration(MessageValue.instance());
    }
}
