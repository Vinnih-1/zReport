package kazumy.plugin.zreport.spigot.report.adapter;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.data.ReportData;
import kazumy.plugin.zreport.spigot.report.enums.Priority;
import kazumy.plugin.zreport.spigot.report.enums.ReportType;
import kazumy.plugin.zreport.spigot.report.viewer.entity.Author;
import kazumy.plugin.zreport.spigot.report.viewer.entity.User;

public class ReportAdapter implements SQLResultAdapter<Report> {

    @Override
    public Report adaptResult(SimpleResultSet result) {
        return new Report().configureReport(report -> {
            report.setUser(new User(result.get("nickname")));
            report.setAuthor(new Author(result.get("author")));
            report.setData(ReportData.builder()
                            .id(result.get("id"))
                            .approved(Boolean.parseBoolean(result.get("approved")))
                            .reason(result.get("reason"))
                            .server(result.get("server"))
                            .evidence(result.get("evidence"))
                            .priority(Priority.valueOf(result.get("priority")))
                            .reportType(ReportType.valueOf(result.get("type")))
                            .date(result.get("date"))
                    .build());
        });
    }
}
