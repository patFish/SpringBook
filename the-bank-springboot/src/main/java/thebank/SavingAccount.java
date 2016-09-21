package thebank;

public class SavingAccount extends Account {

	public SavingAccount(int accountNumber) {
		super(accountNumber);
	}

	@Override
	boolean checkBookingPreCondition(int amount) {
		return balance + amount >= 0;
	}

}
