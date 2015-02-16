<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/commons/meta.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/scripts/ext-2.2/resources/css/ext-all.css" />
    <link rel="stylesheet" id="extjs-skin-style-Id" type="text/css" href="${ctx}/scripts/ext-2.2/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/ext-patch.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/ecums.css" />
 	<script type="text/javascript" src="${ctx}/scripts/ext-2.2/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ext-2.2/ext-all.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ext-2.2/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ext-2.2/pageBar-extend.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/ecums.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/role.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/permission.js"></script>
    <script type="text/javascript">
	Ext.onReady(function(){
		Ext.QuickTips.init();	
		var userId='${param.userId}';
    	var gridPanel=new Ecums.UserPermissionGridPanel({title:'查看用户分配的角色及操作权限'});
        
        //当翻面时，要加载相应的查询参数
        gridPanel.store.on('beforeload',function(store,options){
        	store.baseParams.userId=userId;
        });
        gridPanel.store.load({params:{userId:userId,limit:Ecums.pageSize,start:0}});		
		var viewport = new Ext.Viewport({
    	layout:'fit',
    	items:[gridPanel]
    	});
    	Ecums.comFun.loadCookieSkin();
	})  
    </script>
  </head>
  <body>
  </body>
</html>
