<!DOCTYPE html>
<%-- 
  Copyright (c) impalapay Ltd., June 23, 2014
  
  @author eugene chimita, eugenechimita@impalapay.com
--%>
<%@page import="com.impalapay.airtel.beans.accountmgmt.Account"%>
<%@page import="com.impalapay.airtel.beans.geolocation.Country"%>

<%@page import="com.impalapay.airtel.accountmgmt.admin.SessionConstants"%>

<%@page import="com.impalapay.airtel.cache.CacheVariables"%>

<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="net.sf.ehcache.Element" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar"%>


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

   List<Account> accounts = new ArrayList<Account>();

    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);

     Element element;
    List keys = accountsCache.getKeys();

    for (Object key : keys) {
        if ((element = accountsCache.get(key)) != null) {
            accounts.add((Account) element.getObjectValue());
        }
    }


    int count = 0;  // A generic counter
   
%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Accounts overview</title>
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
                    <a href="dashboard.html" class="brand">Administrator Account overview</a>
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
            <div class="span16">
                <div class="box">
                    <div class="box-header">
                        <i class="icon-sign-blank"></i>
                        <h5>Sample Box</h5>
                        <button class="btn btn-box-right" data-toggle="collapse" data-target=".box-hide-me">
                            <i class="icon-reorder"></i>
                        </button>
                    </div>
                    
                    <div class="box-footer">
                        <h5>The following are a summary on accounts:</h5>
<p>
                    <br />
                    Number of Accounts: <%= accountsCache.getSize()%><br />
                   
                </p>

                    </div>
                </div>
            </div>
        </div>
<div class="row">
            <div class="span16">
                <div class="box">
<div class="box-header">
                            <i class="icon-eye-open icon-large"></i>
                            <h5>Active accounts</h5>
                            
                        </div>
                        <div class="box-content box-table">
                        <table class="table table-hover tablesorter">
                            <thead>
                                <tr>
                                   <th></th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Username</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Creation Date</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                             <%
                                count = 1;
                                for (Account a : accounts) {
                                    out.println("<tr class='alert alert-info'>");
                                    out.println("<td>" + count + "</td>");
                                    out.println("<td>" + a.getFirstName() + "</td>");
                                    out.println("<td>" + a.getLastName() + "</td>");
                                    out.println("<td>" + a.getUsername() + "</td>");
                                    out.println("<td>" + a.getEmail() + "</td>");
                                    out.println("<td>" + a.getPhone() + "</td>");
                                    out.println("<td>" + a.getCreationDate() + "</td>");
                                    out.println("</tr>");

                                    count++;
                                }
                            %>
                            
                               
                            
                            </tbody>
                        </table>
                        </div>

                    </div>
                    
                </div>
            </div>
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