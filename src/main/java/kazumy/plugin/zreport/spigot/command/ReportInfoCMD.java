package kazumy.plugin.zreport.spigot.command;

import kazumy.plugin.zreport.spigot.command.base.BaseCommand;
import kazumy.plugin.zreport.spigot.report.menu.ProfileReportInventory;
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


        val profileReportInventory = new ProfileReportInventory();

    }
}
