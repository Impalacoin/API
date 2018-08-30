<!DOCTYPE html>
<%-- 
  Copyright (c) Impalapay Ltd., July, 2014
  
  @author Eugene Chimita, eugene@impalapay.com
--%>
<%@page import="java.net.URLEncoder"%>

<%@page import="org.apache.commons.lang3.RandomStringUtils"%>

<%@page import="org.jasypt.util.text.BasicTextEncryptor"%>

<%@ page import="com.impalapay.airtel.servlet.util.PropertiesConfig" %>

<%@ page import="com.impalapay.airtel.accountmgmt.admin.SessionConstants" %>

<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@page import="java.util.Calendar"%>

<%
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    // String ENCRYPT_PASSWORD = "Vuwachip2";
    textEncryptor.setPassword(PropertiesConfig.getConfigValue("ENCRYPT_PASSWORD"));
    //textEncryptor.setPassword(ENCRYPT_PASSWORD);


    final int CAPTCHA_LENGTH = 4;
    String captchaStr = RandomStringUtils.randomAlphabetic(CAPTCHA_LENGTH);
    String encryptedCaptchaStr = textEncryptor.encrypt(captchaStr);
%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Admin Portal</title>
    <link  rel="icon" type="image/png" href="css/eugene.gif">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <meta name="layout" content="main"/>
    
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    

    <script src="js/jquery/jquery-1.8.2.min.js" type="text/javascript" ></script>
    <link href="css/customize-template.css" type="text/css" media="screen, projection" rel="stylesheet" />

    <style>
    </style>
</head>
    <body>
        
        <div id="body-container">
                    <div id="body-content">
                        
                        
            <div class='container'>
                
                <div class="signin-row row">
                    <div class="span4"></div>
                    <div class="span8">
                        <div class="container-signin">
                            <legend>Admin Login</legend>
                            <form action='login' method='POST' id='loginForm' class='form-signin' autocomplete='off'>
                              <%
                        String loginErrStr = (String) session.getAttribute(SessionConstants.ADMIN_SIGN_IN_ERROR_KEY);

                        if (StringUtils.isNotEmpty(loginErrStr)) {
                            out.println("<p class=\"alert alert-error\">");
                            out.println("Login error: " + loginErrStr);
                            out.println("</p>");
                            session.setAttribute(SessionConstants.ADMIN_SIGN_IN_ERROR_KEY, null);
                        }
                    %>  
                                <div class="form-inner">
                                    <div class="input-prepend">
                                        
                                        <span class="add-on" rel="tooltip" title="Username or E-Mail Address" data-placement="top"><i class="icon-user"></i></span>
                                        <input type='text' class='span4' id='username' name='username'/>
                                    </div>

                                    <div class="input-prepend">
                                        
                                        <span class="add-on"><i class="icon-key"></i></span>
                                        <input type='password' class='span4' id='password' name='password'/>
                                    </div>
                                    <label class="checkbox" for='remember_me'>Remember me
                                        <input type='checkbox' id='remember_me'
                                               />
                                    </label>
                                    
                                    <%                                                        
                                String fontImageUrl = "../fontImageGenerator?text=" + URLEncoder.encode(encryptedCaptchaStr, "UTF-8");
                            %>
                                      <div class="row">
                                       <label for='captchaAnswer' class="alert alert-info">Word Verification:</label>
                                     <span id="captchaGuidelines">Type the characters you see in the image below. (<em>Letters are not case-sensitive</em>)</span>
                                        <img id="captcha" src=<% out.println("\"" + fontImageUrl + "\"");%> width="68" height="29" />
                                        <input type="text" name="captchaAnswer" id="captchaAnswer" size="5" class="input_normal" />
                                        <input type="hidden" name="captchaHidden" id="captchaHidden"
                                               value=<% out.println("\"" + URLEncoder.encode(encryptedCaptchaStr, "UTF-8") + "\"");%> />
                                        
                                    </div>
                                    <div class="forgotpassword">
                                    <a href="forgotpassword.jsp">Forgot Password?</a>
                                    </div>
                                </div>
                               
                                <footer class="signin-actions">
                                    <input class="btn btn-primary" type='submit' id="submit" value='Login'/>
                                     <input class="button" type="button" class="button" onclick="document.getElementById('login-form').reset()" value="Clear" />
                                </footer>
                            </form>
                        </div>
                    </div>
                    <div class="span3"></div>
                </div>

                
            <!--<div class="span4">

                </div>-->
            </div>
    

            </div>
        </div>

        <div id="spinner" class="spinner" style="display:none;">
            Loading&hellip;
        </div>

       
        <script type="text/javascript">
            $(function(){
                document.forms['loginForm'].elements['j_username'].focus();
                $('body').addClass('pattern pattern-sandstone');
                $("[rel=tooltip]").tooltip();
            });
        </script>
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
        

	</body>
</html>
