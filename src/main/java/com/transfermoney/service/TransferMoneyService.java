package com.transfermoney.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.transfermoney.TransferThread;
import com.transfermoney.bo.Account;
import com.transfermoney.bo.TransactionHist;
import com.transfermoney.dao.AccountDAO;
import com.transfermoney.dao.AccountDAOImpl;
import com.transfermoney.dao.TransactionHistDAO;
import com.transfermoney.dao.TransactionHistDAOImpl;

@Produces(MediaType.APPLICATION_JSON)
@Path("/transfer")
public class TransferMoneyService {

	private static final Logger logger = Logger.getLogger(TransferMoneyService.class);
	private TransactionHistDAO transactionDao = new TransactionHistDAOImpl();
	private AccountDAO accountDao = new AccountDAOImpl();

	@POST
	public Response transferMoney(TransactionHist transaction) {

		String threadname = Thread.currentThread().getName();
		logger.info(threadname + " - calling transferMoney service");

		double amount = transaction.getAmount();
		if (amount <= 0) {
			logger.error("invalid amount.");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		long accountNoFrom = transaction.getAccountFrom();
		long accountNoTo = transaction.getAccountTo();
		if (accountNoFrom == accountNoTo) {
			logger.error("invalid account. Same account.");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		Account accountFrom = accountDao.getAccountById(accountNoFrom);

		if (accountFrom == null) {
			logger.error("invalid source account.");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		Account accountTo = accountDao.getAccountById(accountNoTo);

		if (accountTo == null) {
			logger.error("invalid target account.");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		if (amount > accountFrom.getBalance() || accountFrom.getBalance() <= 0) {
			logger.error("could not perform the transaction, invalid amount.");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		try {
			TransferThread thread = new TransferThread(accountNoFrom, accountNoTo, amount, accountDao, logger);
			thread.start();

		} catch (Exception e) {
			throw new WebApplicationException("Could not perform the transaction",
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		transactionDao.createTransactionHist(transaction);

		return Response.status(Response.Status.OK).build();
	}

	@GET
	@Path("/all")
	public List<TransactionHist> getAllTransactions() {

		logger.info("calling getAllTransactions service");

		List<TransactionHist> allTransactions = transactionDao.getAllTransactions();

		if (allTransactions == null || allTransactions.isEmpty()) {
			logger.error("no transactions found");
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}

		return allTransactions;
	}
}
