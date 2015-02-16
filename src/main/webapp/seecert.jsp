<%@ page language="java" import="java.security.cert.X509Certificate" pageEncoding="UTF-8"%>
<%
    java.security.cert.X509Certificate[] certs=null;
    try{
            certs=(X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");

                if (certs == null) {
                        out.println("No certificates");
                } else if (certs.length == 0) {
                        out.println("Certificates length is 0");
                } else {
                        java.security.cert.X509Certificate cert = certs[0];
                        String dn = cert.getSubjectX500Principal().toString();
                        out.println("SubjectDN: " + dn);
                        out.println();
                        out.println("------------------certification detail--------------------");
                        out.println(cert);
                        out.println("----------------------------------------------------------");
                }
    } catch(Exception e){
                out.println("Exception=" + e.getMessage());
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>CA CERT</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
