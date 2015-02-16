Ecums.AccordionLayout = function(){
	Ecums.AccordionLayout.superclass.constructor.call(this,{
		title:'系统菜单',
        region:'west',
        margins:'5 0 5 5',
        cmargins:'5 5 5 5',
        split:true,
        width: 250,
        minSize: 100,
        maxSize: 250,
        collapsible: true,
        layout:'accordion',
        collapseFirst:false,
        items:Ecums.menuObjList
	})
};

Ext.extend(Ecums.AccordionLayout,Ext.Panel);

Ecums.MainTabLayout = function(){
	Ecums.MainTabLayout.superclass.constructor.call(this,{
		id:'main-layout-tab',
		region:'center',
		minTabWidth: 100,
		margins:'5 2 5 0',
        tabWidth: 100,
        activeTab: 0,
        resizeTabs:true,
		items:[{title:'统一权限管理',html:'<div></div>'}]
	})
};



Ext.extend(Ecums.MainTabLayout,Ext.TabPanel);

function createAccssModeMainPanel(){
	mainLayoutCmp=Ext.getCmp('main-layout-tab');
	tmp=Ext.getCmp('accssModeMainPanel');
	if(!tmp){
		tmp=new Ecums.AccssModeMainPanel();
		mainLayoutCmp.add(tmp);
	}
	mainLayoutCmp.setActiveTab(tmp);
}

function createObjectsMainPanel(){
	mainLayoutCmp=Ext.getCmp('main-layout-tab');
	tmp=Ext.getCmp('objectsMainPanel');
	if(!tmp){
		tmp=new Ecums.ObjectsMainPanel();
		mainLayoutCmp.add(tmp);
	}
	mainLayoutCmp.setActiveTab(tmp);
}


function createGroupsMainPanel(){
	mainLayoutCmp=Ext.getCmp('main-layout-tab');
	tmp=Ext.getCmp('groupsMainPanel');
	if(!tmp){
		tmp=new Ecums.GroupsMainPanel();
		mainLayoutCmp.add(tmp);
	}
	mainLayoutCmp.setActiveTab(tmp);
}

function createRoleMainPanel(){
	mainLayoutCmp=Ext.getCmp('main-layout-tab');
	tmp=Ext.getCmp('roleMainPanel');
	if(!tmp){
		tmp=new Ecums.RoleMainPanel();
		mainLayoutCmp.add(tmp);
	}
	mainLayoutCmp.setActiveTab(tmp);
}
function createUserMainPanel(){
	mainLayoutCmp=Ext.getCmp('main-layout-tab');
	tmp=Ext.getCmp('userMainPanel');
	if(!tmp){
		tmp=new Ecums.UserMainPanel();
		mainLayoutCmp.add(tmp);
	}
	mainLayoutCmp.setActiveTab(tmp);
}

function createSearchMainPanel(){
	mainLayoutCmp=Ext.getCmp('main-layout-tab');
	tmp=Ext.getCmp('permissionSearchMainPanel');
	if(!tmp){
		tmp=new Ecums.PermissionSearchMainPanel();
		mainLayoutCmp.add(tmp);
	}
	mainLayoutCmp.setActiveTab(tmp);
}

function createSkinMainPanel(){
	mainLayoutCmp=Ext.getCmp('main-layout-tab');
	tmp=Ext.getCmp('skinFormPanel');
	if(!tmp){
		tmp=new Ecums.SkinFormPanel();
		mainLayoutCmp.add(tmp);
	}
	mainLayoutCmp.setActiveTab(tmp);
}
function loadCookieSkin(){
    var cp = new Ext.state.CookieProvider({
       		expires: new Date(new Date().getTime()+(1000*60*60*24*30))
   	});
   	var newValue=cp.get('extjs-skin-style-value');
   	if(newValue){
   		Ecums.comFun.changeSkin(newValue);
   	}
}

function updateUserInfo(){
	var objFormPanel=new Ecums.UserFormPanel();
	Ecums.comFun.createCrubeWindow(objFormPanel,'用户修改',400,420,'用户资料修改成功',Ecums.userUrl,'save',null);
	Ext.getCmp('userFormTabpanel-1').activate(Ext.getCmp('userFormTabpanel-1.1'));
	objFormPanel.getForm().load({url:Ecums.userUrl+'?action=edit'});
}
Ext.onReady(function(){
    Ext.QuickTips.init();
    var leftLayout=new Ecums.AccordionLayout();
    var mainLayout=new Ecums.MainTabLayout();
    var viewport = new Ext.Viewport({
    	layout:'border',
    	items:[{
    		xtype: 'box',
		 	region: 'north',
		 	applyTo: 'header',
			height: 30
			},leftLayout,mainLayout]
    });
    if(Ext.isIE){
    	//CollectGarbage();
    	setTimeout("CollectGarbage();", 1);
    	//QTP_Release_Memory(window);
    }
    Ecums.comFun.loadCookieSkin();
    window.history.go(1);
});