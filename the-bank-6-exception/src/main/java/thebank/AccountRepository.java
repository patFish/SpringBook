package thebank;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<SavingAccount, Integer> {

	void saveAccount(AccountMO account);

	AccountMO findAccount(int accountNumber);

}