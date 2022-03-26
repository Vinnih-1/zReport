package kazumy.plugin.zreport.spigot.report;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.configuration.ReportValue;
import kazumy.plugin.zreport.spigot.report.data.ReportData;
import kazumy.plugin.zreport.spigot.report.manager.ReportManager;
import kazumy.plugin.zreport.spigot.report.viewer.entity.Author;
import kazumy.plugin.zreport.spigot.report.viewer.entity.User;
import lombok.Data;
import lombok.val;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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

    /**
     * Linka o Author da denúncia com a denúncia em um HashMap,
     * para setagem da evidência posteriormente no evento de chat
     *
     * @param reportManager Instância da classe ReportManager
     * @return A instância desta classe
     */
    public Report waitEvidence(ReportManager reportManager) {
        reportManager.getWaitingEvidence().put(this.getAuthor(), this);
        return this;
    }

    public Optional<Report> findReport(ReportManager reportManager) {
        return reportManager.getCurrentReport().stream().filter(report -> report.getUser().getName().equals(this.getUser().getName())).findFirst();
    }

    /**
     * Deixa a denúncia pendente para fiscalização da equipe responsável,
     * e alerta todos que estiverem presente
     *
     * @param reportManager Instância da classe ReportManager
     */
    public void enableReport(ReportManager reportManager) {
        reportManager.getCurrentReport().add(this);
        this.sendToDatabase(MainReport.getInstance().getExecutor());

        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("zreport.seereport"))
                .forEach(player -> {
                    val alertMessage = new ArrayList<>(MessageValue.get(MessageValue::reportAlertMessage));
                    alertMessage.replaceAll(message -> message.replace('&', '§')
                            .replace("%apelido", this.getUser().getName())
                            .replace("%autor", this.getAuthor().getName())
                            .replace("%motivo", this.getData().getReason())
                            .replace("%prova", this.getData().getEvidence())
                            .replace("%prioridade", this.getData().getPriority().getName())
                            .replace("%vezes", String.valueOf(reportManager.getAllReportsByName(this.getUser().getName()).size())));
                    alertMessage.forEach(message -> player.sendMessage(message));
                    val component = new TextComponent("  §aClique §a§naqui§a para visualizar");
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reportinfo " + this.getUser().getName()));
                    player.spigot().sendMessage(component);
                });
    }

    /**
     * Faz a setagem da prova da denúncia
     *
     * @param evidence Link da prova dada pelo Author
     */
    public void setEvidenceToReport(String evidence) {
        if (evidence.equalsIgnoreCase("Nenhuma")) {
            if (!this.getAuthor().toPlayer().hasPermission("zreport.semprova")) {
                this.getAuthor().toPlayer().sendMessage(MessageValue.get(MessageValue::withoutEvidence));
                return;
            }
            this.getData().setEvidence("Nenhuma");
        } else this.getData().setEvidence(evidence);
        this.getData().setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).toString());
        this.getData().setServer(ReportValue.get(ReportValue::server));
        this.getAuthor().toPlayer().sendMessage(MessageValue.get(MessageValue::sendedReport));
        this.enableReport(MainReport.getInstance().getReportManager());
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

    /**
     * Caso a denúncia seja concretizada, e o User punido,
     * ele altera a coluna "approved" para true
     *
     * @param executor Instância da classe SQLExecutor
     */
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
     * @param executor Instância da classe SQLExecutor
     */
    public void sendToDatabase(SQLExecutor executor) {
        executor.updateQuery("INSERT INTO denunciations(id, nickname, author, reason, evidence, server, type, priority, approved, date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", s -> {
            s.set(1, this.getData().getId());
            s.set(2, this.getUser().getName().toLowerCase());
            s.set(3, this.getAuthor().getName().toLowerCase());
            s.set(4, this.getData().getReason());
            s.set(5, this.getData().getEvidence());
            s.set(6, this.getData().getServer());
            s.set(7, this.getData().getReportType().toString());
            s.set(8, this.getData().getPriority().toString());
            s.set(9, this.getData().isApproved());
            s.set(10, this.getData().getDate());
        });
    }
}
