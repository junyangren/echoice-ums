var groupTreeParendId=-1;
var groupTreeParendName='用户组';
var currentGroupNode=null;
//用户组列表
Ecums.GroupsGridPanel = function(){
	//配置数据
	this.objectStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'index'},
				url:Ecums.groupUrl,
    			root: 'data',
        		totalProperty: 'totalCount',
        		remoteSort: true,
    			fields: ['groupId', 'alias','name','parentId','type','taxis','opTime'],
    			listeners:{
    					beforeload:function(){
    						this.baseParams.parentId=groupTreeParendId;
    					}
    			}
	});
	
	function reloadData(groupNode,opType){
		Ext.getCmp('groupsGridPanel').store.reload();
		var treePanel=Ext.getCmp('groupsTreePanel');
		if(!currentGroupNode){
			currentGroupNode=Ext.getCmp('groupsTreePanel').root;
		}
		if(groupNode&&currentGroupNode){
			if(opType=='add'){
				currentGroupNode.appendChild(groupNode);
				currentGroupNode.leaf=false;
				currentGroupNode.expand();
				return true;
			}else if(opType=='update'){
				//更新节点
				var tmpNode=treePanel.getNodeById(groupNode.id);
				//var tmpNode=currentGroupNode.findChild('id',groupNode.id);
				if(tmpNode){
					tmpNode.setText(groupNode.text);
				}
				return true;
			}
		}
		treePanel.root.reload();
	}
	
	this.groupParentRender = function(val){
		return groupTreeParendName;
	}
	
	this.addHandler=function(){
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/group_edit_win.jsp?objId=-1',450,410);
			return;
		}	
		var objFormPanel=new Ecums.GroupsFormPanel();
		Ecums.comFun.createCrubeWindow(objFormPanel,'用户组添加',450,410,'用户组添加成功',Ecums.groupUrl,'save',reloadData,'add');
	}
	
	this.updateHandler=function(){
			var grid=Ext.getCmp('groupsGridPanel');
			var store=grid.store;
			var sm = grid.getSelectionModel();
			var selected = sm.getSelections();    
	        if(selected.length!=1){
	        	Ext.Msg.alert('信息提示', '请选择一条记录');
				return;
	        }
			var tmpObj = selected[0].data;
	        var objKeyId=tmpObj.groupId;
	        if(Ext.isIE&&Ecums.isPopWin){
				Ecums.comFun.popWinCenter('console/group_edit_win.jsp?objId='+objKeyId,450,410);
				return;
			}	        
			var objFormPanel=new Ecums.GroupsFormPanel();
			Ecums.comFun.createCrubeWindow(objFormPanel,'用户组修改',450,410,'用户组修改成功',Ecums.groupUrl,'save',reloadData,'update');
			objFormPanel.getForm().load({url:Ecums.groupUrl+'?action=edit&groupId='+objKeyId});	
	}
	
	this.delHandler=function(){
        Ecums.comFun.deleteCrubeHandlerObj('groupsGridPanel','groupId',Ecums.groupUrl,'del','你确认要删除选中的用户组吗？','用户组删除成功',reloadData,'del');
	}
	
	this.assignUserHandler=function(){
	
	}
	
	this.opAssignRoleHandler=function(){
		
		var grid=Ext.getCmp('groupsGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length==0){
        	Ext.Msg.alert('信息提示', '请至少选择一条记录');
        	return;
        }
        var ids = [];    
        for(var i=0;i<selected.length;i+=1){
            var accessModeBean = selected[i].data;    
            if(accessModeBean.groupId) {
                ids.push(accessModeBean.groupId);
            }
        }
        var groupId='';
        if(ids.length==1){
        	groupId=ids[0];
        }
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/group_assign_role_win.jsp?groupId='+groupId+'&ids='+Ext.encode(ids)+'&actionMethod='+actionMethod,400,500);
			return;
		}        
		var objTreeFormPanel=new Ecums.RoleUserTreePanel();
		objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
        		treeLoader.baseParams={
        			parentId:Ecums.TreeConfig.roleAssignTree.subTag(node),
        			action:'roleCheckTree',
        			groupId:groupId
        		}
        	}
        );
		var opGroupRoleFun=function(actionMethod){
			var op='分配';
			if('removeRole'==actionMethod){
				op='移除';
			}
    		var objIds=[];
    		var nodeArr=objTreeFormPanel.getChecked();
    		if(nodeArr.length==0){
    			return;
    		}
    		Ext.each(nodeArr,function(node){
    			objIds.push(parseInt(Ecums.TreeConfig.roleAssignTree.subTag(node)));
    		})
    		
    		Ext.Ajax.request({
					url: Ecums.groupAssignmenUrl,
					method:'post',
					success: function(resp){
					if(Ecums.comFun.failureText(resp)){
						return;
					}			   					
            		Ext.Msg.alert('信息提示', op+'角色成功');
	            },
	            failure : Ecums.comFun.failure,
					params: {groupIds: Ext.encode(ids),
						roleIds: Ext.encode(objIds),
					 	action:actionMethod,
					 	groupId:groupId
					}
			});
    	}
		
		objWin=new Ext.Window({
               width: 400,
               height: 500,
               title:'分配移除用户组角色',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[objTreeFormPanel],
	           buttons: [{
                    text : '分配',
                    handler : function(){
                    	opGroupRoleFun('assignRole');
	                	}
                	},{
                        text : '移除',
                        handler : function(){
                        	opGroupRoleFun('removeRole');
    	                }
                    },{
                    	text     : '重置',
                    	handler  : function(){
                    		objTreeFormPanel.root.reload();
                    	}
                	}]
 		});
		objWin.show();
	}

    this.showPermissionHandler=function(){
 		var grid=Ext.getCmp('groupsGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length!=1){
        	Ext.Msg.alert('信息提示', '请选择一条操作记录');
        	return;
        }
        var groupId=selected[0].data.groupId;
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/group_assign_permission_win.jsp?groupId='+groupId,650,500);
			return;
		}          
    	var gridPanel=new Ecums.UserPermissionGridPanel();
        
        //当翻面时，要加载相应的查询参数
        gridPanel.store.on('beforeload',function(store,options){
        	store.baseParams.groupId=groupId;
        	store.baseParams.action='showGroupPermission';
        });
        
		objWin=new Ext.Window({
               width: 720,
               height: 500,
               title:'查看用户组分配的角色及操作权限',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[gridPanel]
         });
         objWin.show();
         gridPanel.store.load({params:{groupId:groupId,limit:Ecums.pageSize,start:0}});	
    }
    		
	this.pageToolBar=new Ext.PagingToolbar({
        		pageSize: Ecums.pageSize,
        		store: this.objectStore,
        		displayInfo: true,
        		displayMsg: '显示　对象记录 {0} - {1} of {2}',
        		emptyMsg: "暂无记录",
        		plugins:new Ext.ux.Andrie.pPageSize()
    });
    	
    this.checkSelMode=new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});	
	Ecums.GroupsGridPanel.superclass.constructor.call(this,{
		id:'groupsGridPanel',
		title:'用户组列表',
		columnWidth:.75,
		autoHeight: true,
		autoScroll:true,
		split:true,
		store:this.objectStore,
		selModel:this.checkSelMode,
		columns:[this.checkSelMode,
        	{id:'groupId', header: "ID", width: 80, sortable: true, dataIndex: 'groupId'},
       		{header: "标识", width: 150, sortable: true, dataIndex: 'alias'},
        	{header: "名称", width: 150, sortable: true, dataIndex: 'name'},
        	{header: "类型", width: 60, sortable: true, dataIndex: 'type',renderer:function(value,metadata,record,rowIndex,colIndex,store){
        		var types=Ecums.formdata.groupTypes
        		for(i=0;i<types.length;i++){
        			if(value==types[i][0]){
        				return types[i][1];
        			}
        		}
        	}},
        	{header: "排序", width: 60, sortable: true, dataIndex: 'taxis'},
        	{header: "操作时间", sortable: true, dataIndex: 'opTime'}
        	],
        	
    	tbar:[{text:"添加",iconCls:'form-button-new',handler:this.addHandler,hideMode:"visibility"},
    		'-',
    		{text:"修改",iconCls:'form-button-edit',handler:this.updateHandler},
    		'-',
    		{text:"删除",iconCls:'form-button-delete',handler:this.delHandler},
    		'-',
    		{text:"分配移除角色",name:'assignRole',iconCls:'form-button-assign-obj',handler:this.opAssignRoleHandler},
    		/**
    		'-',
    		{text:"移除角色",name:'removeRole',iconCls:'form-button-remove-obj',handler:this.opAssignRoleHandler},**/
    		'-',
    		{text:"查看权限",name:'showPermission',iconCls:'form-button-show-obj',handler:this.showPermissionHandler}],
    	bbar:this.pageToolBar
	})	
}


