package kazumy.plugin.zreport.spigot.listener.custom;

import kazumy.plugin.zreport.spigot.report.Report;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReportCustomEvent extends Event implements Cancellable {

    private final List<Report> allReports;
    private final Report report;
    private boolean isCancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}
