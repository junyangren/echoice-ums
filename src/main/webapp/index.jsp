<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/commons/meta.jsp"%>
  	<link rel="shortcut icon" type="image/x-icon" href="${ctx }/images/favicon.ico">
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
    			<c:forEach items="${item.subMenuObjList}" var="obj">
	     		<div class="menu_icon_center">
		    		<a class="menu_icon" href="javascript:void(0);" title="${obj.name}" onclick="${obj.note}">
						<img src="images/icon_menu/${obj.alias}.png" />
						${obj.name}
					</a>
		    	</div>
	        	</c:forEach>
		  	</div>
		</c:forEach>
		
	    <div id="x-desktop4">
	    	<div class="menu_icon_center">
	    		<a class="menu_icon" href="javascript:void(0);" title="用户资料" onclick="updateUserInfo();">
					<img src="images/icon_menu/user_info.png" />
					用户资料
				</a>
				<a class="menu_icon" href="javascript:void(0);" title="主题设置" onclick="createSkinMainPanel();">
					<img src="images/icon_menu/skin_menu.png" />
					主题设置
				</a>
				<a class="menu_icon" href="opGroup.do?action=updateGroupFullName" title="用户组同步" target="_ablank">
					<img src="images/icon_menu/sync-group.png" />
					用户组同步
				</a>
				<a class="menu_icon" href="logout.jsp" title="退出系统">
					<img src="images/icon_menu/logout_menu.png" />
					退出系统
				</a>
	    	</div>
		        
		</div>
    </div>
		
  </body>
</html>
