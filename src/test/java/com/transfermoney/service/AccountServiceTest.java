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
import org.junit.Test;

import com.transfermoney.bo.Account;
import com.transfermoney.dao.AccountDAO;
import com.transfermoney.dao.AccountDAOImpl;
import com.transfermoney.dao.ConnectionFactory;

public class AccountServiceTest extends JerseyTest {

	private AccountDAO dao = new AccountDAOImpl();

	@Override
	protected Application configure() {
		return new ResourceConfig(AccountService.class);
	}

	@Before
	public void loadTestData() {
		ConnectionFactory.populateTestData();

	}

	@Test
	public void getAllAccountsEmptyListTest() {

		dao.deleteAccount(0);
		Response response = target("/account/all").request().get();

		assertEquals("Http Response should be 204: ", Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void getAllAccountsTest() {
		Response response = target("/account/all").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		List<Account> content = response.readEntity(new GenericType<List<Account>>() {
		});
		assertTrue(content.size() > 0);
	}

	@Test
	public void getAccountsTest() {
		Response response = target("/account/0").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		Account content = response.readEntity(Account.class);
		assertEquals(0, content.getAccountNo());
	}

	@Test
	public void getInexistingAccountsTest() {
		Response response = target("/account/10").request().get();

		assertEquals("Http Response should be 400: ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

	}

	@Test
	public void createAccountsTest() {

		Account account = new Account("test1", 250);

		Response response = target("/account/create").request().put(Entity.entity(account, MediaType.APPLICATION_JSON));

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		Long content = response.readEntity(long.class);

		assertEquals(1, content.longValue());
	}

	@Test
	public void getBalanceTest() {
		Response response = target("/account/0/balance").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		Double content = response.readEntity(double.class);
		assertTrue(content == 1500);
	}

	@Test
	public void getBalanceInexistingAccountTest() {
		Response response = target("/account/10/balance").request().get();

		assertEquals("Http Response should be 400: ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

	}

	@Test
	public void deleteAccountTest() {

		Response response = target("/account/0").request().delete();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());

	}

	@Test
	public void deleteInexistingAccountTest() {

		Response response = target("/account/10").request().delete();

		assertEquals("Http Response should be 400: ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());

	}
}
