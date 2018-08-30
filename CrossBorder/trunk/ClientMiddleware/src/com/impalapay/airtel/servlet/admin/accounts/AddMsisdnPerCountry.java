package com.impalapay.airtel.servlet.admin.accounts;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.impalapay.airtel.accountmgmt.admin.SessionConstants;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.beans.geolocation.CountryMsisdn;
import com.impalapay.airtel.persistence.geolocation.CountryMsisdnDAO;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

/**
 * Servlet that receives parameters from an administrative web form that are
 * used to add an msisdn to a specific country.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Oct 11, 2014
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class AddMsisdnPerCountry extends HttpServlet {
    
    final String ERROR_NO_MSISDN = "Please provide a value for the msisdn ";
    final String ERROR_INVALID_MSISDN = "Please provide a valid  msisdn .";
    final String ERROR_UNABLE_ADD = "Unable to add msisdn to country";
    // These represent form parameters
    private String accountUuid, countryUuid, msisdn;
    private String addDay, addMonth, addYear;
    
    // This is used to store parameter names and values from the form.
    private HashMap<String, String> CountrymsisdnparamHash;
    
    private Cache purchasesCache;
    private CountryMsisdnDAO countrymsisdnDAO;
    
    private Logger logger;
    
    
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        CacheManager mgr = CacheManager.getInstance();
        purchasesCache = mgr.getCache(CacheVariables.CACHE_FLOATPURCHASEPERCOUNTRY_BY_ACCOUNTUUID);

        countrymsisdnDAO = CountryMsisdnDAO.getInstance();
        
        logger=Logger.getLogger(this.getClass());
    }
   

   
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      doPost(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        setClassParameters(request);

        initParamHash();
        session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_MSISDN_PARAMETERS, CountrymsisdnparamHash);

        if (StringUtils.isBlank(msisdn)) {
            session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_MSISDN_ERROR_KEY, ERROR_NO_MSISDN);

        } else if (!StringUtils.isNumeric(msisdn)) {
            session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_MSISDN_ERROR_KEY, ERROR_INVALID_MSISDN);

        }else if(!addMsisdnToCountry()){
            session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_MSISDN_ERROR_KEY, ERROR_UNABLE_ADD);
        
        } else {
            // If we get this far then all parameter checks are ok.		
            session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_MSISDN_SUCCESS_KEY, "s");

            // Reduce our session data
            session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_MSISDN_PARAMETERS, null);
            session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_MSISDN_ERROR_KEY, null);

            
        }


        response.sendRedirect("addCountryMsisdn.jsp");


        //purchasesCache.put(new Element(CacheVariables.CACHE_PURCHASEPERCOUNTRY_KEY, accountPurchaseDAO.getAllClientPurchasesByCountry()));
    }
    
    
    /**
     * Add amount added to each country float.
     * 
     * @return boolean indicating if addition has
     * been added or not.
     */
    private boolean addMsisdnToCountry() {
  
    	CountryMsisdn p = new CountryMsisdn();
        
        
        p.setAccountUuid(accountUuid);
        p.setCountryUuid(countryUuid);
        p.setMsisdn(msisdn);
        Calendar c = Calendar.getInstance();
        c.set(NumberUtils.toInt(addYear), NumberUtils.toInt(addMonth) - 1, NumberUtils.toInt(addDay));
        p.setCreationDate(c.getTime());
        return countrymsisdnDAO.putCountryMsisdn(p);

    }

    /**
     * Set the class variables that represent form parameters.
     *
     * @param request
     */
    private void setClassParameters(HttpServletRequest request) {
        accountUuid = StringUtils.trimToEmpty(request.getParameter("accountUuid"));
        countryUuid = StringUtils.trimToEmpty(request.getParameter("countryUuid"));
        msisdn = StringUtils.trimToEmpty(request.getParameter("msisdn"));
        addDay = StringUtils.trimToEmpty(request.getParameter("addDay"));
        addMonth = StringUtils.trimToEmpty(request.getParameter("addMonth"));
        addYear = StringUtils.trimToEmpty(request.getParameter("addYear"));
    }
    
    /**
     * Place some of the received parameters in our class HashMap.
     *
     */
    private void initParamHash() {
        CountrymsisdnparamHash = new HashMap<>();

        CountrymsisdnparamHash.put("msisdn", msisdn);
    }

}
