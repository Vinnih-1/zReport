package kazumy.plugin.zreport.report;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.report.viewer.Viewer;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.val;

@Data
@ToString
@Builder(builderMethodName = "init", builderClassName = "create")
public class Report {
	
	private List<Viewer> viewers;
	private MainReport plugin;
	private LocalTime time;
	private Viewer viewer;
	private String name;
	private int amount;
	
	public void run(Stream<? extends Player> stream) {
		plugin.getStorage().writeReport(this);
		
		val alert = plugin.getConfig().getStringList("report.messages.alert");
		alert.replaceAll(a -> a.replace('&', '§')
				.replace("%apelido", name)
				.replace("%autor", viewer.getUser().getName())
				.replace("%motivo", viewer.getReason())
				.replace("%prova", viewer.getEvidence())
				.replace("%vezes", String.valueOf(amount)));
		val message = new StringBuilder();
		alert.forEach(a -> message.append(a).append("\n"));
		stream.forEach(p -> p.sendMessage(message.toString()));
		plugin.getDiscord().sendReport(this);
	}
}