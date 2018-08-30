package com.impalapay.airtel.servlet.export.excel;

import com.impalapay.airtel.accountmgmt.session.SessionConstants;
import com.impalapay.airtel.beans.transaction.TransactionStatus;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.accountmgmt.pagination.TransactionPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.persistence.util.DbFileUtils;
import com.impalapay.airtel.util.export.ZipUtil;
import com.impalapay.airtel.util.export.transactions.AllTransactionsExportUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Allows the client to export a list of Transaction activity to a Microsoft Excel 
 * sheet.
 * <p>
 * For a list of HTTP header fields, see
 * <a href="http://en.wikipedia.org/wiki/List_of_HTTP_header_fields">
 * 	http://en.wikipedia.org/wiki/List_of_HTTP_header_fields}
 * </a>
 * <p>
 * For a list of Microsoft Office MIME types, see 
 * <a href="http://bit.ly/aZQzzH">http://bit.ly/aZQzzH</a>
 * <p>
 * Copyright (c) ImpalaPAY Ltd., Jan 31, 2014
 *
 * 
 * @author <a href="mailto:michael@impalapay.com">Michael Wakahe</a>
 * @version %I%, %G%
 */
public class ExportExcel extends HttpServlet {

    private final String SPREADSHEET_NAME = "IMTExport.xlsx";
    private static final long serialVersionUID = 3896751907947782599L;
    
    private Cache accountsCache, countrysCache, transactionStatusCache;
    
    // This is a mapping between the UUIDs of countries and their names
    private HashMap<String,String> countryHash;
    
    // This is a mapping between the UUIDs of TransactionStatuses and their status in English
    private HashMap<String,String> transactionStatusHash;
    
    private DbFileUtils dbFileUtils;
    
    
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        CacheManager mgr = CacheManager.getInstance();
        accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);
        countrysCache = mgr.getCache(CacheVariables.CACHE_COUNTRY_BY_UUID);
        transactionStatusCache = mgr.getCache(CacheVariables.CACHE_TRANSACTIONSTATUS_BY_UUID);

        countryHash = new HashMap<>();
        transactionStatusHash = new HashMap<>();
        
        List keys = countrysCache.getKeys();
        Element element;
        Country country;
        
        for (Object key : keys) {
            element = countrysCache.get(key);
            country = (Country) element.getObjectValue();
            countryHash.put(country.getUuid(), country.getName());
        }
        
        TransactionStatus transactionStatus;
        keys = transactionStatusCache.getKeys();
        
        for (Object key : keys) {
            element = transactionStatusCache.get(key);
            transactionStatus = (TransactionStatus) element.getObjectValue();
            transactionStatusHash.put(transactionStatus.getUuid(), transactionStatus.getDescription());
        }
        
        dbFileUtils = DbFileUtils.getInstance();
    }

    
    /**
     * Returns a zipped MS Excel file of the data specified for exporting.
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/zip");
        response.setHeader("Cache-Control", "cache, must-revalidate");
        response.setHeader("Pragma", "public");
                
        HttpSession session = request.getSession(false);
        Account account;
        String fileName;
        
        String exportExcelOption = request.getParameter("exportExcel");

        String sessionUsername = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY);

        Element element = accountsCache.get(sessionUsername);
        account = (Account) element.getObjectValue();

        fileName = new StringBuffer(account.getFirstName()).append(" ")
                .append(StringUtils.trimToEmpty(account.getLastName()))
                .append(" ")
                .append(SPREADSHEET_NAME)
                .toString();

        response.setHeader("Content-Disposition", "attachment; filename=\""
                + StringUtils.replace(fileName, ".xlsx", ".zip") + "\"");
        

        File excelFile = new File(FileUtils.getTempDirectoryPath() + File.separator + fileName);
        File csvFile = new File(StringUtils.replace(excelFile.getCanonicalPath(), ".xlsx", ".csv"));
        File zippedFile = new File(StringUtils.replace(excelFile.getCanonicalPath(), ".xlsx", ".zip"));
                        
        
        // These are to determine whether or not we have created a CSV & Excel file on disk
        boolean successCSVFile = true, successExcelFile = true;	
        
        if (StringUtils.equalsIgnoreCase(exportExcelOption, "Export All")) { //export all pages
        	successCSVFile = dbFileUtils.sqlResultToCSV(getExportTransactionsSqlQuery(account), 
        			csvFile.toString(), '|');
            
        	if(successCSVFile) {
        		successExcelFile = AllTransactionsExportUtil.createExcelExport(csvFile.toString(), "|", excelFile.toString()); 
        	}    	        	
        			
        } else if(StringUtils.equalsIgnoreCase(exportExcelOption, "Export Page")) { //export a single page
            
        	TransactionPage transactionPage = (TransactionPage) session.getAttribute("currentTransactionPage");

            successExcelFile = AllTransactionsExportUtil.createExcelExport(transactionPage.getContents(), countryHash,
            		transactionStatusHash, "|", excelFile.toString());
            
        } else  {	//export search results
            
        	TransactionPage transactionPage = (TransactionPage) session.getAttribute("currentSearchPage");

            successExcelFile = AllTransactionsExportUtil.createExcelExport(transactionPage.getContents(), countryHash,
            		transactionStatusHash, "|", excelFile.toString());            
        }

        
        if(successExcelFile) { // If we successfully created the MS Excel File on disk  
        	// Zip the Excel file
        	List<File> filesToZip = new ArrayList<>();
        	filesToZip.add(excelFile);
        	ZipUtil.compressFiles(filesToZip, zippedFile.toString());
        	
        	// Push the file to the request
            FileInputStream input = FileUtils.openInputStream(zippedFile);
            IOUtils.copy(input, out);
        }
        
        out.close();
        
        FileUtils.deleteQuietly(excelFile);
        FileUtils.deleteQuietly(csvFile);
        FileUtils.deleteQuietly(zippedFile);
    }

    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    
    /**
     * Gets the String that will be used to export all the topup activity of an 
     * account holder.
     * <p>
     * Note that it is tied to the design of the database.
     * 
     * @param account
     * @return the SQL query to be used
     * 
     * 
     */
    

	
    private String getExportTransactionsSqlQuery(Account account) {
    	
    	StringBuffer query = new StringBuffer("SELECT transaction.uuid,transaction.sourceCountrycode,transaction.senderName,transaction.recipientMobile, ")
		.append("transaction.senderToken,transaction.currencyCode,transaction.recipientcountryUuid,transaction.referenceNumber,")
		.append("transaction.transactionStatusUuid,transaction.clientTime ")
		.append("FROM transaction ")
		.append("INNER JOIN country ON recipientcountryUuid=country.uuid ")
		.append("INNER JOIN transactionStatus ON transaction.transactionStatusUuid=transactionStatus.uuid ")
		.append("WHERE accountUuid = '")
		.append(account.getUuid())
		.append("';");
    	
    	return query.toString();
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
