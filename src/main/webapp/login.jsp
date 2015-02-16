<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title><fmt:message key="ums.title"/></title>
    <link href="styles/login.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="scripts/ext-2.2/resources/css/ext-all.css" />
    <link rel="stylesheet" id="extjs-skin-style-Id" type="text/css" href="scripts/ext-2.2/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="styles/ext-patch.css" />
    <link rel="stylesheet" type="text/css" href="styles/ecums.css" />
 	<script type="text/javascript" src="scripts/ext-2.2/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="scripts/ext-2.2/ext-all.js"></script>
    <script type="text/javascript" src="scripts/ext-2.2/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript">
		Ext.namespace('Ecums');
		Ecums.Path={
			contextPath:'${ctx}/'
		};    
    </script>
    <script type="text/javascript" src="scripts/ums-ext/ecums.js"></script>
    <script type="text/javascript" src="scripts/ums-ext/login.js"></script>
  </head>
  <body>
  
  </body>
</html>
