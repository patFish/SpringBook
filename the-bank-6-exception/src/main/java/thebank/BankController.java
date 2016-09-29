package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/bank")
@RestController
public class BankController {

	@Autowired
	BankService bankService;

	@RequestMapping(value = "/test")
	public String index() {
		return "<b>Wohoooo, Spring Boot !!!</b>";
	}

	@RequestMapping(value = "/saveAccount", method = RequestMethod.POST)
	public Account createAccountController(@RequestBody SavingAccount account) throws AccountCreationException {
		bankService.saveAccount(account);
		return account;
	}

	@RequestMapping(value = "/accounts/test", method = RequestMethod.POST)
	public void receiveMessageFromExternalBank(@RequestBody CommunicationMessage message)
			throws AccountCreationException, AccountOverdrawnException {
		if (message.getOperationType().equals(CommunicationTypeEnum.withdraw)) {
			withdrawController(message.getAccountNumber(), message.getAmount());
		} else {
			Account temp = depositController(message.getAccountNumber(), message.getAmount());
		}
	}

	public CommunicationMessage sendMessageToExternalBank(CommunicationMessage communicationMessage) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://10.10.3.241:8050/accounts/book";
		CommunicationMessage answer = restTemplate.postForObject(url, communicationMessage, CommunicationMessage.class);
		return answer;
	}

	@RequestMapping(value = "/findAccount/{accountNumber}", method = RequestMethod.GET)
	public Account findAccountController(@PathVariable int accountNumber) throws AccountCreationException {

		int firstDigit = Integer.parseInt(Integer.toString(accountNumber).substring(0, 1));

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
	public Account depositController(@PathVariable int accountNumber, @PathVariable int amount)
			throws AccountCreationException, AccountOverdrawnException {

		int firstDigit = Integer.parseInt(Integer.toString(accountNumber).substring(0, 1));
		if (firstDigit == 1) {
			Account account = bankService.lookupAccount(accountNumber);
			bankService.deposit(account, amount);
			bankService.saveAccount(account);
			return account;
		} else {
			CommunicationMessage message = new CommunicationMessage(accountNumber, amount,
					CommunicationTypeEnum.deposit);
			SavingAccount account = new SavingAccount();
			message = sendMessageToExternalBank(message);
			account.setBalance(message.getAmount());
			account.setId(message.getAccountNumber());
			return account;
		}

	}

	@RequestMapping(value = "/withdraw/{accountNumber}/{amount}", method = RequestMethod.GET)
	public Account withdrawController(@PathVariable int accountNumber, @PathVariable int amount)
			throws AccountCreationException, AccountOverdrawnException {

		int firstDigit = Integer.parseInt(Integer.toString(accountNumber).substring(0, 1));
		if (firstDigit == 1) {
			Account account = bankService.lookupAccount(accountNumber);
			bankService.withdrawal(account, amount);
			bankService.saveAccount(account);
			return account;
		} else {
			CommunicationMessage message = new CommunicationMessage(accountNumber, amount,
					CommunicationTypeEnum.withdraw);
			SavingAccount account = new SavingAccount();
			message = sendMessageToExternalBank(message);
			account.setBalance(message.getAmount());
			account.setId(message.getAccountNumber());
			return account;
		}

	}
}
