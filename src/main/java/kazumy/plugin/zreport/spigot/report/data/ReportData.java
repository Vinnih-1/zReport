package kazumy.plugin.zreport.spigot.report.data;

import kazumy.plugin.zreport.spigot.report.type.ReportType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "build")
public class ReportData {

    private boolean approved;
    private Integer id;
    private ReportType reportType;
    private String reason, evidence, server, date;
}
