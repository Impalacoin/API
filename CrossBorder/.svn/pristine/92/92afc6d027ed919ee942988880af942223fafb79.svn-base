package com.impalapay.airtel.beans.transaction;

import java.util.Date;

import com.impalapay.airtel.beans.StorableBean;
/**
 *  represents a  transaction 
 * <p>
 * Copyright (c) impalapay Ltd., June 24, 2014
 * 
 * @author <a href="mailto:eugenechimita@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */

public class Transaction extends StorableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountUuid;
	private String sourceCountrycode;
	private String senderName;
	private String recipientMobile;
	private String senderToken;
	private String currencyCode;
	private String recipientcountryUuid;
	private String referenceNumber;
	private String transactionStatusUuid;
	private String clientTime;
	
	
	private double amount;
	private Date serverTime;
	
	public Transaction(){
		
		accountUuid = "";
		sourceCountrycode = "";
		senderName ="";
		recipientMobile ="";
		senderToken = "";
		currencyCode = "";
		recipientcountryUuid = "";
		amount = 0;
		clientTime = "";
		serverTime = new Date();
		referenceNumber = "";
        transactionStatusUuid ="";
		
	}

	public String getAccountUuid() {
		return accountUuid;
	}

	public String getSourceCountrycode() {
		return sourceCountrycode;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getRecipientMobile() {
		return recipientMobile;
	}

	public String getSenderToken() {
		return senderToken;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getRecipientCountryUuid() {
		return recipientcountryUuid;
	}

	public double getAmount() {
		return amount;
	}

	public String getClientTime() {
		return clientTime;
	}

	public Date getServerTime() {
		return new Date(serverTime.getTime());
	}
    
	public String getReferenceNumber() {
		return referenceNumber;
	}
	
	public String getTransactionStatusUuid() {
		return transactionStatusUuid;
	}
	
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	public void setSourceCountrycode(String sourceCountrycode) {
		this.sourceCountrycode = sourceCountrycode;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public void setRecipientMobile(String recipientMobile) {
		this.recipientMobile = recipientMobile;
	}

	public void setSenderToken(String senderToken) {
		this.senderToken = senderToken;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setRecipientCountryUuid(String recipientCountryUuid) {
		this.recipientcountryUuid = recipientCountryUuid;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setClientTime(String clientTime) {
		this.clientTime =clientTime;
	}

	public void setServerTime(Date d) {
		if (d != null) {
			serverTime = new Date(d.getTime());
		}
	}
	
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
    
	public void setTransactionStatusUuid(String transactionStatusUuid) {
		this.transactionStatusUuid = transactionStatusUuid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [getUuid()=");
		builder.append(getUuid());
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append(", sourceCountrycode=");
		builder.append(sourceCountrycode);
		builder.append(", senderName=");
		builder.append(senderName);
		builder.append(", recipientMobile=");
		builder.append(recipientMobile);
		builder.append(", senderToken=");
		builder.append(senderToken);
		builder.append(", currencyCode=");
		builder.append(currencyCode);
		builder.append(", recipientcountryUuid=");
		builder.append(recipientcountryUuid);
		builder.append(", referenceNumber=");
		builder.append(referenceNumber);
		builder.append(", transactionStatusUuid=");
		builder.append(transactionStatusUuid);
		builder.append(", clientTime=");
		builder.append(clientTime);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", serverTime=");
		builder.append(serverTime);
		builder.append("]");
		return builder.toString();
	}

	
/**
 * @see java.lang.Object#toString()

	
	@Override
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [getUuid()=");
		builder.append(getUuid());
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append(", sourceCountrycode=");
		builder.append(sourceCountrycode);
		builder.append(", senderName=");
		builder.append(senderName);
		builder.append(", recipientMobile=");
		builder.append(recipientMobile);
		builder.append(", senderToken=");
		builder.append(senderToken);
		builder.append(", currencyCode=");
		builder.append(currencyCode);
		builder.append(", recipientcountryUuid=");
		builder.append(recipientcountryUuid);
		builder.append(", referenceNumber=");
		builder.append(referenceNumber);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", clientTime=");
		builder.append(clientTime);
		builder.append(", serverTime=");
		builder.append(serverTime);
		builder.append("]");
		return builder.toString();
	}
 */
}
