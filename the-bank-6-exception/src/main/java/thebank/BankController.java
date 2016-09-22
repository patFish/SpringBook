package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bank")
@RestController
public class BankController {

	public BankController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	BankService bankService;

	@RequestMapping("/test")
	public String index() {
		return "<b>Wohoooo, Spring Boot !!!</b>";
	}

	@RequestMapping(value = "/saveAccount", method = RequestMethod.POST)
	public Account createAccount(@RequestBody SavingAccount account) throws AccountCreationException {
		// bankService.createAccount(AccountType.SAVING, 1000000, 0);
		bankService.saveAccount(account);
		return account;
	}

	@RequestMapping(value = "/findAccount/{accountNumber}", method = RequestMethod.GET)
	public Account findAccount(@PathVariable int accountNumber) throws AccountCreationException {

		int firstDigit = Integer.parseInt(Integer.toString(accountNumber).substring(0, 1));
		System.out.println(firstDigit);
		if (firstDigit == 1) {
			Account account = bankService.lookupAccount(accountNumber);
			return account;
		} else if (firstDigit == 2) {
			Account account = null;
			return account;
		} else {
			throw new RuntimeException();
		}

	}

	@RequestMapping(value = "/deposit/{accountNumber}/{amount}", method = RequestMethod.GET)
	public Account deposit(@PathVariable int accountNumber, @PathVariable int amount)
			throws AccountCreationException, AccountOverdrawnException {

		int firstDigit = Integer.parseInt(Integer.toString(accountNumber).substring(0, 1));
		System.out.println(firstDigit);
		if (firstDigit == 1) {
			Account account = bankService.lookupAccount(accountNumber);
			bankService.deposit(account, amount);
			bankService.saveAccount(account);
			return account;
		} else if (firstDigit == 2) {
			Account account = null;
			return account;
		} else {
			throw new RuntimeException();
		}

	}

	@RequestMapping(value = "/withdraw/{accountNumber}/{amount}", method = RequestMethod.GET)
	public Account withdraw(@PathVariable int accountNumber, @PathVariable int amount)
			throws AccountCreationException, AccountOverdrawnException {

		int firstDigit = Integer.parseInt(Integer.toString(accountNumber).substring(0, 1));
		System.out.println(firstDigit);
		if (firstDigit == 1) {
			Account account = bankService.lookupAccount(accountNumber);
			bankService.withdrawal(account, amount);
			bankService.saveAccount(account);
			return account;
		} else if (firstDigit == 2) {
			Account account = null;
			return account;
		} else {
			throw new RuntimeException();
		}

	}
}
