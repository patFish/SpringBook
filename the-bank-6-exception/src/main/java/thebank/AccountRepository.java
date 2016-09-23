package thebank;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

	public Account findAccountByAccountNumber(Integer accountNumber);

}