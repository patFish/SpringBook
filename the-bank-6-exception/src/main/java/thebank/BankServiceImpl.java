package thebank;

import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements AtmBankService, BankService {

	private AccountFactory accountFactory = new AccountFactory();
	private AccountDao accountDao = new SimpleAccountDao();

	@Override
	public AccountVO createAccount(AccountType type, int balance, int creditLine) throws AccountCreationException {
		try {
			AccountMO account;
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


	@Override
	public void withdrawal(AccountVO accountVO, int amount) throws AccountOverdrawnException {
		// TODO: verify amount is positive and handle exceptions
		// TODO: do we r4lly need accountVO as parameter ? W hat if client has
		// only int accountNumber ?
		AccountMO account = lookupAccount(accountVO.getAccountNumber());
		account.book(amount * -1);
	}


	@Override
	public void deposit(AccountVO accountVO, int amount) throws AccountOverdrawnException {
		// TODO: verify amount is positive
		AccountMO account = lookupAccount(accountVO.getAccountNumber());
		account.book(amount);
	}

	@Override
	public void transfer(AccountVO fromAccount, AccountVO toAccount, int amount)
			throws FatalMoneyLossException, TransferFailedException {
		AccountMO fromMoAccount = lookupAccount(fromAccount.getAccountNumber());
		AccountMO toMoAccount = lookupAccount(toAccount.getAccountNumber());

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

	void storeAccount(AccountMO account) {
		// accounts.put(account.getAccountNumber(), account);
		accountDao.saveAccount(account);
	}

	public AccountMO lookupAccount(int accountNumber) {
//		 TODO: what if account does not exist in accounts map ?
		// TODO: handle missing accounts
		return accountDao.findAccount(accountNumber);
	}

	public void setAccountFactory(AccountFactory accountFactory) {
		this.accountFactory = accountFactory;
	}

}
