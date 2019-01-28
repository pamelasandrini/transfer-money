package com.transfermoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.transfermoney.bo.Account;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public Account getAccountById(long accountNo) {

		String sql = "SELECT CUSTOMER_NAME, BALANCE FROM ACCOUNT WHERE ACCOUNT_NO = ?";
		Account account = null;

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, accountNo);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {

					String customerName = rs.getString("CUSTOMER_NAME");
					double balance = rs.getDouble("BALANCE");

					account = new Account(accountNo, customerName, balance);
				}
			}

			return account;

		} catch (SQLException e) {

			System.out.println("exception in getAccountById " + e);
			return null;
		}

	}

	@Override
	public List<Account> getAccountList() {

		String sql = "SELECT ACCOUNT_NO, CUSTOMER_NAME, BALANCE FROM ACCOUNT";
		List<Account> accountList = new ArrayList<>();

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

			return accountList;

		} catch (SQLException e) {

			System.out.println("exception in getAccountList " + e);
			return Collections.emptyList();
		}

	}

	@Override
	public Account createAccount(Account account) {

		String sql = "INSERT INTO ACCOUNT (ACCOUNT_NO, CUSTOMER_NAME, BALANCE) VALUES (?,?,?)";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, account.getAccountNo());
			stmt.setString(2, account.getCustomerName());
			stmt.setDouble(3, account.getBalance());

			int exec = stmt.executeUpdate();

			if (exec < 0) {
				return account;
			} else {
				return null;
			}

		} catch (SQLException e) {

			System.out.println("exception in createAccount " + e);
			return null;
		}

	}

	@Override
	public int deleteAccount(long accountNo) {

		String sql = "DELETE ACCOUNT WHERE ACCOUNT_NO = ?";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, accountNo);

			return stmt.executeUpdate();

		} catch (SQLException e) {

			System.out.println("exception in deleteAccount " + e);
			return -1;
		}

	}

	@Override
	public int updateBalance(long accountNo, double newBalance) {

		String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_NO = ?";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setDouble(1, newBalance);
			stmt.setLong(2, accountNo);

			int executeUpdate = stmt.executeUpdate();

			conn.commit();

			return executeUpdate;

		} catch (SQLException e) {

			System.out.println("exception in updateBalance " + e);

			// TODO: use hibernate instead
//			conn.rollback();
			return -1;
		}

	}

}
