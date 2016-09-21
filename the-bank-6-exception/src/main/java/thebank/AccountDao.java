package thebank;

import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<SavingAccount, Integer> {

	void saveAccount(AccountMO account);

	AccountMO findAccount(int accountNumber);

}