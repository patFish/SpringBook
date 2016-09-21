package thebank;

public interface AtmBankService {

	void withdrawal(AccountVO accountVO, int amount) throws AccountOverdrawnException;

	void deposit(AccountVO accountVO, int amount) throws AccountOverdrawnException;

}