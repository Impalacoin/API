package com.impalapay.airtel.beans.simulation;

import com.impalapay.airtel.beans.StorableBean;

/**
 * Represents a simulation of comviva response codes
 * <p>
 * Copyright (c) ImpalaPay Ltd.,Feb 19,2015
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */

public class ComvivaSimulation extends StorableBean{
	
	private String mobilenumber;
	private String errorcode;
	private String errorname;
	
	public ComvivaSimulation() {
		super();
	
		mobilenumber = "";
		errorcode = "";
		errorname = "";
		
	}
    
	/**
	 * 
	 * @return
	 */
	public String getMobilenumber() {
		return mobilenumber;
	}
    
	/**
	 * 
	 * @return
	 */
	public String getErrorcode() {
		return errorcode;
	}
    
	/**
	 * 
	 * @return
	 */
	public String getErrorname() {
		return errorname;
	}
    
	/**
	 * 
	 * @param mobilenumber
	 */
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
    
	/**
	 * 
	 * @param errorcode
	 */
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
    
	/**
	 * 
	 * @param errorname
	 */
	public void setErrorname(String errorname) {
		this.errorname = errorname;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComvivaSimulation [getUuid()=");
		builder.append(getUuid());
		builder.append(", mobilenumber=");
		builder.append(mobilenumber);
		builder.append(", errorcode=");
		builder.append(errorcode);
		builder.append(", errorname=");
		builder.append(errorname);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
