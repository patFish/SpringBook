package thebank;

public interface BankService {

	AccountVO createAccount(AccountType type, int balance, int creditLine) throws AccountCreationException;

	/* (non-Javadoc)
	 * @see thebank.AtmBankService#withdrawal(thebank.AccountVO, int)
	 */
	void withdrawal(AccountVO accountVO, int amount) throws AccountOverdrawnException;

	/* (non-Javadoc)
	 * @see thebank.AtmBankService#deposit(thebank.AccountVO, int)
	 */
	void deposit(AccountVO accountVO, int amount) throws AccountOverdrawnException;

	void transfer(AccountVO fromAccount, AccountVO toAccount, int amount)
			throws FatalMoneyLossException, TransferFailedException;

}