package com.transfermoney.bo;

import java.sql.Date;

public class TransactionHist {

	private Date timestamp;

	private long accountFrom;

	private long accountTo;

	private double value;

	public TransactionHist(Date timestamp, long accountFrom, long accountTo, double value) {
		super();
		this.timestamp = timestamp;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.value = value;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(long accountFrom) {
		this.accountFrom = accountFrom;
	}

	public long getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(long accountTo) {
		this.accountTo = accountTo;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
