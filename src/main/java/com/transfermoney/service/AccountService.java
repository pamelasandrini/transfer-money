package com.transfermoney.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.transfermoney.bo.Account;

@Path("/account")
public class AccountService {

	@PUT
	@Path("/create")
	public Account createAccount(Account account) {
		// TODO: insert account in db
		return account;
	}

	@GET
	@Path("/all")
	public List<Account> getAllAccounts() {
		// TODO: get db connection and return all accounts
		return null;
	}

	@GET
	@Path("/{accountNo}")
	public Account getAccount(@PathParam("accountNo") int accountNo) {
		// TODO: get db connection and return the account by id

		System.out.println("calling account service getAccount by id: " + accountNo);

		return null;
	}

	@GET
	@Path("/{accountNo}/balance")
	public double getBalance(@PathParam("accountNo") int accountNo) {
		// throw error Account not found
		// get the account balance

		System.out.println("calling account service getBalace by id: " + accountNo);

		return 0.0;
	}

	@PUT
	@Path("/{accountNo}/deposit/{amount}")
	public Account deposit(@PathParam("accountNo") int accountNo, @PathParam("amount") double amount) {

		// TODO: get account by accountNo
		// TODO: increase balance account.increaseBalance(amount);
		return null;
	}

	@PUT
	@Path("/{accountNo}/withdraw/{amount}")
	public Account withdraw(@PathParam("accountNo") int accountNo, @PathParam("amount") double amount) {

		// TODO: get account by accountNo
		// TODO: decrease balance account.decreaseBalance(amount);
		return null;
	}

	@DELETE
	@Path("/{accountNo}")
	public void deleteAccount(@PathParam("accountNo") int accountNo) {
		// TODO: delete account from db
		// return status
	}
}
