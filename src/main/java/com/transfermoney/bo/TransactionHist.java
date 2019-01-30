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
	private double value;

	public TransactionHist() {
	}

	public TransactionHist(long accountFrom, long accountTo, double value) {
		super();
		setDateNow();
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.value = value;
	}

	public TransactionHist(Date timestamp, long accountFrom, long accountTo, double value) {
		super();
		this.timestamp = timestamp;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.value = value;
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

	public void setDateNow() {
		this.timestamp = new Date(System.currentTimeMillis());
	}

	public Date getTimestamp() {
		return timestamp;
	}

}
