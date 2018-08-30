<!DOCTYPE html>
<%-- 
  Copyright (c) impalapay Ltd., June 23, 2014
  
  @author eugene chimita, eugenechimita@impalapay.com
--%>
<%@page import="com.impalapay.airtel.accountmgmt.pagination.TransactionPage"%>
<%@page import="com.impalapay.airtel.accountmgmt.pagination.SearchUuidPaginator"%>
<%@page import="com.impalapay.airtel.accountmgmt.pagination.SearchRecipientMobilePaginator"%>



<%@page import="com.impalapay.airtel.beans.transaction.TransactionStatus"%>
<%@page import="com.impalapay.airtel.beans.transaction.Transaction"%>

<%@page import="com.impalapay.airtel.beans.accountmgmt.Account"%>



<%@page import="com.impalapay.airtel.accountmgmt.session.SessionStatistics"%>
<%@page import="com.impalapay.airtel.accountmgmt.session.SessionConstants"%>



<%@page import="com.impalapay.airtel.persistence.util.CountUtils"%>

<%@page import="com.impalapay.airtel.cache.CacheVariables"%>

<%@page import="org.apache.commons.lang3.StringUtils" %>

<%@page import="net.sf.ehcache.Cache" %>
<%@page import="net.sf.ehcache.CacheManager" %>
<%@page import="net.sf.ehcache.Element"%>

<%@page import="java.net.URLEncoder"%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.LinkedList"%>

<%
 // The following is for session management.    
                        if (session == null) {
                            response.sendRedirect("../client");
                        }

                        String sessionUsername = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY);

                        if (StringUtils.isEmpty(sessionUsername)) {
                            response.sendRedirect("index.jsp");
                        }

                        session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
                        response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=logout");
                        // End of session management code

                        CacheManager mgr = CacheManager.getInstance();
                        Cache accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);

                        Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_USERNAME);




    Account account = new Account();
    CountUtils countUtils = CountUtils.getInstance();
    List keys;
    int count = 0;  // Generic counter

    int transactionCount = 0; // The current count of the Transactions  

    Element element;

    if ((element = accountsCache.get(sessionUsername)) != null) {
                            account = (Account) element.getObjectValue();
                        }

   
   TransactionPage transactionPage = new TransactionPage();
    List<Transaction> transactionList = new LinkedList<Transaction>();

    // returns an enumeration of all the parameter names
    Enumeration enumeration = request.getParameterNames();
    //String uuid = "", msisdn = "", parameterName = "";
    
    String uuid = "", phone = "", parameterName = "";


    if (enumeration.hasMoreElements()) {
        parameterName = (String) enumeration.nextElement();


        if (StringUtils.equalsIgnoreCase(parameterName, "page") || parameterName == null) {
            parameterName = (String) session.getAttribute("parameterName");

        } else {
            session.setAttribute("parameterName", parameterName);

        }

        //Search by unique id
        if (StringUtils.equalsIgnoreCase(parameterName, "uuid")) {

            uuid = request.getParameter("uuid");

            count = countUtils.getTransactionByUuidCount(account, uuid);

            SearchUuidPaginator paginator = new SearchUuidPaginator(sessionUsername, uuid);

            //if pagination buttons(next,previous,first and last) are pressed
            //parameter value becomes null hence need to store it in a session
            if (uuid == null) {
                uuid = (String) session.getAttribute("uuid");
                paginator = new SearchUuidPaginator(sessionUsername, uuid);


            } else {

                session.setAttribute("uuid", uuid);

            }


            if (count == 0) { // This user has no Transactions in his/her account
                transactionPage = new TransactionPage();
                transactionList = new ArrayList<Transaction>();
                transactionCount = 0;

            } else {

                transactionPage = (TransactionPage) session.getAttribute("currentSearchPage");

                String pageStr = (String) request.getParameter("page");

                // We are to give the first page 
                if (transactionPage == null || pageStr == null
                        || StringUtils.equalsIgnoreCase(pageStr, "first")) {
                    transactionPage = paginator.getFirstPage();

                    // We are to give the last page
                } else if (StringUtils.equalsIgnoreCase(pageStr, "last")) {
                    transactionPage = paginator.getLastPage();

                    // We are to give the previous page
                } else if (StringUtils.equalsIgnoreCase(pageStr, "previous")) {
                    transactionPage = paginator.getPrevPage(transactionPage);

                    // We are to give the next page
                } else if (StringUtils.equalsIgnoreCase(pageStr, "next")) {
                    transactionPage = paginator.getNextPage(transactionPage);
                }

                session.setAttribute("currentSearchPage", transactionPage);

                transactionList = transactionPage.getContents();
                transactionCount = (transactionPage.getPageNum() - 1) * transactionPage.getPagesize() + 1;


            }


        } else if (StringUtils.equalsIgnoreCase(parameterName, "phone")) {


           phone = request.getParameter("phone");

            count = countUtils.getTransactionByRecipientmobileCount(account, phone);

            SearchRecipientMobilePaginator paginator = new SearchRecipientMobilePaginator(sessionUsername, phone);

            //if pagination buttons(next,previous,first and last) are pressed
            //parameter value becomes null hence need to store it in a session
            if (uuid == null) {
                uuid = (String) session.getAttribute("phone");
                paginator = new SearchRecipientMobilePaginator(sessionUsername, phone);


            } else {

                session.setAttribute("phone", phone);

            }


            if (count == 0) { // This user has no Transactions in his/her account
                transactionPage = new TransactionPage();
                transactionList = new ArrayList<Transaction>();
                transactionCount = 0;

            } else {

                transactionPage = (TransactionPage) session.getAttribute("currentSearchPage");

                String pageStr = (String) request.getParameter("page");

                // We are to give the first page 
                if (transactionPage == null || pageStr == null
                        || StringUtils.equalsIgnoreCase(pageStr, "first")) {
                    transactionPage = paginator.getFirstPage();

                    // We are to give the last page
                } else if (StringUtils.equalsIgnoreCase(pageStr, "last")) {
                    transactionPage = paginator.getLastPage();

                    // We are to give the previous page
                } else if (StringUtils.equalsIgnoreCase(pageStr, "previous")) {
                    transactionPage = paginator.getPrevPage(transactionPage);

                    // We are to give the next page
                } else if (StringUtils.equalsIgnoreCase(pageStr, "next")) {
                    transactionPage = paginator.getNextPage(transactionPage);
                }

                session.setAttribute("currentSearchPage", transactionPage);

                transactionList = transactionPage.getContents();
                transactionCount = (transactionPage.getPageNum() - 1) * transactionPage.getPagesize() + 1;

            }

        }

    }




