package org.echoice.ums.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.modules.web.json.bean.JSONTreeNode;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.dao.EcRoleDao;
import org.echoice.ums.domain.EcRole;
import org.echoice.ums.web.view.RoleView;
import org.springframework.web.servlet.ModelAndView;

public class RoleController extends UmsBaseController{

	private EcRoleDao ecRoleDao;
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		EcRole ecRole=new EcRole();
		bindObject(request, ecRole);
		ecRole.setName(null);
		int extjsPage[]=getExtJsPage(request);
		PageBean pageBean=ecRoleDao.searchPageCondition(ecRole, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(), new String[]{"ecPermissions","ecGroupAssignments","ecUsersAssignmens"});
		return null;
	}
	/**
	 * 得到树型对象树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView tree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String parentId=request.getParameter("parentId");
		List list=ecRoleDao.findRoleTreeParent();
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : list) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		String strParentTree=bf.toString();
		List<EcRole> childList=ecRoleDao.findRoleTreeChild(Long.valueOf(parentId));
		List<JSONTreeNode> listTree=new ArrayList<JSONTreeNode>();
		for (EcRole ecRole : childList) {
			JSONTreeNode treeNode=new JSONTreeNode();
			treeNode.setId(ConfigConstants.ROLE_TREE+ecRole.getRoleId());
			treeNode.setText(ecRole.getName());
			if(strParentTree.indexOf("|"+ecRole.getRoleId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			//treeNode.setChecked(false);
			//treeNode.setDisabled(false);
			listTree.add(treeNode);
		}
		JSONArray jsonarr=JSONArray.fromObject(listTree);
		rendTextExtjs(response, jsonarr.toString());
		return null;
	}
	
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EcRole ecRole=new EcRole();
		bindObject(request, ecRole);
		ecRole.setAlias(ecRole.getAlias().trim());
		ecRole.setName(ecRole.getName().trim());
		ExtJsActionView actionView=new ExtJsActionView(); 
		actionView.setSuccess(true);
		if(ecRole.getRoleId()==null){
			List list=ecRoleDao.findByAlias(ecRole.getAlias());
			if(list!=null&&list.size()>0){
				actionView.addErrorCodeMsg("msg", "对不起，角色标识"+ecRole.getAlias()+"已经存在，请换一个");
				renderExtjsActionView(response, actionView);
				return null;
			}else{
				actionView.addDataCodeMsg("leaf", "true");
			}
		}
		ecRole.setOpTime(new Date());
		ecRoleDao.save(ecRole);
		actionView.addDataCodeMsg("id", ConfigConstants.ROLE_TREE+ecRole.getRoleId());
		actionView.addDataCodeMsg("text", ecRole.getName());
		renderExtjsActionView(response, actionView);
		return null;
	}
	
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcRole ecRole=new EcRole();
		bindObject(request, ecRole);	
		ecRole=ecRoleDao.findOne(ecRole.getRoleId());
		RoleView roleView=new RoleView();
		BeanUtils.copyProperties(roleView, ecRole);
		if(ecRole.getParentId()!=null){
			EcRole ecRoleParent=ecRoleDao.findOne(ecRole.getParentId());
			if(ecRoleParent!=null){
				roleView.setParentName(ecRoleParent.getName());
			}else{
				roleView.setParentName("角色组");
			}
		}
		renderExtjsObject(response, roleView, new String[]{"ecPermissions","ecGroupAssignments","ecUsersAssignmens"});
		return null;
	}
	
	public ModelAndView del(HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String ids=request.getParameter("ids");
		JSONArray jsonArray=JSONArray.fromObject(ids);
		Object idsArr[]=jsonArray.toArray();
		for (int i = 0; i < idsArr.length; i++) {
			Long id=new Long((Integer)idsArr[i]);
			ecRoleDao.delete(id);
		}
		return null;
	}
	
	/**
	 * 节点拖动
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView drag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String dragId=request.getParameter("dragId");
		String targetId=request.getParameter("targetId");
		if(StringUtils.isBlank(targetId)){
			targetId="-1";
		}
		getEcRoleDao().updateDrag(Long.valueOf(dragId), Long.valueOf(targetId));
		return null;
	}
	
	public EcRoleDao getEcRoleDao() {
		return ecRoleDao;
	}

	public void setEcRoleDao(EcRoleDao ecRoleDao) {
		this.ecRoleDao = ecRoleDao;
	}
}
