<!DOCTYPE html>
<%-- 
  Copyright (c) impalapay Ltd., June 23, 2014
  
  @author eugene chimita, eugenechimita@impalapay.com
--%>
<%@ page import="com.impalapay.airtel.beans.geolocation.Country" %>
<%@ page import="com.impalapay.airtel.beans.accountmgmt.Account" %>

<%@ page import="com.impalapay.airtel.beans.accountmgmt.AccountStatus" %>

<%@ page import="com.impalapay.airtel.accountmgmt.admin.SessionConstants" %>

<%@ page import="com.impalapay.airtel.persistence.accountmgmt.AccountStatusDAO" %>

<%@page import="com.impalapay.airtel.cache.CacheVariables"%>

<%@ page import="org.apache.commons.lang3.StringUtils" %>

<%@ page import="java.util.List" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>

<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@page import="net.sf.ehcache.Element"%>

<%
    // The following is for session management.    
    if (session == null) {
        response.sendRedirect("../index.jsp");
    }

    String sessionKey = (String) session.getAttribute(SessionConstants.ADMIN_SESSION_KEY);

    if (StringUtils.isEmpty(sessionKey)) {
        response.sendRedirect("../index.jsp");
    }

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../logout");
    // End of session management code


    

    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_UUID);
    Cache countryCache = mgr.getCache(CacheVariables.CACHE_COUNTRY_BY_UUID);
    
    // This HashMap contains the UUIDs of countries as keys and the country names as values
    HashMap<String, String> countryHash = new HashMap<String, String>();

    List keys;

    Element element;
    Country country;

    List<Account> accountList = new ArrayList<Account>();

    keys = accountsCache.getKeysWithExpiryCheck();
    for (Object key : keys) {
        if ((element = accountsCache.get(key)) != null) {
            accountList.add((Account) element.getObjectValue());
        }
    }

   

    keys = countryCache.getKeys();
    for (Object key : keys) {
        element = countryCache.get(key);
        country = (Country) element.getObjectValue();
        countryHash.put(country.getUuid(), country.getName());
    }
    
    Calendar calendar = Calendar.getInstance();
    
    final int DAYS_IN_MONTH = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
    final int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
    final int MONTH = calendar.get(Calendar.MONTH) + 1;
    final int YEAR = calendar.get(Calendar.YEAR);
    final int YEAR_COUNT = YEAR + 10;

%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Administrator add Float</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <meta name="layout" content="main"/>
    
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>

    <script src="../js/jquery/jquery-1.8.2.min.js" type="text/javascript" ></script>
    <link href="../css/customize-template.css" type="text/css" media="screen, projection" rel="stylesheet" />

    <style>
        #body-content { padding-top: 40px;}
    </style>
</head>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <button class="btn btn-navbar" data-toggle="collapse" data-target="#app-nav-top-bar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a href="dashboard.html" class="brand">Administrator Add Float</a>
                    <div id="app-nav-top-bar" class="nav-collapse">
                        
                        <ul class="nav pull-right">
                            <li>
                                <!--<a href="login.html">Logout</a>-->
                            </li>
                            
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div id="body-container">
            <div id="body-content">
                
                    <div class="body-nav body-nav-vertical">
                        <div class="container">
                            <ul>
                                <li>
                                    <a href="addAccount.jsp">
                                        <i class="icon-user icon-large"></i> Add Account
                                    </a>
                                </li>
                                <li>
                                    <a href="addFloat.jsp">
                                        <i class="icon-money icon-large"></i> Add Balance
                                    </a>
                                </li>
                                <li>
                                    <a href="addCountryMsisdn.jsp">
                                        <i class="icon-check icon-large"></i> Country Msisdn
                                    </a>
                                </li>
                                
                            </ul>
                        </div>
                    </div>
                
                
        <section class="nav nav-page">
        <div class="container">
            <div class="row">
                
                <div class="page-nav-options">
                    <div class="span12">
                        
                        <ul class="nav nav-tabs">
                           <li>
                                <a href="../dashboard.jsp"><i class="icon-home"></i>Dashboard</a>
                            </li>
                          
                            <li class="active"><a href="accountsIndex.jsp">Accounts</a></li>
                            <li><a href="#">Topup</a></li>
                            <li><a href="#">Setting</a></li>
                            <li><a href="#">Help</a></li>
                            <li><a href="#">Search Transaction</a></li>
                        </ul>
