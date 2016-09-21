package thebank;

public class AccountLoggingProxy implements AccountMO {

	private AccountMO proxiedAccount;

	public AccountLoggingProxy(AccountMO proxiedAccount) {
		super();
		this.proxiedAccount = proxiedAccount;
	}

	@Override
	public int getBalance() {
		return proxiedAccount.getBalance();
	}

	@Override
	public int getAccountNumber() {
		return proxiedAccount.getAccountNumber();
	}

	@Override
	public void book(int amount) throws AccountOverdrawnException {
		// before
		System.out.println("Account has: " + proxiedAccount.getBalance() + " and we change by " + amount);
		// call
		proxiedAccount.book(amount);
		// after
		System.out.println("Account has: " + proxiedAccount.getBalance() + " and we change by " + amount);
	}

}