Ext.extend(Ecums.GroupsGridPanel,Ext.grid.GridPanel);

Ecums.GroupsTreePanel=function(){
	this.myTreeLoader = new Ext.tree.TreeLoader({  
    	url:Ecums.groupUrl,
    	listeners:{
    		beforeload:function(treeLoader, node){
	        		treeLoader.baseParams={
	        		parentId:Ecums.TreeConfig.groupTree.subTag(node),
	        		action:'tree'
        		}
        	}
        }
	});
	    
	Ecums.GroupsTreePanel.superclass.constructor.call(this,{
		id:'groupsTreePanel',
		title:'用户组层级',
		columnWidth:.25,
		height:450,
		margins:'10 10 10 10',
		autoScroll:true,    
	    //autoHeight: true,    
	    animate:true,
	    enableDD:true,   
	    rootVisible:true,    
	    containerScroll: true,    
	    loader: this.myTreeLoader,
	 	lines: true,
	 	singleExpand: false,
	 	useArrows: false,
	 	collapseMode:'mini',
		collapseFirst:false,
    	root: {
        	nodeType:'async',    
        	text: '用户组',
        	id: Ecums.TreeConfig.groupTree.tag+'-1'
    	},
    	listeners:{
    		click:function(node,eo){
    			currentGroupNode=node;
    			var tmpStore=Ext.getCmp('groupsGridPanel').store;
    			groupTreeParendId=Ecums.TreeConfig.groupTree.subTag(node);
    			groupTreeParendName=node.attributes.text;
    			tmpStore.load({params:{start:0,limit:Ecums.pageSize,parentId:groupTreeParendId}});
    		},
    		nodedrop:function(e){
				if(e.point=='append'){
					var dragId=Ecums.TreeConfig.groupTree.subTag(e.dropNode);
					var targetId=Ecums.TreeConfig.groupTree.subTag(e.target);
					Ext.Ajax.request({
			   			url: Ecums.groupUrl,
			   			success: function(resp){
							if(Ecums.comFun.failureText(resp)){
								return;
							}			   			
	                		Ext.Msg.alert('信息提示', '用户组移动成功');
					    },
			            failure : Ecums.comFun.failure,					              
			   			params: {action:'drag',
			   					dragId:dragId,
			   					targetId:targetId
			   			}
					});
			     }else if(e.point=='above'){
			     }else if(e.point=='below'){ 
			     }
    		}    		
    	}
	})
};

