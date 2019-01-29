package com.transfermoney.bo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountTest {

	private static Account account1;
	private static Account account2;
	private static Account account3;
	private static Account account4;

	@BeforeClass
	public static void createAccount() {
		account1 = new Account("Bill", 1000);
		account2 = new Account("Karen", 2500);
		account3 = new Account("Mike", 400);
		account4 = new Account("Tina", 00);
	}

	@Test
	public void idGenerationTest() {

		Assert.assertTrue(account1.getAccountNo() == 1);
		Assert.assertTrue(account2.getAccountNo() == 2);
		Assert.assertFalse(account1.getAccountNo() == account2.getAccountNo());

	}

	@Test
	public void increaseBalanceTest() throws Exception {

		account1.increaseBalance(1000);
		Assert.assertTrue(account1.getBalance() == 2000);
	}

	@Test
	public void increaseBalanceInvalidValueTest() {

		try {
			account1.increaseBalance(-1000);
		} catch (Exception e) {
			Assert.assertEquals("value not allowed", e.getMessage());
		}

		Assert.assertTrue(account1.getBalance() == 2000);
	}

	@Test
	public void decreaseBalanceTest() throws Exception {

		account2.decreaseBalance(500);

		Assert.assertTrue(account2.getBalance() == 2000);

	}

	@Test
	public void decreaseBalanceValueMoreThanAllowedTest() {
		try {
			account3.decreaseBalance(3000);
		} catch (Exception e) {
			Assert.assertEquals("value not allowed", e.getMessage());
		}

		Assert.assertTrue(account3.getBalance() == 400);
	}

	@Test
	public void decreaseBalanceValueLessThanZeroTest() {
		try {
			account3.decreaseBalance(-40);
		} catch (Exception e) {
			Assert.assertEquals("value not allowed", e.getMessage());
		}

		Assert.assertTrue(account3.getBalance() == 400);
	}

	@Test
	public void decreaseBalanceEmptyBalanceTest() {
		try {
			account4.decreaseBalance(300);
		} catch (Exception e) {
			Assert.assertEquals("value not allowed", e.getMessage());
		}

		Assert.assertTrue(account4.getBalance() == 0);
	}

}
