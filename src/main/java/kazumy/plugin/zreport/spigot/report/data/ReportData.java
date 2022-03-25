package kazumy.plugin.zreport.spigot.report.data;

import kazumy.plugin.zreport.spigot.report.type.ReportType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "build")
public class ReportData {

    private Integer id, amount;
    private ReportType reportType;
    private String reason, evidence, server, date;
}
