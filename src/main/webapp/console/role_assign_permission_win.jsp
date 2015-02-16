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
		var roleId='${param.roleId}';
		
        var permissionGridPanel=new Ecums.PermissionGridPanel({title:'查看角色已分配的对象操作权限'});
        //当翻面时，要加载相应的查询参数
        permissionGridPanel.store.on('beforeload',function(store,options){
        	store.baseParams.roleId=roleId;
        });		
		
		permissionGridPanel.store.load({params:{roleId:roleId,limit:Ecums.pageSize,start:0}});	
				
		var viewport = new Ext.Viewport({
    	layout:'fit',
    	items:[permissionGridPanel]
    	});
    	Ecums.comFun.loadCookieSkin();
	})  
    </script>
  </head>
  <body>
  </body>
</html>
