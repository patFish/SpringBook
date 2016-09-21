package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bank")
@RestController
public class BankController {

	@Autowired
	BankService bank;

	@RequestMapping("/accounts")
	public AccountVO findAccount() throws AccountCreationException {
		return bank.createAccount(AccountType.SAVING, 1000000, 0);
	}
}
