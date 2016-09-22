package thebank;

public class CommunicationMessage {
	private int accountNumber;
	private int amount;
	private CommunicationTypeEnum operationType;

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CommunicationTypeEnum getOperationType() {
		return operationType;
	}

	public void setOperationType(CommunicationTypeEnum operationType) {
		this.operationType = operationType;
	}

	public CommunicationMessage(int accountNumber, int balance, CommunicationTypeEnum communicationTypeEnum) {
		this.accountNumber = accountNumber;
		this.amount = balance;
		this.operationType = communicationTypeEnum;

	}

	public CommunicationMessage() {
	}

}
