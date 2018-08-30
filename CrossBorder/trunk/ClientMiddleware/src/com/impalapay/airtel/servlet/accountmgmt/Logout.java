package com.impalapay.airtel.servlet.accountmgmt;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.impalapay.airtel.accountmgmt.session.SessionConstants;
import com.impalapay.airtel.cache.CacheVariables;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * Used to discontinue the session of a logged in user.
 * <p>
 * Copyright (c) Impalapay ., June 12, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene  Chimita</a>
 * @version %I%, %G%
 */
public class Logout extends HttpServlet {

    private Cache statisticsCache;

    /**
     *
     * @param config
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        CacheManager mgr = CacheManager.getInstance();
        statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_USERNAME);

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("logout.jsp");

        // get current session, and don't create one if it doesn't exist
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Remove the statistics of this user from cache
            String username = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY);
            statisticsCache.remove(username);

            session.invalidate();
        }
    }

    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
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