<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="org.echoice.ums.service.UmsServiceFactory"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/commons/meta.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx }/scripts/ext-3.0/resources/css/ext-all-notheme.css" />
    <link rel="stylesheet" id="extjs-skin-style-Id" type="text/css" href="${ctx }/scripts/ext-3.0/resources/css/xtheme-blue.css" />
    
    <link rel="stylesheet" type="text/css" href="${ctx }/styles/ecums.css" />
 	<script type="text/javascript" src="${ctx }/scripts/ext-3.0/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ext-3.0/ext-all.js"></script>
    <script type="text/javascript" src="${ctx }/scripts/ext-3.0/locale/ext-lang-zh_CN.js"></script>
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
    <div id="header" class="header-box"><h1>Echoice Ums System V2.0</h1></div>
    <div style="display: none">
    <%
    	//boolean isPass=UmsServiceFactory.getValidPermissionForUmsService().checkHasPermission(request);
    	boolean isPass=true;
    	if(isPass){
     %>    
	    <div id="x-desktop1">
		    <dl id="x-shortcuts">
		        <dt id="access-win-shortcut">
		            <a href="javascript:void(0);" onclick="createAccssModeMainPanel();"><img src="${ctx }/images/s.gif" />
		            <div>操作管理</div></a>
		        </dt>
		        <dt id="obj-win-shortcut">
		            <a href="javascript:void(0);" onclick="createObjectsMainPanel();"><img src="${ctx }/images/s.gif" />
		            <div>对象管理</div></a>
		        </dt>
		    </dl>
		</div>
	    <div id="x-desktop2">
		    <dl id="x-shortcuts">
		        <dt id="ec-group-shortcut">
		            <a href="javascript:void(0);" onclick="createGroupsMainPanel();"><img src="${ctx }/images/s.gif" />
		            <div>用户组管理</div></a>
		        </dt>
		        <dt id="ec-user-shortcut">
		            <a href="javascript:void(0);" onclick="createUserMainPanel();"><img src="${ctx }/images/s.gif" />
		            <div>用户管理</div></a>
		        </dt>
		    </dl>
		</div>
	    <div id="x-desktop3">
		    <dl id="x-shortcuts">
		        <dt id="ec-role-shortcut">
		            <a href="javascript:void(0);" onclick="createRoleMainPanel();"><img src="${ctx }/images/s.gif" />
		            <div>角色管理</div></a>
		        </dt>
		        <dt id="ec-search-shortcut">
		            <a href="javascript:void(0);" onclick="createSearchMainPanel();"><img src="${ctx }/images/s.gif" />
		            <div>权限检索</div></a>
		        </dt>
		    </dl>
		</div>
	<%
		}
	 %>		
	    <div id="x-desktop4">
		    <dl id="x-shortcuts">
		        <dt id="ec-user-shortcut">
		            <a href="javascript:void(0)" onclick="updateUserInfo();"><img src="${ctx }/images/s.gif" />
		            <div>用户资料</div></a>
		        </dt>		    
		        <dt id="ec-skin-shortcut">
		            <a href="javascript:void(0)" onclick="createSkinMainPanel();"><img src="${ctx }/images/s.gif" />
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
