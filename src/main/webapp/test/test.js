Ext.namespace("Ext.maintainSys");

Ext.maintainSys.Box = function()
{
	if(arguments){
		Ext.maintainSys.Box.superclass.constructor.call(this,arguments[0]);
	}
	Ext.maintainSys.Box.superclass.constructor.call(this,{
		 region:'north',
         el:'north',
         height:50
	});
};
Ext.extend(Ext.maintainSys.Box,Ext.BoxComponent);

Ext.maintainSys.TabPanel = function()
{
	if(arguments){
		Ext.maintainSys.TabPanel.superclass.constructor.call(this,arguments[0]);
	}
	Ext.maintainSys.TabPanel.superclass.constructor.call(this,{
		 region:'center',
         deferredRender:false,
         margins:'0 5 5 0',
         activeTab:0,
         items:[{
                title: 'Close Me',
                closable:true,
                autoScroll:true
               },
               {
                title: 'Center Panel',
                autoScroll:true
               }]
	});
};

Ext.extend(Ext.maintainSys.TabPanel,Ext.TabPanel);

Ext.maintainSys.WestPanel = function()
{
	if(arguments){
		Ext.maintainSys.WestPanel.superclass.constructor.call(this,arguments[0]);
	}
	Ext.maintainSys.WestPanel.superclass.constructor.call(this,{
					region:'west',
                    title:'菜单栏',
                    split:true,
                    width: 200,
                    minSize: 175,
                    maxSize: 400,
                    collapsible: false,
                    margins:'0 0 5 5',
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    },
                    items: [{
                        title:'Navigation',
                        border:false,
                        iconCls:'navIcon',
                        items:[new Ext.Panel({contentEl:'x-desktop'})]
                    },{
                        title:'Settings',
                        html:'xxx',
                        border:false,
                        iconCls:'settingsIcon'
                    }]
	});
};
Ext.extend(Ext.maintainSys.WestPanel,Ext.Panel);

Ext.maintainSys.Viewport = function()
{
	Ext.maintainSys.Viewport.superclass.constructor.call(this,{
		id:'abcd',
        layout:'border',
        loadMask:true,
        items:[
        new Ext.maintainSys.Box(),
        new Ext.maintainSys.TabPanel(),
        new Ext.maintainSys.WestPanel()]
	})
};
Ext.extend(Ext.maintainSys.Viewport,Ext.Viewport);
Ext.onReady(function(){
  	Ext.QuickTips.init();
	var view=new Ext.maintainSys.Viewport();
 })