package com.transfermoney.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.transfermoney.bo.Account;
import com.transfermoney.dao.AccountDAO;
import com.transfermoney.dao.AccountDAOImpl;

@Path("/account")
public class AccountService {

	private AccountDAO dao = new AccountDAOImpl();

	@PUT
	@Path("/create")
	public Account createAccount(Account account) {

		System.out.println("calling createAccount : " + account);
		dao.createAccount(account);

		return account;
	}

	@GET
	@Path("/all")
	public List<Account> getAllAccounts() {

		System.out.println("calling getAllAccounts");

		List<Account> accountList = dao.getAccountList();
		System.out.println("accounts: " + accountList);

		return accountList;
	}

	@GET
	@Path("/{accountNo}")
	public Account getAccount(@PathParam("accountNo") long accountNo) {

		System.out.println("calling account service getAccount by id: " + accountNo);

		Account account = dao.getAccountById(accountNo);
		System.out.println("account: " + account);

		return account;
	}

	@GET
	@Path("/{accountNo}/balance")
	public double getBalance(@PathParam("accountNo") long accountNo) {

		System.out.println("calling account service getBalace by id: " + accountNo);

		// TODO: check if that's correct
		Account account = getAccount(accountNo);
		// TODO: throw error Account not found
		System.out.println("balance: " + account.getBalance());

		return account.getBalance();
	}

	@PUT
	@Path("/{accountNo}/deposit/{amount}")
	public Account deposit(@PathParam("accountNo") long accountNo, @PathParam("amount") double amount) {

		Account account = getAccount(accountNo);
		account.increaseBalance(amount);

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
			System.out.println(e);
			return null;
		}

		int updateBalance = dao.updateBalance(accountNo, account.getBalance());

		return null;
	}

	@DELETE
	@Path("/{accountNo}")
	public void deleteAccount(@PathParam("accountNo") long accountNo) {

		System.out.println("calling deleteAccount, accountNo " + accountNo);
		dao.deleteAccount(accountNo);
		// TODO: return status
	}
}
