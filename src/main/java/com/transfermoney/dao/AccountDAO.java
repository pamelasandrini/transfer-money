package com.transfermoney.dao;

import java.util.List;

import com.transfermoney.bo.Account;

public interface AccountDAO {

	/**
	 * Get the account by <code>accountNo</code>
	 * 
	 * @param accountNo
	 * @return null if account not found.
	 */
	public Account getAccountById(long accountNo);

	/**
	 * Get a list of all accounts
	 * 
	 * @return empty list if no account is found
	 */
	public List<Account> getAccountList();

	/**
	 * Create an account
	 * 
	 * @param account
	 * @return the generated id, zero or less than zero if any error has occurred
	 */
	public long createAccount(Account account);

	/**
	 * Delete the account
	 * 
	 * @param accountNo
	 * @return more than zero if account deleted successfully, zero or less than
	 *         zero if any error has occurred
	 */
	public int deleteAccount(long accountNo);

	/**
	 * Update the account balance to <code>newBalance</code>
	 * 
	 * @param accountNo
	 * @param newBalance
	 * @return more than zero if balance is updated successfully, zero or less than
	 *         zero if any error has occurred
	 */
	public int updateBalance(long accountNo, double newBalance);

}
