package kazumy.plugin.zreport.spigot.report.reputation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Reputation {

    NONE("§7§lNENHUMA"),
    MEDIAN("§a§lMEDIANA"),
    CONTRIBUTOR("§b§lCONTRIBUIDOR");

    @Getter private final String name;
}