%>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>ImpalaPay|search</title>
        <link  rel="icon" type="image/png"  href="../images/Airtel.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <meta name="layout" content="main" />

        <script type="text/javascript" src="http://www.google.com/jsapi"></script>

        <script src="../js/jquery/jquery-1.8.2.min.js" type="text/javascript"></script>
        <link href="../css/customize-template.css" type="text/css"
              media="screen, projection" rel="stylesheet" />

        <style>
            #body-content {
                padding-top: 40px;
            }
        </style>
    </head>


    <body>
        
            <!-- header -->
            <div class="navbar navbar-fixed-top">
                <div class="navbar-inner">
                    <div class="container">
                        <button class="btn btn-navbar" data-toggle="collapse" data-target="#app-nav-top-bar">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                      <a href="index.jsp" class="brand">IMT Portal</a>
                                        <!--
                                        <ul class="nav">
                                            <li class="#"><a href="index.jsp">Dashboard</a></li>
                                            <li><a href="viewtrans.jsp" class="active">Transactions</a></li>
                                            <li><a href="#about" data-toggle="modal">Help</a></li>
                                            <li><a href="search.jsp">Search</a></li>
					   <!-- <li><a href="highcharttest.jsp">Reports</a></li>-
                                            <li>
                                                <a href="accountmanage.jsp">Account Management <b class="caret"></b></a>


                                            </li>
                                        </ul>-->

                                       <div align="right">
                                        <form name="logoutForm" method="post" action="logout">
                                            
                                            <input type="submit" class="btn btn-danger" name="logout"
                                                   id="logout" value="Logout">
                                        </form>
                                    </div>

                    </div>

                </div>
            </div>

            	<div id="body-container">
                	<div id="body-content">

        <section class="nav nav-page">
	  <div class="container">
            <div class="row">
                
                <div class="page-nav-options">
                   
                        
                        <ul class="nav nav-tabs">
                        
                        <li><a href="index.jsp"><i class="icon-home"></i>Dashboard</a></li>
                        <li><a href="viewtrans.jsp"><i class="icon-briefcase"></i>Transactions</a></li>
                        <li><a href="#about" data-toggle="modal"><i class="icon-exclamation-sign"></i>Help</a></li>
                        <li class="active"><a href="search.jsp"><i class="icon-search"></i>Search</a></li>
			<!--<li><a href="highcharttest.jsp">Reports</a></li>-->
                        <li>
                            <a href="accountmanage.jsp"><i class="icon-pushpin"></i>Account Management</a>
                        </li>
                        &nbsp;&nbsp;&nbsp;
