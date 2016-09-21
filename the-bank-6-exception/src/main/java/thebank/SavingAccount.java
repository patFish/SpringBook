package thebank;

import javax.persistence.*;

@Entity
public class SavingAccount extends Account {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SavingAccount(int accountNumber) {
		super(accountNumber);
	}

	@Override
	boolean checkBookingPreCondition(int amount) {
		return balance + amount >= 0;
	}

}
