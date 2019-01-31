package com.transfermoney.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.transfermoney.bo.Account;
import com.transfermoney.dao.AccountDAO;
import com.transfermoney.dao.AccountDAOImpl;

@Produces(MediaType.APPLICATION_JSON)
@Path("/account")
public class AccountService {

	private static final Logger logger = Logger.getLogger(AccountService.class);

	private AccountDAO dao = new AccountDAOImpl();

	@PUT
	@Path("/create")
	public long createAccount(Account account) {

		logger.info("calling createAccount service");
		long id = dao.createAccount(account);

		if (id <= 0) {
			logger.error("an error has occurred while creating the account");
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}

		return id;
	}

	@GET
	@Path("/all")
	public List<Account> getAllAccounts() {

		logger.info("calling getAllAccounts service");

		List<Account> accountList = dao.getAccountList();
		logger.debug("accounts: " + accountList);

		if (accountList == null || accountList.isEmpty()) {
			logger.error("no accounts found");
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}

		return accountList;
	}

	@GET
	@Path("/{accountNo}")
	public Account getAccount(@PathParam("accountNo") long accountNo) {

		logger.info("calling getAccount service");

		Account account = dao.getAccountById(accountNo);
		logger.debug("account: " + account);

		if (account == null) {
			logger.error("invalid account");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		return account;
	}

	@GET
	@Path("/{accountNo}/balance")
	public double getBalance(@PathParam("accountNo") long accountNo) {

		logger.info("calling getBalance service");

		Account account = getAccount(accountNo);

		if (account == null) {
			logger.error("invalid account");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		logger.info("balance: " + account.getBalance());

		return account.getBalance();
	}

	@DELETE
	@Path("/{accountNo}")
	public Response deleteAccount(@PathParam("accountNo") long accountNo) {

		logger.info("calling deleteAccount service");
		int deleteAccount = dao.deleteAccount(accountNo);

		if (deleteAccount <= 0) {
			logger.error("an error has occurred while deleting the account");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		return Response.status(Response.Status.OK).build();
	}
}
