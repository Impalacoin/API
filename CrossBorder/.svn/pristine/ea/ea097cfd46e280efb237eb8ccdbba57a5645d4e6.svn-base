<!DOCTYPE html>
<%-- 
  Copyright (c) impalapay Ltd., June 23, 2014
  
  @author eugene chimita, eugenechimita@impalapay.com
--%>
<%@page
    import="com.impalapay.airtel.accountmgmt.pagination.TransactionPage"%>
    <%@page
        import="com.impalapay.airtel.accountmgmt.pagination.TransactionPaginator"%>

        <%@page
            import="com.impalapay.airtel.beans.transaction.TransactionStatus"%>
            <%@page import="com.impalapay.airtel.beans.transaction.Transaction"%>

            <%@page import="com.impalapay.airtel.beans.accountmgmt.Account"%>

            <%@page import="com.impalapay.airtel.beans.geolocation.Country"%>

            <%@page
                import="com.impalapay.airtel.accountmgmt.session.SessionStatistics"%>
                <%@page
                    import="com.impalapay.airtel.accountmgmt.session.SessionConstants"%>

                    <%@page import="com.impalapay.airtel.cache.CacheVariables"%>

                    <%@page import="org.apache.commons.lang3.StringUtils"%>

                    <%@page import="net.sf.ehcache.Cache"%>
                    <%@page import="net.sf.ehcache.CacheManager"%>
                    <%@page import="net.sf.ehcache.Element"%>

                    <%@page import="java.text.DecimalFormat"%>

                    <%@page import="java.net.URLEncoder"%>

                    <%@page import="java.util.Map"%>
                    <%@page import="java.util.Iterator"%>
                    <%@page import="java.util.ArrayList"%>
                    <%@page import="java.util.Calendar"%>
                    <%@page import="java.util.List"%>
                    <%@page import="java.util.HashMap"%>

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
                        
			Cache countryCache = mgr.getCache(CacheVariables.CACHE_COUNTRY_BY_UUID);
                        
                        Cache statusCache = mgr.getCache(CacheVariables.CACHE_TRANSACTIONSTATUS_BY_UUID);

                        //These HashMaps contains the UUIDs of Countries as keys and the country code of countries as values
    			HashMap<String, String> countryHash = new HashMap<String, String>();
                        
                        HashMap<String, String> statusHash = new HashMap<String, String>();
			
			

                        Account account = new Account();
                        SessionStatistics statistics = new SessionStatistics();
    
                        int count = 0; //generic counter
                        Element element;

                        if ((element = accountsCache.get(sessionUsername)) != null) {
                            account = (Account) element.getObjectValue();
                        }
                        
			List keys;
			//fetch from cache
			Country country;
	   		keys = countryCache.getKeys();
	    		for(Object key : keys) {
	        	element = countryCache.get(key);
	        	country = (Country) element.getObjectValue();
	        	countryHash.put(country.getUuid(), country.getCountrycode());	        
	    		}
                        
                        TransactionStatus transactionStatus;
	   		keys = statusCache.getKeys();
	    		for(Object key : keys) {
	        	element = statusCache.get(key);
	        	transactionStatus = (TransactionStatus) element.getObjectValue();
	        	statusHash.put(transactionStatus.getUuid(), transactionStatus.getStatus());	        
	    		}
                        

                        if ((element = statisticsCache.get(sessionUsername)) != null) {
                            statistics = (SessionStatistics) element.getObjectValue();
                        }

   

                        count = statistics.getTransactionCountTotal();
                       //count = 2;

 
  
                        TransactionPage transactionPage;
                        List<Transaction> transactionList;

                        int transactionCount; // The current count of transactions
                        TransactionPaginator paginator = new TransactionPaginator(sessionUsername);

                        if (count == 0) { // This user has no transactions in his/her account
                            transactionPage = new TransactionPage();
                            transactionList = new ArrayList<Transaction>();
                            transactionCount = 0;

                        } else {
                            transactionPage = (TransactionPage) session.getAttribute("currentTransactionPage");
                            String referrer = request.getHeader("referer");
                            String pageParam = (String) request.getParameter("page");

                            // We are to give the first page
                            if (transactionPage == null
                                    || !StringUtils.endsWith(referrer, "viewtrans.jsp")
                                    || StringUtils.equalsIgnoreCase(pageParam, "first")) {
                                transactionPage = paginator.getFirstPage();

                                // We are to give the last page
                            } else if (StringUtils.equalsIgnoreCase(pageParam, "last")) {
                                transactionPage = paginator.getLastPage();


                                // We are to give the previous page
                            } else if (StringUtils.equalsIgnoreCase(pageParam, "previous")) {
                                transactionPage = paginator.getPrevPage(transactionPage);

                                // We are to give the next page 
                            } else {
                                transactionPage= paginator.getNextPage(transactionPage);
                            }

                            session.setAttribute("currentTransactionPage", transactionPage);

                            transactionList = transactionPage.getContents();
                            transactionCount = (transactionPage.getPageNum() - 1) * transactionPage.getPagesize() + 1;
                        }
                    %>
                    <html lang="en">
                        <head>
                            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
                            <title>ImpalaPay</title>
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
                        <li class="active"><a href="viewtrans.jsp"><i class="icon-briefcase"></i>Transactions</a></li>
                        <li><a href="#about" data-toggle="modal"><i class="icon-exclamation-sign"></i>Help</a></li>
                        <li><a href="search.jsp"><i class="icon-search"></i>Search</a></li>
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

                                    <section class="page container">

                                        <div class="row">
                                          <div class="span16">
                                            <div class="box-content">
                                                <!--<table class="table table-bordered table-striped table-condensed">-->
                                                <table class="table table-striped bootstrap-datatable datatable">

						    <div class="alert alert-info">
                                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                        <strong><i class="icon-user icon-large"></i>&nbsp;Transactions Table</strong>
                                                    </div>

                                                    <div class="row-fluid">
                                                        <div class="pull-left">
