package com.transfermoney.bo;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountTest {

	private static final Logger logger = Logger.getLogger(AccountTest.class);
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

		try {
			account2.decreaseBalance(2000);
		} catch (Exception e) {
			logger.error("Unexpected error in decreaseBalanceTest - " + e);
		}
		Assert.assertTrue(account2.getBalance() == 500);

		try {
			account2.decreaseBalance(600);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "value not allowed");
		}

		Assert.assertTrue(account2.getBalance() == 500);
	}

}
