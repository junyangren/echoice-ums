//角色权限组列表
Ecums.PermissionGridPanel = function(){
	//配置数据
	this.objectStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'showRolePermission'},
				url:Ecums.permissionUrl,
    			root: 'data',
        		totalProperty: 'totalCount',
        		remoteSort: true,
    			fields: ['paId','roleId','operId','objId', 'objAlias','objName','accessId','accessAlias','accessName']
	});

	this.pageToolBar=new Ext.PagingToolbar({
        		pageSize: Ecums.pageSize,
        		store: this.objectStore,
        		displayInfo: true,
        		displayMsg: '显示　对象记录 {0} - {1} of {2}',
        		emptyMsg: "暂无记录",
        		plugins:new Ext.ux.Andrie.pPageSize()
    });
    
    this.opPermissionHandler=function(){
		var grid=Ext.getCmp('permissionGridPanel');
		var store=grid.store;
		var sm = grid.getSelectionModel();
		var selected = sm.getSelections();    
        var ids = [];    
        for(var i=0;i<selected.length;i+=1){
            var tmpObj = selected[i].data;    
            ids.push(tmpObj.paId);
        }
        
        if(ids.join('')==''){
        	Ext.Msg.alert('信息提示', '请选择相应的记录');
        	return;
        }
        
		Ext.MessageBox.confirm('信息提示', '你确认要移除选中的角色权限吗？', function(btn){
				if(btn=='yes'){
					Ext.Ajax.request({
			   			url: Ecums.permissionUrl,
			   			success: function(resp){
							if(Ecums.comFun.failureText(resp)){
								return;
							}
		   					Ext.Msg.alert('信息提示', '移除角色权限成功');
                			store.reload();
			             },
			            failure :Ecums.comFun.failure,
			   			params: {ids: Ext.encode(ids),
			   				action:'removePermissionByPaId'
			   			}
					});
				}
			}
		);    	
    }	
    this.checkSelMode=new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	if(arguments){
		Ecums.PermissionGridPanel.superclass.constructor.call(this,arguments[0]);
	}
	
    this.searchHandler=function(){
    	var grid=Ext.getCmp('permissionGridPanel');
    	var store=grid.store;
    	var objName=Ext.getCmp('searchObjNamePs').getValue();
    	var accessName=Ext.getCmp('searchAccessNamePs').getValue();
    	store.baseParams['objName']=objName;
    	store.baseParams['accessName']=accessName;
    	store.load({params:{start:0,limit:Ecums.pageSize}});
    	
    }
    
	Ecums.PermissionGridPanel.superclass.constructor.call(this,{
		id:'permissionGridPanel',
		store:this.objectStore,
		selModel:this.checkSelMode,
		columns:[this.checkSelMode,
        	{id:'paId', header: "ID",width:50,sortable: true, dataIndex: 'paId',align:'center'},
        	{header: "对象ID",width:50, sortable: true, dataIndex: 'objId',align:'center'},
       		{header: "对象标识",width:180, sortable: true, dataIndex: 'objAlias'},
        	{header: "对象名称",width:120, sortable: true, dataIndex: 'objName'},
        	{header: "操作ID",width:50, sortable: true, dataIndex: 'accessId',align:'center'},
        	{header: "操作标识",width:120, dataIndex: 'accessAlias'},
        	{header: "操作名称",width:100, dataIndex: 'accessName'}],
        tbar:[{text:"移除权限",name:'removePermission',iconCls:'form-button-remove-obj',handler:this.opPermissionHandler},
              '-','对象名称：',new Ext.form.TextField({id:'searchObjNamePs',name:'searchObjNamePs'}),
              '操作名称：',new Ext.form.TextField({id:'searchAccessNamePs',name:'searchAccessNamePs'}),
              {text:"搜索",name:'searchButton',iconCls:'',pressed: true,handler:this.searchHandler}],	        	
    	bbar:this.pageToolBar
	})	
}
Ext.extend(Ecums.PermissionGridPanel,Ext.grid.GridPanel);




