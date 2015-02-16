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
		var idsRole=${param.ids};
		var actionMethod='${param.actionMethod}';
		var op='分配';
		if('assignPermission'!=actionMethod){
			op='移除';
		}		
		var objTreeFormPanel=new Ecums.ObjectsCheckPermissionTreePanel();
		var accssModeStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'findAccessByObjID'},
				url:Ecums.operatorUrl,
	   			root: 'data',
	       		totalProperty: 'totalCount',
	       		remoteSort: true,
	   			fields: ['accssId', 'alias','name','status','note']
		});	
		var accssModeCheckGridPanel=new Ecums.AccssModeCheckGridPanel({id:'accssModeCheckGridPanel',store:accssModeStore,bbar:[{
                    text : '提交',
                    pressed:true,
                    handler : function(){
                    		var objIds=[];
                    		var idsAccess=[];
                    		var nodeArr=objTreeFormPanel.getChecked();
                    		var smAccess = accssModeCheckGridPanel.getSelectionModel();    
					        var selectedAccess = smAccess.getSelections();
					        
                    		if(nodeArr.length==0){
                    			Ext.Msg.alert('信息提示', '请在对象树列表中选择一个对象');
                    			return;
                    		}
                    		
					        if(selectedAccess.length==0){
					        	Ext.Msg.alert('信息提示', '请选择要分配的对象操作权限');
					        	return;
					        }
					                            		
                    		Ext.each(nodeArr,function(node){
                    			objIds.push(parseInt(Ecums.TreeConfig.objAssignTree.subTag(node)));
                    		})
                    		
                    		Ext.each(selectedAccess,function(accessBean){
                    			idsAccess.push(accessBean.data.accssId);
                    		})
                    		
                    		Ext.Ajax.request({
			   					url: Ecums.permissionUrl,
			   					method:'post',
			   					success: function(){
			                		Ext.Msg.alert('信息提示', op+'对象操作权限成功');
					            },
					            failure : Ecums.comFun.failure,			            
			   					params: {idsRole: Ext.encode(idsRole),
			   						objIds: Ext.encode(objIds),
			   					 	idsAccess:Ext.encode(idsAccess),
			   					 	action:actionMethod
			   					}
							});						
	                	}
                	}]});
		objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
        		treeLoader.baseParams={
        			parentId:Ecums.TreeConfig.objAssignTree.subTag(node),
        			action:'operatorCheckTree'
        		}
        	}
        );	
				
		var viewport = new Ext.Viewport({
    	layout:'column',
    	items:[objTreeFormPanel,accssModeCheckGridPanel]
    	});
    	Ecums.comFun.loadCookieSkin();
	})  
    </script>
  </head>
  <body>
  </body>
</html>
