package kazumy.plugin.zreport.spigot.report.viewer.impl;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import kazumy.plugin.zreport.spigot.report.enums.Reputation;
import kazumy.plugin.zreport.spigot.report.viewer.Viewer;
import kazumy.plugin.zreport.spigot.report.viewer.adapter.ViewerAdapter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Data
@RequiredArgsConstructor
public class ViewerImpl implements Viewer {

    private final String name;

    private Reputation reputation;
    private int id, contributions;

    public Viewer findViewer(SQLExecutor executor) {
        val viewer = executor.resultOneQuery("SELECT * FROM reputations WHERE nickname = ?", s -> {
            s.set(1, this.getName());
        }, ViewerAdapter.class);

        return viewer == null ? this : viewer;
    }
}
