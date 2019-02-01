package com.transfermoney.bo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountTest {

	private static Account account1;
	private static Account account2;

	@BeforeClass
	public static void createAccount() {
		account1 = new Account(1, "Karen", 1000);
		account2 = new Account(2, "Bill", 2500);
	}

	@Test
	public void depositTest() throws Exception {

		account1.deposit(1000);
		Assert.assertTrue(account1.getBalance() == 2000);
	}

	@Test
	public void withdrawTest() throws Exception {

		account2.withdraw(500);

		Assert.assertTrue(account2.getBalance() == 2000);

	}

}
