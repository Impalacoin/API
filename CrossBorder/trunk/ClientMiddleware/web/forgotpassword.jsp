<!DOCTYPE html>
<%-- 
  Copyright (c) Impalapay Ltd., Jun 10, 2014
  
  @author Eugene Chimita, eugenechimita@impalapay.com
--%>
<%--<%@page import="ke.co.shujaa.airtimegw.server.util.EmailUtil"%>--%>

<%@page import="com.impalapay.airtel.cache.CacheVariables"%>
<%@page import="com.impalapay.airtel.accountmgmt.session.SessionConstants"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.RandomStringUtils"%>

<%@page import="org.apache.commons.mail.DefaultAuthenticator"%>
<%@page import="org.apache.commons.mail.SimpleEmail"%>
<%@page import="org.apache.commons.mail.Email"%>

<%@page import="com.impalapay.airtel.servlet.util.PropertiesConfig"%>

<%@page import="com.impalapay.airtel.beans.accountmgmt.Account"%>

<%@page import="com.impalapay.airtel.persistence.accountmgmt.AccountDAO"%>

<%@page import="java.util.Calendar"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    final String senderEmail = PropertiesConfig.getConfigValue("EMAIL_DEFAULT_EMAIL_FROM");
    final String outgoingEmailServer = PropertiesConfig.getConfigValue("EMAIL_OUTGOING_SMTP");
    final int outgoingEmailPort = Integer.parseInt(PropertiesConfig.getConfigValue("EMAIL_OUTGOING_SMTP_PORT"));

    CacheManager mgr = CacheManager.getInstance();
    Cache accountCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_EMAIL);


    String recipientEmail = request.getParameter("email");
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Forgot password | ImpalaPay</title>
        <link  rel="icon" type="image/png" href="css/eugene.gif">

        <script type="text/javascript" src="http://www.google.com/jsapi"></script>

        <script src="js/jquery/jquery-1.8.2.min.js" type="text/javascript" ></script>
        <link href="css/customize-template.css" type="text/css" media="screen, projection" rel="stylesheet" />

        <!--[if lt IE 9]>
            <script type="text/javascript" src="js/html5_IE.js"></script>
        <![endif]-->
    </head>

    <body id="signin">
    <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <button class="btn btn-navbar" data-toggle="collapse" data-target="#app-nav-top-bar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    
                    <ul class="nav">
                        <li class="alpha"><a href=""><span><span>Home</span></span></a></li>
                        <li><a href=""><span><span>Features</span></span> </a></li>
                        <li><a href=""><span><span>FAQ</span></span> </a></li>                
                        <li><a href=""><span><span>Contacts</span></span></a></li>
                        <li class="omega" id="menu_active"><a href=""><span><span>Sign In</span></span> </a></li>
                    </ul>


                </div>

            </div>
        </div>
        <div class="container-fluid">
            <!-- header -->
            
            <!-- / header -->
            <div class="container">
            <header>
                <div class="alert alert-info">
                    <h1><a href="index.jsp" id="logo">ImapalaPay Ltd.</a></h1>
                    
                </div>
                
                
                <div class="wrapper">
                    <div class="text">
                        <h2 class="page_title">Reset Password</h2>
                    </div>      
                </div>
            </header>
            <section id="content">
                <div class="text container-signin">
                    <div id="login-box">
                        <div class="banners">
                            <form id="login-form" name="passwordForm" method="post" action="forgotpassword.jsp">
                                <%


                                    if (recipientEmail != null) {
                                        Account account;


                                        AccountDAO accountDAO = AccountDAO.getInstance();

                                        account = accountDAO.getAccountEmail(recipientEmail);

                                        if (account != null) {
                                            //Generate random alpha-numeric password 
                                            //and update the database and cache

                                            String randomPassword = RandomStringUtils.randomAlphanumeric(8);
                                            String body = "Your password is :" + randomPassword;
                                            account.setLoginPasswd(randomPassword);


                                            accountDAO.updateAccount(account.getUuid(), account);
                                            accountCache.put(new Element(account.getEmail(), account));



                                            //send an email containing the random password recipientEmail the account holder
                                           // EmailUtil.sendEmail(body, senderEmail, recipientEmail, "Reset Password", outgoingEmailServer, outgoingEmailPort);

                                            response.sendRedirect("index.jsp");
                                        } else {

                                            out.println("<p class=\" error \">The supplied email does not exist !</p>");

                                        }
                                    }
                                %>
                                <div>
                                    <div class="wrapper">
                                        <label for="email">Email:</label>
                                        <div class="bg"><input class="input" type="text" name="email" id="email" /></div>
                                    </div>

                                    


                                    <input class="btn btn-primary" type="submit"  class="button" name="submit"  value="Submit" />
                                    <input class="btn btn-danger" type="button" class="button" onclick="document.getElementById('login-form').reset();" value="Clear" />
                                </div>


                            </form>
                        </div>
                    </div>
                </div>

            </section>
            
            <footer>
                <p><a href="">Terms &amp; Conditions</a> | <a href="">Privacy Policy</a> | ImpalaPay Ltd <%= Calendar.getInstance().get(Calendar.YEAR)%>. All rights reserved.</p>
                <img id="madeInKenya" alt="Made in Kenya" src="css/eugene.gif" width="100" height="100" />
            </footer>
            
            </div>
            <!-- content -->
            
            <!-- / content -->
            <!-- footer -->
            
            <!-- / footer -->
        </div>

        
    </body>
</html>