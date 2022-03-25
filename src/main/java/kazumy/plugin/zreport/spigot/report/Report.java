package kazumy.plugin.zreport.spigot.report;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.report.data.ReportData;
import kazumy.plugin.zreport.spigot.report.manager.ReportManager;
import kazumy.plugin.zreport.spigot.report.viewer.entity.Author;
import kazumy.plugin.zreport.spigot.report.viewer.entity.User;
import lombok.Data;
import lombok.val;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.function.Consumer;

@Data
public class Report {

    private User user;
    private Author author;
    private ReportData data;

    /**
     * Apenas para configuração dos atributos desta classe
     *
     * @param consumer
     */
    public void configure(Consumer<Report> consumer) {
        consumer.accept(this);
    }

    public Optional<Report> findReport(ReportManager reportManager) {
        return reportManager.getCurrentReport().stream().filter(report -> report.getUser().getName().equals(this.getUser().getName())).findAny();
    }

    public void enableReport(ReportManager reportManager) {
        reportManager.getCurrentReport().add(this);
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("zreport.seereport"))
                .forEach(player -> {
                    val alertMessage = MessageValue.get(MessageValue::reportAlertMessage);
                    alertMessage.replaceAll(message -> message.replace('&', '§')
                            .replace("%apelido", this.getUser().getName())
                            .replace("%autor", this.getAuthor().getName())
                            .replace("%motivo", this.getData().getReason())
                            .replace("%prova", this.getData().getEvidence())
                            .replace("%vezes", String.valueOf(this.getData().getAmount())));
                    alertMessage.forEach(player::sendMessage);
                    val component = new TextComponent("§aClique aqui para teleportar");
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teleport " + this.getUser().getName()));
                    player.spigot().sendMessage(component);
                });
    }

    /**
     * Valida o report, caso o denunciado seja punido por alguém.
     * Também recompensa o @Author para melhorar sua reputação
     *
     * @param executor para enviar o report ao banco de dados
     */
    public void validade(SQLExecutor executor) {
        executor.updateQuery("INSERT INTO denunciations(nickname, author, reason, evidence, server, date) VALUES(?, ?, ?, ?, ?, ?)", s -> {
            s.set(1, this.getUser().getName().toLowerCase());
            s.set(2, this.getAuthor().getName().toLowerCase());
            s.set(3, this.getData().getReason());
            s.set(4, this.getData().getEvidence());
            s.set(5, this.getData().getServer());
            s.set(6, this.getData().getDate());
        });
        this.getAuthor().rewardContributor(executor);
    }

    /**
     * Executado quando algum staff invalida o report,
     * ou quando o report é expirado pelo tempo.
     */
    public void invalidate() {
    }
}
