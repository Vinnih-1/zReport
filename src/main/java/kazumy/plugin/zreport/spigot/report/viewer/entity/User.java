package kazumy.plugin.zreport.spigot.report.viewer.entity;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import kazumy.plugin.zreport.spigot.report.Report;
import kazumy.plugin.zreport.spigot.report.adapter.ReportAdapter;
import kazumy.plugin.zreport.spigot.report.manager.ReportManager;
import kazumy.plugin.zreport.spigot.report.viewer.impl.ViewerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User extends ViewerImpl {

    public User(String name) {
        super(name);
    }

    /**
     * Retorna todos as denúncias que estiverem na memória
     *
     * @param reportManager Instância da classe ReportManager para acessar a Lista de denúncias
     * @return A lista de denúncias que estiverem com o mesmo nome do User
     */
    public List<Report> getAllReportsEnabled(ReportManager reportManager) {
        return reportManager.getCurrentReport().stream().filter(report -> report.getUser().getName().equals(this.getName())).collect(Collectors.toList());
    }

    public List<Report> getAllReportsStored(SQLExecutor executor) {
        return new ArrayList<>(executor.resultManyQuery("SELECT * FROM denunciations WHERE nickname = ?", s -> {
            s.set(1, this.getName().toLowerCase());
        }, ReportAdapter.class));
    }
}
