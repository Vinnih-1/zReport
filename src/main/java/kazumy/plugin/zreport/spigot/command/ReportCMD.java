package kazumy.plugin.zreport.spigot.command;

import kazumy.plugin.zreport.spigot.command.base.BaseCommand;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.report.type.ReportType;
import lombok.val;
import lombok.var;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.command.CommandSender;

public class ReportCMD extends BaseCommand {

    @Override
    @Command(name = "report", target = CommandTarget.PLAYER, async = true)
    public void execute(Context<CommandSender> context) {
        val sender = context.getSender();
        var reportType = ReportType.MENU_REPORT;

        if (context.getArgs().length == 0) {
            sender.sendMessage("§c§lERRO §fUtilize o comando correto: §c/report [Jogador] [Motivo]");
            return;
        }
        if (context.getArgs().length == 1 && !sender.hasPermission(ReportType.FAST_REPORT.getPermission())) {
            sender.sendMessage(MessageValue.get(MessageValue::withoutPermission));
            return;
        } else {
            reportType = ReportType.FAST_REPORT;

        }

    }
}
