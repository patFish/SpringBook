package thebank;

import javax.persistence.Entity;

@Entity
public class CreditAccount extends Account {

	private int creditLine;

	public CreditAccount(int accountNumber) {
		super(accountNumber);
	}


	public void setCreditLine(int creditLine) {
		this.creditLine = creditLine;
	}

	@Override
	boolean checkBookingPreCondition(int amount) {
		return balance + creditLine + amount >= 0;
	}

}
