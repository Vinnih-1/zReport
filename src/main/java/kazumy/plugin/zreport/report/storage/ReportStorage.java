package kazumy.plugin.zreport.report.storage;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import kazumy.plugin.zreport.MainReport;
import kazumy.plugin.zreport.configuration.ReportValue;
import kazumy.plugin.zreport.report.Report;
import kazumy.plugin.zreport.report.viewer.Viewer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class ReportStorage {
	
	@NonNull private MainReport plugin;
	
	@Getter private final List<Report> reportPresent = new LinkedList<>();
	@Getter private final Map<String, Report> reportPending = new HashMap<>();
	
	/**
	 * Insere o report na lista de reports,
	 * caso ele já não esteja lá.
	 * Caso o contrário, ele pega o que já está na lista e sobrescreve
	 * 
	 * @param report
	 */
	public void writeReport(Report report) {
		Predicate<Report> predicate = this::hasPresent;
		
		if (reportPresent.stream().anyMatch(predicate)) {
			reportPresent.stream().filter(predicate).findFirst()
				.ifPresent(this::overwriteReport);
		} else {
			report.getViewers().add(report.getViewer());
			reportPresent.add(report);
		}
	}
	
	// Registrar no DB e fazer as estatísticas do User atualizar
	
	/**
	 * Roda uma tarefa para checar e limpar
	 * reports que estão expirados na lista
	 * 
	 * @return
	 */
	public ReportStorage runReportTask() {
		Bukkit.getScheduler().runTaskTimer(plugin
				, () -> {
					System.out.println(reportPresent);
					
					val expired = reportPresent.stream().filter(this::hasExpired)
						.collect(Collectors.toList());
					expired.forEach(reportPresent::remove);
				}, 0, 20);

		return this;
	}
	
	/**
	 * Verifica se o report passado no parâmetro
	 * está expirado com o tempo-limite da config.yml
	 * 
	 * @param report
	 * @return
	 */
	private boolean hasExpired(Report report) {
		return report.getTime().isAfter(LocalTime.now(ZoneId.of("America/Sao_Paulo"))
				.minusMinutes(ReportValue.get(ReportValue::getTimeout)));
	}
	
	/**
	 * Pega o report existente na lista
	 * e atualiza todos seus atributos
	 * 
	 * @param report
	 */
	private void overwriteReport(Report report) {
		reportPresent.stream().filter(this::hasPresent)
			.findFirst().ifPresent(r -> {
				r.setAmount(r.getAmount() + 1);
				r.setTime(report.getTime());

				if (r.getViewers().contains(r.getViewer()))
					overwriteViewers(r.getViewers(), report.getViewer());
				else r.getViewers().add(report.getViewer());
			});
	}

	private void overwriteViewers(List<Viewer> viewers, Viewer viewer) {
		viewers.stream().filter(v -> v.getUser().getName().equals(viewer.getUser().getName()))
			.findFirst().ifPresent(v -> {
				v.setEvidence(viewer.getEvidence());
				v.setReason(viewer.getReason());
				v.setTime(LocalTime.now(ZoneId.of("America/Sao_Paulo")));
			});
	}
	
	private boolean hasPresent(Report report) {
		return reportPresent.stream().anyMatch(r -> r.getName().equals(report.getName()));
	}
}
