package kazumy.plugin.zreport.database;

import java.io.File;

import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import com.henryfabio.sqlprovider.executor.SQLExecutor;

import kazumy.plugin.zreport.MainReport;
import lombok.Data;

@Data(staticConstructor = "of")
public class SQLProvider {
	
	private final MainReport plugin;
	
	public SQLConnector connect() {
		plugin.log("§a[zReport] §fConexão com o banco de dados §eSQLite §frealizada!");
		return SQLiteDatabaseType.builder()
				.file(new File(plugin.getDataFolder(), "database/zreport.db"))
				.build().connect();
	}
	
	public static void setup(SQLExecutor executor) {
		executor.updateQuery("CREATE TABLE IF NOT EXISTS zreport(`name` TEXT, `amount` TEXT, `historic` TEXT)");
	}
}
