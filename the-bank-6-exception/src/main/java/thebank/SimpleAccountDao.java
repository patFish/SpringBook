package thebank;

import java.util.HashMap;
import java.util.Map;

public class SimpleAccountDao implements AccountRepository {
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

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Integer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SavingAccount arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends SavingAccount> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Integer arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<SavingAccount> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<SavingAccount> findAll(Iterable<Integer> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SavingAccount findOne(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SavingAccount> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SavingAccount> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