Ext.extend(Ecums.GroupsTreePanel,Ext.tree.TreePanel);


Ecums.GroupsUserTreePanel=function(){
	this.myTreeLoader = new Ext.tree.TreeLoader({  
    	url:Ecums.groupUrl,
    	listeners:{
    		beforeload:function(treeLoader, node){
	        		treeLoader.baseParams={
	        		parentId:Ecums.TreeConfig.groupTree.subTag(node),
	        		action:'tree'
        		}
        	}
        }
	});
	    
	Ecums.GroupsUserTreePanel.superclass.constructor.call(this,{
		id:'groupsUserTreePanel',
		title:'用户组层级',
		columnWidth:.25,
		height:450,
		margins:'10 10 10 10',
		autoScroll:true,    
	    //autoHeight: true,    
	    animate:true,
	    enableDD:true,   
	    rootVisible:true,    
	    containerScroll: true,    
	    loader: this.myTreeLoader,
	 	lines: true,
	 	singleExpand: false,
	 	useArrows: false,
	 	collapseMode:'mini',
		collapseFirst:false, 
    	root: {
        	nodeType:'async',    
        	text: '用户组',
        	id: Ecums.TreeConfig.groupTree.tag+'-1'
    	},
    	listeners:{
    		click:function(node,eo){
    			var tmpStore=Ext.getCmp('userGridPanel').store;
    			tmpStore.baseParams=[];
    			tmpStore.baseParams.action='index';
    			groupTreeParendId=Ecums.TreeConfig.groupTree.subTag(node);
    			groupTreeParendName=node.attributes.text;
    			tmpStore.baseParams.groupId=groupTreeParendId;
    			tmpStore.load({params:{start:0,limit:Ecums.pageSize}});
    		}
    	}
	})
};

