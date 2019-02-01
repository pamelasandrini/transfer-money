package com.transfermoney.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.transfermoney.bo.Account;
import com.transfermoney.bo.TransactionHist;
import com.transfermoney.dao.AccountDAO;
import com.transfermoney.dao.AccountDAOImpl;
import com.transfermoney.dao.ConnectionFactory;
import com.transfermoney.dao.TransactionHistDAO;
import com.transfermoney.dao.TransactionHistDAOImpl;

public class TransferMoneyServiceTest extends JerseyTest {

	private AccountDAO accountDao = new AccountDAOImpl();
	private TransactionHistDAO transactionDao = new TransactionHistDAOImpl();

	@Override
	protected Application configure() {
		return new ResourceConfig(TransferMoneyService.class);
	}

	@Before
	public void loadTestData() {
		ConnectionFactory.populateTestData();

	}

	@Ignore
	@Test
	public void transferMoneyMultiThreadTest() {

		Account newAccount1 = new Account("test1", 1000);
		Account newAccount2 = new Account("test2", 0);
		long accountFrom = accountDao.createAccount(newAccount1);
		long accountTo = accountDao.createAccount(newAccount2);

		for (int i = 0; i < 1000; i++) {

			TransactionHist transaction = new TransactionHist(accountFrom, accountTo, 1);

			Response response = target("/transfer").request()
					.post(Entity.entity(transaction, MediaType.APPLICATION_JSON));
			assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());

		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<TransactionHist> allTransactions = transactionDao.getAllTransactions();
		double account1NewBalace = accountDao.getAccountById(accountFrom).getBalance();
		double account2NewBalace = accountDao.getAccountById(accountTo).getBalance();

		assertTrue(allTransactions.size() > 1000);
		assertTrue(account1NewBalace == 0);
		assertTrue(account2NewBalace == 1000);

	}

	@Test
	public void transferMoneyInvalidAmountTest() {

		TransactionHist transaction = new TransactionHist(10, 11, 0);

		Response response = target("/transfer").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));

		assertEquals("Http Response should be 400: ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

	}

	@Test
	public void transferMoneyInvalidAccountFromTest() {

		TransactionHist transaction = new TransactionHist(10, 11, 300);

		Response response = target("/transfer").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));

		assertEquals("Http Response should be 400: ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

	}

	@Test
	public void transferMoneyInvalidAccountToTest() {

		TransactionHist transaction = new TransactionHist(0, 10, 300);

		Response response = target("/transfer").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));

		assertEquals("Http Response should be 400: ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

	}

	@Test
	public void transferMoneyTest() {

		TransactionHist transaction = new TransactionHist(1, 2, 300);

		Response response = target("/transfer").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));

		try {
			// sleep due to persistence
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Account account1NewBalance = accountDao.getAccountById(1);
		Account account2NewBalance = accountDao.getAccountById(2);

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		assertTrue(account1NewBalance.getBalance() == 700);
		assertTrue(account2NewBalance.getBalance() == 2800);

	}

	@Test
	public void transferMoneyInvalidBalanceTest() {
		double account1OldBalance = accountDao.getAccountById(1).getBalance();
		double account2OldBalance = accountDao.getAccountById(2).getBalance();

		TransactionHist transaction = new TransactionHist(1, 2, 2000);

		Response response = target("/transfer").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));

		try {
			// sleep due to persistence
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double account1NewBalance = accountDao.getAccountById(1).getBalance();
		double account2NewBalance = accountDao.getAccountById(2).getBalance();

		assertEquals("Http Response should be 400: ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		assertTrue(account1OldBalance == account1NewBalance);
		assertTrue(account2OldBalance == account2NewBalance);
	}

	@Test
	public void getAllTransactionsTest() {

		Response response = target("/transfer/all").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		List<TransactionHist> content = response.readEntity(new GenericType<List<TransactionHist>>() {
		});

		assertTrue(content.size() > 0);

	}

}
