<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="org.echoice.modules.web.json.bean.ExtJsActionView"%>
<%@page import="net.sf.json.JSONObject"%>
<%
	response.setContentType("text/html; charset=UTF-8");
	//ExtJsActionView actionView=new ExtJsActionView();
	//actionView.addErrorCodeMsg("msg","系统异常:");
	//JSONObject jsObject=JSONObject.fromObject(actionView);
	//String str=jsObject.toString();
    //out.println(str);
    String action=request.getParameter("action");
    if("save".equals(action)){
    	
    }else{
    	//out.println("{'failure':true}");
    }
    out.println("{'failure':true}");
%>
