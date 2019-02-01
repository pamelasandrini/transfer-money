package com.transfermoney.bo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.transfermoney.dao.AccountDAO;

public class Transfer {

	public void transfer(long from, long to, double amount, Logger logger, AccountDAO accountDao) {

		synchronized (Transfer.class) {

			try {

				Account accountFrom = accountDao.getAccountById(from);
				Account accountTo = accountDao.getAccountById(to);

				if (amount > accountFrom.getBalance() || accountFrom.getBalance() <= 0) {
					logger.error("could not perform the transaction, invalid amount.");
					throw new WebApplicationException(Response.Status.BAD_REQUEST);
				}

				String threadName = Thread.currentThread().getName();
				logger.info(threadName + " - balance before transfer: " + accountFrom + " : " + accountTo);

				accountFrom.withdraw(amount);
				accountTo.deposit(amount);

				int exec = accountDao.transferAmount(accountFrom, accountTo);
				if (exec <= 0) {
					logger.error("could not perform the transaction, error in data persistence.");
					throw new WebApplicationException("Could not perform the transaction",
							Response.Status.INTERNAL_SERVER_ERROR);
				}

				double total = accountFrom.getBalance() + accountTo.getBalance();
				logger.info(threadName + " - balance after transfer: " + accountFrom + " : " + accountTo + " total: "
						+ total);

			} catch (Exception e) {
				logger.error("could not perform the transaction.");
				throw new WebApplicationException("Could not perform the transaction",
						Response.Status.INTERNAL_SERVER_ERROR);
			}
		}
	}

}
