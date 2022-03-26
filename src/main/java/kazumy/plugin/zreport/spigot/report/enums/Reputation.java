package kazumy.plugin.zreport.spigot.report.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Reputation {

    NONE("§7§lNENHUMA", 0),
    MEDIAN("§a§lMEDIANA", 15),
    CONTRIBUTOR("§b§lCONTRIBUIDOR", 50);

    @Getter private final String name; // Apenas o nome da contribuição com as cores
    @Getter private final int requiredContributions; // Número de contribuições necessárias para alcançar a reputação
}
