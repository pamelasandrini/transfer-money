package com.transfermoney.dao;

import java.util.List;

import com.transfermoney.bo.Account;

public interface AccountDAO {

	public Account getAccountById(long accountNo);

	public List<Account> getAccountList();

	public Account createAccount(Account account);

	public int deleteAccount(long accountNo);

	public int updateBalance(long accountNo, double newBalance);

}
