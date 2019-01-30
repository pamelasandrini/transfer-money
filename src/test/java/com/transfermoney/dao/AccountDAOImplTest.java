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

		Account account = dao.getAccountById(10);

		Assert.assertTrue(account == null);
	}

	@Test
	public void createAccountTest() {

		Account newAccount1 = new Account("Test", 350);
		Account newAccount2 = new Account("Test2", 500);

		long idAccount1 = dao.createAccount(newAccount1);
		long idAccount2 = dao.createAccount(newAccount2);

		Assert.assertFalse(idAccount1 == idAccount2);
		Assert.assertEquals(3, dao.getAccountList().size());

	}

	@Test
	public void getAccountListTest() {

		List<Account> accountList = dao.getAccountList();

		Assert.assertEquals(3, accountList.size());
		Assert.assertEquals("Karen", accountList.get(0).getCustomerName());
		Assert.assertTrue(350 == accountList.get(1).getBalance());
		Assert.assertEquals("Test2", accountList.get(2).getCustomerName());
	}

	@Test
	public void updateBalanceTest() throws Exception {

		Account account = dao.getAccountById(0);
		double oldBalance = account.getBalance();

		account.decreaseBalance(50);

		int updateBalance = dao.updateBalance(0, account.getBalance());

		double newBalance = dao.getAccountById(0).getBalance();

		Assert.assertFalse(oldBalance == newBalance);
		Assert.assertTrue(updateBalance > 0);

	}

	@Test
	public void deleteAccountTest() throws Exception {

		Account newAccount = new Account("Test3", 2000);

		long idAccount = dao.createAccount(newAccount);

		int deleteAccount = dao.deleteAccount(idAccount);

		Assert.assertTrue(deleteAccount > 0);
		Assert.assertTrue(dao.getAccountById(idAccount) == null);

	}

	@Test
	public void deleteNonExistingAccountTest() throws Exception {

		int deleteAccount = dao.deleteAccount(10);

		Assert.assertFalse(deleteAccount > 0);

	}
}
