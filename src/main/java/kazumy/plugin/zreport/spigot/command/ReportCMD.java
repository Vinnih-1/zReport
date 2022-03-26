package kazumy.plugin.zreport.spigot.command;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.command.base.BaseCommand;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.configuration.ReportValue;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.data.ReportData;
import kazumy.plugin.zreport.spigot.report.enums.Priority;
import kazumy.plugin.zreport.spigot.report.enums.ReportType;
import kazumy.plugin.zreport.spigot.report.menu.BuildReportInventory;
import kazumy.plugin.zreport.spigot.report.viewer.entity.Author;
import kazumy.plugin.zreport.spigot.report.viewer.entity.User;
import lombok.val;
import lombok.var;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCMD extends BaseCommand {

    @Override
    @Command(name = "report", target = CommandTarget.PLAYER, async = true)
    public void execute(Context<CommandSender> context) {
        val sender = context.getSender();

        if (context.getArgs().length == 0) {
            sender.sendMessage("§c§lERRO §fUtilize o comando correto: §c/report [Jogador] [Motivo]");
            return;
        }
        val currentReport = new Report().configureReport(report -> {
            report.setAuthor(new Author(sender.getName()));
            report.setUser(new User(context.getArg(0)));
        });
        if (currentReport.getUser().toPlayer() == null) {
            sender.sendMessage(MessageValue.get(MessageValue::offlinePlayer));
            return;
        }
        val reportManager = MainReport.getInstance().getReportManager();
        // Não esquecer de bloquear o report em si mesmo

        if (context.getArgs().length >= 2) {
            if (!sender.hasPermission(ReportType.FAST_REPORT.getPermission())) {
                sender.sendMessage(MessageValue.get(MessageValue::withoutPermission));
                return;
            }
            val reason = new StringBuilder(String.join(" ", context.getArgs()));
            var id = RandomStringUtils.randomAlphabetic(8);

            while (reportManager.getReportById(id) != null) {
                id = RandomStringUtils.randomAlphabetic(8);
            }

            currentReport.setData(ReportData.builder()
                            .id(id)
                            .reason(reason.delete(0, context.getArg(0).length() + 1).toString())
                            .priority(Priority.NORMAL)
                            .reportType(ReportType.FAST_REPORT)
                            .approved(false)
                    .build());
            currentReport.waitEvidence(reportManager);
            MessageValue.get(MessageValue::evidenceMessage).forEach(message -> sender.sendMessage(message));
            return;
        }
        new BuildReportInventory(currentReport).openInventory((Player) sender);
    }
}
