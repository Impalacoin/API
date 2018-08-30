package com.impalapay.airtel.servlet.report.chart.pie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.impalapay.airtel.servlet.api.APIConstants;
import com.impalapay.airtel.accountmgmt.session.SessionStatistics;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.util.SecurityUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

/**
 * Allows for querying of forex through an HTTP API.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class TransactionPieBar extends HttpServlet {

	private Cache accountsCache;

	 private Cache statisticsCache;
	    
	 private String username = "";

	

	/**
	 * 
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		CacheManager mgr = CacheManager.getInstance();
        statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_USERNAME);
	}

		
		

	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 *             , IOException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		
		//username = request.getParameter("username");
		
		username = "demo";

		response.setContentType("text/plain;charset=UTF-8");
		response.setDateHeader("Expires", new Date().getTime()); // Expiration
																	// date
		response.setDateHeader("Date", new Date().getTime()); // Date and time
																// that the
																// message was
																// sent
		out.write(check().getBytes());
		out.flush();
		out.close();
		
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String check(){
		
			Gson g = new GsonBuilder().disableHtmlEscaping().create();
		
		    HashMap<String, Double> countHash = new HashMap<>();
	        Iterator<String> keyIter;
	        String key;
	        Element element;
	        SessionStatistics statistics = null;

	        if ((element = statisticsCache.get(username)) != null) {
	            statistics = (SessionStatistics) element.getObjectValue();
	        }


	        Map<Country, Double> countryTransactionAmount = statistics.getCountryTransactionAmountSuccess();
	        Iterator<Country> transactionIter = countryTransactionAmount.keySet().iterator();
	        Country country;

	        while (transactionIter.hasNext()) {
	            country = transactionIter.next();
	            countHash.put(country.getName(), countryTransactionAmount.get(country));
	        }

		
	        String jsonResult = g.toJson(countHash);

			return jsonResult;

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 *             , IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}