Ecums.RoleAssignUserPsGridPanel = function(){
	//配置数据
	this.objectStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'showRoleAssignUser'},
				url:Ecums.permissionUrl,
    			root: 'data',
        		totalProperty: 'totalCount',
        		remoteSort: true,
    			fields: ['uaId','userId','alias','name']
	});

	this.pageToolBar=new Ext.PagingToolbar({
        		pageSize: Ecums.pageSize,
        		store: this.objectStore,
        		displayInfo: true,
        		displayMsg: '显示　对象记录 {0} - {1} of {2}',
        		emptyMsg: "暂无记录",
        		plugins:new Ext.ux.Andrie.pPageSize()
    });
    
    this.opPermissionHandler=function(){
		var grid=Ext.getCmp('roleAssignUserPs');
		var store=grid.store;
		var sm = grid.getSelectionModel();
		var selected = sm.getSelections();    
        var ids = [];    
        for(var i=0;i<selected.length;i+=1){
            var tmpObj = selected[i].data;    
            ids.push(tmpObj.uaId);
        }
        
        if(ids.join('')==''){
        	Ext.Msg.alert('信息提示', '请选择相应的记录');
        	return;
        }
        
		Ext.MessageBox.confirm('信息提示', '你确认要移除授权用户吗？', function(btn){
				if(btn=='yes'){
					Ext.Ajax.request({
			   			url: Ecums.permissionUrl,
			   			success: function(resp){
							if(Ecums.comFun.failureText(resp)){
								return;
							}
		   					Ext.Msg.alert('信息提示', '移除角色授权用户成功');
                			store.reload();
			            },
			            failure :Ecums.comFun.failure,
			   			params: {ids: Ext.encode(ids),
			   				action:'removeRoleAssignUser'
			   			}
					});
				}
			}
		);    	
    }
    
    this.searchHandler=function(){
    	var grid=Ext.getCmp('roleAssignUserPs');
    	var store=grid.store;
    	var value=Ext.getCmp('searchRoleUserName').getValue();
    	var field=Ext.getCmp('roleUserConditionField').getValue();
    	store.baseParams.alias='';
    	store.baseParams.name='';
    	store.baseParams[field]=value;	
    	store.load({params:{start:0,limit:Ecums.pageSize}});
    }
    
    this.checkSelMode=new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	if(arguments){
		Ecums.RoleAssignUserPsGridPanel.superclass.constructor.call(this,arguments[0]);
	}    	
	Ecums.RoleAssignUserPsGridPanel.superclass.constructor.call(this,{
		id:'roleAssignUserPs',
		store:this.objectStore,
		selModel:this.checkSelMode,
		columns:[this.checkSelMode,
        	{id:'usId', header: "ID",width:50,sortable: true, dataIndex: 'uaId',align:'center'},
        	{header: "登入名",width:150, sortable: true, dataIndex: 'alias',align:'center'},
       		{header: "名称",width:150, sortable: true, dataIndex: 'name'}],
        tbar:[{text:"移除用户",name:'removePermission',iconCls:'form-button-remove-obj',handler:this.opPermissionHandler},
              '-',
      		'条件:',
      		new Ext.form.ComboBox({
				fieldLabel: '状态',
				id:'roleUserConditionField',
				name: 'roleUserConditionField',
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
    		new Ext.form.TextField({id:'searchRoleUserName',name:'searchRoleUserName'}),
    		{text:"搜索",name:'searchButton',iconCls:'',pressed: true,handler:this.searchHandler}],	        	
    	bbar:this.pageToolBar
	})	
}
Ext.extend(Ecums.RoleAssignUserPsGridPanel,Ext.grid.GridPanel);


//用户分配角色及权限列表
Ecums.UserPermissionGridPanel = function(){
	//配置数据
	this.objectStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'showUserPermission'},
				url:Ecums.permissionUrl,
    			root: 'data',
        		totalProperty: 'totalCount',
        		remoteSort: true,
    			fields: ["roleId","roleAlias","roleName","objId","objAlias","objName","accessId","accessAlias","accessName"]
	});

	this.pageToolBar=new Ext.PagingToolbar({
        		pageSize: Ecums.pageSize,
        		store: this.objectStore,
        		displayInfo: true,
        		displayMsg: '显示　对象记录 {0} - {1} of {2}',
        		emptyMsg: "暂无记录",
        		plugins:new Ext.ux.Andrie.pPageSize()
    });
    
    this.checkSelMode=new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
    
    this.searchHandler=function(){
    	var grid=Ext.getCmp('userPermissionGridPanel');
    	var store=grid.store;
    	var objName=Ext.getCmp('searchObjNameForUser').getValue();
    	var accessName=Ext.getCmp('searchAccessNameForUser').getValue();
    	var roleName=Ext.getCmp('searchRoleNameForUser').getValue();
    	store.baseParams['objName']=objName;
    	store.baseParams['accessName']=accessName;
    	store.baseParams['roleName']=roleName;
    	
    	store.load({params:{start:0,limit:Ecums.pageSize}});
    }
    
	if(arguments){
		Ecums.UserPermissionGridPanel.superclass.constructor.call(this,arguments[0]);
	}    	
	Ecums.UserPermissionGridPanel.superclass.constructor.call(this,{
		id:'userPermissionGridPanel',
		store:this.objectStore,
		selModel:this.checkSelMode,
		columns:[this.checkSelMode,
        	{header: "角色标识",sortable: true, dataIndex: 'roleAlias'},
        	{header: "角色名称",width:120,sortable: true, dataIndex: 'roleName'},
        	//{header: "对象ID",width:50, sortable: true, dataIndex: 'objId',align:'center'},
       		{header: "对象标识", sortable: true, dataIndex: 'objAlias'},
        	{header: "对象名称",width:120, sortable: true, dataIndex: 'objName'},
        	//{header: "操作ID",width:50, sortable: true, dataIndex: 'accessId',align:'center'},
        	{header: "操作标识", dataIndex: 'accessAlias'},
        	{header: "操作名称", dataIndex: 'accessName'}],
        tbar:['角色名称：',new Ext.form.TextField({id:'searchRoleNameForUser',name:'searchRoleNameForUser'}),
        	  '对象名称：',new Ext.form.TextField({id:'searchObjNameForUser',name:'searchObjNameForUser'}),
              '操作名称：',new Ext.form.TextField({id:'searchAccessNameForUser',name:'searchAccessNameForUser'}),
              {text:"搜索",name:'searchButton',iconCls:'',pressed: true,handler:this.searchHandler}],	
    	bbar:this.pageToolBar
	})	
}
Ext.extend(Ecums.UserPermissionGridPanel,Ext.grid.GridPanel);

