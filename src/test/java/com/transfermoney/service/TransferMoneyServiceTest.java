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
import org.junit.BeforeClass;
import org.junit.Test;

import com.transfermoney.bo.Account;
import com.transfermoney.bo.TransactionHist;
import com.transfermoney.dao.AccountDAO;
import com.transfermoney.dao.AccountDAOImpl;
import com.transfermoney.dao.ConnectionFactory;

public class TransferMoneyServiceTest extends JerseyTest {

	private AccountDAO dao = new AccountDAOImpl();

	@Override
	protected Application configure() {
		return new ResourceConfig(TransferMoneyService.class);
	}

	@BeforeClass
	public static void loadTestData() {
		ConnectionFactory.populateTestData();

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

		Account newAccount1 = new Account("test1", 1500);
		Account newAccount2 = new Account("test2", 300);
		newAccount1.setAccountNo(dao.createAccount(newAccount1));
		newAccount2.setAccountNo(dao.createAccount(newAccount2));

		TransactionHist transaction = new TransactionHist(newAccount1.getAccountNo(), newAccount2.getAccountNo(), 300);

		Response response = target("/transfer").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));

		Account account1NewBalance = dao.getAccountById(newAccount1.getAccountNo());
		Account account2NewBalance = dao.getAccountById(newAccount2.getAccountNo());

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		assertTrue(account1NewBalance.getBalance() == 1200);
		assertTrue(account2NewBalance.getBalance() == 600);

	}

	@Test
	public void transferMoneyInvalidBalanceTest() {

		Account newAccount1 = new Account("test1", 1500);
		Account newAccount2 = new Account("test2", 300);
		newAccount1.setAccountNo(dao.createAccount(newAccount1));
		newAccount2.setAccountNo(dao.createAccount(newAccount2));

		TransactionHist transaction = new TransactionHist(newAccount1.getAccountNo(), newAccount2.getAccountNo(), 2000);

		Response response = target("/transfer").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));

		Account account1NewBalance = dao.getAccountById(newAccount1.getAccountNo());
		Account account2NewBalance = dao.getAccountById(newAccount2.getAccountNo());

		assertEquals("Http Response should be 400: ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		assertTrue(account1NewBalance.getBalance() == 1500);
		assertTrue(account2NewBalance.getBalance() == 300);
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
