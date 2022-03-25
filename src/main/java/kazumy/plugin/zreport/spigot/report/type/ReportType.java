package kazumy.plugin.zreport.spigot.report.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReportType {

    FAST_REPORT("zreport.fastreport"), MENU_REPORT("");

    @Getter private final String permission;
}
