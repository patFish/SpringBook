package thebank;

import java.util.HashMap;
import java.util.Map;

public class SimpleAccountDao implements AccountDao {
	private Map<Integer, AccountMO> accounts = new HashMap<>();

	/* (non-Javadoc)
	 * @see thebank.AccountDao#saveAccount(thebank.AccountMO)
	 */
	@Override
	public void saveAccount(AccountMO account) {
		accounts.put(account.getAccountNumber(), account);
	}

	/* (non-Javadoc)
	 * @see thebank.AccountDao#findAccount(int)
	 */
	@Override
	public AccountMO findAccount(int accountNumber) {
		// TODO: what if account does not exist in accounts map ?
		return accounts.get(accountNumber);
	}
}
