<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
	session.invalidate();
	String serName=request.getServerName();
	int serPort=request.getServerPort();
	String url="http://"+serName+":"+serPort+"/ecums";	
	String casSerName="localhost";
	response.sendRedirect("http://"+casSerName+":81/cas/logout?service="+url);
	//response.sendRedirect("jump.jsp");
 %>

