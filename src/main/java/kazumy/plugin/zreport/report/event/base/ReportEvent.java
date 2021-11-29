package kazumy.plugin.zreport.report.event.base;

import kazumy.plugin.zreport.report.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class ReportEvent<E> {
	
	@Getter private final Report report;
	
	public abstract void event(E event);
}
