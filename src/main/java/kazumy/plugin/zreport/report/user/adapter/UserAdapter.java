package kazumy.plugin.zreport.report.user.adapter;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;

import kazumy.plugin.zreport.report.user.User;

public class UserAdapter implements SQLResultAdapter<User> {

	@Override
	public User adaptResult(SimpleResultSet result) {
		return User.builder()
				.name(String.valueOf(result.get("name")))
				.amount(Integer.parseInt(String.valueOf(result.get("amount"))))
				.historic(result.get("historic"))
				.build();
	}
}
