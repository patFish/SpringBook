package thebank;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BankServiceImplTest {

	private BankServiceImpl bank;

	@Before
	public void setUp() {
		bank = new BankServiceImpl();
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
		BankServiceImpl bank = new BankServiceImpl() {
			@Override
			void storeAccount(AccountMO account) {
				Assert.assertSame(savingAccount, account);
				//flagHolder.flag = true;
			}
		};
		// inject mock into sut
		bank.setAccountFactory(accountFactory);

		EasyMock.expect(accountFactory.createAccount(type, balance, creditLine)).andReturn(savingAccount);

		// finish recording phase
		EasyMock.replay(accountFactory);

		// call unit (sut)
		AccountVO returnedAccount = bank.createAccount(type, balance, creditLine);

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
			AccountVO savingAccount = bank.createAccount(AccountType.SAVING, balance, creditLine);
			AccountVO creditAccount = testSavingAccount(balance,savingAccount);
			Assert.assertEquals(2, savingAccount.getBalance());

			int expectedCreditAccountBalance = testCreditAccount(creditAccount);

			bank.transfer(savingAccount, creditAccount, savingAccount.getBalance());

			Assert.assertEquals(expectedCreditAccountBalance, creditAccount.getBalance());

		} catch (AccountOverdrawnException | AccountCreationException | FatalMoneyLossException
				| TransferFailedException e) {
			// something went terribly wrong...
			e.printStackTrace();
		}

	}

	private int testCreditAccount(AccountVO creditAccount) {
		Assert.assertEquals(-500, creditAccount.getBalance());

		int expectedCreditAccountBalance = creditAccount.getBalance() + 2;
		return expectedCreditAccountBalance;
	}

	private AccountVO testSavingAccount(int balance, AccountVO savingAccount)
			throws AccountOverdrawnException, AccountCreationException {
		int creditLine;
		Assert.assertEquals(balance, savingAccount.getBalance());
		// TODO: test fails, account does not exist yet.
		bank.withdrawal(savingAccount, balance);
		Assert.assertEquals(0, savingAccount.getBalance());

		// balance = 0
		//bank.withdrawal(savingAccount, 1);
		int newAmount = 2;

		bank.deposit(savingAccount, newAmount);

		Assert.assertEquals(newAmount, savingAccount.getBalance());

		// --------------------------------------------------------------------------------
		// CreditAccount --->
		balance = 500;
		creditLine = 500;
		AccountVO creditAccount = bank.createAccount(AccountType.CREDIT, balance, creditLine);

		bank.withdrawal(creditAccount, creditLine + balance);

		Assert.assertEquals(-500, creditAccount.getBalance());
		return creditAccount;
	}

}