package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

	private AccountFactory accountFactory;

	@Autowired
	private SimpleAccountDao accountDao;

	@Override
	public Account createAccount(AccountType type, int balance, int creditLine) throws AccountCreationException {
		try {
			Account account;
			// call factory to create account...
			account = accountFactory.createAccount(type, balance, creditLine);
			accountDao.saveAccount(account);
			// store account
			// storeAccount(account);
			return account;
		} catch (AccountOverdrawnException e) {
			throw new AccountCreationException("Could not create account of type: " + type + " with balance: " + balance
					+ " and creditLine: " + creditLine);
		}

	}

	public void saveAccount(Account account) {
		accountDao.saveAccount(account);
	}

	@Override
	public void withdrawal(Account Account, int amount) throws AccountOverdrawnException {
		// TODO: verify amount is positive and handle exceptions
		// TODO: do we r4lly need Account as parameter ? W hat if client has
		// only int accountNumber ?
		if (amount >= 0) {
			Account account = lookupAccount(Account.getAccountNumber());
			account.book(amount * -1);
		}

	}

	@Override
	public void deposit(Account Account, int amount) throws AccountOverdrawnException {
		// TODO: verify amount is positive
		if (amount >= 0) {
			Account account = lookupAccount(Account.getAccountNumber());
			account.book(amount);
		}
	}

	@Override
	public void transfer(Account fromAccount, Account toAccount, int amount)
			throws FatalMoneyLossException, TransferFailedException {
		Account fromMoAccount = lookupAccount(fromAccount.getAccountNumber());
		Account toMoAccount = lookupAccount(toAccount.getAccountNumber());

		try {
			this.withdrawal(fromMoAccount, amount);
		} catch (AccountOverdrawnException aoe) {
			// TODO: verify state change and reset if required
			throw new TransferFailedException(
					"Could not transfer " + amount + " from account: " + fromMoAccount + " to account: " + toMoAccount);
		}

		try {
			this.deposit(toMoAccount, amount);
		} catch (AccountOverdrawnException aoe) {
			// TODO: deposit deducted money again to reinitialize previous state
			try {
				deposit(fromMoAccount, amount);
				throw new TransferFailedException("Could not transfer " + amount + " from account: " + fromMoAccount
						+ " to account: " + toMoAccount);
			} catch (AccountOverdrawnException aoe2) {
				// oh oh oh... Houston ?
				throw new FatalMoneyLossException("Could not reset state for account: " + fromMoAccount, aoe2);
			}
		}
	}

	void storeAccount(Account account) {
		// accounts.put(account.getAccountNumber(), account);
		accountDao.saveAccount(account);
	}

	public Account lookupAccount(int accountNumber) {
		// TODO: what if account does not exist in accounts map ?
		// TODO: handle missing accounts
		return accountDao.findAccount(accountNumber);
	}

	@Autowired
	public void setAccountFactory(AccountFactory accountFactory) {
		this.accountFactory = accountFactory;
	}

}
