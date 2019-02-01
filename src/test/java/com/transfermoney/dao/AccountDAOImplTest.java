package com.transfermoney.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.transfermoney.bo.Account;

public class AccountDAOImplTest {

	private AccountDAO dao = new AccountDAOImpl();

	@Before
	public void loadTestData() {
		ConnectionFactory.populateTestData();

	}

	@Test
	public void getAccountByIdTest() {

		Account account = dao.getAccountById(1);

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

		long idAccount1 = dao.createAccount(newAccount1);

		Account accountCreated = dao.getAccountById(idAccount1);

		Assert.assertFalse(accountCreated == null);

	}

	@Test
	public void getAccountListTest() {

		List<Account> accountList = dao.getAccountList();

		Assert.assertFalse(accountList == null);
		Assert.assertFalse(accountList.isEmpty());
		Assert.assertEquals("Karen", accountList.get(0).getCustomerName());
	}

	@Test
	public void transferAmountTest() throws Exception {

		Account newAccount1 = new Account("Test", 350);
		Account newAccount2 = new Account("Test2", 500);

		newAccount1.setAccountNo(dao.createAccount(newAccount1));
		newAccount2.setAccountNo(dao.createAccount(newAccount2));

		newAccount1.withdraw(50);
		newAccount2.deposit(50);

		int exec = dao.transferAmount(newAccount1, newAccount2);
		Account account1WithNewBalance = dao.getAccountById(newAccount1.getAccountNo());
		Account account2WithNewBalance = dao.getAccountById(newAccount2.getAccountNo());

		Assert.assertTrue(exec > 0);
		Assert.assertTrue(account1WithNewBalance.getBalance() == 300);
		Assert.assertTrue(account2WithNewBalance.getBalance() == 550);

	}

	@Test
	public void transferAmountInexistingAccountFromTest() throws Exception {

		Account newAccount1 = new Account(10, "Test", 350);
		Account newAccount2 = new Account(11, "Test2", 500);

		int exec = dao.transferAmount(newAccount1, newAccount2);

		Assert.assertTrue(exec < 0);

	}

	@Test
	public void transferAmountInexistingAccountToTest() throws Exception {

		Account newAccount1 = dao.getAccountById(1);
		Account newAccount2 = new Account(11, "Test2", 500);

		int exec = dao.transferAmount(newAccount1, newAccount2);

		Assert.assertTrue(exec < 0);

	}

	@Test
	public void deleteAccountTest() throws Exception {

		int exec = dao.deleteAccount(2);

		Assert.assertTrue(exec > 0);
		Assert.assertTrue(dao.getAccountById(2) == null);

	}

	@Test
	public void deleteNonExistingAccountTest() throws Exception {

		int deleteAccount = dao.deleteAccount(10);

		Assert.assertFalse(deleteAccount > 0);

	}
}
