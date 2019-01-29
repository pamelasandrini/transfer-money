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
	public Account createAccount(Account account) {

		String sql = "INSERT INTO ACCOUNT (ACCOUNT_NO, CUSTOMER_NAME, BALANCE) VALUES (?,?,?)";

		logger.info("calling createAccount : " + account);

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, account.getAccountNo());
			stmt.setString(2, account.getCustomerName());
			stmt.setDouble(3, account.getBalance());

			int exec = stmt.executeUpdate();

			if (exec < 0) {
				if (logger.isDebugEnabled()) {

					logger.debug("account created successfully");
				}
				return account;
			} else {
				if (logger.isDebugEnabled()) {

					logger.debug("error in creating account");
				}
				return null;
			}

		} catch (SQLException e) {

			logger.error("exception in createAccount - " + e);
			return null;
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
	public int updateBalance(long accountNo, double newBalance) {

		String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_NO = ?";

		logger.info("calling updateBalance accountNo [" + accountNo + "] new balance [" + newBalance + "]");

		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);

			stmt.setDouble(1, newBalance);
			stmt.setLong(2, accountNo);

			int executeUpdate = stmt.executeUpdate();

			conn.commit();

			if (logger.isDebugEnabled()) {

				logger.debug("balance updated successfully");
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
