package kazumy.plugin.zreport.report.viewer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import kazumy.plugin.zreport.report.ReportPriority;
import kazumy.plugin.zreport.report.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder(builderMethodName = "builder", builderClassName = "build")
public class Viewer {
	
	private ReportPriority priority;
	private String reason, evidence;
	private LocalTime time;
	private User user;
	
	// Fazer o menu de reports para linkar o reason lá
	// E fazer o botão para linkar o evidence e remover do GameCommand
	
	public String getFormattedTime() {
		return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
	}
	
	public Player toPlayer() {
		return Bukkit.getPlayer(user.getName());
	}
}
