package kazumy.plugin.zreport.spigot.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.function.Function;

@TranslateColors
@Getter @Accessors(fluent = true)
@ConfigSection("report")
@ConfigFile("config.yml")
public class ReportValue {

    @Getter private static final ReportValue instance = new ReportValue();

    @ConfigField("server") private String server;
    @ConfigField("timeout") private int timeout;
    @ConfigField("punicao-preventiva") private ConfigurationSection preventivePunishment;
    @ConfigField("motivos") private List<String> reasons;

    public static <T> T get(Function<ReportValue, T> function) {
        return function.apply(instance);
    }
}
