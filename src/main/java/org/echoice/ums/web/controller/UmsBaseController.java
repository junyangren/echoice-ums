package org.echoice.ums.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.echoice.modules.web.controller.SpringBaseController;
import org.echoice.modules.web.json.DateJsonValueProcessor;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.util.CasUmsUtil;
import org.springframework.web.servlet.ModelAndView;

public class UmsBaseController extends SpringBaseController {

	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 得到单一表单数据
	 * @param response
	 * @param obj
	 * @param excudeField
	 */
	protected void renderExtjsObject(HttpServletResponse response,Object obj,String[] excudeField) throws IOException{
		StringBuffer bf=new StringBuffer();
		bf.append("{");
		bf.append("success: true,");
		bf.append("data:");
		if(obj!=null){
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			if(excudeField!=null){
				jsonConfig.setExcludes(excudeField);
			}
			JSONObject jsObject=JSONObject.fromObject(obj,jsonConfig);
			bf.append(jsObject.toString());
		}else{
			bf.append("{}");
		}
		bf.append("}");
		rendTextExtjs(response, bf.toString());
	}
	/**
	 * 得到列表数据
	 * @param response
	 * @param list
	 * @param excudeField
	 */
	protected void renderExtjsList(HttpServletResponse response,List list,long total,String[] excudeField) throws IOException{
		StringBuffer bf=new StringBuffer();
		bf.append("{");
		bf.append("totalCount:"+total);
		bf.append(",data:");
		if(total!=0){
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			if(excudeField!=null){
				jsonConfig.setExcludes(excudeField);
			}
			JSONArray jSONArray=JSONArray.fromObject(list,jsonConfig);
			bf.append(jSONArray.toString());
		}else{
			bf.append("[]");
		}
		bf.append("}");
		//logger.info(bf.toString());
		rendTextExtjs(response, bf.toString());
	}
	
	/**
	 * 返回表单处理对象
	 * @param response
	 * @param extjsActionView
	 * @throws IOException
	 */
	protected void renderExtjsActionView(HttpServletResponse response,ExtJsActionView extjsActionView) throws IOException{
		JSONObject jsObject=JSONObject.fromObject(extjsActionView);
		String str=jsObject.toString();
		logger.debug(str);
		rendTextExtjs(response, str);
	}
	
	protected void renderExtjsActionView(HttpServletResponse response,ExtJsActionView extjsActionView,boolean isText) throws IOException{
		JSONObject jsObject=JSONObject.fromObject(extjsActionView);
		String str=jsObject.toString();
		logger.debug(str);
		if(isText){
			rendText(response, str);
		}else{
			rendTextExtjs(response, str);
		}
		
	}
	
	protected void rendTextExtjs(HttpServletResponse response, String content) throws IOException {
        rendJS(response, content);
    }
	
    protected void rendExtJSON(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(content);
    }
	/**
	 * 
	 * @param request
	 * @return
	 */
	protected String getSessionUserName(HttpServletRequest request){
		String userName=CasUmsUtil.getUser(request);
		return userName;
	}
	
    protected int[] getExtJsPage(HttpServletRequest request){
		String pageSize=request.getParameter("limit");
		String start=request.getParameter("start");
		int pageIntArr[]=new int[2];
		if(StringUtils.isNotEmpty(pageSize)){
			try{
				pageIntArr[1]=Integer.parseInt(pageSize);
			}catch(NumberFormatException ex){
				
			}
			if(pageIntArr[1]>PageBean.MAX_PAGE_SIZE){
				pageIntArr[1]= PageBean.MAX_PAGE_SIZE;
			}else if(pageIntArr[1]==0){
				pageIntArr[1]= PageBean.DEFAULT_PAGE_SIZE;
			}
		}else{
			pageIntArr[1]=PageBean.DEFAULT_PAGE_SIZE;
		}
		
		if(StringUtils.isNotEmpty(start)){
			try{
				pageIntArr[0]=Integer.parseInt(start);
			}catch(NumberFormatException ex){
				
			}
			pageIntArr[0]=pageIntArr[0]/pageIntArr[1]+1;
		}else{
			pageIntArr[0]=1;
		}
		return pageIntArr;
	}
	
	public static void main(String[] args) {
		ExtJsActionView actionView=new ExtJsActionView();
		actionView.addErrorCodeMsg("aa", "bb");
		JSONObject aSONObject=JSONObject.fromObject(actionView);
		System.out.println(aSONObject.toString());
		
	}
}
