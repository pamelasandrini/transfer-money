package com.transfermoney.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.transfermoney.bo.Account;
import com.transfermoney.bo.TransactionHist;
import com.transfermoney.dao.TransactionHistDAO;
import com.transfermoney.dao.TransactionHistDAOImpl;
import com.transfermoney.util.PropertiesUtil;

@Produces(MediaType.APPLICATION_JSON)
@Path("transfer")
public class TransferMoneyRest {

	private static final String ACCOUNT_SERVICE_URL = PropertiesUtil.getProperty("account_service_url");
	private Client client = ClientBuilder.newClient();

	private static final Logger logger = Logger.getLogger(TransferMoneyRest.class);
	private TransactionHistDAO dao = new TransactionHistDAOImpl();

	@POST
	public int transferMoney(@HeaderParam("accountNoFrom") long accountNoFrom,
			@HeaderParam("accountNoTo") long accountNoTo, @HeaderParam("value") double value) {

		logger.info("calling transferMoney service");

		Account accountFrom = client.target(ACCOUNT_SERVICE_URL).path(String.valueOf(accountNoFrom))
				.request(MediaType.APPLICATION_JSON).get(Account.class);

		Account accountTo = client.target(ACCOUNT_SERVICE_URL).path(String.valueOf(accountNoTo))
				.request(MediaType.APPLICATION_JSON).get(Account.class);

		if (accountFrom == null || accountTo == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		// get accountFrom balance
		// try to withdraw

		// try to deposit

		dao.createTransactionHist(new TransactionHist(accountNoFrom, accountNoTo, value));

		return 0;
	}

	@GET
	@Path("/all")
	public List<TransactionHist> getAllTransactions() {

		logger.info("calling getAllTransactions service");

		List<TransactionHist> allTransactions = dao.getAllTransactions();

		if (allTransactions == null || allTransactions.isEmpty()) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}

		return allTransactions;
	}
}
