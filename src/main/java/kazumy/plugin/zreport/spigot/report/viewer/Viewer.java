package kazumy.plugin.zreport.spigot.report.viewer;

import kazumy.plugin.zreport.spigot.report.enums.Reputation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public interface Viewer {

    int getId();

    int getContributions();

    String getName();

    Reputation getReputation();

    default Player toPlayer() {
        return Bukkit.getPlayer(this.getName());
    }
}