var objTreeParendId=-1;
var objTreeParendName='对象';
var contextNode;
var currentObjTreeNode;
var objTreeIdForRoleAssign;
//对象列表显示
Ecums.ObjectsGridPanel = function(){
	//配置数据
	this.objectStore=new Ext.data.JsonStore({
				autoLoad:false,
				baseParams :{action:'index'},
				url:Ecums.objectUrl,
    			root: 'data',
        		totalProperty: 'totalCount',
        		remoteSort: true,
    			fields: ['objId', 'alias','name','type','icon','parentId','status','parentName','taxis'],
    			listeners:{
    					beforeload:function(){
    						//this.baseParams.parentId=objTreeParendId;
    					}
    			}
	});
	        	
	this.iconRender = function(val){
		if(val||val==''){
			val='images/gif-noimg.gif';
		}
		tmp='<img src="'+val+'" width="15" height="15">';
		return tmp;
	};
	
	this.groupParentRender = function(val){
		return objTreeParendName;
	};
	this.groupGrideParentRender = function(val){
		if(val){
			return val;
		}
		return '对象';
	};
	
	function reloadData(objNode,opType){
		Ext.getCmp('objectsGridPanel').store.reload();
		//Ext.getCmp('objectsTreePanel').root.reload();
		var treePanel=Ext.getCmp('objectsTreePanel');
		
		if(!currentObjTreeNode){
			currentObjTreeNode=Ext.getCmp('objectsTreePanel').root;
		}
		if(objNode&&currentObjTreeNode){
			if(opType=='add'){
				currentObjTreeNode.appendChild(objNode);
				currentObjTreeNode.leaf=false;
				currentObjTreeNode.expand();
				return true;
			}else if(opType=='update'){
				//更新节点
				var tmpNode=treePanel.getNodeById(objNode.id);
				if(tmpNode){
					tmpNode.setText(objNode.text);
				}
				return true;
			}
		}
		treePanel.root.reload();
	}
	
	//处理对象添加
	this.addHandler = function(e){
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/object_edit_win.jsp?objId=-1',400,450);
			return;
		}
		var objFormPanel=new Ecums.ObjectsFormPanel();
		Ecums.comFun.createCrubeWindow(objFormPanel,'对象添加',500,450,'对象添加成功',Ecums.objectUrl,'save',reloadData,'add');
	};
	
	this.updateHandler = function(){
			var grid=Ext.getCmp('objectsGridPanel');
			var store=grid.store;
			var sm = grid.getSelectionModel();
			var selected = sm.getSelections();    
	        if(selected.length!=1){
	        	Ext.Msg.alert('信息提示', '请选择一条记录');
				return;
	        }
			var tmpObj = selected[0].data;
	        var objKeyId=tmpObj.objId;
	        //alert(objKeyId);
			updateObjHandler(objKeyId);
	};
	
	updateObjHandler=function(objKeyId){
	        if(Ext.isIE&&Ecums.isPopWin){
				Ecums.comFun.popWinCenter('console/object_edit_win.jsp?objId='+objKeyId,400,450);
				return;
			}
			var objFormPanel=new Ecums.ObjectsFormPanel();
			Ecums.comFun.createCrubeWindow(objFormPanel,'对象修改',500,450,'对象修改成功',Ecums.objectUrl,'save',reloadData,'update');
			objFormPanel.getForm().load({url:Ecums.objectUrl+'?action=edit&objId='+objKeyId});
	}
	
	this.delHandler = function(){
        Ecums.comFun.deleteCrubeHandlerObj('objectsGridPanel','objId',Ecums.objectUrl,'del','你确认要删除选中的对象吗？','对象删除成功',reloadData,'del');
	};
	
	this.delObjHandler = function(ids){
        Ecums.comFun.deleteCrubeBaseHandler(ids,Ecums.objectUrl,'del','你确认要删除选中的对象吗？','对象删除成功',reloadData,'del');
	};
	
		
	this.opAccessHandler=function(button){	
			var grid=Ext.getCmp('objectsGridPanel');
			var sm = grid.getSelectionModel();
			var selectedObj = sm.getSelections();    
	        if(selectedObj.length<=0){
	        	Ext.Msg.alert('信息提示', '请选择记录');
				return;
	        }
	        var idsObj=[];
			Ext.each(selectedObj,function(accessBean){
               idsObj.push(accessBean.data.objId);
            });
            
	        if(Ext.isIE&&Ecums.isPopWin){
				Ecums.comFun.popWinCenter('console/obj_assign_access_win.jsp?objIds='+Ext.encode(idsObj)+'&actionMethod='+actionMethod,450,400);
				return;
			}
			
			accssModeStore=new Ext.data.JsonStore({
					autoLoad:false,
					baseParams :{action:'index',optype:'assign'},
					url:Ecums.accessModeUrl,
		   			root: 'data',
		       		totalProperty: 'totalCount',
		       		remoteSort: true,
		   			fields: ['accssId', 'alias','name','status','note'],
		   			listeners :{
		   				load:function(store,records,options){
		   					if(idsObj.length==1){
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
				                    		var sm2 = accessModePanel.getSelectionModel();
				                    		sm2.selectRecords(reArr);
										}
						            },	            
				   					params: {
				   						objId: idsObj[0],
				   					 	action:'findAccessByObjID'
				   					}
								});
		   					}
		   				}
		   			}
			});
				
			var accessModePanel=new Ecums.AccssModeCheckGridPanel({id:'AccssModeCheckGridPanel-2',store:accssModeStore},true);
			accessModePanel.title='';
			
			var opAccessFun=function(actionMethod){
				var op="分配";
				if('removeObj'==actionMethod){
					op='移除';
				}
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
   					success: function(resp){
						if(Ecums.comFun.failureText(resp)){
							return;
						}			   						
                		Ext.Msg.alert('信息提示',op+'对象操作成功');
		            },
		            failure : Ecums.comFun.failure,		            
   					params: {ids: Ext.encode(idsAccess),
   						objIds: Ext.encode(idsObj),
   					 	action:actionMethod
   					}
				});
			}
				
			var objWin=new Ext.Window({
                width: 550,
                height: 400,
                title:'分配移除资源对象操作',
                layout:'fit',
                closable : true,
                modal: Ecums.modal,
                shadow:false,
                closeAction :'close',
				items:[accessModePanel],
	           	buttons: [{
                    text : '分配',
                    handler : function(){
                    		opAccessFun('assignObj');
                    	}
	                },{
	                    text : '移除',
	                    handler : function(){
	                    	opAccessFun('removeObj');
	                    }
		             },{
                    	text     : '关闭',
                    	handler  : function(){
                        	objWin.close();
                    	}
                	}]                
               })
            objWin.show();
			accessModePanel.store.load();
	}
    this.searchHandler=function(){
    	var grid=Ext.getCmp('objectsGridPanel');
    	var store=grid.store;
    	var value=Ext.getCmp('searchObjName').getValue();
    	var field=Ext.getCmp('objConditionField').getValue();
    	store.baseParams=[];
    	store.baseParams.action='index';
    	store.baseParams[field]=value;	
    	store.load({params:{start:0,limit:Ecums.pageSize}});
    	
    }	
	this.pageToolBar=new Ext.PagingToolbar({
				id:"objectsPagingToolbar",
        		pageSize: Ecums.pageSize,
        		store: this.objectStore,
        		displayInfo: true,
        		displayMsg: '显示　对象记录 {0} - {1} of {2}',
        		emptyMsg: "暂无记录",
        		plugins:new Ext.ux.Andrie.pPageSize()
    });
    
    this.checkSelMode=new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});	
	Ecums.ObjectsGridPanel.superclass.constructor.call(this,{
		id:'objectsGridPanel',
		title:'对象列表',
		columnWidth:.75,
		autoHeight: true,
		autoScroll:true,
		//height:500,
		split:true,
		store:this.objectStore,
		selModel:this.checkSelMode,
		columns:[this.checkSelMode,
        	{id:'objId', header: "ID", width: 60, sortable: true, dataIndex: 'objId'},
       		{header: "标识",width:150, sortable: true, dataIndex: 'alias'},
        	{header: "名称",width:150, sortable: true, dataIndex: 'name'},
        	{header: "类型", sortable: true, dataIndex: 'type'},
        	{header: "图标",width:60, renderer:this.iconRender,dataIndex: 'icon'},
        	{header: "父对象",dataIndex: 'parentName',renderer:this.groupGrideParentRender},
        	{header: "状态",width:60,dataIndex: 'status',renderer:Ecums.comFun.renderStatus},
        	{header: "排序", width: 60, sortable: true, dataIndex: 'taxis'}],
        	
    	tbar:[{id:'obj-button-add',text:"添加",iconCls:'form-button-new',handler:this.addHandler},
    		'-',{text:"修改",iconCls:'form-button-edit',handler:this.updateHandler},
    		'-',{text:"删除",iconCls:'form-button-delete',handler:this.delHandler},
    		'-',{text:"分配移除操作",name:'assignObj',iconCls:'form-button-assign-obj',handler:this.opAccessHandler},
    		'-',
			'条件:',
			new Ext.form.ComboBox({
				fieldLabel: '状态',
				id:'objConditionField',
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
    		new Ext.form.TextField({id:'searchObjName',name:'searchObjName'}),
    		{text:"搜索",name:'searchButton',iconCls:'',pressed: true,handler:this.searchHandler}
    		],
    	bbar:this.pageToolBar
	})
	
};

Ext.extend(Ecums.ObjectsGridPanel,Ext.grid.GridPanel);

Ecums.ObjectsTreePanel=function(){
	this.myTreeLoader = new Ext.tree.TreeLoader({  
    	url:Ecums.objectUrl,
    	listeners:{
    		beforeload:function(treeLoader, node){
	        		treeLoader.baseParams={
	        		parentId:Ecums.TreeConfig.objtree.subTag(node),
	        		action:'tree'
        		}
        	}
        }
	});
	    
	Ecums.ObjectsTreePanel.superclass.constructor.call(this,{
		id:'objectsTreePanel',
		title:'对象层级',
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
        	text: '对象',
        	id: Ecums.TreeConfig.objtree.tag+'-1'
    	},
    	listeners:{
    		click:function(node,eo){
    			currentObjTreeNode=node;
    			var tmpStore=Ext.getCmp('objectsGridPanel').store;
    			objTreeParendId=Ecums.TreeConfig.objtree.subTag(node);
    			objTreeParendName=node.attributes.text;
    			tmpStore.baseParams=[];
    			tmpStore.baseParams.action='index';
    			tmpStore.baseParams.parentId=objTreeParendId;
    			tmpStore.load({params:{start:0,limit:Ecums.pageSize}});
    		},
    		nodedrop:function(e){
				if(e.point=='append'){ 
					var dragId=Ecums.TreeConfig.objtree.subTag(e.dropNode);
					var targetId=Ecums.TreeConfig.objtree.subTag(e.target);
					Ext.Ajax.request({
			   			url: Ecums.objectUrl,
			   			success: function(resp){
							if(Ecums.comFun.failureText(resp)){
								return;
							}			   			
	                		Ext.Msg.alert('信息提示', '对象节点移动成功');
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
    		},
    		contextmenu:function(node,e){
    			e.preventDefault(); //这行是必须的
    			//contextNode=node;
			    var rightClick = new Ext.menu.Menu({
			        items : [{ 
			            text : '添加',
			            iconCls:'form-button-new',
			            handler:function (item){
			            	 objTreeParendId=Ecums.TreeConfig.objtree.subTag(node);
    						 objTreeParendName=node.attributes.text;
			                 Ext.getCmp('objectsGridPanel').addHandler();
			            }
			        }, {
			            text : '修改',
			            iconCls:'form-button-edit',
			            handler:function (item){
			            	updateObjHandler(Ecums.TreeConfig.objtree.subTag(node));
			            }
			        }, {
			            text : '删除',
			            iconCls:'form-button-delete',
			            handler:function (item){
			            	var id=parseInt(Ecums.TreeConfig.objtree.subTag(node));
			            	Ext.getCmp('objectsGridPanel').delObjHandler([id]);
			            }
			        }] 
			     });
			     rightClick.showAt(e.getXY());//取得鼠标点击坐标，展示菜单 
    		}
    	}
	})
};

Ext.extend(Ecums.ObjectsTreePanel,Ext.tree.TreePanel);

Ecums.ObjectsFormPanel=function(objArg){
	if(arguments){
		Ecums.ObjectsFormPanel.superclass.constructor.call(this,arguments[0]);
	}
	Ecums.ObjectsFormPanel.superclass.constructor.call(this,{
		id:'objectsFormPanel',
		defaultType: 'textfield',
		margins:'10 0 10 0',
		labelWidth:80,
		labelAlign:'right',
		fileUpload:true,
		items: [{
                fieldLabel: '对象ID',
                name: 'objId',
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
            	fieldLabel: '类别',
                name: 'type',
                value:'ecums'
            },{
                fieldLabel: '图标',
                name: 'icon',
                inputType:'file',
                anchor:'95%'
            },{
                fieldLabel: '父对象',
                name: 'parentName',
                value:objTreeParendName,
                readOnly:true
            },{
                fieldLabel: '父ID',
                name: 'parentId',
                xtype:'hidden',
                value:objTreeParendId,
                readOnly:true
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
	});
};

Ext.extend(Ecums.ObjectsFormPanel,Ext.form.FormPanel);

//对象管理主窗口
Ecums.ObjectsMainPanel = function(){
	this.objectsGridPanel=new Ecums.ObjectsGridPanel();
	
	this.objectTreePanel=new Ecums.ObjectsTreePanel();
	Ecums.ObjectsMainPanel.superclass.constructor.call(this,{
		title:'对象管理',
		id:'objectsMainPanel',
		closable: true,
		layout:'column',
		listeners:{destroy:function()
					{
						objTreeParendId=-1;
						objTreeParendName='对象';
					}
		},
		items:[this.objectTreePanel,this.objectsGridPanel]
	});
	this.objectsGridPanel.store.load({params:{limit:Ecums.pageSize,start:0}});
}
Ext.extend(Ecums.ObjectsMainPanel,Ext.Panel);

//对象复选框，可选层级树
Ecums.ObjectsCheckShowTreePanel=function(){
	this.myTreeLoader = new Ext.tree.TreeLoader({
    	url:Ecums.operatorUrl,   	
        baseAttrs: {uiProvider: Ext.ux.TreeCheckNodeUI}
	});

	
	Ecums.ObjectsCheckShowTreePanel.superclass.constructor.call(this,{
		id:'objectsCheckShowTreePanel',
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
		checkModel:'childCascade',
    	root: {
        	nodeType:'async',    
        	text: '对象',
        	id: Ecums.TreeConfig.objAssignTree.tag+'-1'
    	},
    	bbar:[{text:"全部展开",iconCls:'',handler:function(){Ext.getCmp('objectsCheckShowTreePanel').expandAll()}},'-',
    	{text:"全部收起",iconCls:'',handler:function(){Ext.getCmp('objectsCheckShowTreePanel').collapseAll()}}]
	});
	
	if(arguments){
		Ecums.ObjectsCheckShowTreePanel.superclass.constructor.call(this,arguments[0]);	
	}	
};
Ext.extend(Ecums.ObjectsCheckShowTreePanel,Ext.tree.TreePanel);


//对象复选框，可选层级树（角色权限分配）
Ecums.ObjectsCheckPermissionTreePanel=function(id,gridFormId,roleIdArr){
	var defaultId=id;
	if(!defaultId){
		defaultId='objectsCheckPermissionTreePanel';
	}
	var gridId=gridFormId;
	if(!gridId){
		gridId='accssModeCheckGridPanel';
	}
	
	this.myTreeLoader = new Ext.tree.TreeLoader({
    	url:Ecums.operatorUrl,   	
        baseAttrs: {uiProvider: Ext.ux.TreeCheckNodeUI}
	});
	    
	Ecums.ObjectsCheckPermissionTreePanel.superclass.constructor.call(this,{
		id:defaultId,
		title:'对象层级',
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
		checkModel:'single',
		columnWidth:.3,
		height:445,
    	root: {
        	nodeType:'async',    
        	text: '对象',
        	id: Ecums.TreeConfig.objAssignTree.tag+'-1'
    	},
    	bbar:[{text:"全部展开",iconCls:'',handler:function(){Ext.getCmp(defaultId).expandAll()}},'-',
    	{text:"全部收起",iconCls:'',handler:function(){Ext.getCmp(defaultId).collapseAll()}}],    	
    	listeners:{
    		click:function(node,eo){
    			//var id=Ecums.TreeConfig.objAssignTree.subTag(node);
    			//var tmpStore=Ext.getCmp('accssModeCheckGridPanel').store;
    			//tmpStore.load({params:{start:0,limit:Ecums.pageSize,objId:id}});
    		},
    		check:function(node,checked){
    			if(checked){
	    			var id=Ecums.TreeConfig.objAssignTree.subTag(node);
	    			objTreeIdForRoleAssign=id;
	    			var tmpStore=Ext.getCmp(gridId).store;
	    			tmpStore.load({params:{start:0,limit:Ecums.pageSize,objId:id}});
	    			
    			}  				
    		}
    	}	
	})
};
Ext.extend(Ecums.ObjectsCheckPermissionTreePanel,Ext.tree.TreePanel);