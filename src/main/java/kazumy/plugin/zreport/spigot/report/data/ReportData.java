package kazumy.plugin.zreport.spigot.report.data;

import kazumy.plugin.zreport.spigot.report.enums.Priority;
import kazumy.plugin.zreport.spigot.report.enums.ReportType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder(builderClassName = "build")
public class ReportData {

    private boolean approved;
    private ReportType reportType;
    private Priority priority;
    private String id, reason, evidence, server, date;
}
