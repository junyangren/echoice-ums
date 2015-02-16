Ecums.UserFormPanel=function(){
	if(arguments){
		Ecums.UserFormPanel.superclass.constructor.call(this,arguments[0]);
	}
	Ecums.UserFormPanel.superclass.constructor.call(this,{
		id:'userFormPanel',
		defaultType: 'textfield',
		margins:'10 0 10 0',
		labelWidth:80,
		labelAlign:'right',
		items:{
				xtype:'tabpanel',
				id:'userFormTabpanel-1',
            	activeTab: 1,
            	defaults:{autoHeight:true, bodyStyle:'padding:10px'},
            	items:[{title:'基本信息',
                		layout:'form',
                		id:'userFormTabpanel-1.1',
                		defaultType: 'textfield',
						items: [
							{
				                fieldLabel: '用户ID',
				                name: 'userId',
				                readOnly:true
				            },{
				                fieldLabel: '登入名',
				                id:'userFormAliasId',
				                name: 'alias',
				                anchor:'95%',
				                allowBlank:false,
				                maxLength:30
				            },{
				                fieldLabel: '工号',
				                name: 'jobNumber',
				                anchor:'95%',
				                allowBlank:true,
				                maxLength:30
				            },{
				                fieldLabel: '用户名',
				                name: 'name',
				                anchor:'95%',
				                allowBlank:false,
				                maxLength:30
				            },{
				                fieldLabel: '密码',
				                id:'usPasswordId',
				                name: 'password',
				                inputType:'password',
				                minLength:6,
				                allowBlank:false,
				                maxLength:64,
				                anchor:'95%'
				            },{
				                fieldLabel: '确认密码',
				                id:'confirmPasswordId',
				                name: 'confirmPassword',
				                inputType:'password',
				                minLength:6,
				                allowBlank:false,
				                maxLength:64,
				                vtype:'confirmPwd',
				                confirmPwd : {
                            		first : 'usPasswordId',
                            		second : 'confirmPasswordId'
                        		},
				                anchor:'95%'
				            },{
				                fieldLabel: '邮件',
				                name: 'email',
				                anchor:'95%',
				                vtype:'email'
				            },{
				            	id:'userForm-groupName',
				                fieldLabel: '用户组',
				                //xtype:'textarea',
				                name: 'groupNames',
				                readOnly:true,
				                anchor:'95%'
				            },new Ecums.StatusComboBox(),{
				                fieldLabel: '备注',
				                xtype:'textarea',
				                anchor:'95%',
				                name: 'note',
				                maxLength:256
				            },{
				                fieldLabel: '排序',
				                name: 'taxis',
				                maxLength:10,
				                allowBlank:false,
				                value:99999
				            },{
				            	id:'userForm-groupId',
				                fieldLabel: '',
				                name: 'groupIds',
				                xtype:'hidden'
				                //value:groupTreeParendId
				            }]                		
                		},{
			                title:'扩展信息',
			                id:'userFormTabpanel-1.2',
			                layout:'form',
			                defaultType: 'textfield',
			                items:[{
				                fieldLabel: '手机',
				                name: 'mobile',
				                anchor:'95%',
				                maxLength:30
				            },{
				                fieldLabel: '电话',
				                name: 'tel',
				                anchor:'95%',
				                maxLength:30
				            },{
				                fieldLabel: 'msn',
				                name: 'msn',
				                anchor:'95%',
				                maxLength:64
				            },{
				                fieldLabel: 'qq',
				                name: 'qq',
				                anchor:'95%',
				                maxLength:30
				            },{
				                fieldLabel: '地址',
				                name: 'textarea',
				                anchor:'95%',
				                maxLength:128
				            },{
				                fieldLabel: '职责',
				                xtype:'textarea',
				                anchor:'95%',
				                name: 'duty',
				                maxLength:256
				            }]               		
                		}
                		
               ]		
		}
	})
};
Ext.extend(Ecums.UserFormPanel,Ext.form.FormPanel);


