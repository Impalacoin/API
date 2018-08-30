package com.impalapay.airtel.beans.topup;

import com.impalapay.airtel.beans.StorableBean;

import java.util.Date;

/**
 * Represents a client topup.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class Topup extends StorableBean {

	private String accountUuid;
	private Date topupTime;
	private double amount;

	
	/**
	 * 
	 */
	public Topup() {
		super();
		
		accountUuid = "";
		topupTime = new Date();
		amount = 0;
	}

    /**
     * 
     * @return the accountuuid
     */
	public String getAccountUuid() {
		return accountUuid;
	}

    /**
     * 
     * @return topuptime
     */
	public Date getTopupTime() {
		return new Date(topupTime.getTime());
	}

    /**
     * 
     * @return the amount
     */
	public double getAmount() {
		return amount;
	}

    /**
     * 
     * @param accountUuid
     * 					the accountUuid to set
     */
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

    /**
     * 
     * @param d
     * 		   
     */
	public void setTopupTime(Date d) {
		if (d != null) {
			topupTime = new Date(d.getTime());
		}
	}

    /**
     * 
     * @param amount
     * 				the amount to set
     */
	public void setAmount(double amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Topup [getUuid()=");
		builder.append(getUuid());
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append(", topupTime=");
		builder.append(topupTime);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}
    
	
	
		
}