<a href="" class="btn btn-primary">logged-in as:&nbsp;<i class="icon-user"></i><%= sessionUsername%>&nbsp;
                                                </a>
                                            
                        </ul>
                    
                </div>
            </div>
        </div>
    </section>

                    <%
                        if (count != 0) {
                    %>
                    <!-- / header -->
                    <!-- content -->
                    <section class="page container">

                        
                                <p class="alert alert-danger">The following is a summary of all the Transaction. They are ordered chronologically with the latest being the first.</p>                                                

                               

                                    <div class="row">
                                       <div class="span16">

                                        <div class="box-content">
                                            <!--<table class="table table-bordered table-striped table-condensed">-->
                                            <table class="table table-striped bootstrap-datatable datatable">



                                                <div class="alert alert-info">
                                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                    <strong><i class="icon-user icon-large"></i>&nbsp;Transaction
                                                        Table</strong>
                                                </div>

                                                <div class="row-fluid">

                                                    <div id="pagination" class="pull-left"></div>
<!--
                                                    <div align="right">
                                                        <a href="" class="btn btn-primary">Export Summary To PDF<i
                                                                class="icon-bar-chart icon-large"></i>
                                                        </a>
                                                    </div>-->
                                                    <div id="pagination">
                                                        <form name="pageForm" method="post" action="viewtrans.jsp">
                                                            <%
                            if (!transactionPage.isFirstPage()) {
                                                            %>
                                                            <input class="btn btn-primary" type="submit" name="page"
                                                                   value="First" /> <input class="btn btn-primary" type="submit"
                                                                   name="page" value="Previous" />
                                                            <%                                    }
                                                            %>
                                                            <span class="pageInfo">Page <span
                                                                    class="pagePosition currentPage"><%= transactionPage.getPageNum()%></span>
                                                                of <span class="pagePosition"><%= transactionPage.getTotalPage()%></span>
                                                            </span>
                                                            <%
                            if (!transactionPage.isLastPage()) {
                                                            %>
                                                            <input class="btn btn-primary" type="submit" name="page"
                                                                   value="Next"> <input class="btn btn-primary" type="submit"
                                                                   name="page" value="Last">
                                                            <%                                    
                            }
                                                            %>
                                                        </form>
                                                    </div>
                                                </div>
                                            <!--
                                                <div class="row-fluid">
                                               
                                                    <div align="right">
                                                        <a href="" class="btn btn-primary">Export<i
                                                                class="icon-paper-clip icon-large"></i>
                                                        </a>
                                                    </div>
                                                </div>-->
                                                <thead>
                                                    <tr class="alert alert-success">
                                                        <th>&nbsp;</th>
                                                        <th>transaction Uuid</th>
                                                        <th>Source country code</th>
                                                        <th>Sender name</th>
                                                        <th>Recipient phone number</th>
                                                        <th>Amount</th>
                                                        <th>Recipient Country Code</th>
                                                        <th>Sender Token</th>
                                                        <th>Server Time</th>
                                                        <th>Client Time</th>

                                                    </tr>
                                                </thead>

                                                <tbody>
                                                    <%
                        count = transactionList.size();

                       // out.println(count);
                        for (int j = 0; j < count; j++) {
                            out.println("<tr class='alert alert-info'>");
                            out.println("<td>" + transactionCount + "</td>");
                            out.println("<td>" + transactionList.get(j).getUuid() + "</td>");
                            out.println("<td>" + transactionList.get(j).getSourceCountrycode() + "</td>");
                            out.println("<td>" + transactionList.get(j).getSenderName() + "</td>");
                            out.println("<td>" + transactionList.get(j).getRecipientMobile() + "</td>");
                            out.println("<td>" + transactionList.get(j).getAmount() + "</td>");
                            out.println("<td>" + transactionList.get(j).getRecipientCountryUuid() + "</td>");
                            out.println("<td>" + transactionList.get(j).getSenderToken() + "</td>");
                            out.println("<td>" + transactionList.get(j).getClientTime() + "</td>");
                            out.println("<td>" + transactionList.get(j).getServerTime() + "</td>");
                            out.println("</tr>");
                            transactionCount++;
                        }
                                                    %>





                                                </tbody>
                                            </table>

                                            <div class="row-fluid">


                                                <div id="export" align="right">   
                                                    <form id="exportToExcel" name="exportExcelForm" method="post" action="exportExcel" target="_blank">
                                                        <p>
                                                            <i class="icon-bar-chart icon-large"></i>
                                                            <input class="btn btn-primary" type="submit" name="exportExcel" value="Export Search Results" >

                                                        </p>
                                                    </form>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
</div>
                                

                            <%
                                } else {

                                    out.println("<p align='centre' class='alert alert-info'>Sorry.The search value doesn't match any rows !<br>Please try again</p>");
                                }
                            %>
                        </div>
                    </section>
                    <!-- / content -->
                </div>           <!-- footer -->


            
           <div class="row">
    <footer class="footer">
        <div class="container">

            <div class="disclaimer">

                <p>Copyright@ImpalaPay 2014-2015</p>
                 <a href="#">Terms &amp; Conditions</a> | <a href="#">Privacy
                        Policy</a> | ImpalaPay Ltd <%= Calendar.getInstance().get(Calendar.YEAR)%>. All rights reserved.
                    <img id="madeInKenya" alt="Made in Kenya" src="#" width="100" height="100" />
                </p>
            </div>
        </div>
    </footer>
</div>

            <!-- / footer -->
    </body>
</html>
