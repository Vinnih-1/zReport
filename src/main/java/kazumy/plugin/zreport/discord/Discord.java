package kazumy.plugin.zreport.discord;

import java.util.HashMap;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.report.Report;
import lombok.Data;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

@SuppressWarnings("deprecation")
@Data(staticConstructor = "of")
public class Discord {

	private final MainReport plugin;
	private JDA jda;

	public Discord start() {
		if (!plugin.getConfig().getBoolean("bot.habilitado"))
			return this;

		try {
			this.jda = JDABuilder.createDefault(plugin.getConfig().getString("bot.token")).build();
			jda.getPresence().setPresence(OnlineStatus.ONLINE,
					Activity.playing(plugin.getConfig().getString("bot.status")));
			plugin.log("§a[zReport] §fBot inicializado com sucesso");
		} catch (Exception e) {
			plugin.log("§c[zReport] §fAlgo deu errado na inicialização do seu bot!");
		}
		return this;
	}

	public void sendReport(Report report) {
		if (!plugin.getConfig().getBoolean("bot.habilitado"))
			return;

		val section = plugin.getConfig().getConfigurationSection("bot.embed");
		val replacement = new HashMap<String, String>();
		replacement.put("%apelido", report.getName());
		replacement.put("%autor", report.getViewer().getUser().getName());
		replacement.put("%motivo", report.getViewer().getReason());
		replacement.put("%prova", report.getViewer().getEvidence());
		replacement.put("%server", plugin.getConfig().getString("report.server"));
		replacement.put("%data", report.getViewer().getFormattedTime());

		val embed = new EmbedBuilder();

		embed.setTitle(section.getString("title"));
		embed.setThumbnail(section.getString("thumbnail").replace("%apelido", report.getName()));
		embed.setFooter(section.getString("footer"), section.getString("footer-image"));
		
		section.getConfigurationSection("builder").getKeys(false).forEach(s -> {
			val name = section.getString("builder." + s + ".name");
			val value = section.getString("builder." + s + ".value");
			embed.addField(replacement.containsKey(name) ? replacement.get(name) : name,
					replacement.containsKey(value) ? replacement.get(value) : value,
					section.getBoolean("builder." + s + ".inline"));

		});
		jda.getTextChannelById(plugin.getConfig().getString("bot.chat-id")).sendMessage(embed.build()).queue();
	}
}