package com.transfermoney.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("transfer")
public class TransferMoneyRest {

	// TODO: get the account from, the account to and the amount
	// checks if the balance accountFrom is greater or equals the amount
	// decrease the balance accountFrom
	// increase the accountTo
	
	@POST
	public int transferMoney(@PathParam("accountFrom") long accountFrom, @PathParam("accountTo") long accountTo, @PathParam("value") double value)
	{
//		AccountService
		
		return 0;
	}
}
