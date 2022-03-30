package kazumy.plugin.zreport.spigot.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.function.Function;

@ConfigSection("bot")
@Getter @Accessors(fluent = true)
@ConfigFile("config.yml")
public class DiscordValue implements ConfigurationInjectable {

    @Getter private static final DiscordValue instance = new DiscordValue();

    @ConfigField("token") private String token;
    @ConfigField("status") private String status;
    @ConfigField("chat-id") private String chatId;
    @ConfigField("enabled") private boolean enabled;

    public static <T> T get(Function<DiscordValue, T> function) {
        return function.apply(instance);
    }
}
