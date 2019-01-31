package com.transfermoney.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.transfermoney.bo.TransactionHist;

public class TransactionHistDAOImplTest {

	private TransactionHistDAO dao = new TransactionHistDAOImpl();

	@BeforeClass
	public static void loadTestData() {
		ConnectionFactory.populateTestData();

	}

	@Test
	public void getAllTransactionsTest() {
		List<TransactionHist> allTransactions = dao.getAllTransactions();

		Assert.assertEquals(0, allTransactions.get(0).getAccountFrom());
		Assert.assertEquals(0, allTransactions.get(0).getAccountTo());
	}

	@Test
	public void createTransactionsTest() {

		TransactionHist transaction = new TransactionHist(1, 2, 600.50);
		int exec = dao.createTransactionHist(transaction);

		Assert.assertTrue(exec >= 0);

	}
}
