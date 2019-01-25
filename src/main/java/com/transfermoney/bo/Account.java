package com.transfermoney.bo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * BO class for account
 * 
 * @author pborsoni
 *
 */
public class Account {

	private int accountNo;

	private String customerName;

	private double balance;

	private static AtomicInteger uniqueId = new AtomicInteger();

	public Account(String customerName, double balance) {
		this.customerName = customerName;
		this.balance = balance;

		uniqueId.incrementAndGet();

		this.accountNo = uniqueId.get();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getBalance() {
		return balance;
	}

	public void increaseBalance(double value) {
		this.balance += value;
	}

	public void decreaseBalance(double value) {
		this.balance -= value;
	}

	public int getAccountNo() {
		return accountNo;
	}

	@Override
	public String toString() {
		return "[" + accountNo + " - " + customerName + " : " + balance + "]";
	}

}
