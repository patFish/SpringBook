package thebank;

public class AccountFactory {

	private int accountNumberCounter = 1;

	public AccountMO createAccount(AccountType type, int balance, int creditLine) throws AccountOverdrawnException {
		int accountNumber = this.retrieveAccountNumber();
		AccountMO account;
		switch (type) {
		case CREDIT:
			CreditAccount creditAccount = new CreditAccount(accountNumber);
			creditAccount.setCreditLine(creditLine);
			account = creditAccount;
			break;
		default:
			account = new SavingAccount(accountNumber);
			break;
		}
		account.book(balance);
		//account = new AccountLoggingProxy(account);
		return account;
	}

	// TODO: extract AccountNumberGenerator
	// TODO: maybe define type for accountNumbers ?!
	private int retrieveAccountNumber() {
		return accountNumberCounter++;
	}
}
