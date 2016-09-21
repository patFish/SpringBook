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
	BankService bank;
	@Autowired
	AccountRepository accountDao;
	
	@ResponseBody
	@RequestMapping("/test")
	public String index(){
		return "<b>Wohoooo, Spring Boot !!!</b>";
	}
	

	@RequestMapping(value = "/findAccount/{id}", method = RequestMethod.GET)
	public AccountVO findAccount(@PathVariable int accountNumber) throws AccountCreationException {
		bank.createAccount(AccountType.SAVING, 1000000, 0);
		AccountVO account = bank.lookupAccount(accountNumber);
		accountDao.save((SavingAccount)account);
		return account;
	}
	
	@RequestMapping(value = "/deposit/{id,amount}", method = RequestMethod.GET)
	public AccountVO deposit(@PathVariable int accountNumber,@PathVariable int amount) throws AccountCreationException, AccountOverdrawnException {
		AccountVO account = bank.lookupAccount(accountNumber);
		bank.deposit(account, amount);
		accountDao.save((SavingAccount)account);
		return account;
	}
	
	@RequestMapping(value = "/withdraw/{id,amount}", method = RequestMethod.GET)
	public AccountVO withdraw(@PathVariable int accountNumber,@PathVariable int amount) throws AccountCreationException, AccountOverdrawnException {
		AccountVO account = bank.lookupAccount(accountNumber);
		bank.withdrawal(account, amount);
		//accountDao.save((SavingAccount)account);
		return account;
	}
}
