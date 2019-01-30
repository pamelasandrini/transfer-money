package com.transfermoney.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.transfermoney.bo.TransactionHist;

public class TransactionHistDAOImpl implements TransactionHistDAO {

	static final Logger logger = Logger.getLogger(TransactionHistDAOImpl.class);

	@Override
	public List<TransactionHist> getAllTransactions() {

		String sql = "SELECT TIME_STAMP, ACCOUNT_FROM, ACCOUNT_TO, AMOUNT FROM TRANSACTION_HIST";
		List<TransactionHist> transactionList = new ArrayList<>();

		logger.info("calling getAllTransactions ");

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				Date date = rs.getDate("TIME_STAMP");
				long accountFrom = rs.getLong("ACCOUNT_FROM");
				long accountTo = rs.getLong("ACCOUNT_TO");
				double amount = rs.getDouble("AMOUNT");

				TransactionHist transactionHist = new TransactionHist(date, accountFrom, accountTo, amount);
				transactionList.add(transactionHist);
			}
			if (logger.isDebugEnabled()) {

				logger.debug("found [" + transactionList.size() + "] transactions");
			}

			return transactionList;

		} catch (SQLException e) {

			logger.error("exception in getAllTransactions - " + e);
			return Collections.emptyList();
		}

	}

	@Override
	public int createTransactionHist(TransactionHist transaction) {

		String sql = "INSERT INTO TRANSACTION_HIST (TIME_STAMP, ACCOUNT_FROM, ACCOUNT_TO, AMOUNT) VALUES (?,?,?,?)";

		logger.info("calling createTransactionHist : " + transaction);

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setDate(1, transaction.getTimestamp());
			stmt.setLong(2, transaction.getAccountFrom());
			stmt.setLong(3, transaction.getAccountTo());
			stmt.setDouble(4, transaction.getValue());

			return stmt.executeUpdate();

		} catch (SQLException e) {

			logger.error("exception in createAccount - " + e);
			return 0;
		}

	}

}
