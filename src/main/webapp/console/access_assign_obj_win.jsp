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
		var accessId='${param.accessId}';
		var ids=${param.ids};
		var objTreeFormPanel=new Ecums.ObjectsCheckShowTreePanel({title:op+'对象',tbar:[{
                    text : '提交',
                    pressed:true,
                    handler : function(){
                    		var objIds=[];
                    		var nodeArr=objTreeFormPanel.getChecked();
                    		if(nodeArr.length==0){
                    			return;
                    		}
                    		Ext.each(nodeArr,function(node){
                    			objIds.push(parseInt(Ecums.TreeConfig.objAssignTree.subTag(node)));
                    		})
                    		
                    		Ext.Ajax.request({
			   					url: Ecums.operatorUrl,
			   					method:'post',
			   					success: function(){
			                		Ext.Msg.alert('信息提示', op+'对象成功');
					            },
					            failure : Ecums.comFun.failure,		            
			   					params: {ids: Ext.encode(ids),
			   						objIds: Ext.encode(objIds),
			   					 	action:actionMethod,
			   					 	accessId:accessId
			   					}
							});
	                	}
                	}]});
		objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
        		treeLoader.baseParams={
        			parentId:Ecums.TreeConfig.objAssignTree.subTag(node),
        			action:'operatorCheckTree',
        			accessId:accessId
        		}
        	}
        );
		var viewport = new Ext.Viewport({
    	layout:'fit',
    	items:[objTreeFormPanel]
    	});
    	Ecums.comFun.loadCookieSkin();
	})    
    </script>
  </head>
  <body>
  </body>
</html>
