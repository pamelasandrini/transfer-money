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

import com.transfermoney.bo.Account;
import com.transfermoney.bo.TransactionHist;
import com.transfermoney.dao.AccountDAO;
import com.transfermoney.dao.AccountDAOImpl;
import com.transfermoney.dao.TransactionHistDAO;
import com.transfermoney.dao.TransactionHistDAOImpl;
import com.transfermoney.util.PropertiesUtil;

@Produces(MediaType.APPLICATION_JSON)
@Path("/transfer")
public class TransferMoneyService {

	public static final String ACCOUNT_SERVICE_URL = PropertiesUtil.getProperty("account_service_url");

	private static final Logger logger = Logger.getLogger(TransferMoneyService.class);
	private TransactionHistDAO transactionDao = new TransactionHistDAOImpl();
	private AccountDAO accountDao = new AccountDAOImpl();

	@POST
	public Response transferMoney(TransactionHist transaction) {

		logger.info("calling transferMoney service");

		if (transaction.getAccountFrom() == transaction.getAccountTo() || transaction.getValue() <= 0) {
			logger.error("invalid account or invalid amount");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		Account accountFrom = accountDao.getAccountById(transaction.getAccountFrom());

		if (accountFrom == null) {
			logger.error("invalid accountFrom");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		Account accountTo = accountDao.getAccountById(transaction.getAccountTo());

		if (accountTo == null) {
			logger.error("invalid accountTo");
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		try {
			accountFrom.decreaseBalance(transaction.getValue());
			accountTo.increaseBalance(transaction.getValue());
		} catch (Exception e) {
			logger.error("invalid amount");
			throw new WebApplicationException("Could not perform the transaction", Response.Status.BAD_REQUEST);
		}

		int transferExec = accountDao.transferAmount(accountFrom, accountTo);

		if (transferExec <= 0) {
			logger.error("an error has occurred while transfering amount");
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