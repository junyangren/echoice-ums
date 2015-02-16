<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/commons/meta.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx }/scripts/ext-2.2/resources/css/ext-all.css" />
    <link rel="stylesheet" id="extjs-skin-style-Id" type="text/css" href="scripts/ext-2.2/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="${ctx }/styles/ext-patch.css" />
    <link rel="stylesheet" type="text/css" href="${ctx }/styles/ecums.css" />
 	<script type="text/javascript" src="${ctx }/scripts/ext-2.2/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ext-2.2/ext-all.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ext-2.2/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ext-plugins/pageBar-extend.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ext-plugins/TreeCheckNodeUI-min.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/ecums-init.jsp"></script>      
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/ecums.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/index-layout.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/object.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/accessMode.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/group.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/user.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/role.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/permission.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ums-ext/skin.js"></script>
  </head>
  <body>
    <div id="header" class="header-box"><h1>统一用户权限管理系统（UMS2.0）</div>
    <div style="display: none">
     	<c:forEach items="${accordion}" var="item">
     		<div id="${item.parentMenuObj.alias}">
     			<dl id="x-shortcuts">
     				<c:forEach items="${item.subMenuObjList}" var="obj">
     				<dt id="${obj.alias}">
			            <a href="javascript:void(0);" onclick="${obj.note}"><img src="images/s.gif" />
			            <div>${obj.name}</div></a>
		        	</dt>
		        	</c:forEach>
     			</dl>
		  	</div>
		</c:forEach>
		
	    <div id="x-desktop4">
		    <dl id="x-shortcuts">
		        <dt id="user_menu">
		            <a href="javascript:void(0)" onclick="updateUserInfo();"><img src="images/s.gif" />
		            <div>用户资料</div></a>
		        </dt>		    
		        <dt id="ec-skin-shortcut">
		            <a href="javascript:void(0)" onclick="createSkinMainPanel();"><img src="images/s.gif" />
		            <div>主题设置</div></a>
		        </dt>
		        <dt id="ec-logout-shortcut">
		            <a href="logout.jsp"><img src="images/s.gif" />
		            <div>退出系统</div></a>
		        </dt>         	        
		    </dl>    
		</div>
    </div>
		
  </body>
</html>
