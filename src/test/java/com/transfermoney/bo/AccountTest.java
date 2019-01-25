package com.transfermoney.bo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountTest {

	private static Account account1;
	private static Account account2;

	@BeforeClass
	public static void createAccount() {
		account1 = new Account("Bill", 1000);
		account2 = new Account("Karen", 2500);
	}

	@Test
	public void idGenerationTest() {

		Assert.assertTrue(account1.getAccountNo() == 1);
		Assert.assertTrue(account2.getAccountNo() == 2);
		Assert.assertFalse(account1.getAccountNo() == account2.getAccountNo());

	}

	@Test
	public void increaseBalanceTest() {

		account1.increaseBalance(1000);
		Assert.assertTrue(account1.getBalance() == 2000);
	}

	@Test
	public void decreaseBalanceTest() {

		account2.decreaseBalance(2000);
		Assert.assertTrue(account2.getBalance() == 500);
	}

}
