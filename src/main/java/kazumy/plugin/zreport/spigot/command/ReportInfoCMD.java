package kazumy.plugin.zreport.spigot.command;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.command.base.BaseCommand;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.report.menu.ProfileReportInventory;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class ReportInfoCMD extends BaseCommand {

    @Override
    @Command(name = "reportinfo", permission = "zreport.seereport", target = CommandTarget.PLAYER)
    public void execute(Context<CommandSender> context) {
        val sender = context.getSender();

        if (context.getArgs().length == 0) {
            sender.sendMessage("§c§lERRO §fUtilize o comando correto: §c/reportinfo [Jogador]");
            return;
        }
        val allReportsByName = MainReport.getInstance().getReportManager().getCurrentReport().stream()
                .filter(report -> report.getUser().getName().equals(context.getArg(0)))
                .collect(Collectors.toList());

        if (!allReportsByName.isEmpty())
            new ProfileReportInventory(allReportsByName.get(allReportsByName.size() - 1)).openInventory((Player) sender);
        else sender.sendMessage(MessageValue.get(MessageValue::reportNotFounded));
    }
}
