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

	private long accountNo;

	private String customerName;

	private double balance;

	private static AtomicInteger uniqueId = new AtomicInteger();

	public Account(String customerName, double balance) {
		this.customerName = customerName;
		this.balance = balance;

		uniqueId.incrementAndGet();

		this.accountNo = uniqueId.get();
	}

	public Account(long accountNo, String customerName, double balance) {
		this.customerName = customerName;
		this.balance = balance;

		this.accountNo = accountNo;
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

	public void decreaseBalance(double value) throws Exception {

		if (balance > 0 && value <= balance) {
			this.balance -= value;
		} else {
			throw new Exception("value not allowed");
		}
	}

	public long getAccountNo() {
		return accountNo;
	}

	@Override
	public String toString() {
		return "[" + accountNo + " - " + customerName + " : " + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountNo ^ (accountNo >>> 32));
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNo != other.accountNo)
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		return true;
	}

}
