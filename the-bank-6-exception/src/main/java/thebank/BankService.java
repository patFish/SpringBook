package thebank;

public interface BankService {

	Account createAccount(AccountType type, int balance, int creditLine) throws AccountCreationException;

	/* (non-Javadoc)
	 * @see thebank.AtmBankService#withdrawal(thebank.Account, int)
	 */
	void withdrawal(Account Account, int amount) throws AccountOverdrawnException;

	/* (non-Javadoc)
	 * @see thebank.AtmBankService#deposit(thebank.Account, int)
	 */
	void deposit(Account Account, int amount) throws AccountOverdrawnException;

	void transfer(Account fromAccount, Account toAccount, int amount)
			throws FatalMoneyLossException, TransferFailedException;

	Account lookupAccount(int accountNumber);
	
	public void saveAccount(Account account);
}