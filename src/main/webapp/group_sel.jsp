<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>统一权限管理系统</title>
    <link href="styles/login.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx }/scripts/validator1.0.5/validator1.0.5-utf.js"></script>  
    <style type="text/css">
    	.groupPanel{
    		width: 400px;
    		height: 180px;
    		margin-left: auto;
    		margin-right: auto;
    		margin-top: 200px;
    		border: 1px solid #BAE0E4;
    		text-align: left;
    		background-color: white;
    	}
    	.groupTitle{
    		background-color: #1899A7;
    		line-height: 45px;
    		font-size: 16px;
    		font-weight: bold;
    		color: white;
    		padding-left: 36px;
    		background-image: url("images/shortcut/security.png");
    		background-repeat: no-repeat;
    		background-position: left center;
    	}
    	.groupContent{
    		padding-left: 100px;
    		margin-top: 30px;
    	}
    	.groupBntPanel{
    		padding-left: 48px;
    		margin-top: 6px;
    	}
    	.groupColPanel{
    		line-height: 30px;
    	}
    </style>
    <script type="text/javascript">
		function subForm(){
			var dForm=document.forms[0];
			var valid=Validator.Validate(dForm,3);
			if(valid){
				dForm.submit();
			}
		}
    </script>
  </head>
  <body style="text-align: center;">
  	<form action="login.do?action=selGroup" method="post">
  	<div class="groupPanel">
  		<div class="groupTitle">登入用户组选择</div>
  		<div class="groupContent">
  			<div class="groupColPanel">
  			用户组：<select name="groupId" dataType="Require" msg="*">
  					<option value="">---请选择---</option>
  					<c:forEach var="listone" items="${groupList}">
  					<option value="${listone.groupId }">${listone.name }</option>
  					</c:forEach>
  				 </select>
  			</div>
  			<div class="groupBntPanel"><button type="button" id="selGroupBtn" onclick="subForm();">进入</button></div> 	
  		</div>
  	</div>
  	</form>
  </body>
</html>
