package kazumy.plugin.zreport.configuration;

import java.util.List;
import java.util.function.Function;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@TranslateColors
@ConfigSection("report")
@ConfigFile("config.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportValue implements ConfigurationInjectable {
	
	private static final ReportValue instance = new ReportValue();
	
	@ConfigField("timeout") private long timeout;
	@ConfigField("messages.alert") private List<String> reportAlert;
	@ConfigField("messages.reason") private List<String> reportReason;
	@ConfigField("messages.evidence") private List<String> evidenceMessage;
	
	public static <T> T get(Function<ReportValue, T> function) {
		return function.apply(instance);
	}
}
