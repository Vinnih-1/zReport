package kazumy.plugin.zreport.spigot.report.manager;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.ReportValue;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.adapter.ReportAdapter;
import kazumy.plugin.zreport.spigot.report.viewer.entity.Author;
import lombok.Data;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data(staticConstructor = "of")
public class ReportManager {

    private final MainReport plugin;
    private final List<Report> currentReport = new ArrayList<>();
    private final Map<Author, Report> waitingEvidence = new HashMap<>();

    /**
     * Roda a cada 1 segundo para checar e limpar as denúncias expirados
     *
     * @return Retorna uma instância desta classe
     */
    public ReportManager run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            System.out.println(ReportValue.get(ReportValue::timeout));
            currentReport.stream()
                    .filter(report -> LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).minusMinutes(ReportValue.get(ReportValue::timeout))
                            .isAfter(LocalDateTime.parse(report.getData().getDate())))
                    .collect(Collectors.toSet()).forEach(report -> report.invalidateReport(this));
        }, 0, 20L);

        return this;
    }

    public List<Report> getAllReportsByName(String name) {
        return currentReport.stream().filter(report -> report.getUser().getName().equals(name)).collect(Collectors.toList());
    }

    public Report getReportById(String id) {
        return plugin.getExecutor().resultOneQuery("SELECT * FROM denunciations WHERE id = ?", s -> s.set(1, id), ReportAdapter.class);
    }
}
