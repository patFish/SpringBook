package thebank;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes=BankApplication.class)
public class BankServiceImplTest {

	private BankServiceImpl bankServiceImpl;

	@Autowired
	AccountRepository accountRepository;
	
	@Before
	public void setUp() {
		bankServiceImpl = new BankServiceImpl();
	}

	@Test
	public void createAccountWithMock() throws Exception {
		// create mock
		AccountFactory accountFactory = EasyMock.createMock(AccountFactory.class);

		// record expected behaviour
		AccountType type = AccountType.SAVING;
		int balance = 1000;
		int creditLine = 0;
		int timesCalled;
		SavingAccount savingAccount = new SavingAccount(0);
		
//		class BooleanFalagHolder  {
//			boolean flag = false;
//		}
		
		//BooleanFalagHolder flagHolder = new BooleanFalagHolder();

		// create sut
//		BankServiceImpl bank = new BankServiceImpl() {
//			@Override
//			void storeAccount(Account account) {
//				Assert.assertSame(savingAccount, account);
//				//flagHolder.flag = true;
//			}
//		};
		// inject mock into sut
		bankServiceImpl.setAccountFactory(accountFactory);

		EasyMock.expect(accountFactory.createAccount(type, balance, creditLine)).andReturn(savingAccount);

		// finish recording phase
		EasyMock.replay(accountFactory);

		// call unit (sut)
		Account returnedAccount = bankServiceImpl.createAccount(type, balance, creditLine);

		// verify returned result
		// same => == ; equals => .equals(...)
		Assert.assertSame(savingAccount, returnedAccount);

		//Assert.assertTrue(flagHolder.flag);

		// verify mock
		EasyMock.verify(accountFactory);
	}

	@Test
	public void functionalTest() {
		int balance = 1000;
		int creditLine = 0;

		try {
			Account savingAccount = bankServiceImpl.createAccount(AccountType.SAVING, balance, creditLine);
			Account creditAccount = testSavingAccount(balance,savingAccount);
			Assert.assertEquals(2, savingAccount.getBalance());

			int expectedCreditAccountBalance = testCreditAccount(creditAccount);

			bankServiceImpl.transfer(savingAccount, creditAccount, savingAccount.getBalance());

			Assert.assertEquals(expectedCreditAccountBalance, creditAccount.getBalance());

		} catch (AccountOverdrawnException | AccountCreationException | FatalMoneyLossException
				| TransferFailedException e) {
			// something went terribly wrong...
			e.printStackTrace();
		}

	}

	private int testCreditAccount(Account creditAccount) {
		Assert.assertEquals(-500, creditAccount.getBalance());

		int expectedCreditAccountBalance = creditAccount.getBalance() + 2;
		return expectedCreditAccountBalance;
	}

	private Account testSavingAccount(int balance, Account savingAccount)
			throws AccountOverdrawnException, AccountCreationException {
		int creditLine;
		Assert.assertEquals(balance, savingAccount.getBalance());
		// TODO: test fails, account does not exist yet.
		bankServiceImpl.withdrawal(savingAccount, balance);
		Assert.assertEquals(0, savingAccount.getBalance());

		// balance = 0
		//bank.withdrawal(savingAccount, 1);
		int newAmount = 2;

		bankServiceImpl.deposit(savingAccount, newAmount);

		Assert.assertEquals(newAmount, savingAccount.getBalance());

		// --------------------------------------------------------------------------------
		// CreditAccount --->
		balance = 500;
		creditLine = 500;
		Account creditAccount = bankServiceImpl.createAccount(AccountType.CREDIT, balance, creditLine);

		bankServiceImpl.withdrawal(creditAccount, creditLine + balance);

		Assert.assertEquals(-500, creditAccount.getBalance());
		return creditAccount;
	}
	
	

	@Ignore
	@Test
	public void testName() throws Exception {
		int balance = 100;
		int creditLine =0;
		Account account = bankServiceImpl.createAccount(AccountType.SAVING, balance, creditLine);
		accountRepository.save((SavingAccount)account);
	}
	

}