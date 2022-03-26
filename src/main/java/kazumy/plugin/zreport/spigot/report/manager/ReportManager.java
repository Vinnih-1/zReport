package kazumy.plugin.zreport.spigot.report.manager;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.configuration.ReportValue;
import kazumy.plugin.zreport.spigot.report.Report;
import lombok.Data;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data(staticConstructor = "of")
public class ReportManager {

    private final MainReport plugin;
    private final List<Report> currentReport = new ArrayList<>();

    /**
     * Roda a cada 1 segundo para checar e limpar as denúncias expirados
     *
     * @return Retorna uma instância desta classe
     */
    public ReportManager run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            currentReport.stream()
                    .filter(report -> LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).minusMinutes(ReportValue.get(ReportValue::timeout))
                            .isAfter(LocalDateTime.parse(report.getData().getDate())))
                    .collect(Collectors.toSet()).forEach(report -> report.invalidateReport(this));
        }, 0, 20L * 60);

        return this;
    }

    public List<Report> getAllReportsByName(String name) {
        return currentReport.stream().filter(report -> report.getUser().getName().equals(name)).collect(Collectors.toList());
    }
}
