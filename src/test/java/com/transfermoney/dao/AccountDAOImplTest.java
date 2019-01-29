package com.transfermoney.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.transfermoney.bo.Account;

public class AccountDAOImplTest {

	private AccountDAO dao = new AccountDAOImpl();

	@BeforeClass
	public static void loadTestData() {
		ConnectionFactory.populateTestData();

	}

	@Test
	public void getAccountByIdTest() {

		Account account = dao.getAccountById(0);

		Assert.assertFalse(account == null);
		Assert.assertEquals("Karen", account.getCustomerName());
	}

	@Test
	public void getNonExistingAccountByIdTest() {

		Account account = dao.getAccountById(1);

		Assert.assertTrue(account == null);
	}

	@Test
	public void createAccountTest() {

		Account newAccount = new Account("Test", 350);

		Account createdAccount = dao.createAccount(newAccount);

		Assert.assertTrue(newAccount.equals(createdAccount));
		Assert.assertEquals(2, dao.getAccountList().size());

	}

	@Test
	public void createExistingAccountTest() {

		Account account = dao.getAccountById(0);

		Account createdAccount = dao.createAccount(account);

		Assert.assertTrue(createdAccount == null);

	}

	@Test
	public void getAccountListTest() {

		List<Account> accountList = dao.getAccountList();

		Assert.assertEquals(2, accountList.size());
		Assert.assertEquals("Karen", accountList.get(0).getCustomerName());
		Assert.assertTrue(350 == accountList.get(1).getBalance());
	}

	@Test
	public void updateBalanceTest() throws Exception {

		Account account = dao.getAccountById(0);
		double oldBalance = account.getBalance();

		account.decreaseBalance(50);

		dao.updateBalance(0, account.getBalance());

		double newBalance = dao.getAccountById(0).getBalance();

		Assert.assertFalse(oldBalance == newBalance);

	}

	@Test
	public void deleteAccountTest() throws Exception {

		dao.deleteAccount(1);

		Account deletedAccount = dao.getAccountById(1);

		Assert.assertTrue(deletedAccount == null);
		Assert.assertEquals(1, dao.getAccountList().size());

	}
}
