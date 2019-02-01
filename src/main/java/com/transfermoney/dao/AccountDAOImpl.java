package com.transfermoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.transfermoney.bo.Account;

public class AccountDAOImpl implements AccountDAO {

	static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

	@Override
	public Account getAccountById(long accountNo) {

		String sql = "SELECT CUSTOMER_NAME, BALANCE FROM ACCOUNT WHERE ACCOUNT_NO = ?";
		Account account = null;

		logger.info("calling getAccountById : " + accountNo);

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, accountNo);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {

					String customerName = rs.getString("CUSTOMER_NAME");
					double balance = rs.getDouble("BALANCE");

					account = new Account(accountNo, customerName, balance);
				}
			}
			if (logger.isDebugEnabled()) {

				logger.debug("found account " + account);
			}

			return account;

		} catch (SQLException e) {

			logger.error("exception in getAccountById - " + e);
			return null;
		}

	}

	@Override
	public List<Account> getAccountList() {

		String sql = "SELECT ACCOUNT_NO, CUSTOMER_NAME, BALANCE FROM ACCOUNT";
		List<Account> accountList = new ArrayList<>();

		logger.info("calling getAccountList ");

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				long accountNo = rs.getLong("ACCOUNT_NO");
				String customerName = rs.getString("CUSTOMER_NAME");
				double balance = rs.getDouble("BALANCE");

				Account account = new Account(accountNo, customerName, balance);
				accountList.add(account);
			}
			if (logger.isDebugEnabled()) {

				logger.debug("found [" + accountList.size() + "] accounts");
			}

			return accountList;

		} catch (SQLException e) {

			logger.error("exception in getAccountList - " + e);
			return Collections.emptyList();
		}

	}

	@Override
	public long createAccount(Account account) {

		String sql = "INSERT INTO ACCOUNT (CUSTOMER_NAME, BALANCE) VALUES (?,?)";

		logger.info("calling createAccount : " + account);
		ResultSet generatedKeys = null;

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, account.getCustomerName());
			stmt.setDouble(2, account.getBalance());

			int execRow = stmt.executeUpdate();

			if (execRow > 0) {
				generatedKeys = stmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					// return the id
					return generatedKeys.getLong(1);
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug("error in creating account");
			}

			return execRow;

		} catch (SQLException e) {

			logger.error("exception in createAccount - " + e);
			return 0;
		} finally {
			DbUtils.closeQuietly(generatedKeys);
		}

	}

	@Override
	public int deleteAccount(long accountNo) {

		String sql = "DELETE ACCOUNT WHERE ACCOUNT_NO = ?";

		logger.info("calling deleteAccount : " + accountNo);

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, accountNo);

			return stmt.executeUpdate();

		} catch (SQLException e) {

			logger.error("exception in deleteAccount - " + e);
			return -1;
		}

	}

	@Override
	public synchronized int transferAmount(Account accountFrom, Account accountTo) {

		String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_NO = ?";

		logger.info("calling transferAmount");

		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);

			stmt.setDouble(1, accountFrom.getBalance());
			stmt.setLong(2, accountFrom.getAccountNo());

			int executeUpdate = stmt.executeUpdate();

			if (executeUpdate <= 0) {

				conn.rollback();

				return -1;
			}

			stmt.setDouble(1, accountTo.getBalance());
			stmt.setLong(2, accountTo.getAccountNo());

			executeUpdate = stmt.executeUpdate();

			if (executeUpdate > 0) {
				conn.commit();

			} else {
				conn.rollback();

				return -1;
			}

			return executeUpdate;

		} catch (SQLException e) {

			logger.error("exception in updateBalance  - " + e);

			try {

				conn.rollback();

			} catch (SQLException ex) {

				logger.error("exception in rollback transaction - " + ex);
			}

			return -1;
		} finally {

			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(stmt);

		}

	}

}
