<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--
    This page is displayed when there is a 500 server error (internal server error).
--%>
<%
    String contextPath = getServletContext().getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>We're sorry, but something went wrong (500)</title>
        <style type="text/css">
            body { background-color: #efefef; color: #333; font-family: Georgia,Palatino,'Book Antiqua',serif;padding:0;margin:0;text-align:center; }
            p {font-style:italic;}
            div.dialog {
                width: 490px;
                margin: 4em auto 0 auto;
            }
            img { border:none; }
        </style>
    </head>

    <body>
      <div class="dialog">
        <a href="<%= contextPath %>/index.jsp"><img src="<%= contextPath %>/images/error.png" /></a>
        <p>
            Oh dear, it looks like something went horribly wrong.<br /><br />
            We'll be taking a look at that shortly.<br /><br />
            Please go back to the <a href="<%= contextPath %>/index.jsp">Home Page</a>.
        </p>
        
      </div>
    </body>
    
</html>