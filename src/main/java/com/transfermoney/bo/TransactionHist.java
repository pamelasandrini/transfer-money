package com.transfermoney.bo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionHist {

	@JsonProperty(required = false)
	private Date timestamp;

	@JsonProperty(required = true)
	private long accountFrom;

	@JsonProperty(required = true)
	private long accountTo;

	@JsonProperty(required = true)
	private double amount;

	public TransactionHist() {
	}

	public TransactionHist(long accountFrom, long accountTo, double value) {
		super();
		setDateNow();
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = value;
	}

	public TransactionHist(Date timestamp, long accountFrom, long accountTo, double value) {
		super();
		this.timestamp = timestamp;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = value;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setDateNow() {
		this.timestamp = new Date(System.currentTimeMillis());
	}

	public Date getTimestamp() {
		return timestamp;
	}

}
