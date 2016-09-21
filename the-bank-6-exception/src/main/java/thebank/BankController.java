package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bank")
@RestController
public class BankController {

	@Autowired
	BankService bankService;
	
	@ResponseBody
	@RequestMapping("/test")
	public String index(){
		return "<b>Wohoooo, Spring Boot !!!</b>";
	}
	

	@RequestMapping(value = "/findAccount/{id}", method = RequestMethod.GET)
	public Account findAccount(@PathVariable int accountNumber) throws AccountCreationException {
		bankService.createAccount(AccountType.SAVING, 1000000, 0);
		Account account = bankService.lookupAccount(accountNumber);
		bankService.saveAccount(account);
		return account;
	}
	
	@RequestMapping(value = "/deposit/{id,amount}", method = RequestMethod.GET)
	public Account deposit(@PathVariable int accountNumber,@PathVariable int amount) throws AccountCreationException, AccountOverdrawnException {
		Account account = bankService.lookupAccount(accountNumber);
		bankService.deposit(account, amount);
		bankService.saveAccount(account);
		return account;
	}
	
	@RequestMapping(value = "/withdraw/{id,amount}", method = RequestMethod.GET)
	public Account withdraw(@PathVariable int accountNumber,@PathVariable int amount) throws AccountCreationException, AccountOverdrawnException {
		Account account = bankService.lookupAccount(accountNumber);
		bankService.withdrawal(account, amount);
		bankService.saveAccount(account);
		return account;
	}
}
