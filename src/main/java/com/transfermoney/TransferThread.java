package com.transfermoney;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.transfermoney.bo.Transfer;
import com.transfermoney.dao.AccountDAO;

public class TransferThread extends Thread {

	private long from;
	private long to;
	private double amount;
	private AccountDAO accountDao;
	private Logger logger;

	public TransferThread(long from, long to, double amount, AccountDAO accountDao, Logger logger) {
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.accountDao = accountDao;
		this.logger = logger;

	}

	@Override
	public void run() {

		Transfer t = new Transfer();

		try {
			t.transfer(from, to, amount, logger, accountDao);

		} catch (Exception e) {
			logger.error("an error has occurred while transfering amount.");
			throw new WebApplicationException("Could not perform the transaction",
					Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

}
