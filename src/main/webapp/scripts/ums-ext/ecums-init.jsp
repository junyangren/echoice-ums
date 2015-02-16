<%@ page contentType="text/javascript; charset=UTF-8" language="java"%>
<%@ include file="/commons/taglibs.jsp" %>
Ext.namespace('Ecums');
Ecums.Path={
	contextPath:'${ctx}/'
};
Ecums.menuObjList=[];
Ext.onReady(function(){
	<%--
	Ecums.menuObjList=[{title:'对象操作管理',html:Ext.get('obj_accordion')?Ext.get('obj_accordion').dom.innerHTML:''},
        		{title:'用户组织管理',html:Ext.get('user_accordion')?Ext.get('user_accordion').dom.innerHTML:''},
        		{title:'权限分配管理',html:Ext.get('per_accordion')?Ext.get('per_accordion').dom.innerHTML:''},
        		{title:'系统其他管理',html:Ext.get('x-desktop4')?Ext.get('x-desktop4').dom.innerHTML:''}];
   --%>     		
   Ecums.menuObjList=[<c:forEach items="${accordion}" var="listone">
   						{title:'${listone.parentMenuObj.name}',html:${listone.parentMenuObj.note}},
   					 </c:forEach>
   					 {title:'系统其他管理',html:Ext.get('x-desktop4')?Ext.get('x-desktop4').dom.innerHTML:''}
   					 ] ;  		
});