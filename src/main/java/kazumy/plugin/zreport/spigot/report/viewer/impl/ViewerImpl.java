package kazumy.plugin.zreport.spigot.report.viewer.impl;

import kazumy.plugin.zreport.spigot.report.reputation.Reputation;
import kazumy.plugin.zreport.spigot.report.viewer.Viewer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ViewerImpl implements Viewer {

    private final String name;
    private final int id;

    public Reputation getReputation() {


        return null;
    }
}
