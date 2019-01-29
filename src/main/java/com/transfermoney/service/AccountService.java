package com.transfermoney.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import com.transfermoney.bo.Account;
import com.transfermoney.dao.AccountDAO;
import com.transfermoney.dao.AccountDAOImpl;

@Path("/account")
public class AccountService {

	private static final Logger logger = Logger.getLogger(AccountService.class);

	private AccountDAO dao = new AccountDAOImpl();

	@PUT
	@Path("/create")
	public Account createAccount(Account account) {

		logger.info("calling createAccount : " + account);
		dao.createAccount(account);

		return account;
	}

	@GET
	@Path("/all")
	public List<Account> getAllAccounts() {

		logger.info("calling getAllAccounts");

		List<Account> accountList = dao.getAccountList();
		logger.debug("accounts: " + accountList);

		return accountList;
	}

	@GET
	@Path("/{accountNo}")
	public Account getAccount(@PathParam("accountNo") long accountNo) {

		logger.info("calling account service getAccount by id: " + accountNo);

		Account account = dao.getAccountById(accountNo);
		logger.debug("account: " + account);

		return account;
	}

	@GET
	@Path("/{accountNo}/balance")
	public double getBalance(@PathParam("accountNo") long accountNo) {

		logger.info("calling account service getBalace by id: " + accountNo);

		// TODO: check if that's correct
		Account account = getAccount(accountNo);
		// TODO: throw error Account not found
		logger.info("balance: " + account.getBalance());

		return account.getBalance();
	}

	@PUT
	@Path("/{accountNo}/deposit/{amount}")
	public Account deposit(@PathParam("accountNo") long accountNo, @PathParam("amount") double amount) {

		Account account = getAccount(accountNo);
		try {
			account.increaseBalance(amount);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

		int updateBalance = dao.updateBalance(accountNo, account.getBalance());

		return null;
	}

	@PUT
	@Path("/{accountNo}/withdraw/{amount}")
	public Account withdraw(@PathParam("accountNo") long accountNo, @PathParam("amount") double amount) {

		Account account = getAccount(accountNo);
		try {
			account.decreaseBalance(amount);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

		int updateBalance = dao.updateBalance(accountNo, account.getBalance());

		return null;
	}

	@DELETE
	@Path("/{accountNo}")
	public void deleteAccount(@PathParam("accountNo") long accountNo) {

		logger.info("calling deleteAccount, accountNo " + accountNo);
		dao.deleteAccount(accountNo);
		// TODO: return status
	}
}