<!--
                                                            <a href="" class="btn btn-primary">Refresh<i class="icon-refresh icon-large"></i>
                                                            </a>-->
                                                          <form id="exportToPDF" name="exportPDFForm" method="post" action="exportPDF" target="_blank">
												<p>
			                     		      <input class="btn btn-primary" type="submit" name="exportPDF" value="Export Summary To PDF" />
							      </p>
			                                      </form>
                                                        </div>
                                                  <div id="pagination" class="pull-left"></div>
                                                        <div align="right">
                                                             <form id="exportToPDF" name="exportPDFForm" method="post" action="exportPDF" target="_blank">
												<p>
			                     		      <input class="btn btn-primary" type="submit" name="exportPDF" value="Export Summary To PDF" />
							      </p>
			                                      </form>
                                                           
                                                        </div>
                                                      </div>
                                                    <div class="row-fluid">
                                                     <div align="right">
                                                      <form id="exportToExcel" name="exportExcelForm" method="post" action="exportExcel" target="_blank" >
						       <input class="btn btn-primary" type="submit" name="exportExcel" value="Export All" class="icon-paper-clip icon-large"  />
                                                      <input class="btn btn-primary" type="submit" name="exportExcel" value="Export Page" class="icon-paper-clip icon-large"  />
							
												</p>
											    </form>
                                                        </div>
                                                      <div align="right">
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
                                                    </div>
                                                    <thead>
                                                        <tr class="alert alert-success">
                                                            <th>&nbsp;</th>
                                                            <th>transaction Uuid</th>
                                                            <!--<th>Status</th>-->
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


                            //out.println(count);
                            for (int j = 0; j < count; j++) {
                                out.println("<tr class='alert alert-info'>");
                                out.println("<td>" + transactionCount + "</td>");
                                out.println("<td>" + transactionList.get(j).getUuid() + "</td>");
                                //out.println("<td>" + statusHash.get(transactionList.get(j).getTransactionStatusUuid()) + "</td>");
                                out.println("<td>" + transactionList.get(j).getSourceCountrycode() + "</td>");
                                out.println("<td>" + transactionList.get(j).getSenderName() + "</td>");
                                out.println("<td>" + transactionList.get(j).getRecipientMobile() + "</td>");
                                out.println("<td>" + transactionList.get(j).getAmount() + "</td>");
                                out.println("<td>" + countryHash.get(transactionList.get(j).getRecipientCountryUuid()) + "</td>");
                                out.println("<td>" + transactionList.get(j).getSenderToken() + "</td>");
                                out.println("<td>" + transactionList.get(j).getClientTime() + "</td>");
                                out.println("<td>" + transactionList.get(j).getServerTime() + "</td>");
                                out.println("</tr>");
                                transactionCount++;
                            }
                                                        %>





                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                       </div>
                                    </section>
                                </div>




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

                                <script src="../js/bootstrap/bootstrap-transition.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-alert.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-modal.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-dropdown.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-scrollspy.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-tab.js" type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-tooltip.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-popover.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-button.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-collapse.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-carousel.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-typeahead.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-affix.js"
                                type="text/javascript"></script>
                                <script src="../js/bootstrap/bootstrap-datepicker.js"
                                type="text/javascript"></script>
                                <script src="../js/jquery/jquery-tablesorter.js"
                                type="text/javascript"></script>
                                <script src="../js/jquery/jquery-chosen.js" type="text/javascript"></script>
                                <script src="../js/jquery/virtual-tour.js" type="text/javascript"></script>
                                <script type="text/javascript">
                                    $(function() {
                                        $('#sample-table').tablesorter();
                                        $('#datepicker').datepicker();
                                        $(".chosen").chosen();
                                    });
                                </script>
                        </body>
                    </html>
