package thebank;

import org.springframework.stereotype.Component;

@Component
public class AccountFactory {

	private int accountNumberCounter = 1;

	public Account createAccount(AccountType type, int balance, int creditLine) throws AccountOverdrawnException {
		int accountNumber = this.retrieveAccountNumber();
		Account account;
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
