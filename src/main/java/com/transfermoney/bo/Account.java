package com.transfermoney.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * BO class for account
 * 
 * @author pborsoni
 *
 */
public class Account {

	@JsonProperty(required = false)
	private long accountNo;

	@JsonProperty(required = true)
	private String customerName;

	@JsonProperty(required = true)
	private double balance;

	public Account() {
	}

	public Account(String customerName, double balance) {
		this.customerName = customerName;
		this.balance = balance;
	}

	public Account(long accountNo, String customerName, double balance) {
		this.accountNo = accountNo;
		this.customerName = customerName;
		this.balance = balance;
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

	public void increaseBalance(double value) throws Exception {

		if (value > 0) {
			this.balance += value;
		} else {
			throw new Exception("value not allowed");
		}
	}

	public void decreaseBalance(double value) throws Exception {

		if (balance > 0 && value <= balance && value > 0) {
			this.balance -= value;
		} else {
			throw new Exception("value not allowed");
		}
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
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
