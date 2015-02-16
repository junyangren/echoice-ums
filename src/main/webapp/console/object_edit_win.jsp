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
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/ecums.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/object.js"></script>
    <script type="text/javascript">
	Ext.onReady(function(){
		Ext.QuickTips.init();
		var objId=${param.objId};
		objTreeParendId=opener.window.objTreeParendId;
		objTreeParendName=opener.window.objTreeParendName;
		var contxtUrl=Ecums.objectUrl;
		var objFormPanel=new Ecums.ObjectsFormPanel({title:'对象管理',border:false,
		buttons:Ecums.comFun.getFormButton('objectsFormPanel',contxtUrl,'save','对象保存成功')});
		if(objId!=-1){
			objFormPanel.getForm().load({url:contxtUrl+'?action=edit&objId='+objId});
		}
		var viewport = new Ext.Viewport({
    	layout:'fit',
    	items:[objFormPanel]
    	});
    	Ecums.comFun.loadCookieSkin();
	})    
    </script>
  </head>
  <body>
  </body>
</html>