//用户列表
Ecums.UserGridPanel = function(){
	//配置数据
	this.objectStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'index'},
				url:Ecums.userUrl,
    			root: 'data',
        		totalProperty: 'totalCount',
        		remoteSort: true,
    			fields: ['userId', 'alias','name','email','status','opTime','taxis'],
    			listeners:{
					beforeload:function(){
						//this.baseParams.groupId=groupTreeParendId;
					}
    			}
	});
	
	function reloadData(){
		Ext.getCmp('userGridPanel').store.reload();
		var obj=Ext.getCmp('userForm-groupId');
		if(obj!=null){
			var groupId=Ext.getCmp('userForm-groupId').getRawValue();
			if(groupId==''){
				Ext.getCmp('userForm-groupId').setRawValue(groupTreeParendId);
				Ext.getCmp('userForm-groupName').setRawValue(groupTreeParendName);
			}
		}
	}
	
	this.addHandler=function(){
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/user_edit_win.jsp?objId=-1',500,510);
			return;
		}
		if(groupTreeParendId==-1){
			Ext.Msg.alert('信息提示', '请选择一个用户组，再添加用户记录！！');
			return;
		}
		var objFormPanel=new Ecums.UserFormPanel();
		Ecums.comFun.createCrubeWindow(objFormPanel,'用户添加',500,510,'用户资料添加成功',Ecums.userUrl,'save',reloadData,true);
		Ext.getCmp('userFormTabpanel-1').activate(Ext.getCmp('userFormTabpanel-1.1'));
		Ext.getCmp('userForm-groupId').setRawValue(groupTreeParendId);
		Ext.getCmp('userForm-groupName').setRawValue(groupTreeParendName);
		//Ext.getCmp('userForm-groupId2').setRawValue(groupTreeParendId);
		//Ext.getCmp('userForm-groupName2').setRawValue(groupTreeParendName);
	}
	
	this.updateHandler=function(){
			var grid=Ext.getCmp('userGridPanel');
			var sm = grid.getSelectionModel();
			var selected = sm.getSelections();    
	        if(selected.length!=1){
	        	Ext.Msg.alert('信息提示', '请选择一条记录');
				return;
	        }
			var tmpObj = selected[0].data;
	        var objKeyId=tmpObj.userId;
	        if(Ext.isIE&&Ecums.isPopWin){
				Ecums.comFun.popWinCenter('console/user_edit_win.jsp?objId='+objKeyId,500,510);
				return;
			}	        
			var objFormPanel=new Ecums.UserFormPanel();
			Ecums.comFun.createCrubeWindow(objFormPanel,'用户修改',500,510,'用户资料修改成功',Ecums.userUrl,'save',reloadData);
			Ext.getCmp('userFormTabpanel-1').activate(Ext.getCmp('userFormTabpanel-1.1'));
			Ext.getDom('userFormAliasId').setAttribute('readOnly',true);
			objFormPanel.getForm().load({url:Ecums.userUrl+'?action=edit&userId='+objKeyId});
			
	}
	
	this.delHandler=function(){
        Ecums.comFun.deleteCrubeHandlerObj('userGridPanel','userId',Ecums.userUrl,'del','你确认要删除选中的用户吗？','用户删除成功',reloadData);	
	}
	
	this.opAssignRoleHandler=function(){
		var grid=Ext.getCmp('userGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length==0){
        	Ext.Msg.alert('信息提示', '请至少选择一条记录');
        	return;
        }
        var ids = [];    
        for(var i=0;i<selected.length;i+=1){
            var accessModeBean = selected[i].data;    
            if(accessModeBean.userId) {
                ids.push(accessModeBean.userId);
            }
        }
        var userId='';
        if(ids.length==1){
        	userId=ids[0];
        }
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/user_assign_role_win.jsp?userId='+userId+'&ids='+Ext.encode(ids)+'&actionMethod='+actionMethod,400,500);
			return;
		}         
		var objTreeFormPanel=new Ecums.RoleUserTreePanel();
		objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
        		treeLoader.baseParams={
        			parentId:Ecums.TreeConfig.roleAssignTree.subTag(node),
        			action:'roleCheckTree',
        			userId:userId
        		}
        	}
        );
		
		var opRoleFun=function(actionMethod){
			var op='';
			if('assignRole'!=actionMethod){
				op='移除';
			}else{
				op='分配';
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
					url: Ecums.usersAssignmenUrl,
					method:'post',
					success: function(resp){
					if(Ecums.comFun.failureText(resp)){
						return;
					}			   					
            		Ext.Msg.alert('信息提示', op+'角色成功');
	            },
	            failure : Ecums.comFun.failure,
					params: {ids: Ext.encode(ids),
						objIds: Ext.encode(objIds),
					 	action:actionMethod,
					 	userId:userId
					}
			});
    	}
		//removeRole
		objWin=new Ext.Window({
               width: 400,
               height: 500,
               title:'分配移除用户角色',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[objTreeFormPanel],
	           buttons: [{
                    text : '分配',
                    handler : function(){
                    		opRoleFun('assignRole');
	                	}
                	},{
                        text : '移除',
                        handler : function(){
                        	opRoleFun('removeRole');
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

	this.opAssignGroupHandler=function(){
		//var actionMethod=this.name;
		//var op='分配';
		//if('assignGroup'!=actionMethod){
		//	op='移除';
		//}
		var grid=Ext.getCmp('userGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length==0){
        	Ext.Msg.alert('信息提示', '请至少选择一条记录');
        	return;
        }
        var ids = [];    
        for(var i=0;i<selected.length;i+=1){
            var accessModeBean = selected[i].data;    
            if(accessModeBean.userId) {
                ids.push(accessModeBean.userId);
            }
        }
        var userId='';
        if(ids.length==1){
        	userId=ids[0];
        }
		if(Ext.isIE&&Ecums.isPopWin){
			//Ecums.comFun.popWinCenter('console/user_assign_role_win.jsp?userId='+userId+'&ids='+Ext.encode(ids)+'&actionMethod='+actionMethod,400,500);
			//return;
		}         
		var objTreeFormPanel=new Ecums.UserAssignGroupTreePanel();
		objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
        		treeLoader.baseParams={
        			parentId:Ecums.TreeConfig.groupTree.subTag(node),
        			action:'treeAssignGroup',
        			userId:userId
        		}
        	}
        );
        var opFun=function(op){
                    		var objIds=[];
                    		var nodeArr=objTreeFormPanel.getChecked();
                    		if(nodeArr.length==0){
                    			Ext.Msg.alert('信息提示', '请至少选择一记录');
                    			return;
                    		}
                    		Ext.each(nodeArr,function(node){
                    			objIds.push(parseInt(Ecums.TreeConfig.groupTree.subTag(node)));
                    		})
                    		Ext.Ajax.request({
			   					url: Ecums.usersAssignmenUrl,
			   					method:'post',
			   					success: function(resp){
									if(Ecums.comFun.failureText(resp)){
										return;
									}			   					
			                		Ext.Msg.alert('信息提示', op+'用户组成功');
			                		//objTreeFormPanel.root.reload();
			                		//objTreeFormPanel.expandAll();
					            },
					            failure : Ecums.comFun.failure,
			   					params: {ids: Ext.encode(ids),
			   						objIds: Ext.encode(objIds),
			   					 	action:actionMethod,
			   					 	userId:userId
			   					}
							});
	                	}
         //};		
		objWin=new Ext.Window({
               width: 400,
               height: 500,
               title:'用户组分配移除',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[objTreeFormPanel],
	           buttons: [{
                    text : '分配',
                    handler : function(){
                    		actionMethod='assignGroup';
                    		opFun('分配');
	                	}
                	},{
                    text : '移除',
                    handler : function(){
                    		actionMethod='removeAssignGroup';
                    		opFun('移除');
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
 		var grid=Ext.getCmp('userGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length!=1){
        	Ext.Msg.alert('信息提示', '请选择一条操作记录');
        	return;
        }
        var userId=selected[0].data.userId;
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/user_assign_permission_win.jsp?userId='+userId,650,500);
			return;
		}         
    	var gridPanel=new Ecums.UserPermissionGridPanel();
        
        //当翻面时，要加载相应的查询参数
        gridPanel.store.on('beforeload',function(store,options){
        	store.baseParams.userId=userId;
        });
        
		objWin=new Ext.Window({
               width: 720,
               height: 500,
               title:'查看用户分配的角色及操作权限',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[gridPanel],
               listeners:{
               		close:function(){
               			gridPanel.destroy();
               			gridPanel=null;
               			objWin.destroy();
               			objWin=null;
               		}
              } 
         });
         objWin.show();
         gridPanel.store.load({params:{userId:userId,limit:Ecums.pageSize,start:0}});	
    }
    
    this.searchHandler=function(){
    	var grid=Ext.getCmp('userGridPanel');
    	var store=grid.store;
    	var value=Ext.getCmp('searchName').getValue();
    	var field=Ext.getCmp('userConditionField').getValue();
    	store.baseParams=[];
    	store.baseParams.action='search';
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
   
	Ecums.UserGridPanel.superclass.constructor.call(this,{
		id:'userGridPanel',
		title:'用户列表',
		columnWidth:.75,
		autoHeight: true,
		autoScroll:true,
		split:true,
		store:this.objectStore,
		selModel:this.checkSelMode,
		columns:[this.checkSelMode,
        	{id:'ID', header: "ID", width: 60, sortable: true, dataIndex: 'userId'},
       		{header: "登入名",width:120,sortable: true, dataIndex: 'alias'},
        	{header: "名称",width:120,sortable: true, dataIndex: 'name'},
        	{header: "邮件",width:150,sortable: true, dataIndex: 'email'},
        	{header: "状态",dataIndex: 'status',renderer:Ecums.comFun.renderStatus},
        	{header: "排序", width: 60, sortable: true, dataIndex: 'taxis'},
        	{header: "操作时间",dataIndex: 'opTime'}],
        	
    	tbar:[{text:"添加",iconCls:'form-button-new',handler:this.addHandler},
    		'-',
    		{text:"修改",iconCls:'form-button-edit',handler:this.updateHandler},
    		'-',
    		{text:"删除",iconCls:'form-button-delete',handler:this.delHandler},
    		'-',
    		{text:"分配移除组",name:'assignGroup',iconCls:'form-button-assign-obj',handler:this.opAssignGroupHandler},
    		'-',
    		{text:"分配移除角色",name:'assignRole',iconCls:'form-button-assign-obj',handler:this.opAssignRoleHandler},
    		'-',
    		/**
    		{text:"移除角色",name:'removeRole',iconCls:'form-button-remove-obj',handler:this.opAssignRoleHandler},
    		'-',**/
    		{text:"查看权限",name:'showPermission',iconCls:'form-button-show-obj',handler:this.showPermissionHandler},
    		'-',
    		'条件:',
			new Ext.form.ComboBox({
				fieldLabel: '状态',
				id:'userConditionField',
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
    		new Ext.form.TextField({id:'searchName',name:'searchName'}),
    		{text:"搜索",name:'searchButton',iconCls:'',pressed: true,handler:this.searchHandler}
    		],
    	tools:[{
    		id:'refresh',
    		qtip: '刷新',
    		handler: function(event, toolEl, panel){
        		panel.store.reload();
    		}
		}],
    	bbar:this.pageToolBar
	})	
}
Ext.extend(Ecums.UserGridPanel,Ext.grid.GridPanel);


//用户管理主窗口
Ecums.UserMainPanel = function(){
	this.objectsGridPanel=new Ecums.UserGridPanel();
	this.objectTreePanel=new Ecums.GroupsUserTreePanel();
	Ecums.UserMainPanel.superclass.constructor.call(this,{
		title:'用户管理',
		id:'userMainPanel',
		closable: true,
		layout:'column',
		items:[this.objectTreePanel,this.objectsGridPanel]
	});
	this.objectsGridPanel.store.load({params:{limit:Ecums.pageSize,start:0}});
}
Ext.extend(Ecums.UserMainPanel,Ext.Panel);