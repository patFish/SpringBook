package thebank;

import javax.persistence.*;

//@MappedSuperclass
@Entity
public abstract class Account implements AccountMO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private int accountNumber;
	protected int balance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Account(int accountNumber) {
		super();
		this.accountNumber = accountNumber;
	}

	/**
	 * book method uses abstract method checkBookingPreCondition() --> Template
	 * method pattern
	 * 
	 * @throws AccountOverdrawnException
	 */
	@Override
	public void book(int amount) throws AccountOverdrawnException {
		if (checkBookingPreCondition(amount)) {
			balance += amount;
		} else {
			throw new AccountOverdrawnException("Could not bool '" + amount + "'  from account: " + this);
		}
	}

	abstract boolean checkBookingPreCondition(int amount);

	@Override
	public int getBalance() {
		return balance;
	}

	@Override
	public int getAccountNumber() {
		return accountNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber != other.accountNumber)
			return false;
		return true;
	}

}
