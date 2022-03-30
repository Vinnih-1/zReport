package kazumy.plugin.zreport.spigot.report.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
public enum Priority {

    LOW("§7§lBAIXA", Color.GRAY), NORMAL("§e§lNORMAL", Color.YELLOW), HIGHEST("§c§lALTA", Color.RED);

    @Getter private final String name;
    @Getter private final Color color;
}
