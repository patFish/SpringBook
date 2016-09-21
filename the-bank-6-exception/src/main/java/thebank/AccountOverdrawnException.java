package thebank;

public class AccountOverdrawnException extends Exception {

	public AccountOverdrawnException() {
		super();
	}

	public AccountOverdrawnException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public AccountOverdrawnException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccountOverdrawnException(String arg0) {
		super(arg0);
	}

	public AccountOverdrawnException(Throwable arg0) {
		super(arg0);
	}

}
