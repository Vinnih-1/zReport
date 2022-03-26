package kazumy.plugin.zreport.spigot.report;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.report.data.ReportData;
import kazumy.plugin.zreport.spigot.report.manager.ReportManager;
import kazumy.plugin.zreport.spigot.report.viewer.entity.Author;
import kazumy.plugin.zreport.spigot.report.viewer.entity.User;
import lombok.Data;
import lombok.val;
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
    public Report configureReport(Consumer<Report> consumer) {
        consumer.accept(this);
        return this;
    }

    public Optional<Report> findReport(ReportManager reportManager) {
        return reportManager.getCurrentReport().stream().filter(report -> report.getUser().getName().equals(this.getUser().getName())).findAny();
    }

    public void enableReport(ReportManager reportManager) {
        reportManager.getCurrentReport().add(this);
        this.sendToDatabase(MainReport.getInstance().getExecutor());
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("zreport.seereport"))
                .forEach(player -> {
                    val alertMessage = MessageValue.get(MessageValue::reportAlertMessage);
                    alertMessage.replaceAll(message -> message.replace('&', '§')
                            .replace("%apelido", this.getUser().getName())
                            .replace("%autor", this.getAuthor().getName())
                            .replace("%motivo", this.getData().getReason())
                            .replace("%prova", this.getData().getEvidence())
                            .replace("%vezes", String.valueOf(reportManager.getAllReportsByName(this.getUser().getName()).size())));
                    alertMessage.forEach(message -> player.sendMessage(message));
//                    val component = new TextComponent("§aClique aqui para teleportar");
//                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teleport " + this.getUser().getName()));
//                    player.spigot().sendMessage(component);
                });
    }

    /**
     * Valida o report, caso o denunciado seja punido por alguém.
     * Também recompensa o @Author para melhorar sua reputação
     *
     * @param executor para enviar o report ao banco de dados
     */
    public void validadeReport(SQLExecutor executor) {
        this.approveReportInDatabase(executor, MainReport.getInstance().getReportManager());
        this.getAuthor().rewardContributor(executor);
    }

    /**
     * Executado quando algum staff invalida o report,
     * ou quando o report é expirado pelo tempo.
     */
    public void invalidateReport(ReportManager reportManager) {
        reportManager.getAllReportsByName(this.getUser().getName()).forEach(reportManager.getCurrentReport()::remove);
    }

    public void approveReportInDatabase(SQLExecutor executor, ReportManager reportManager) {
        reportManager.getAllReportsByName(this.getUser().getName()).forEach(report -> {
            executor.updateQuery("UPDATE denunciations SET approved = ? WHERE nickname = ?", s -> {
                s.set(1, this.getData().isApproved());
                s.set(2, this.getUser().getName().toLowerCase());
            });
        });
    }

    /**
     * Envia o objeto para o banco de dados
     *
     * @param executor
     */
    public void sendToDatabase(SQLExecutor executor) {
        executor.updateQuery("INSERT INTO denunciations(nickname, author, reason, evidence, server, type, approved, date) VALUES(?, ?, ?, ?, ?, ?)", s -> {
            s.set(1, this.getUser().getName().toLowerCase());
            s.set(2, this.getAuthor().getName().toLowerCase());
            s.set(3, this.getData().getReason());
            s.set(4, this.getData().getEvidence());
            s.set(5, this.getData().getServer());
            s.set(6, this.getData().getReportType().toString());
            s.set(7, this.getData().isApproved());
            s.set(8, this.getData().getDate());
        });
    }
}
