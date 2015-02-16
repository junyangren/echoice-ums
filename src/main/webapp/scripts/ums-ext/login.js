function changeImg(){   
    var imgSrc = Ext.getDom("verifyCodeServletImgID");
    imgSrc.setAttribute("src",chgUrl('verifyCodeServlet')); 
    //imgSrc.src =chgUrl('verifyCodeServlet');  
}   
//时间戳   
//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳   
function chgUrl(url){   
    var timestamp = (new Date()).valueOf();   
    url = url.substring(0,17);   
    if((url.indexOf("&")>=0)){   
        url = url + "×tamp=" + timestamp;   
    }else{   
        url = url + "?timestamp=" + timestamp;   
    }   
    return url;   
}   


Ext.onReady(function(){
	Ext.QuickTips.init();
	function subLoginForm(button){
		var objForm=Ext.getCmp('loginForm').getForm();
	   	if(objForm.isValid()){
		     objForm.submit({
		     	url:'login.do',
		     	params:{action:'index'},
		     	method:'post',
		     	success:function(form,action){
		     		if(action.result.errorsMap.msg){
		      		Ext.Msg.alert('信息提示', action.result.errorsMap.msg);
		      	}else{
		    				objForm.getEl().dom.action = 'index.jsp';
		    				objForm.getEl().dom.submit();
		      	}
		     	},
		     	failure : Ecums.comFun.failure
		     });                    	
	   	}else{
	   		Ext.Msg.alert('信息提示', "请填写完整登入信息");
	   	}                    	
    }
	
	var loginForm=new Ext.form.FormPanel({
		id:'loginForm',
		defaultType: 'textfield',
		labelWidth:140,
		labelAlign:'right',
		bodyStyle:'login-form',
		keys:[{key:Ext.EventObject.ENTER, 
    			fn:subLoginForm, 
    			scope:this
    		}
		],
		items:[{fieldLabel: '登入名',
			name: 'alias',
			width:150,
			allowBlank:false,
			maxLength:30
		},{fieldLabel: '密码',
			name: 'password',
			inputType:'password',
            minLength:6,
            allowBlank:false,
            maxLength:64,
            width:150
        },{fieldLabel: '验证码',
			name: 'authPassword',
            minLength:4,
            allowBlank:false,
            maxLength:6,
            width:150,
            listeners:{
            	change:function(fiels,newValue,oldValue){
            		fiels.setValue(newValue.toUpperCase());
            	}
            }
         },{xtype:'label',html:'<div style="padding-left:148px;float:left"><img style="width:65px" id="verifyCodeServletImgID" src="verifyCodeServlet"/></div>&nbsp;<a href="javascript:void(0);" onclick="changeImg()">换一张</a>'}]
	});
    var win;
	if(!win){
        win = new Ext.Window({
        	title:'统一用户权限管理系统（UMS2.0）',
            layout      : 'fit',
            width       : 400,
            height      : 190,
            resizable:false,
            closeAction :'hide',
            plain       : true,
            autoShow	:true,
            closable	:false,
            items:[loginForm],
            buttons: [{
                text     : '登入',
                handler  : subLoginForm
            },{
                text     : '重置',
                handler  : function(){
                    loginForm.getForm().reset();
                }
            }]
        });
    }
    win.show();
    Ecums.comFun.loadCookieSkin();	
});