package kazumy.plugin.zreport.spigot.command;

import kazumy.plugin.zreport.spigot.command.base.BaseCommand;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.menu.ListReportInventory;
import kazumy.plugin.zreport.spigot.report.menu.PlayerReportInventory;
import kazumy.plugin.zreport.spigot.report.viewer.entity.User;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportsCMD extends BaseCommand {

    @Override
    @Command(name = "reports", permission = "zreport.seereport", target = CommandTarget.PLAYER, async = true)
    public void execute(Context<CommandSender> context) {
        val sender = context.getSender();

        if (context.getArgs().length == 0) {
            new ListReportInventory().openInventory((Player) sender);
            return;
        }
        new PlayerReportInventory(new Report().configureReport(report -> report.setUser(new User(context.getArg(0))))).openInventory((Player) sender);
    }
}