var roleTreeParendId=-1;
var roleTreeParendName='角色';
var currentRoleNode=null;
//角色列表
Ecums.RoleGridPanel = function(){
	//配置数据
	this.objectStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'index'},
				url:Ecums.roleUrl,
    			root: 'data',
        		totalProperty: 'totalCount',
        		remoteSort: true,
    			fields: ['roleId', 'alias','name','parentId','status','taxis','opTime','parentName'],
    			listeners:{
    					beforeload:function(){
    						//this.baseParams.parentId=roleTreeParendId;
    					}
    			}
	});
	function reloadData(roleNode,opType){
		Ext.getCmp('roleGridPanel').store.reload();
		var treePanel=Ext.getCmp('roleTreePanel');
		if(!currentRoleNode){
			currentRoleNode=Ext.getCmp('roleTreePanel').root;
		}
		if(roleNode&&currentRoleNode){
			if(opType=='add'){
				currentRoleNode.appendChild(roleNode);
				currentRoleNode.leaf=false;
				currentRoleNode.expand();
				return true;
			}else if(opType=='update'){
				//更新节点
				var tmpNode=treePanel.getNodeById(roleNode.id);
				//var tmpNode=currentRoleNode.findChild('id',roleNode.id);
				if(tmpNode){
					tmpNode.setText(roleNode.text);
				}
				return true;
			}
		}
		treePanel.root.reload();
		
	}
	
	this.groupParentRender = function(val){
		return roleTreeParendName;
	};
	
	this.groupRoleParentRender = function(val){
		if(val){
			return val;
		}
		return '对象';
	};
		
	this.addHandler=function(){
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/role_edit_win.jsp?objId=-1',400,320);
			return;
		}	
		var objFormPanel=new Ecums.RoleFormPanel();
		Ecums.comFun.createCrubeWindow(objFormPanel,'角色添加',400,320,'角色添加成功',Ecums.roleUrl,'save',reloadData,'add');
	}
	
	this.updateHandler=function(){
			var grid=Ext.getCmp('roleGridPanel');
			var store=grid.store;
			var sm = grid.getSelectionModel();
			var selected = sm.getSelections();    
	        if(selected.length!=1){
	        	Ext.Msg.alert('信息提示', '请选择一条记录');
				return;
	        }
	        var objKeyId=selected[0].data.roleId;
	        if(Ext.isIE&&Ecums.isPopWin){
				Ecums.comFun.popWinCenter('console/role_edit_win.jsp?objId='+objKeyId,400,320);
				return;
			}		        
			var objFormPanel=new Ecums.RoleFormPanel();
			Ecums.comFun.createCrubeWindow(objFormPanel,'角色修改',400,320,'角色修改成功',Ecums.roleUrl,'save',reloadData,'update');
			objFormPanel.getForm().load({url:Ecums.roleUrl+'?action=edit&roleId='+objKeyId});	
	}
	
	this.delHandler=function(){
        Ecums.comFun.deleteCrubeHandlerObj('roleGridPanel','roleId',Ecums.roleUrl,'del','你确认要删除选中的角色吗？','角色删除成功',reloadData,'del');
	}
	
	this.opPermissionHandler=function(){
		var grid=Ext.getCmp('roleGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length==0){
        	Ext.Msg.alert('信息提示', '请选择至少一条记录');
        	return;
        }
        var idsRole = [];
        for(var i=0;i<selected.length;i+=1){
            var accessModeBean = selected[i].data;    
            if(accessModeBean.roleId) {
                idsRole.push(accessModeBean.roleId);
            }
        }
        if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/role_assign_permission_edit_win.jsp?ids='+Ext.encode(idsRole)+'&actionMethod='+actionMethod,650,440);
			return;
		}        
		var objTreeFormPanel=new Ecums.ObjectsCheckPermissionTreePanel();
		var accssModeStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'findAccessByObjID'},
				url:Ecums.operatorUrl,
	   			root: 'data',
	       		totalProperty: 'totalCount',
	       		remoteSort: true,
	   			fields: ['accssId', 'alias','name','status','note'],
	   			listeners :{
		   				load:function(store,records,options){
		   					if(idsRole.length==1){
		   						//通过AJAX取数据
			   					Ext.Ajax.request({
				   					url: Ecums.operatorUrl,
				   					method:'post',
				   					success: function(resp){
										var respS=Ext.util.JSON.decode(resp.responseText);
										var seReArr=respS.data;
										if(seReArr.length>0){
											var reArr=[];
											Ext.each(records,function(oneRecord){
				                    			for(i=0;i<seReArr.length;i++){
				                    				if(seReArr[i].accssId==oneRecord.data.accssId){
				                    					reArr.push(oneRecord);
				                    					break;
				                    				}
				                    			}
				                    		});
				                    		var sm2 = accssModeCheckGridPanel.getSelectionModel();
				                    		sm2.selectRecords(reArr);
										}
						            },	            
				   					params: {
				   						objId: objTreeIdForRoleAssign,
				   						roleId:idsRole[0],
				   					 	action:'findAccessByObjIDAndRoleId',
				   					 	rnd:Math.random()
				   					}
								});
		   					}
		   				}
	   			}
		});	
		var accssModeCheckGridPanel=new Ecums.AccssModeCheckGridPanel({id:'accssModeCheckGridPanel',store:accssModeStore});
		objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
        		treeLoader.baseParams={
        			parentId:Ecums.TreeConfig.objAssignTree.subTag(node),
        			action:'operatorCheckTree'
        		}
        	}
        );
        //
		var opPermissionFun=function(actionMethod){
			var op='分配';
			if('removePermission'==actionMethod){
				op='移除';
			}
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
					success: function(resp){
					if(Ecums.comFun.failureText(resp)){
						return;
					}			   					
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
		
		objWin=new Ext.Window({
               width: 720,
               height: 520,
               title:'分配移除角色对象操作权限',
               layout:'column',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[objTreeFormPanel,accssModeCheckGridPanel],
	           buttons: [{
                    text : '分配',
                    handler : function(){
                    		opPermissionFun('assignPermission');
	                	}
                	},{
                        text : '移除',
                        handler : function(){
                        	opPermissionFun('removePermission');
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
		var grid=Ext.getCmp('roleGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length!=1){
        	Ext.Msg.alert('信息提示', '请选择一条操作记录');
        	return;
        }
        var roleId=selected[0].data.roleId;        
		Ecums.comFun.createRpWindow(roleId);
	}

	this.showAssignUserHandler=function(){
		var grid=Ext.getCmp('roleGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length!=1){
        	Ext.Msg.alert('信息提示', '请选择一条操作记录');
        	return;
        }
        var roleId=selected[0].data.roleId;
		Ecums.comFun.createRoleAsWindow(roleId);
	}
		
    this.searchHandler=function(){
    	var grid=Ext.getCmp('roleGridPanel');
    	var store=grid.store;
    	var value=Ext.getCmp('searchRoleName').getValue();
    	var field=Ext.getCmp('roleConditionField').getValue();
    	store.baseParams=[];
    	store.baseParams.action='index';
    	store.baseParams[field]=value;
    	store.load({params:{start:0,limit:Ecums.pageSize}});
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
	Ecums.RoleGridPanel.superclass.constructor.call(this,{
		id:'roleGridPanel',
		title:'角色列表',
		columnWidth:.75,
		autoHeight: true,
		autoScroll:true,
		split:true,
		store:this.objectStore,
		selModel:this.checkSelMode,
		columns:[this.checkSelMode,
        	{id:'roleId', header: "ID", width: 60, sortable: true, dataIndex: 'roleId'},
       		{header: "标识",width: 150, sortable: true, dataIndex: 'alias'},
        	{header: "名称",width: 150, sortable: true, dataIndex: 'name'},
        	{header: "父角色名",width: 150,dataIndex: 'parentName',renderer:this.groupRoleParentRender},
        	{header: "状态",width: 60,dataIndex: 'status',renderer:Ecums.comFun.renderStatus},
        	{header: "排序",width: 60,dataIndex: 'taxis',sortable: true},
        	{header: "操作时间",dataIndex: 'opTime'}],
        	
    	tbar:[{text:"添加",iconCls:'form-button-new',handler:this.addHandler,hideMode:"visibility"},
    		'-',
    		{text:"修改",iconCls:'form-button-edit',handler:this.updateHandler},
    		'-',
    		{text:"删除",iconCls:'form-button-delete',handler:this.delHandler},
    		'-',
    		{text:"分配移除权限",name:'assignPermission',iconCls:'form-button-assign-obj',handler:this.opPermissionHandler},
    		'-',
    		/**
    		{text:"移除权限",name:'removePermission',iconCls:'form-button-remove-obj',handler:this.opPermissionHandler},
    		'-',**/
    		{text:"查看权限",name:'showPermission',iconCls:'form-button-show-obj',handler:this.showPermissionHandler},
    		'-',
    		{text:"授权",name:'showPermission',iconCls:'form-button-show-obj',handler:this.showAssignUserHandler},
    		'-',
    		'条件:',
			new Ext.form.ComboBox({
				fieldLabel: '状态',
				id:'roleConditionField',
				name: 'conditionField',
				width:60,
	            triggerAction: 'all',
	            editable:false,
	            store: new Ext.data.SimpleStore({
					fields: ['code', 'desc'],
					data : Ecums.formdata.searchFieldList
				}),
				value:'alias',
	           	valueField:'code',
	            displayField:'desc',
	            mode:'local'
           	}),    		
    		new Ext.form.TextField({id:'searchRoleName',name:'searchRoleName'}),
    		{text:"搜索",name:'searchButton',iconCls:'',pressed: true,handler:this.searchHandler}
    		],
    	bbar:this.pageToolBar
	})	
}


Ext.extend(Ecums.RoleGridPanel,Ext.grid.GridPanel);

Ecums.RoleTreePanel=function(){
	this.myTreeLoader = new Ext.tree.TreeLoader({  
    	url:Ecums.roleUrl,
    	listeners:{
    		beforeload:function(treeLoader, node){
	        		treeLoader.baseParams={
	        		parentId:Ecums.TreeConfig.roleTree.subTag(node),
	        		action:'tree'
        		}
        	}
        }
	});
	    
	Ecums.RoleTreePanel.superclass.constructor.call(this,{
		id:'roleTreePanel',
		title:'角色层级',
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
		//collapsible: true,
    	root: {
        	nodeType:'async',    
        	text: '角色',
        	id: Ecums.TreeConfig.roleTree.tag+'-1'
    	},
    	listeners:{
    		click:function(node,eo){
    			currentRoleNode=node;
    			var tmpStore=Ext.getCmp('roleGridPanel').store;
    			roleTreeParendId=Ecums.TreeConfig.roleTree.subTag(node);
    			roleTreeParendName=node.attributes.text;
    			tmpStore.baseParams=[];
    			tmpStore.baseParams.action='index';
    			tmpStore.baseParams.parentId=roleTreeParendId
    			tmpStore.load({params:{start:0,limit:Ecums.pageSize}});
    		},
    		nodedrop:function(e){
				if(e.point=='append'){
					var dragId=Ecums.TreeConfig.roleTree.subTag(e.dropNode);
					var targetId=Ecums.TreeConfig.roleTree.subTag(e.target);
					Ext.Ajax.request({
			   			url: Ecums.roleUrl,
			   			success: function(resp){
							if(Ecums.comFun.failureText(resp)){
								return;
							}
	                		Ext.Msg.alert('信息提示', '角色节点移动成功');
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

Ext.extend(Ecums.RoleTreePanel,Ext.tree.TreePanel);


Ecums.RoleUserTreePanel=function(){
	this.myTreeLoader = new Ext.tree.TreeLoader({  
    	url:Ecums.usersAssignmenUrl,
        baseAttrs: {uiProvider: Ext.ux.TreeCheckNodeUI}
	});
	if(arguments){
		Ecums.RoleUserTreePanel.superclass.constructor.call(this,arguments[0]);
	}	    
	Ecums.RoleUserTreePanel.superclass.constructor.call(this,{
		id:'roleUserTreePanel',
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
		//checkModel:'parentCascade childCascade',
		//checkModel:'single',
    	root: {
        	nodeType:'async',
        	text: '角色组',
        	//disabled:true,
        	id: Ecums.TreeConfig.roleAssignTree.tag+'-1'
    	},
    	bbar:[{text:"全部展开",iconCls:'',handler:function(){Ext.getCmp('roleUserTreePanel').expandAll()}},'-',
    	{text:"全部收起",iconCls:'',handler:function(){Ext.getCmp('roleUserTreePanel').collapseAll()}}]    	
	})
};

Ext.extend(Ecums.RoleUserTreePanel,Ext.tree.TreePanel);

Ecums.RoleFormPanel=function(){
	if(arguments){
		Ecums.RoleFormPanel.superclass.constructor.call(this,arguments[0]);
	}
	Ecums.RoleFormPanel.superclass.constructor.call(this,{
		id:'roleFormPanel',
		defaultType: 'textfield',
		margins:'10 0 10 0',
		labelWidth:80,
		labelAlign:'right',
		items: [{
                fieldLabel: 'ID',
                name: 'roleId',
                readOnly:true
            },{
                fieldLabel: '标识',
                name: 'alias',
                anchor:'95%',
                maxLength:64,
                allowBlank:false
            },{
                fieldLabel: '名称',
                name: 'name',
                anchor:'95%',
                maxLength:64,
                allowBlank:false
            },{
                fieldLabel: '上级角色',
                name: 'parentName',
                value:roleTreeParendName,
                readOnly:true
            },{
                fieldLabel: '',
                name: 'parentId',
                xtype:'hidden',
                value:roleTreeParendId
            },{
                fieldLabel: '排序',
                name: 'taxis',
                maxLength:10,
                allowBlank:false,
                value:99999
            },new Ecums.StatusComboBox(),{
                fieldLabel: '备注',
                xtype:'textarea',
                anchor:'95%',
                name: 'note',
                maxLength:512
			}]
	})
};

Ext.extend(Ecums.RoleFormPanel,Ext.form.FormPanel);

//角色组管理主窗口
Ecums.RoleMainPanel = function(){
	this.objectsGridPanel=new Ecums.RoleGridPanel();
	
	this.objectTreePanel=new Ecums.RoleTreePanel();
	Ecums.RoleMainPanel.superclass.constructor.call(this,{
		title:'角色管理',
		id:'roleMainPanel',
		closable: true,
		layout:'column',
		listeners:{destroy:function()
			{
				roleTreeParendId=-1;
				roleTreeParendName='角色';		
			}
		},
		items:[this.objectTreePanel,this.objectsGridPanel]
	});
	//this.objectsGridPanel.store.baseParams.parentId=roleTreeParendId
	this.objectsGridPanel.store.load({params:{limit:Ecums.pageSize,start:0}});
}
Ext.extend(Ecums.RoleMainPanel,Ext.Panel);
