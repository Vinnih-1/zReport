package kazumy.plugin.zreport.spigot.command;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.command.base.BaseCommand;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.menu.ProfileReportInventory;
import kazumy.plugin.zreport.spigot.report.viewer.entity.Author;
import kazumy.plugin.zreport.spigot.report.viewer.entity.User;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.command.CommandSender;

public class ReportInfoCMD extends BaseCommand {

    @Override
    @Command(name = "reportinfo", permission = "zreport.inforeport", target = CommandTarget.PLAYER)
    public void execute(Context<CommandSender> context) {
        val sender = context.getSender();

        if (context.getArgs().length == 0) {
            sender.sendMessage("§c§lERRO §fUtilize o comando correto: §c/reportinfo [Jogador]");
            return;
        }
        val currentReport = new Report().configureReport(report -> {
            report.setUser(new User(context.getArg(0)));
            report.setAuthor(new Author(sender.getName()));
        }).findReport(MainReport.getInstance().getReportManager());

        if (currentReport.isPresent())
            new ProfileReportInventory(currentReport.get());
        else sender.sendMessage(MessageValue.get(MessageValue::reportNotFounded));
    }
}
