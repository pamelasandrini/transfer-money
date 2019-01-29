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

import com.google.gson.Gson;
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
	public Response createAccount(Account account) {

		logger.info("calling createAccount service");
		Account accountCreated = dao.createAccount(account);

		return Response.status(accountCreated == null ? Response.Status.NO_CONTENT : Response.Status.OK).build();
	}

	@GET
	@Path("/all")
	public String getAllAccounts() {

		logger.info("calling getAllAccounts service");

		List<Account> accountList = dao.getAccountList();
		logger.debug("accounts: " + accountList);

		if (accountList == null || accountList.isEmpty()) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}

		return new Gson().toJson(accountList);
	}

	@GET
	@Path("/{accountNo}")
	public String getAccount(@PathParam("accountNo") long accountNo) {

		logger.info("calling getAccount service");

		Account account = dao.getAccountById(accountNo);
		logger.debug("account: " + account);

		if (account == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		return new Gson().toJson(account);
	}

	@GET
	@Path("/{accountNo}/balance")
	public String getBalance(@PathParam("accountNo") long accountNo) {

		logger.info("calling getBalance service");

		Account account = new Gson().fromJson(getAccount(accountNo), Account.class);

		if (account == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		logger.info("balance: " + account.getBalance());

		return new Gson().toJson(account.getBalance());
	}

	@PUT
	@Path("/{accountNo}/deposit/{amount}")
	public Response deposit(@PathParam("accountNo") long accountNo, @PathParam("amount") double amount) {

		logger.info("calling deposit service");

		Account account = new Gson().fromJson(getAccount(accountNo), Account.class);

		if (account == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		try {
			account.increaseBalance(amount);
		} catch (Exception e) {
			logger.error(e);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		int updateBalance = dao.updateBalance(accountNo, account.getBalance());

		return Response.status(updateBalance > 0 ? Response.Status.OK : Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	@PUT
	@Path("/{accountNo}/withdraw/{amount}")
	public Response withdraw(@PathParam("accountNo") long accountNo, @PathParam("amount") double amount) {

		logger.info("calling withdraw service");

		Account account = new Gson().fromJson(getAccount(accountNo), Account.class);

		if (account == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		try {
			account.decreaseBalance(amount);
		} catch (Exception e) {
			logger.error(e);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		int updateBalance = dao.updateBalance(accountNo, account.getBalance());

		return Response.status(updateBalance > 0 ? Response.Status.OK : Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	@DELETE
	@Path("/{accountNo}")
	public Response deleteAccount(@PathParam("accountNo") long accountNo) {

		logger.info("calling deleteAccount service");
		dao.deleteAccount(accountNo);

		return Response.status(Response.Status.OK).build();
	}
}
