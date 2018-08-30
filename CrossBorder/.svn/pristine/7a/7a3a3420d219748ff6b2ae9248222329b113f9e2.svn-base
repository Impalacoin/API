package com.impalapay.airtel.util.export.transactions;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the {@link AllTransactionsExportUtil}
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Oct 31, 2013  
 * 
 * @author <a href="mailto:michael@shujaa.co.ke">Michael Wakahe</a>
 * @version %I%, %G%
 * 
 */
public class TestAllTransactionsExportUtil {

	final String CSV_FILE = "/tmp/airtel/Transaction.csv";
	final String EXCEL_FILE = "/tmp/airtel2/Transactions.xlsx";
	
	
	/**
	 * Test method for {@link com.impalapay.airtel.util.export.transactions.AllTransactionsExportUtil#createExcelExport(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCreateExcelExport() {
		assertTrue(AllTransactionsExportUtil.createExcelExport(CSV_FILE, "|", EXCEL_FILE));
	}

}

/*
** Local Variables:
**   mode: java
**   c-basic-offset: 2
**   tab-width: 2
**   indent-tabs-mode: nil
** End:
**
** ex: set softtabstop=2 tabstop=2 expandtab:
**
*/