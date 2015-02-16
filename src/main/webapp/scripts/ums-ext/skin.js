Ecums.SkinSytlesComboBox=function(){
	Ecums.SkinSytlesComboBox.superclass.constructor.call(this,{
		id:'skinSytlesComboBox',
		fieldLabel: '主题设置',
		name: 'statusShow',
		emptyText:'选择',
        triggerAction: 'all',
        editable:false,
        width:100,
        store: new Ext.data.SimpleStore({
				fields: ['code', 'desc'],
				data : Ecums.formdata.skinSytles
		}),
        valueField:'code',
        displayField:'desc',
        hiddenName:'status',//果有form提交,这个值一定要设置,不然记下选了那个值
        mode:'local',
        allowBlank: false,
        listeners:{
        	change:function(field,newValue,oldValue){
        		//Ecums.comFun.changeSkin(newValue);
        	}
        }
	})
}
Ext.extend(Ecums.SkinSytlesComboBox,Ext.form.ComboBox);
//换肤管理
Ecums.SkinFormPanel = function(){
	this.panel=new Ext.form.FormPanel({
		title:'主题设置',
		layout:'form',
		labelWidth:80,
		labelAlign:'right',
		tbar:[{text:'确认',pressed: true,handler:function(){
			var newValue=Ext.getCmp('skinSytlesComboBox').getValue();
			Ecums.comFun.changeSkin(newValue);
			var cp = new Ext.state.CookieProvider({
	       		expires: new Date(new Date().getTime()+(1000*60*60*24*30))
	   		});
	   		cp.set('extjs-skin-style-value',newValue);
	   		Ext.state.Manager.setProvider(cp);				
		}}],
		items:[new Ecums.SkinSytlesComboBox()]
	});
	Ecums.SkinFormPanel.superclass.constructor.call(this,{
		title:'换肤管理',
		id:'skinFormPanel',
		layout:'fit',
		closable: true,
		items:[this.panel]
	});
}
Ext.extend(Ecums.SkinFormPanel,Ext.Panel);