Ext.BLANK_IMAGE_URL = '/ecums/images/s.gif';
Ext.namespace('Ecums');
Ecums.Path={
	contextPath:'/ecums/'
};
Ecums = {
	author: "junyang",
    version: "2.0",
    contextPath:'ecums',
    baseSkinPath:Ecums.Path.contextPath+'scripts/ext-2.2/resources/css/',
    objectUrl:Ecums.Path.contextPath+"opObjects.do",
    accessModeUrl:Ecums.Path.contextPath+"opAccssMode.do",
    operatorUrl:Ecums.Path.contextPath+"opOperator.do",
    groupUrl:Ecums.Path.contextPath+"opGroup.do",
    userUrl:Ecums.Path.contextPath+"opUser.do",
    roleUrl:Ecums.Path.contextPath+'opRole.do',
    usersAssignmenUrl:Ecums.Path.contextPath+'opUsersAssignmen.do',
    groupAssignmenUrl:Ecums.Path.contextPath+'opGroupAssignmen.do',
    permissionUrl:Ecums.Path.contextPath+'opPermission.do',
    pageSize:15,
    isPopWin:false,
    modal:true
};

Ext.namespace('Ecums.TreeConfig');

Ecums.TreeConfig.objtree={
	tag:'ec.object.tree.',
	subTag:function(node){
		return (node.attributes.id.substring(this.tag.length));
	}
};

Ecums.TreeConfig.objAssignTree={
	tag:'ec.object.assign.tree.',
	subTag:function(node){
		return (node.attributes.id.substring(this.tag.length));
	}
};

Ecums.TreeConfig.groupTree={
	tag:'ec.group.tree.',
	subTag:function(node){
		return (node.attributes.id.substring(this.tag.length));
	}
};

Ecums.TreeConfig.roleTree={
	tag:'ec.role.tree.',
	subTag:function(node){
		return (node.attributes.id.substring(this.tag.length));
	}
};

Ecums.TreeConfig.roleAssignTree={
	tag:'ec.role.assign.tree.',
	subTag:function(node){
		return (node.attributes.id.substring(this.tag.length));
	}
};

Ext.namespace('Ecums.comFun');

Ecums.StatusComboBox=function(){
	Ecums.StatusComboBox.superclass.constructor.call(this,{
		fieldLabel: '状态',
		name: 'statusShow',
		emptyText:'选择',
        triggerAction: 'all',
        editable:false,
        width:80,
        store: new Ext.data.SimpleStore({
				fields: ['code', 'desc'],
				data : Ecums.formdata.status
		}),
        valueField:'code',
        displayField:'desc',
        hiddenName:'status',//果有form提交,这个值一定要设置,不然记下选了那个值
        mode:'local',
        allowBlank: false
	})
}

Ext.extend(Ecums.StatusComboBox,Ext.form.ComboBox);

Ecums.GroupTypeComboBox=function(){
	Ecums.GroupTypeComboBox.superclass.constructor.call(this,{
		fieldLabel: '类型',
		name: 'typeShow',
		emptyText:'选择',
        triggerAction: 'all',
        editable:false,
        width:80,
        store: new Ext.data.SimpleStore({
				fields: ['code', 'desc'],
				data : Ecums.formdata.groupTypes
		}),
        valueField:'code',
        displayField:'desc',
        hiddenName:'type',//果有form提交,这个值一定要设置,不然记下选了那个值
        mode:'local',
        allowBlank: false
	})
}

Ext.extend(Ecums.GroupTypeComboBox,Ext.form.ComboBox);

