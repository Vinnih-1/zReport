package kazumy.plugin.zreport.spigot.report.viewer.adapter;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import kazumy.plugin.zreport.spigot.report.enums.Reputation;
import kazumy.plugin.zreport.spigot.report.viewer.Viewer;
import kazumy.plugin.zreport.spigot.report.viewer.impl.ViewerImpl;
import lombok.val;

public class ViewerAdapter implements SQLResultAdapter<Viewer> {

    @Override
    public Viewer adaptResult(SimpleResultSet result) {
        val viewer = new ViewerImpl(result.get("nickname"));
        viewer.setContributions(result.get("contributions"));
        viewer.setReputation(Reputation.valueOf(result.get("reputation")));
        viewer.setId(result.get("id"));

        return viewer;
    }
}
