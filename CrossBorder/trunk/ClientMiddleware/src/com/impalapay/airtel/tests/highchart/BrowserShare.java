package com.impalapay.airtel.tests.highchart;

/**
 * Test our {@link com.impalapay.airtel.persistence.accountmgmt.AccountStatusDAO}
 * <p>
 * Copyright (c) ImpalaPay LTD., November 05, 2014
 *
 * @author <a href="mailto:kmuli@impalapay.com">Kelvin Muli</a>
 * 
 */


public class BrowserShare {
	
	//Browser name
	 private String name;
	    //Share
	    private float share;
	    
	    public BrowserShare(String name, float share) {
	        super();
	        this.name = name;
	        this.share = share;
	    }
	    
	    public float getShare() {
	        return share;
	    }

	    public void setShare(float share) {
	        this.share = share;
	    }

	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
	    

}
