package kazumy.plugin.zreport.commands;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.inventories.RepointInventory;
import kazumy.plugin.zreport.inventories.ReportBuildInventory;
import kazumy.plugin.zreport.report.Report;
import kazumy.plugin.zreport.report.ReportPriority;
import kazumy.plugin.zreport.report.user.User;
import kazumy.plugin.zreport.report.viewer.Viewer;
import lombok.AllArgsConstructor;
import lombok.val;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.message.MessageType;
import me.saiintbrisson.minecraft.command.target.CommandTarget;

@AllArgsConstructor
public class GameCommand {
	
	private final MainReport plugin;
	
	@Command(name = "report", aliases = {"denunciar", "reportar"}, 
			usage = "§cUtilize /report [Jogador]",
			target = CommandTarget.PLAYER, async = true)
	public void reportCommand(Context<CommandSender> context) {
		val user = User.builder().name(context.getSender().getName()).build();
		user.recoveryUser(plugin.getSqlExecutor());
		val viewer = Viewer.builder().user(user).evidence("Nenhuma")
			.priority(ReportPriority.BAIXA)
			.time(LocalTime.now(ZoneId.of("America/Sao_Paulo")))
			.build();
		val report = Report.init().name(context.getArg(0))
			.viewer(viewer).plugin(plugin).amount(1)
			.viewers(new ArrayList<Viewer>())
			.time(viewer.getTime())
			.build();
		user.toPlayer().openInventory(ReportBuildInventory.of(report).build());
	}
	
	@Command(name = "reports", aliases = "denuncias",
			target = CommandTarget.PLAYER)
	public void reportsCommand(Context<CommandSender> context) {
	}
	
	@Command(name = "repoint", aliases = "repoints", 
			target = CommandTarget.PLAYER,
			async = true)
	public void repointsCommand(Context<CommandSender> context) {
		val user = User.builder().name(context.getSender().getName()).build();
		user.recoveryUser(plugin.getSqlExecutor());
		user.toPlayer().openInventory(RepointInventory.of(user).build(user.toPlayer()));
	}
	
	public void registerCommand(BukkitFrame frame) {
		frame.registerCommands(this);
		frame.getMessageHolder().setMessage(MessageType.NO_PERMISSION, "Você não tem permissão para isto.");
	}
}
