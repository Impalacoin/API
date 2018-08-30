<!DOCTYPE html>
<%-- 
  Copyright (c) impalapay Ltd., June 23, 2014
  
  @author eugene chimita, eugenechimita@impalapay.com
--%>
<%@page import="com.impalapay.airtel.beans.accountmgmt.Account"%>



<%@page import="com.impalapay.airtel.accountmgmt.session.SessionStatistics"%>
<%@page import="com.impalapay.airtel.accountmgmt.session.SessionConstants"%>

<%@page import="com.impalapay.airtel.cache.CacheVariables"%>

<%@page import="org.apache.commons.lang3.StringUtils" %>

<%@page import="java.text.SimpleDateFormat" %>

<%@page import="java.util.Calendar" %>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>

<%@page import="org.apache.commons.lang3.StringUtils" %>

<%@page import="net.sf.ehcache.Cache" %>
<%@page import="net.sf.ehcache.CacheManager" %>
<%@page import="net.sf.ehcache.Element" %>

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

    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);

    //Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_EMAIL);

    Account account = new Account();
    SessionStatistics statistics = new SessionStatistics();

    Element element;

    if ((element = accountsCache.get(sessionUsername)) != null) {
        account = (Account) element.getObjectValue();
    }

     SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd yyyy");
%>
<html lang="en">
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
                        <li><a href="viewtrans.jsp"><i class="icon-briefcase"></i>Transactions</a></li>
                        <li><a href="#about" data-toggle="modal"><i class="icon-exclamation-sign"></i>Help</a></li>
                        <li><a href="search.jsp"><i class="icon-search"></i>Search</a></li>
			<!--<li><a href="highcharttest.jsp">Reports</a></li>-->
                        <li class="active">
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
                <div class="box">
                    <div class="box-header">
                        <i class="icon-bookmark"></i>
                        <h5>Manage Account</h5>
                    </div>
                    <div class="box-content">
                                    <div class="tabbable">
                                    <!--              Declare the CSS style of a selected link.     -->
                                    <%! String selectedLinkStyle = "class=\"btn btn-success\"";%>

                                    <%! String unSelectedLinkStyle = "\"\"";%>
                                    <ul class="nav nav-tabs">
                                        <li <%= (StringUtils.equals(request.getParameter("tabSelected"), "accountDetails")
      || request.getParameter("tabSelected") == null)? selectedLinkStyle : unSelectedLinkStyle%> 
                                            ><a href="accountmanage.jsp?tabSelected=accountDetails">View Account Details</a>
                                        </li>
                                        <li <%= (StringUtils.equals(request.getParameter("tabSelected"), "changepassword"))
                                    ? selectedLinkStyle : unSelectedLinkStyle%>>
                                            <a href="accountmanage.jsp?tabSelected=changepassword">Change Login Password</a>
                                        </li>


                                    </ul>
                                    <div class="tab-content">
                                        <!--Display account details tab-->
                                        <%
                                        if (StringUtils.equals(request.getParameter("tabSelected"), "accountDetails")
                                            || request.getParameter("tabSelected") == null) {

                                        %>
                                        <div id="accountDetails" class="tab-pane active">

                                            <div class="alert alert-info">
                                                <p><span>First Name: &nbsp;</span>
                                                    <span><%= account.getFirstName()%></span></p>
                                                <hr>
                                                <p><span class="subject">Last Name: &nbsp;</span>
                                                    <span><%= account.getLastName()%></span></p>
                                                <hr>
                                                <p><span>Username:&nbsp;</span>
                                                    <span><%= account.getUsername()%></span></p>
                                            </div>


                                            <div class="alert alert-success">
                                                <p><span>Email:&nbsp;</span>
                                                    <span><%= account.getEmail()%></span></p>
                                                <hr>
                                                <p><span>Phone:&nbsp;</span>
                                                    <span><%= account.getPhone()%></span></p>
                                                <hr>

                                                <p><span>Account Creation Date:&nbsp;</span>
                                                    <span><%= dateFormatter.format(account.getCreationDate())%></span></p>
                                            </div>
                                        </div> 
                                    </div>
                                    <div class="tab-content">
                                        <%
                               // end 'if (StringUtils.equals(request.getParameter("tabSelected"), "accountDetails")

                           } else if (StringUtils.equals(request.getParameter("tabSelected"), "changepassword")) {
                                        %>
                                        <div id="changepassword" class="tab-pane active">

                                            <div id="editDetails">

                                <%

                                    //Get status message when changing account holder's password
                                    String statusMessagePassword = (String) session.getAttribute(SessionConstants.ACCOUNT_CHANGE_PASSWORD_KEY);
                                    if (StringUtils.equals(statusMessagePassword, SessionConstants.CORRECT_PASSWORD)) {
                                        session.setAttribute(SessionConstants.ACCOUNT_CHANGE_PASSWORD_KEY, "");
                                    }

                                    if (StringUtils.isNotEmpty(statusMessagePassword)) {
                                        if (StringUtils.equals(statusMessagePassword, SessionConstants.CORRECT_PASSWORD)) {
                                            out.print("<p class=\'alert alert-success\'>" + statusMessagePassword + "</p>");
                                        } else {
                                            out.print("<p class=\'alert alert-danger\'>" + statusMessagePassword + "</p>");
                                        }
                                    }
                                %>

                                 <div class="form-inner">
                                     <div class="container">
                                <form id="editDetailsField" class="form-horizontal"name="ChangePassword" action="changePassword" onsubmit="return validatePassword('ChangePassword');" method="post">
                                    <div class="input-prepend subje">
                                        <label class="alert alert-success" for="currentPassword">Current Login Password:</label>
                                            <input type="password" name="currentPassword" id="input" value="" />
				    </div>
				   <div class="input-prepend subje">

                                            <label class="alert alert-info" for="newPassword">New Login Password:</label>
                                            <input type="password" name="newPassword" id="input" value="" />
				  </div>
				  <div class="input-prepend subje">
                                            <label class="alert alert-danger" for="confirmPassword">Confirm New Login Password:</label>
                                            <input type="password" name="confirmPassword" id="input" value="" />

                                    </div>
                                    <div class="subje2">
                                    <p id="accountButton">
                                        <input type="submit"  class="btn btn-info"name="submit" id="submit" value="Change Password" />
                                        <input type="hidden" name="accountUuid" id="accountUuid" value="<%= account.getUuid()%>" /> 
                                    </p>
                                    </div>
                                </form> 
                                     </div>
				</div>
                            </div>
                                        </div> 
                                        <%
                                        } 
                                        %>
                                    </div>



                                </div><!-- /.tab-content -->
                            </div><!-- /.tabbable -->
                    </div>
                </div>
            </div>
           </div>
         </section>




              



      </div>
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