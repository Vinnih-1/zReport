package kazumy.plugin.zreport.spigot.report.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Priority {

    LOW("§7§lBAIXA"), NORMAL("§e§lNORMAL"), HIGHEST("§c§lALTA");

    @Getter private final String name;
}
