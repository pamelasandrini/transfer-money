package com.transfermoney.dao;

import java.util.List;

import com.transfermoney.bo.TransactionHist;

public interface TransactionHistDAO {

	/**
	 * Get a list of all transactions in TRANSACTION_HIST.
	 * 
	 * @return
	 */
	public List<TransactionHist> getAllTransactions();

	/**
	 * Create an entry in TRANSACTION_HIST.
	 * 
	 * @param transaction
	 * @return
	 */
	public int createTransactionHist(TransactionHist transaction);

}
