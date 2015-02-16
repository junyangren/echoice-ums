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
    <script type="text/javascript" src="${ctx}/scripts/ext-2.2/TreeCheckNodeUI-min.js"></script>  
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/ecums.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/object.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/accessMode.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/role.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ums-ext/permission.js"></script>
    <script type="text/javascript">
	Ext.onReady(function(){
		Ext.QuickTips.init();
		var actionMethod='${param.actionMethod}';
		var op='分配';
		if('assignObj'!=actionMethod){
			op='移除';
		}
		var objIds=${param.objIds};
		accssModeStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'index'},
				url:Ecums.accessModeUrl,
	   			root: 'data',
	       		totalProperty: 'totalCount',
	       		remoteSort: true,
	   			fields: ['accssId', 'alias','name','status','note']
		});
			
		var accessModePanel=new Ecums.AccssModeCheckGridPanel({id:'AccssModeCheckGridPanel-2',store:accssModeStore,tbar:[{
                    text : '提交',
                    pressed:true,
                    handler : function(){
                     		var sm2 = accessModePanel.getSelectionModel();
                    		var selectedAccess = sm2.getSelections();
                    		if(selectedAccess.length<=0){
                    			Ext.Msg.alert('信息提示', '请选择相应记录');
                    		}
                    		var idsAccess=[];
                    		
                    		Ext.each(selectedAccess,function(accessBean){
                    			idsAccess.push(accessBean.data.accssId);
                    		});
                    		
                    		Ext.Ajax.request({
			   					url: Ecums.operatorUrl,
			   					method:'post',
			   					success: function(){
			                		Ext.Msg.alert('信息提示',op+'对象操作成功');
					            },
					            failure : Ecums.comFun.failure,		            
			   					params: {ids: Ext.encode(idsAccess),
			   						objIds: Ext.encode(objIds),
			   					 	action:actionMethod
			   					}
							});                   
                    }
		}]});
		accessModePanel.title=op+'操作';
		var viewport = new Ext.Viewport({
    	layout:'fit',
    	items:[accessModePanel]
    	});
    	accessModePanel.store.load();
    	Ecums.comFun.loadCookieSkin();
	})    
    </script>
  </head>
  <body>
  </body>
</html>