Ext.extend(Ecums.GroupsUserTreePanel,Ext.tree.TreePanel);


Ecums.UserAssignGroupTreePanel=function(){
	this.myTreeLoader = new Ext.tree.TreeLoader({  
    	url:Ecums.groupUrl,
        baseAttrs: {uiProvider: Ext.ux.TreeCheckNodeUI}
	});
	if(arguments){
		Ecums.UserAssignGroupTreePanel.superclass.constructor.call(this,arguments[0]);
	}	    
	Ecums.UserAssignGroupTreePanel.superclass.constructor.call(this,{
		id:'userAssignGroupTreePanel',
	    animate:true,  
	    rootVisible:true,    
	    containerScroll: true,
	    autoScroll:true,
	    loader: this.myTreeLoader,
	 	lines: true,
	 	singleExpand: false,
	 	useArrows: false,
	 	collapseMode:'mini',
		collapseFirst:false,
		//checkModel:'parentCascade',
    	root: {
        	nodeType:'async',
        	text: '用户组',
        	id: Ecums.TreeConfig.groupTree.tag+'-1'
    	},
    	//Ext.getCmp('userAssignGroupTreePanel').expandAll()
    	bbar:[{text:"全部展开",iconCls:'',handler:function(){}},'-',
    	{text:"全部收起",iconCls:'',handler:function(){Ext.getCmp('userAssignGroupTreePanel').collapseAll()}}]    	
	})
};

Ext.extend(Ecums.UserAssignGroupTreePanel,Ext.tree.TreePanel);

Ecums.GroupsFormPanel=function(){
	if(arguments){
		Ecums.GroupsFormPanel.superclass.constructor.call(this,arguments[0]);
	}
	Ecums.GroupsFormPanel.superclass.constructor.call(this,{
		id:'groupsFormPanel',
		defaultType: 'textfield',
		margins:'10 0 10 0',
		labelWidth:110,
		labelAlign:'right',
		items: [{
                fieldLabel: '组ID(系统生成)',
                name: 'groupId',
                readOnly:true
            },{
                fieldLabel: '标识(系统生成)',
                name: 'alias',
                anchor:'95%',
                maxLength:64,
                allowBlank:true,
                readOnly:true
            },{
                fieldLabel: '名称',
                name: 'name',
                anchor:'95%',
                maxLength:64,
                allowBlank:false
            },new Ecums.GroupTypeComboBox(),
            {
                fieldLabel: '上级组',
                name: 'parentName',
                anchor:'95%',
                value:groupTreeParendName,
                readOnly:true
            },{
                fieldLabel: '',
                name: 'parentId',
                xtype:'hidden',
                value:groupTreeParendId
            },{
                fieldLabel: '排序',
                name: 'taxis',
                maxLength:10,
                allowBlank:false,
                value:99999
            },{
                fieldLabel: '备注',
                xtype:'textarea',
                anchor:'95%',
                name: 'note',
                height:40,
                maxLength:512
            },{
                fieldLabel: '扩展属性1',
                xtype:'textarea',
                anchor:'95%',
                name: 'note2',
                height:40,
                maxLength:512
            },{
                fieldLabel: '扩展属性2',
                xtype:'textarea',
                anchor:'95%',
                name: 'note3',
                height:40,
                maxLength:512
            }]
	})
};

Ext.extend(Ecums.GroupsFormPanel,Ext.form.FormPanel);

//用户组管理主窗口
Ecums.GroupsMainPanel = function(){
	this.objectsGridPanel=new Ecums.GroupsGridPanel();
	
	this.objectTreePanel=new Ecums.GroupsTreePanel();
	Ecums.GroupsMainPanel.superclass.constructor.call(this,{
		title:'用户组管理',
		id:'groupsMainPanel',
		closable: true,
		layout:'column',
		listeners:{destroy:function()
			{
				groupTreeParendId=-1;
				groupTreeParendName='用户组';
			}
		},
		items:[this.objectTreePanel,this.objectsGridPanel]
	});
	this.objectsGridPanel.store.load({params:{limit:Ecums.pageSize,start:0}});
}
Ext.extend(Ecums.GroupsMainPanel,Ext.Panel);
