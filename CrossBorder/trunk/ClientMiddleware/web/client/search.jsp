<!DOCTYPE HTML>
<%-- 
  Copyright (c) Shujaa Solutions Ltd., Jan 30, 2013
  
  @author Anthony Maganga, anthonym@shujaa.co.ke
--%>


<%@page import="com.impalapay.airtel.beans.accountmgmt.Account"%>

<%@page import="com.impalapay.airtel.accountmgmt.session.SessionConstants"%>


<%@ page import="org.apache.commons.lang3.StringUtils" %>

<%@ page import="org.apache.log4j.Logger" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>

<%@ page import="java.io.UnsupportedEncodingException" %>


<%
    // The following is for session management.
    if (session == null) {
        response.sendRedirect("../index.jsp");
    }

    String sessionUsername = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY);

    if (StringUtils.isEmpty(sessionUsername)) {
        response.sendRedirect("../index.jsp");
    }

   session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=logout");
    // End of session management code

    Logger logger = Logger.getLogger(this.getClass());

    
%>

<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>ImpalaPay</title>
        <link  rel="icon" type="image/png"  href="../images/Airtel.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <meta name="layout" content="main"/>

        <script type="text/javascript" src="http://www.google.com/jsapi"></script>

       
        <link href="../css/customize-template.css" type="text/css" media="screen, projection" rel="stylesheet" />

        <style>
            #body-content { padding-top: 40px;}
        </style>
    </head>

    <body id="static" onload="initializeSmsSearchTextFields()">
        
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
        		
         
            <!-- / header -->
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

            
            <!-- content -->
<section class="page container">
        <div class="row">
            <div class="span16">
                <div class="box">
                    <div class="box-header">
                        <i class="icon-search"></i>
                        <h5>Search Transactions</h5>
                    </div>
                    <div class="box-content">
 <div class=" alert alert-info tabbable">
                    <div class="text">
                        

                        <!-- SMS Search navigation -->
                        <!-- Declare the CSS style of a selected link. -->    
                        <%! String selectedLinkStyle = "class=\"btn btn-success\"";%>
                        <!-- Declare the CSS style of an unselected link. -->    
                        <%! String unSelectedLinkStyle = "\"\"";%>
                        

                        <ul class="nav nav-tabs">
                           <!-- Allow each link to forward to this page and also set a parameter in the request object. -->                                
                            <li <%= (StringUtils.equals(request.getParameter("searchType"), "msisdn")
                                || request.getParameter("searchType") == null) ? selectedLinkStyle : unSelectedLinkStyle%> 
                                class="alpha"><a href="search.jsp?searchType=msisdn">Search by Receiver Phone-Number</a></li>
                                
                                
                            <li <%= (StringUtils.equals(request.getParameter("searchType"), "uniqueCode"))
                                ? selectedLinkStyle : unSelectedLinkStyle%>
                                ><a href="search.jsp?searchType=uniqueCode">Search by Transaction Uuid</a></li>
                        </ul>

                        <div class="clear"></div>
                    </div>
                    <section id="searchPanel">
                <div class="wrapper">
                    <div class="wrapper">
                        <div class="banners">
                            
                            <%   if (StringUtils.equals(request.getParameter("searchType"), "uniqueCode")) {
                            %>

                            <form id="searchByUniqueCodeForm" class="airtimeSearchForm" name="searchByUniqueCodeForm" method="post" action="searchResults.jsp">
                                <p><span>1</span>&nbsp;&nbsp;&nbsp;Enter Transaction Uuid: 
                                    <input id="uniqueCodeField" class="searchTextField" type="text" name="uuid" 
                                           onFocus="checkInput('uniqueCodeField', 'uniqueCodeFieldAlert')" 
                                           onKeyDown="checkInput('uniqueCodeField', 'uniqueCodeFieldAlert')" 
                                           onKeyUp="checkInput('uniqueCodeField', 'uniqueCodeFieldAlert')" />
                                    <span id="uniqueCodeFieldAlert"></span></p>
                                <p><span>2</span>&nbsp;&nbsp;&nbsp;Submit your search: <input class="btn btn-info" type="submit" value="Search" /></p>
                            </form>

                            <%  } else {
                            %>

                            <form id="searchByMsisdnForm" class="airtimeSearchForm" name="searchByMsisdnForm" method="post" action="searchResults.jsp">
                                <p><span>1</span>&nbsp;&nbsp;&nbsp;Enter Phone Number: 
                                    <input id="msisdnField" class="searchTextField" type="text" name="phone" 
                                           onFocus="checkInput('msisdnField', 'msisdnFieldAlert')" 
                                           onKeyDown="checkInput('msisdnField', 'msisdnFieldAlert')" 
                                           onKeyUp="checkInput('msisdnField', 'msisdnFieldAlert')" />
                                    <span id="msisdnFieldAlert"></span></p>
                                <p><span>2</span>&nbsp;&nbsp;&nbsp;Submit your search: <input class="btn btn-info" type="submit" value="Search" /></p>
                            </form>

                            <%    }
                            %>
                        </div>
                    </div>
                </div>
                        
                    </div>
                </div>
            </div>
           </div>
            </section>
                    
                    </div>
                    </div>
</div>
</div>
            
            <!-- / content -->
            <!-- footer -->
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
</div
            <!-- / footer -->
        </div>

        <script type="text/javascript" src="js/searchField.js"></script>
        <script src="js/validateSearchForms.js"></script>
    </body>
</html>