//权限检索主窗口
Ecums.PermissionSearchMainPanel = function(){
	objTreeFormPanel=new Ecums.ObjectsCheckPermissionTreePanel('objectsCheckPermissionTreePanel-1','accssModeCheckGridPanel-1');
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
		   					//当只用一条操作时，只接选中方便检索
		   					if(records&&records.length==1){
		   						var sm2 = accssModeCheckGridPanel.getSelectionModel();
				                sm2.selectRecords(records);
		   					}
		   				}
   			}
	});	
	accssModeCheckGridPanel=new Ecums.AccssModeCheckGridPanel({id:'accssModeCheckGridPanel-1',store:accssModeStore});
	objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
     		treeLoader.baseParams={
     			parentId:Ecums.TreeConfig.objAssignTree.subTag(node),
     			action:'operatorCheckTree'
     		}
     	}
	);
	accssModeCheckGridPanel.columnWidth=.38;
	rolePanel=new Ecums.PermissionRoleGridPanel();
	this.searchHandler=function(){
		rolePanel.store.removeAll();
		var objIds=[];
        var idsAccess=[];
        var nodeArr=objTreeFormPanel.getChecked();
        var smAccess =accssModeCheckGridPanel.getSelectionModel();    
        var selectedAccess = smAccess.getSelections();
        
        if(nodeArr.length==0){
        	Ext.Msg.alert('信息提示', '请选择一个对象');
        	return;
        }
               		
        if(selectedAccess.length==0){
        	Ext.Msg.alert('信息提示', '请在对象已经分配列表中，选择对应操作');
        	return;
        }
		Ext.each(nodeArr,function(node){
			objIds.push(parseInt(Ecums.TreeConfig.objAssignTree.subTag(node)));
		})
		
		Ext.each(selectedAccess,function(accessBean){
			idsAccess.push(accessBean.data.accssId);
		})
		rolePanel.store.load({params:{objId:objIds[0],idsAccess:Ext.encode(idsAccess)}});
		       		
	};
	Ecums.PermissionSearchMainPanel.superclass.constructor.call(this,{
		title:'权限检索',
		id:'permissionSearchMainPanel',
		closable: true,
		layout:'column',
		items:[objTreeFormPanel,accssModeCheckGridPanel,rolePanel],
		tbar:[{text:'检索角色',iconCls:'form-button-search-obj',handler:this.searchHandler},'-']
	});
}
Ext.extend(Ecums.PermissionSearchMainPanel,Ext.Panel);


Ecums.PermissionRoleGridPanel = function(){
	//配置数据
	this.objectStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'showPermissionRoleList'},
				url:Ecums.permissionUrl,
    			root: 'data',
        		totalProperty: 'totalCount',
        		remoteSort: true,
        		failure : Ecums.comFun.failure,
    			fields: ['roleId', 'roleAlias','roleName']
	});
	
	Ecums.PermissionRoleGridPanel.superclass.constructor.call(this,{
		id:'permissionRoleGridPanel',
		title:'对象+操作已分配角色',
		columnWidth:.32,
		height:445,
		autoScroll:true,
		store:this.objectStore,
		columns:[
        	{header: "ID", width: 40, sortable: true, dataIndex: 'roleId'},
       		{header: "标识", sortable: true, dataIndex: 'roleAlias'},
        	{header: "名称", sortable: true, dataIndex: 'roleName'},
        	{header: "权限", width: 40,sortable: true, dataIndex: 'roleId',renderer:function(val){
        			return '<a href="javascript:Ecums.comFun.createRpWindow('+val+')">查看</a>';
        		}
        	},
        	{header: "授权", width: 40,sortable: true, dataIndex: 'roleId',renderer:function(val){
        			return '<a href="javascript:Ecums.comFun.createRoleAsWindow('+val+')">查看</a>';
        		}
        	}]        	
        }       
    )	
}

Ext.extend(Ecums.PermissionRoleGridPanel,Ext.grid.GridPanel);

