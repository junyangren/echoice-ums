<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/commons/meta.jsp"%> 
	<link rel="stylesheet" type="text/css" href="${ctx }/scripts/ext-2.2/resources/css/ext-all.css" /> 
	<script type="text/javascript" src="${ctx }/scripts/ext-2.2/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ext-2.2/ext-all.js"></script>  
	<script type="text/javascript">
		Ext.onReady(function(){
			Ext.getDom('jumpFormID').submit();
		});	
		
	</script>
  </head>
  <body>
  <form action="${ctx }/login.jsp" id="jumpFormID" method="post">
  
  </form>	
  </body>
</html>
