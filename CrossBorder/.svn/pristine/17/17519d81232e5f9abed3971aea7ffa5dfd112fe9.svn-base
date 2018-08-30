
package com.impalapay.airtel.beans;

import java.io.Serializable;

import java.util.UUID;

/**
 * This class represents an object in the Airtel Api architecture that can be
 * stored in the RDBMS as well as cached.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class StorableBean implements Serializable {
    
    private static final long serialVersionUID = 8318403044459687572L;
	
	private int id;
     
	private String uuid;
	
	/**
	 * 
	 */
	public StorableBean() {
		id = 0;
		uuid = UUID.randomUUID().toString();
	}

	
	/**
	 * @return the id
	 *
	public int getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 *
	public void setId(int id) {
		this.id = id;
	}

	
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
