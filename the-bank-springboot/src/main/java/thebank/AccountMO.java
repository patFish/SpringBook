package thebank;

public interface AccountMO extends AccountVO {
	void book(int amount) throws AccountOverdrawnException;
}