<div align="right"> 

                    <form name="logoutForm" method="post" action="../logout">  
                        <p><a href="" class="btn btn-primary" >logged-in as Administrator:&nbsp;<%=sessionKey%>&nbsp;</a>
                        <input type="submit" class="btn btn-danger" name="logout" id="logout" value="Logout"> </p>         
                    </form>
                </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="page container">
        
     <div class="row">
     <section id="my-account-security-form" class="page container">
            <form id="userSecurityForm" class="form-horizontal" action="addMasterFloat" method="post">
                <div class="container">

                    
                    <div class="row">
                        
                        <div class="span8">
                        <div class="alert alert-block alert-info">
                        <p>
                            Add Master Float
                        </p>
                    </div>
 <%
                    String addMasterErr = (String) session.getAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_ERROR_KEY);
                    String addMasterSuccess = (String) session.getAttribute(
                            SessionConstants.ADMIN_ADD_MASTER_FLOAT_SUCCESS_KEY);
                    HashMap<String, String> ParamHash = (HashMap<String, String>) session.getAttribute(
                            SessionConstants.ADMIN_ADD_MASTER_FLOAT_PARAMETERS);

                    if (ParamHash == null) {
                        ParamHash = new HashMap<String, String>();
                    }

                    if (StringUtils.isNotEmpty(addMasterErr)) {
                        out.println("<p class=\"alert alert-error\">");
                        out.println("Form error: " + addMasterErr);
                        out.println("</p>");
                        session.setAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_ERROR_KEY, null);
                    }

                    if (StringUtils.isNotEmpty(addMasterSuccess)) {
                        out.println("<p class=\"alert alert-success\">");
                        out.println("You have successfully added float to the master float  account. You may add more below. "
                                + "Please relogin to system to get an updated master float.");
                        out.println("</p>");
                        session.setAttribute(SessionConstants.ADMIN_ADD_MASTER_FLOAT_SUCCESS_KEY, null);
                    }
                %>


                            <fieldset>
                                <div class="control-group ">
                                    <label class="control-label">Account</label>
                                    <div class="controls">
                                        <select name="accountUuid" id="input">
                                
                                <%


                                        for (Account a : accountList) {

                                            out.println("<option value=\"" + a.getUuid() + "\">"
                                                    + a.getFirstName() + " " + a.getLastName() + "</option>");
                                        
                                    }//end for (Account a : accountList) 
                                %>
                            </select>
                                        

                                    </div>
                                </div>
                                <div class="control-group ">
                                    <label class="control-label">Amount</label>
                                    <div class="controls">
                                        <input id="input" name="amount" class="span5" type="text" autocomplete="false"  <%
                                       out.println("value=\"" + StringUtils.trimToEmpty(ParamHash.get("amount")) + "\"");
                                   %> >

                                    </div>
                                </div>
                                <div class="control-group ">
                                
                                   <td><label class="control-label">Date&nbsp;</label></td>
                                    <div class="controls">
                                    
                                       <td><select name="addDay" id="input" class="span2">
                                <%
                                    for (int j = 1; j < DAYS_IN_MONTH; j++) {
                                        if (j == DAY_OF_MONTH) {
                                            out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                        } else {
                                            out.println("<option value=\"" + j + "\">" + j + "</option>");
                                        }
                                    }
                                %>
                            </select>-</td>
                                      <td> <select name="addMonth" id="input" class="span2">
                                <%
                                    for (int j = 1; j < 13; j++) {
                                        if (j == MONTH) {
                                            out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                        } else {
                                            out.println("<option value=\"" + j + "\">" + j + "</option>");
                                        }
                                    }
                                %>
                            </select>-</td>
                                      <td> <select name="addYear" id="input" class="span2">
                                <%
                                    for (int j = YEAR; j < YEAR_COUNT; j++) {
                                        if (j == YEAR) {
                                            out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                        } else {
                                            out.println("<option value=\"" + j + "\">" + j + "</option>");
                                        }
                                    }
                                %>
                            </select></td>
                                     
                                    </div>
                                    
                                </div>
                                
                            </fieldset>
                        
                   
                    <footer id="submit-actions" class="form-actions">
                        <button id="submit-button" type="submit" class="btn btn-primary" name="action" value="CONFIRM">Add</button>
                        
                    </footer>
                    </div>
                    </form>
                    <!---division for setting up client url-->
                    <div class="span8">
                        <div class="alert alert-block alert-info">
                        <p>
                            Add Float by Country
                        </p>
                    </div>
                   <form id="userSecurityForm" class="form-horizontal" action="addFloatPerCountry" method="post">
                    <%
                    String addClientErr = (String) session.getAttribute(SessionConstants.ADMIN_ADD_COUNTRY_FLOAT_ERROR_KEY);
                    String addClientSuccess = (String) session.getAttribute(
                            SessionConstants.ADMIN_ADD_COUNTRY_FLOAT_SUCCESS_KEY);

                    HashMap<String, String> CountryfloatparamHash = (HashMap<String, String>) session.getAttribute(
                            SessionConstants.ADMIN_ADD_COUNTRY_FLOAT_PARAMETERS);
                    
                    if (CountryfloatparamHash == null) {
                        CountryfloatparamHash = new HashMap<String, String>();
                    }

                    if (StringUtils.isNotEmpty(addClientErr)) {
                        out.println("<p class=\"alert alert-error\">");
                        out.println("Form error: " + addClientErr);
                        out.println("</p>");
                        session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_FLOAT_ERROR_KEY, null);
                    }

                    if (StringUtils.isNotEmpty(addClientSuccess)) {
                        out.println("<p class=\"alert alert-success\">");
                        out.println("You have successfully added float to the country. You may add others below. "
                                + "Please relogin to system to get new listing of country floats.");
                        out.println("</p>");
                        session.setAttribute(SessionConstants.ADMIN_ADD_COUNTRY_FLOAT_SUCCESS_KEY, null);
                    }
                %>

                            <fieldset>
                                <div class="control-group ">
                                    <label class="control-label">Account</label>
                                    <div class="controls">
                                         <select name="accountUuid" id="input">
                                
                                <%


                                        for (Account a : accountList) {

                                            out.println("<option value=\"" + a.getUuid() + "\">"
                                                    + a.getFirstName() + " " + a.getLastName() + "</option>");
                                        
                                    }//end for (Account a : accountList) 
                                %>
                            </select>

                                    </div>
                                </div>
                                <div class="control-group ">
                                    <label class="control-label">Country</label>
                                    <div class="controls">
                                         <select id="input" name="countryUuid">

                                <%
                                    Iterator i = countryHash.keySet().iterator();
                                   
                                    while (i.hasNext()) {

                                        String uuid = (String) i.next();

                                        String name = (String) countryHash.get(uuid);
                                %>

                                <option value="<%=uuid%>" ><%=name%></option>

                                <%
                                    }
                                %>

                            </select>

                                    </div>
                                </div>
                                
                                <div class="control-group ">
                                    <label class="control-label">Amount</label>
                                    <div class="controls">
                                        <input id="input" name="amount" class="span5" type="text" autocomplete="false" <%
                                       out.println("value=\"" + StringUtils.trimToEmpty(CountryfloatparamHash.get("amount")) + "\"");
                                   %> >

                                    </div>
                                </div>
                                <div class="control-group ">
                                
                                   <td><label class="control-label">Date&nbsp;</label></td>
                                    <div class="controls">
                                    
                                       <td><select name="addDay" id="input" class="span2">
                                <%
                                    for (int j = 1; j < DAYS_IN_MONTH; j++) {
                                        if (j == DAY_OF_MONTH) {
                                            out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                        } else {
                                            out.println("<option value=\"" + j + "\">" + j + "</option>");
                                        }
                                    }
                                %>
                            </select>-</td>
                                      <td> <select name="addMonth" id="input" class="span2">
                                <%
                                    for (int j = 1; j < 13; j++) {
                                        if (j == MONTH) {
                                            out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                        } else {
                                            out.println("<option value=\"" + j + "\">" + j + "</option>");
                                        }
                                    }
                                %>
                            </select>-</td>
                                      <td> <select name="addYear" id="input" class="span2">
                                <%
                                    for (int j = YEAR; j < YEAR_COUNT; j++) {
                                        if (j == YEAR) {
                                            out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                        } else {
                                            out.println("<option value=\"" + j + "\">" + j + "</option>");
                                        }
                                    }
                                %>
                            </select></td>
                                     
                                    </div>
                                    
                                </div>
                                
                            </fieldset>
                        
                   
                    <footer id="submit-actions" class="form-actions">
                        <button id="submit-button" type="submit" class="btn btn-primary" name="action" value="CONFIRM">Add</button>
                        
                    </footer>
                    </div>
                </form>
                    <!--end setting client url-->
                </div>
            
        </section>
     </div>   
        

        
        
        
    </section>

    

            </div>
        </div>

        <div id="spinner" class="spinner" style="display:none;">
            Loading&hellip;
        </div>

       <div class="row">
    <footer class="footer">
        <div class="container">

            <div class="disclaimer">

                <p>Copyright@ImpalaPay 2014-2015</p>
                 <a href="#">Terms &amp; Conditions</a> | <a href="#">Privacy
                        Policy</a> | ImpalaPay Ltd <%= Calendar.getInstance().get(Calendar.YEAR)%>. All rights reserved.
                    <!--<img id="madeInKenya" alt="Made in Kenya" src="#" width="100" height="100" />-->
                </p>
            </div>
        </div>
    </footer>
</div>
        
        <script src="../js/bootstrap/bootstrap-transition.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-alert.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-modal.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-dropdown.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-scrollspy.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-tab.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-tooltip.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-popover.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-button.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-collapse.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-carousel.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-typeahead.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-affix.js" type="text/javascript" ></script>
        <script src="../js/bootstrap/bootstrap-datepicker.js" type="text/javascript" ></script>
        <script src="../js/jquery/jquery-tablesorter.js" type="text/javascript" ></script>
        <script src="../js/jquery/jquery-chosen.js" type="text/javascript" ></script>
        <script src="../js/jquery/virtual-tour.js" type="text/javascript" ></script>
        <script type="text/javascript">
        $(function() {
            $('#sample-table').tablesorter();
            $('#datepicker').datepicker();
            $(".chosen").chosen();
        });
    </script>

    </body>
</html>
