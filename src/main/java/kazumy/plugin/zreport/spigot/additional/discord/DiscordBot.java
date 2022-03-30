package kazumy.plugin.zreport.spigot.additional.discord;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.DiscordValue;
import kazumy.plugin.zreport.spigot.configuration.EmbedValue;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.manager.ReportManager;
import lombok.Data;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;

import javax.security.auth.login.LoginException;
import java.awt.*;

@Data(staticConstructor = "of")
public class DiscordBot {

    private final MainReport plugin;
    private JDA discordBot;

    public DiscordBot initialize() {
        if (!DiscordValue.get(DiscordValue::enabled)) return this;

        try {
            this.discordBot = JDABuilder.createDefault(DiscordValue.get(DiscordValue::token))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();
            this.discordBot.getPresence().setPresence(OnlineStatus.ONLINE,
                    Activity.playing(DiscordValue.get(DiscordValue::status)));

            Bukkit.getConsoleSender().sendMessage("§a[zReport] §fBot §e" + this.discordBot.getSelfUser().getId()
                    + " §finicializado e funcionando corretamente.");
        } catch (LoginException e) {
            Bukkit.getConsoleSender().sendMessage("§c[zReport] §fOcorreu um erro ao inicializar seu bot");
        }
        return this;
    }

    public void discordNotify(Report report) {
        val reportSection = EmbedValue.get(EmbedValue::reportEmbed);
        val reportEmbed = new EmbedBuilder()
                .setColor(report.getData().getPriority().getColor())
                .setTitle(reportSection.getString("title"))
                .setThumbnail(reportSection.getString("thumbnail").replace("%apelido", report.getUser().getName()))
                .setFooter(reportSection.getString("footer"), reportSection.getString("footer-image"));

        reportSection.getConfigurationSection("builder").getKeys(false)
                .forEach(builder -> {
                    val value = reportSection.getString("builder." + builder + ".value")
                            .replace("%id", report.getData().getId())
                            .replace("%apelido", report.getUser().getName())
                            .replace("%autor", report.getAuthor().getName())
                            .replace("%motivo", report.getData().getReason())
                            .replace("%prioridade", report.getData().getPriority().toString())
                            .replace("%prova", report.getData().getEvidence())
                            .replace("%server", report.getData().getServer())
                            .replace("%data", ReportManager.formattedDate(report.getData().getDate()));
                    val name = reportSection.getString("builder." + builder + ".name");
                    val inline = reportSection.getBoolean("builder." + builder + ".inline");
                    reportEmbed.addField(name, value, inline);
                });
        System.out.println(DiscordValue.get(DiscordValue::chatId));
        this.discordBot.getTextChannelById(DiscordValue.get(DiscordValue::chatId)).sendMessage(reportEmbed.build()).queue();
    }
}
