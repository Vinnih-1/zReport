package kazumy.plugin.zreport.spigot.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@ConfigSection("mensagem-discord.embed")
@Getter @Accessors(fluent = true)
@ConfigFile("config.yml")
public class EmbedValue implements ConfigurationInjectable {

    @Getter private static final EmbedValue instance = new EmbedValue();

    @ConfigField("report") private ConfigurationSection reportEmbed;

    public static <T> T get(Function<EmbedValue, T> function) {
        return function.apply(instance);
    }
}
