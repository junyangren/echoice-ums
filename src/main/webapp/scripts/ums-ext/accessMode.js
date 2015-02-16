
//可编辑的操作列表
Ecums.AccssModeGridPanel = function(){
	this.accssModeStore=new Ext.data.JsonStore({
			autoLoad:false,
			baseParams :{action:'index'},
			url:Ecums.accessModeUrl,
   			root: 'data',
       		totalProperty: 'totalCount',
       		remoteSort: true,
   			fields: ['accssId', 'alias','name','status','taxis','note']
	});
	
	var AccessModeBean = Ext.data.Record.create([    
           {name: 'accssId', type: 'int'},    
           {name: 'name', type: 'string'},    
           {name: 'alias'},    
           {name: 'status',type:'String'},
           {name: 'taxis', type: 'int'},
           {name: 'note',type:'String'}  
    ]);
     
	this.pageToolBar=new Ext.PagingToolbar({
        		pageSize: Ecums.pageSize,
        		store: this.accssModeStore,
        		displayInfo: true,
        		displayMsg: '显示　操作记录 {0} - {1} of {2}',
        		emptyMsg: "暂无记录",
        		plugins:new Ext.ux.Andrie.pPageSize()
    });
    
	this.statusStore = new Ext.data.SimpleStore({
        fields: ['code', 'desc'],
        data : Ecums.formdata.status
    });     
    
	this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	 
	this.cm = new Ext.grid.ColumnModel([this.sm,{
			header:'ID',
			dataIndex:'accssId',
			sortable: true,
			width:60,
			editor: new Ext.form.TextField({ 
               allowBlank: true,
               readOnly:true
           })
         },{
         	header:'标识',
			dataIndex:'alias',
			sortable: true,
			width:150,
			editor: new Ext.form.TextField({ 
               allowBlank: false,
               maxLength:30 
           })
         },{
         	header:'名称',
			dataIndex:'name',
			sortable: true,
			width:150,
			editor: new Ext.form.TextField({ 
               allowBlank: false,
               maxLength:30
           })
         },{
         	header:'状态',
			dataIndex:'status',
			sortable: true,
			width:80,
			renderer:Ecums.comFun.renderStatus,
			editor: new Ext.form.ComboBox({
	                triggerAction: 'all',
	                editable:false,
	                store: this.statusStore,
	                valueField:'code',
	                displayField:'desc',
	                mode:'local',
	                allowBlank: false
           	})
          },{
         	header:'排序',
			dataIndex:'taxis',
			sortable: true,
			editor: new Ext.form.NumberField({
               allowBlank: true,
               value:9999
           })
         },{
         	header:'备注',
			dataIndex:'note',
			editor: new Ext.form.TextField({
               allowBlank: true
           })
         }
	]);
	//添加
	this.addHandler=function(){
		var rec=new AccessModeBean({name:'',alias:'',status:'y'});
		var tmp=Ext.getCmp('accssModeGridPanel');
		tmp.stopEditing();    
        tmp.store.insert(0, rec);
        tmp.startEditing(0, 2);    
        tmp.store.getAt(0).dirty=true;
	};
	//删除
	this.delHandler=function(){
		var grid=Ext.getCmp('accssModeGridPanel');
		var store=grid.store;
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();    
        var ids = [];    
        for(var i=0;i<selected.length;i+=1){
            var accessModeBean = selected[i].data;    
            if(accessModeBean.accssId) {
            	//如果有ID属性,则表示该行数据是被修改过的,所以需要访问数据库进行删除
                ids.push(accessModeBean.accssId);
            }else{    
                //如果没有ID属性,则表示该行数据是新添加的未保存的数据,所以不需要访问数据库进行删除该行    
                store.remove(selected[i]);
            }
        }
        
        if(ids.join('')==''){
        	return;
        }
        
        Ext.Msg.confirm('信息提示','确定要删除所选项吗?',function(btn){
			if(btn=='yes'){
				Ext.Ajax.request({
		   			url: Ecums.accessModeUrl,
		   			method:'post',
		   			success: function(resp){
						if(Ecums.comFun.failureText(resp)){
							return;
						}		   			
		                Ext.Msg.alert('信息提示', '对象删除成功');
		                store.reload();
				   	},
				    failure : Ecums.comFun.failure,          
		   			params: {ids: Ext.encode(ids),
		   					 action:'del'
		   					}
				});
			}
        })
	}
	//保存
	this.saveHandler=function(){
		var grid=Ext.getCmp('accssModeGridPanel');
		var store=grid.store;
		var json = [];    
        for(i=0,cnt=store.getCount();i<cnt;i+=1){    
            var record = store.getAt(i);    
            if(record.dirty&&record.data.name!=''){// 得到所有修改过的数据    
                json.push(record.data);    
        	}
        }
        
        if(json.length==0){    
            Ext.Msg.alert('信息提示','没有对数据进行任何更改');    
            return false;    
        }
        
        str=Ext.encode(json);
        Ext.Ajax.request({
   			url: Ecums.accessModeUrl,
   			method:'post',
   			success: function(resp){
				if(Ecums.comFun.failureText(resp)){
					return;
				}  			
   				Ext.Msg.alert('信息提示','数据保存成功');
   				store.reload()
   			},
            failure : Ecums.comFun.failure,		
			params: { accesModes: str,action:'save' }
		});
	}
	//分配与移除对象
	this.opObjHandler=function(){
		
		var grid=Ext.getCmp('accssModeGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length==0){
        	Ext.Msg.alert('信息提示', '请至少选择一条记录');
        	return;
        }
        var ids = [];    
        for(var i=0;i<selected.length;i+=1){
            var accessModeBean = selected[i].data;    
            if(accessModeBean.accssId) {
                ids.push(accessModeBean.accssId);
            }
        }
        var accessId='';
        if(ids.length==1){
        	accessId=ids[0];
        }
        
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/access_assign_obj_win.jsp?accessId='+accessId+'&ids='+Ext.encode(ids)+'&actionMethod='+actionMethod,400,500);
			return;
		}        
        
		var objTreeFormPanel=new Ecums.ObjectsCheckShowTreePanel();
		objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
        		treeLoader.baseParams={
        			parentId:Ecums.TreeConfig.objAssignTree.subTag(node),
        			action:'operatorCheckTree',
        			accessId:accessId
        		}
        	}
        );
		
		var opObjFun=function(actionMethod){
			op='分配';
			if('removeObj'==actionMethod){
				op='移除';
			}
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
					success: function(resp){
					if(Ecums.comFun.failureText(resp)){
						return;
					}			   					
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
		
		objWin=new Ext.Window({
               width: 450,
               height: 500,
               title:'分配移除操作资源对象',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[objTreeFormPanel],
	           buttons: [{
                    text : '分配',
                    handler : function(){
                    	opObjFun('assignObj');
	                	}
                	},{
                        text : '移除',
                        handler : function(){
                        	opObjFun('removeObj');
    	                }
                    },{
                    	text     : '重置',
                    	handler  : function(){
                    		objTreeFormPanel.root.reload();
                    	}
                	}]
 		});
		objWin.show();
	};
	
	
	this.showObjHandler=function(){
		var grid=Ext.getCmp('accssModeGridPanel');
		var sm = grid.getSelectionModel();    
        var selected = sm.getSelections();   
        if(selected.length!=1){
        	Ext.Msg.alert('信息提示', '请选择一条操作记录');
        	return;
        }
        var accessId=selected[0].data.accssId;
		var objTreeFormPanel=new Ecums.ObjectsCheckShowTreePanel();
		objTreeFormPanel.loader.on('beforeload',function(treeLoader, node){
        		treeLoader.baseParams={
        			parentId:Ecums.TreeConfig.objAssignTree.subTag(node),
        			action:'operatorCheckTree',
        			accessId:accessId
        		}
        	}
        );
		objWin=new Ext.Window({
               width: 500,
               height: 500,
               title:'查看对象',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[objTreeFormPanel]
         });
         objWin.show();
         //objTreeFormPanel.expandAll();
	}
	
    this.searchHandler=function(){
    	var grid=Ext.getCmp('accssModeGridPanel');
    	var store=grid.store;
    	var value=Ext.getCmp('searchAccessModeName').getValue();
    	var field=Ext.getCmp('accessModeConditionField').getValue();
    	store.baseParams=[];
    	store.baseParams.action='index';
    	store.baseParams[field]=value;	
    	store.load({params:{start:0,limit:Ecums.pageSize}});
    }
	
	Ecums.AccssModeGridPanel.superclass.constructor.call(this,{
			id:'accssModeGridPanel',
			title:'操作列表',
			store:this.accssModeStore,
			columns:this.cm,
			tbar:[{text:"添加",iconCls:'form-button-new',handler:this.addHandler},
    			'-',{text:'删除',iconCls:'form-button-delete',handler:this.delHandler},
    			'-',{text:'保存',iconCls:'form-button-save',handler:this.saveHandler},
    			'-',{text:"分配移除对象",name:'assignObj',iconCls:'form-button-assign-obj',handler:this.opObjHandler},
    			'-',{text:"已分配对象",name:'showObj',iconCls:'form-button-show-obj',handler:this.showObjHandler},
    			'-','条件:',
    			new Ext.form.ComboBox({
    				fieldLabel: '状态',
    				id:'accessModeConditionField',
    				name: 'accessModeConditionField',
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
        		new Ext.form.TextField({id:'searchAccessModeName',name:'searchAccessModeName'}),
        		{text:"搜索",name:'searchButton',iconCls:'',pressed: true,handler:this.searchHandler}
    			],
    		bbar:this.pageToolBar
	});
}

Ext.extend(Ecums.AccssModeGridPanel,Ext.grid.EditorGridPanel);


//角色分配权限时定义可选择的列表框
Ecums.AccssModeCheckGridPanel = function(){

	this.statusStore = new Ext.data.SimpleStore({
        fields: ['code', 'desc'],
        data : Ecums.formdata.status
    });
    
    this.statRender = function(status){
     	return status=='y'?'启用':'禁用';
    };
     
    
	this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	 
	this.cm = new Ext.grid.ColumnModel([this.sm,{
			header:'ID',
			dataIndex:'accssId',
			width:50,
			sortable: true
         },{
         	header:'标识',
			dataIndex:'alias',
			width:120,
			sortable: true
         },{
         	header:'名称',
         	width:180,
			dataIndex:'name',
			sortable: true
         },{
         	header:'状态',
         	width:60,
			dataIndex:'status',
			sortable: true,
			renderer:this.statRender
          }
	]);
    
	if(arguments){
		Ecums.AccssModeCheckGridPanel.superclass.constructor.call(this,arguments[0]);
		var gridId=arguments[0].id;
	    this.searchHandler=function(){
	    	var grid=Ext.getCmp(gridId);
	    	var store=grid.store;
	    	var value=Ext.getCmp('searchObjAccessModeName').getValue();
	    	var field=Ext.getCmp('objAccessModeConditionField').getValue();
	    	store.baseParams['alias']='';
	    	store.baseParams['name']='';
	    	store.baseParams[field]=value;	
	    	store.load({params:{start:0,limit:Ecums.pageSize}});
	    };
	}
	
	if(arguments&&arguments.length>1&&arguments[1]){		
		Ecums.AccssModeCheckGridPanel.superclass.constructor.call(this,{
			title:'对象已分配操作列表',
			height: 445,
			columns:this.cm,
			columnWidth:.7,
			border:false,
			tbar:['条件:',
	    			new Ext.form.ComboBox({
	    				fieldLabel: '状态',
	    				id:'objAccessModeConditionField',
	    				name: 'objAccessModeConditionField',
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
	        		new Ext.form.TextField({id:'searchObjAccessModeName',name:'searchObjAccessModeName'}),
	        		{text:"搜索",name:'searchButton',iconCls:'',pressed: true,handler:this.searchHandler}
	    			]
		});
		
	}else{
		Ecums.AccssModeCheckGridPanel.superclass.constructor.call(this,{
			title:'对象已分配操作列表',
			height: 445,
			columns:this.cm,
			columnWidth:.7
		});		
	}
}

Ext.extend(Ecums.AccssModeCheckGridPanel,Ext.grid.GridPanel);


//操作主窗口
Ecums.AccssModeMainPanel = function(){
	this.accssModeGridPanel=new Ecums.AccssModeGridPanel();
	this.accssModeGridPanel.store.load({params:{limit:Ecums.pageSize,start:0}});
	Ecums.AccssModeMainPanel.superclass.constructor.call(this,{
		title:'操作管理',
		id:'accssModeMainPanel',
		autoScroll:true,
		closable: true,
		layout:'fit',
		items:[this.accssModeGridPanel]
	});
}
Ext.extend(Ecums.AccssModeMainPanel,Ext.Panel);