Ecums.comFun={
	failure:function(form, action) {
		Ext.Msg.alert('信息提示', "对不起，系统异常操作失败");
	},
	failureText:function(resp){
		if(resp.responseText!=''){
			var respJson=Ext.decode(resp.responseText);
			if(respJson.failure){
				if(respJson.msg){
					Ext.Msg.alert('信息提示', respJson.msg);
				}else{
					Ecums.comFun.failure();
				}
				return true;
			}else{
				return true;
			}
		}
		return false;
	},
	renderStatus:function(val){
		return val=='y'?'启用':'禁用';
	},
	changeSkin:function(skinCss){
		var styleLinkDom=Ext.getDom('extjs-skin-style-Id');
		styleLinkDom.href=Ecums.baseSkinPath+skinCss;	
	},
	loadCookieSkin:function(){
	    var cp = new Ext.state.CookieProvider({
	       		expires: new Date(new Date().getTime()+(1000*60*60*24*30))
	   	});
	   	var newValue=cp.get('extjs-skin-style-value');
	   	if(newValue){
	   		this.changeSkin(newValue);
	   	}
	},
	popWinCenter:function(url,w,h){
	    x=w;
	    y=h;
	    l=(screen.width/2)-(x/2);
	    t=(screen.height/2)-(y/2);
	    s="toolbar=no,location=no,status=no,menubar=no,resizable=no,scrollbars=yes";
	    s+=" width=" + x + ", height=" + y + ", left=" + l + ", top=" + t;
	    MRV=window.open(url,"",s);
	},
	createRpWindow:function(roleId){
		if(Ext.isIE&&Ecums.isPopWin){
			Ecums.comFun.popWinCenter('console/role_assign_permission_win.jsp?roleId='+roleId,600,500);
			return;
		} 	
        var permissionGridPanel=new Ecums.PermissionGridPanel();
        //当翻面时，要加载相应的查询参数
        permissionGridPanel.store.on('beforeload',function(store,options){
        	store.baseParams.roleId=roleId;
        });
        
		objWin=new Ext.Window({
               width: 720,
               height: 500,
               title:'查看角色已分配的对象操作权限',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[permissionGridPanel]
         });
         objWin.show();
         permissionGridPanel.store.load({params:{roleId:roleId,limit:Ecums.pageSize,start:0}});	
	},
	createRoleAsWindow:function(roleId){
		if(Ext.isIE&&Ecums.isPopWin){
			//Ecums.comFun.popWinCenter('console/role_assign_permission_win.jsp?roleId='+roleId,600,500);
			//return;
		} 	
        var permissionGridPanel=new Ecums.RoleAssignUserPsGridPanel();
        //当翻面时，要加载相应的查询参数
        permissionGridPanel.store.on('beforeload',function(store,options){
        	store.baseParams.roleId=roleId;
        });
        
		objWin=new Ext.Window({
               width: 600,
               height: 500,
               title:'查看角色授权用户',
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[permissionGridPanel]
         });
         objWin.show();
         permissionGridPanel.store.load({params:{roleId:roleId,limit:Ecums.pageSize,start:0}});	
	},	
	//Crube操作，用于数据添加、修改
	createCrubeWindow:function(objFormPanel,title,width,height,msg,actionUrl,actionMethod,reloadData,opType){
		objWin=new Ext.Window({
               width: width,
               height: height,
               title:title,
               layout:'fit',
               closable : true,
               closeAction :'close',
               modal: Ecums.modal,
               items:[objFormPanel],
	           buttons: [{
                    text : '提交',
                    handler : function(){
                    	var objForm=objFormPanel.getForm();
                    	if(objForm.isValid()){
		                    objForm.submit({
		                    	url:actionUrl,
		                    	params:{action:actionMethod},
		                    	method:'post',
		                    	success:function(form,action){
		                    		if(action.result.errorsMap.msg){
			                    		Ext.Msg.alert('信息提示', action.result.errorsMap.msg);
			                    	}else{
	                    				Ext.Msg.alert('信息提示', msg);
	                    				if(opType&&opType=='add'){
	                    					objForm.reset();
	                    				}
	                    				if(reloadData){
	                    					reloadData.call(this,action.result.dataMap,opType);
	                    				}
			                    	}
		                    	},
		                    	failure : Ecums.comFun.failure,
		                    	waitMsg:'保存数据中...'
		                    });                    	
                    	}else{
                    		Ext.Msg.alert('信息提示', "请填写完整表单信息");
                    	}
	                }},{
                    	text     : '重置',
                    	handler  : function(){
                        	objFormPanel.getForm().reset();
                    	}
                	}]
 		});
		objWin.show();		
	},
	//crube操作，根据记录ID删除记录
	deleteCrubeHandlerObj:function(gridPanelId,flagId,actionUrl,actionMethod,confirmMsg,sucessMsg,reloadData){
		var grid=Ext.getCmp(gridPanelId);
		var sm = grid.getSelectionModel();
		var selected = sm.getSelections();    
        var ids = [];    
        for(var i=0;i<selected.length;i+=1){
            var tmpObj = selected[i].data;    
            ids.push(tmpObj[flagId]);
        }
        
        if(ids.join('')==''){
        	Ext.Msg.alert('信息提示', '请选择要删除的记录');
        	return;
        }	
		Ext.MessageBox.confirm('信息提示', confirmMsg, function(btn){
				if(btn=='yes'){
					Ext.Ajax.request({
			   			url: actionUrl,
			   			success: function(resp){
							if(Ecums.comFun.failureText(resp)){
								return;
							}			   			
		   					Ext.Msg.alert('信息提示', sucessMsg);
                			reloadData.call();
					    },
			            failure : function(){Ext.Msg.alert('信息提示', '操作失败！！');},					              
			   			params: {ids: Ext.encode(ids),
			   					action:actionMethod
			   			}
					});
				}
			}
		);	
	},
	deleteCrubeBaseHandler:function(ids,actionUrl,actionMethod,confirmMsg,sucessMsg,reloadData){        
        if(ids.join('')==''){
        	Ext.Msg.alert('信息提示', '请选择要删除的记录');
        	return;
        }
		Ext.MessageBox.confirm('信息提示', confirmMsg, function(btn){
				if(btn=='yes'){
					Ext.Ajax.request({
			   			url: actionUrl,
			   			success: function(){
	                		Ext.Msg.alert('信息提示', sucessMsg);
	                		reloadData.call();
					    },
			            failure : Ecums.comFun.failure,					              
			   			params: {ids: Ext.encode(ids),
			   					action:actionMethod
			   			}
					});
				}
			}
		);	
	},	
	getFormButton:function(formPanelId,actionUrl,actionMethod,msg){
		var tmp=[{
                    text : '保存',
                    handler : function(){
                    	var objForm=Ext.getCmp(formPanelId).getForm();
                    	if(objForm.isValid()){
		                    objForm.submit({
		                    	url:actionUrl,
		                    	params:{action:actionMethod},
		                    	method:'post',
		                    	success:function(form,action){
		                    		if(action.result.errorsMap.msg){
			                    		Ext.Msg.alert('信息提示', action.result.errorsMap.msg);
			                    	}else{
	                    				Ext.Msg.alert('信息提示', msg);
	                    				//reloadData.call();
			                    	}
		                    	},
		                    	failure : Ecums.comFun.failure,
		                    	waitMsg:'保存数据中...'
		                    });                    	
                    	}else{
                    		Ext.Msg.alert('信息提示', "请填写完整表单信息");
                    	}
	                }},{
                    	text     : '重置',
                    	handler  : function(){
                    		var objForm=Ext.getCmp(formPanelId).getForm();
                        	objForm.getForm().reset();
                    	}
                	}];
      return tmp;	
	}	
}
Ext.namespace('Ecums.formdata');
Ecums.formdata.status = [['y','启用'],['n','禁用']];
Ecums.formdata.skinSytles = [['ext-all.css','默认主题'],['xtheme-black.css','黑色主题'],['xtheme-darkgray.css','暗灰主题'],['xtheme-gray.css','灰色主题'],['xtheme-purple.css','紫色主题'],['xtheme-olive.css','橄榄绿主题'],['xtheme-slate.css','暗蓝灰主题']];
Ecums.formdata.searchFieldList = [['alias','标识'],['name','名称']];
Ecums.formdata.groupTypes = [['01','用户组'],['02','库点'],['03','OA用户组']];
Ext.apply(Ext.form.VTypes, {
                confirmPwd : function(val, field) {
                    if (field.confirmPwd) {
                        var firstPwdId = field.confirmPwd.first;
                        var secondPwdId = field.confirmPwd.second;
                        this.firstField = Ext.getCmp(firstPwdId);
                        this.secondField = Ext.getCmp(secondPwdId);
                        var firstPwd = this.firstField.getValue();
                        var secondPwd = this.secondField.getValue();
                        if (firstPwd == secondPwd) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                },
                confirmPwdText : '两次输入的密码不一致!'
            });



