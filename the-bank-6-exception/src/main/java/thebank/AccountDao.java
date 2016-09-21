package thebank;

public interface AccountDao {

	void saveAccount(AccountMO account);

	AccountMO findAccount(int accountNumber);

}