package org.echoice.ums.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.echoice.modules.web.controller.SpringBaseController;
import org.echoice.modules.web.json.bean.JSONTreeNode;
import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.domain.EcGroup;
import org.springframework.web.servlet.ModelAndView;

public class ExtJsTreeController extends SpringBaseController {
	private EcGroupDao ecGroupDao;
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String parentId=request.getParameter("parentId");
		List list=ecGroupDao.findGroupTreeParent();
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : list) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		String strParentTree=bf.toString();
		
		List<EcGroup> childList=ecGroupDao.findGroupTreeChild(Long.valueOf(parentId));
		List<JSONTreeNode> listTree=new ArrayList<JSONTreeNode>();
		for (EcGroup ecGroup : childList) {
			JSONTreeNode treeNode=new JSONTreeNode();
			treeNode.setId("group-"+ecGroup.getGroupId());
			treeNode.setText(ecGroup.getName());
			if(strParentTree.indexOf("|"+ecGroup.getGroupId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			listTree.add(treeNode);
		}
		JSONArray jsonarr=JSONArray.fromObject(listTree);
		rendText(response, jsonarr.toString());
		return null;
	}
	
	public ModelAndView groupAsyncTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setContentType("text/javascript; charset=UTF-8");
		String content="";
		content="var itemsPaneMenu=[];";
		for (int i = 0; i < 10; i++) {
			content+="itemsPaneMenu["+i+"] = new Ext.Panel({"+
	                "title: 'Accordion ÏîÄ¿ "+i+"',";
	         if(i==0){
	        	 content+="items:mytree});";
	         }else{
	        	 content+="html: '&lt;empty panel&gt;'"+
		            "});";
	         }
	         	
		}
		response.getWriter().write(content);
		return null;
	}

	public EcGroupDao getEcGroupDao() {
		return ecGroupDao;
	}

	public void setEcGroupDao(EcGroupDao ecGroupDao) {
		this.ecGroupDao = ecGroupDao;
	}
	
}
