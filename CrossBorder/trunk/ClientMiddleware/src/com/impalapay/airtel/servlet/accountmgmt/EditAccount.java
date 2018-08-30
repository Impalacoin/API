package com.impalapay.airtel.servlet.accountmgmt;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;

import com.impalapay.airtel.accountmgmt.session.SessionConstants;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;

/**
 * Edit details associated 
 * with the account holder
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class EditAccount extends HttpServlet {
   
    private Cache accountCache;
    
    private  AccountDAO accountDAO;
 
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        CacheManager mgr = CacheManager.getInstance();
        accountCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);
        
        accountDAO = AccountDAO.getInstance();
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
        doGet(request, response);
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
        Account account;

        String email = StringUtils.trimToEmpty(request.getParameter("email"));
        String phone = StringUtils.trimToEmpty(request.getParameter("phone"));
        String smsPassword= StringUtils.trimToEmpty(request.getParameter("smsPassword"));
        String accountUuid = StringUtils.trimToEmpty(request.getParameter("accountUuid"));
       
        account = accountDAO.getAccount(accountUuid);
        
        account.setEmail(email);
        account.setPhone(phone);
        account.setApiPasswd(smsPassword);
        
        //update the cache and the database          
        accountDAO.updateAccount(account.getUuid(), account);
        accountCache.put(new Element(account.getEmail(), account));

        session.setAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY, email);
        response.sendRedirect("accountmgmt.jsp");
       
    }

    

}
