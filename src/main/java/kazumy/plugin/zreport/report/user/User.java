package kazumy.plugin.zreport.report.user;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.henryfabio.sqlprovider.executor.SQLExecutor;

import kazumy.plugin.zreport.report.Report;
import kazumy.plugin.zreport.report.user.adapter.UserAdapter;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.val;

@Data
@ToString
@Builder(builderMethodName = "builder", builderClassName = "build")
public class User {
	
	private int amount;
	private String name;
	private List<Report> historic;
	
	public void recoveryUser(SQLExecutor executor) {
		val user = executor.resultOneQuery("SELECT * FROM zreport WHERE name = ?", u -> {
			u.set(1, name);
		}, UserAdapter.class);
		
		if (user == null) {
			this.amount = 0;
			this.historic = new ArrayList<>();
		} else {
			this.amount = user.amount;
			this.historic = user.historic;
		}
	}
	
	public void registryUser(SQLExecutor executor) {
	}
	
	public Player toPlayer() {
		return Bukkit.getPlayer(name);
	}
}