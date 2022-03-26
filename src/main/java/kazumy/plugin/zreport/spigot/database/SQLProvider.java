package kazumy.plugin.zreport.spigot.database;

import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import kazumy.plugin.zreport.spigot.MainReport;
import lombok.Data;
import lombok.val;
import org.bukkit.Bukkit;

import java.io.File;

@Data(staticConstructor = "of")
public class SQLProvider {

    private final MainReport plugin;

    public SQLExecutor createDefaults() {
        val connector = SQLiteDatabaseType.builder()
                .file(new File(plugin.getDataFolder(), "database/zreport.db"))
                .build().connect();
        val executor = new SQLExecutor(connector);

        executor.updateQuery("CREATE TABLE IF NOT EXISTS denunciations (\n" +
                "    id       TEXT,\n" +
                "    nickname VARCHAR (40) NOT NULL,\n" +
                "    author   VARCHAR (40) NOT NULL,\n" +
                "    reason   TEXT,\n" +
                "    evidence TEXT,\n" +
                "    server   TEXT,\n" +
                "    type   TEXT,\n" +
                "    priority   TEXT,\n" +
                "    approved   BOOLEAN,\n" +
                "    date     DATETIME     NOT NULL\n" +
                ");\n");

        executor.updateQuery("CREATE TABLE IF NOT EXISTS reputations (\n" +
                "    id         TEXT,\n" +
                "    nickname   VARCHAR (40) NOT NULL,\n" +
                "    reputation VARCHAR (15) NOT NULL,\n" +
                "    contributions         INTEGER      NOT NULL\n" +
                ");");

        Bukkit.getConsoleSender().sendMessage("§a[zReport] §fConexão com o banco de dados §eSQLite §frealizada!");
        return executor;
    }
}
