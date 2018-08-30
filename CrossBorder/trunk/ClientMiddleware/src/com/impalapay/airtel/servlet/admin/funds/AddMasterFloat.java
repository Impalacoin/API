package com.impalapay.airtel.servlet.admin.funds;

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
import com.impalapay.airtel.beans.accountmgmt.balance.MasterAccountFloatPurchase;
import com.impalapay.airtel.persistence.accountmgmt.balance.AccountPurchaseDAO;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Servlet that receives parameters from an administrative web form that are
 * used to add bulk airtime to the master account.
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Oct 11, 2013
 *
 * @author <a href="mailto:anthonym@shujaa.co.ke">Antony Wafula</a>
 * @version %I%, %G%
 */
public class AddMasterFloat extends HttpServlet {

    final String ERROR_NO_AMOUNT = "Please provide a value for the float amount ";
    final String ERROR_INVALID_AMOUNT = "Please provide a valid float amount .";
    final String ERROR_UNABLE_ADD = "Unable to add master float .";
    
    // These represent form parameters
    private String accountUuid, amount;
    private String addDay, addMonth, addYear;
    
    // This is used to store parameter names and values from the form.
    private HashMap<String, String> paramHash;
    
    private Cache purchasesCache;
    private AccountPurchaseDAO accountPurchaseDAO;

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

        accountPurchaseDAO = AccountPurchaseDAO.getInstance();
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
        session.setAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_PARAMETERS, paramHash);

        if (StringUtils.isBlank(amount)) {
            session.setAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_ERROR_KEY, ERROR_NO_AMOUNT);

        } else if (!StringUtils.isNumeric(amount)) {
            session.setAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_ERROR_KEY, ERROR_INVALID_AMOUNT);

        } else {
            // If we get this far then all parameter checks are ok.		
            session.setAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_SUCCESS_KEY, "s");

            // Reduce our session data
            session.setAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_PARAMETERS, null);
            session.setAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_ERROR_KEY, null);

            addFloat();
        }


        response.sendRedirect("addFloat.jsp");


        purchasesCache.put(new Element(CacheVariables.CACHE_MASTERPURCHASE_KEY, accountPurchaseDAO.getMasterFloat()));

    }

    /**
     * Add airtime purchased in bulk
     *
     */
    private void addFloat() {
        MasterAccountFloatPurchase p = new MasterAccountFloatPurchase();

        p.setAccountUuid(accountUuid);
        p.setAmount(NumberUtils.toDouble(amount));

        Calendar c = Calendar.getInstance();
        c.set(NumberUtils.toInt(addYear), NumberUtils.toInt(addMonth) - 1, NumberUtils.toInt(addDay));
        p.setPurchaseDate(c.getTime());


        accountPurchaseDAO.putMasterFloat(p);

    }

    /**
     * Set the class variables that represent form parameters.
     *
     * @param request
     */
    private void setClassParameters(HttpServletRequest request) {
        accountUuid = StringUtils.trimToEmpty(request.getParameter("accountUuid"));


        amount = StringUtils.trimToEmpty(request.getParameter("amount"));

        addDay = StringUtils.trimToEmpty(request.getParameter("addDay"));
        addMonth = StringUtils.trimToEmpty(request.getParameter("addMonth"));
        addYear = StringUtils.trimToEmpty(request.getParameter("addYear"));
    }

    /**
     * Place some of the received parameters in our class HashMap.
     *
     */
    private void initParamHash() {
        paramHash = new HashMap<>();

        paramHash.put("amount", amount);
    }
}
