package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleAccountDao  {

	@Autowired
	private AccountRepository accountRepository;
	
	/* (non-Javadoc)
	 * @see thebank.AccountDao#saveAccount(thebank.AccountMO)
	 */
	public void saveAccount(Account account) {
		accountRepository.save(account);
	}

	/* (non-Javadoc)
	 * @see thebank.AccountDao#findAccount(int)
	 */
	public Account findAccount(int accountNumber) {
		// TODO: what if account does not exist in accounts map ?
		return accountRepository.findOneByAccountNumber(accountNumber);
	}

}
