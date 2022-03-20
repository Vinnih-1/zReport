package kazumy.plugin.zreport.spigot.additional.discord;

import kazumy.plugin.zreport.spigot.MainReport;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

@Data(staticConstructor = "of")
public class DiscordBot {

    @Getter(AccessLevel.NONE) private String chatId;

    private final MainReport plugin;
    private JDA discordBot;

    public DiscordBot initialize() {
        val config = plugin.getConfig().getConfigurationSection("bot");
        if (!config.getBoolean("enabled")) return this;

        try {
            this.discordBot = JDABuilder.createDefault(plugin.getConfig().getString("token"))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();
            this.discordBot.getPresence().setPresence(OnlineStatus.ONLINE,
                    Activity.playing(plugin.getConfig().getString("status")));

            this.chatId = config.getString("chat-id");

            plugin.getLogger().info("§a[zReport] §fBot §e" + this.discordBot.getSelfUser().getId()
                    + " §finicializado e funcionando corretamente.");
        } catch (LoginException e) {
            plugin.getLogger().info("§c[zReport] §fOcorreu um erro ao inicializar seu bot");
        }
        return this;
    }
}
