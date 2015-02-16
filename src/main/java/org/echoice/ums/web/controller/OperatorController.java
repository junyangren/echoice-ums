package org.echoice.ums.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.echoice.modules.web.json.ExtJsUtil;
import org.echoice.modules.web.json.bean.JSONCheckTreeNode;
import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.dao.EcObjectsDao;
import org.echoice.ums.dao.EcOperatorDao;
import org.echoice.ums.dao.EcPermissionDao;
import org.echoice.ums.domain.EcObjects;
import org.springframework.web.servlet.ModelAndView;

public class OperatorController extends UmsBaseController {
	private EcOperatorDao ecOperatorDao;
	private EcObjectsDao ecObjectsDao;
	private EcPermissionDao ecPermissionDao;
	/**
	 * 给对象分配操作
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView assignObj(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Long accessIdArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "ids");
		Long objIdsArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "objIds");
		ecOperatorDao.saveBatch(accessIdArr,objIdsArr);
		return null;
	}
	
	/**
	 * 移除操作对应的对象
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeObj(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Long accessIdArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "ids");
		Long objIdsArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "objIds");
		ecOperatorDao.removeBatch(accessIdArr,objIdsArr);
		return null;
	}
	/**
	 * 当选择一个操作后列出所有选择对象
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView operatorCheckTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String parentId=request.getParameter("parentId");
		String accessId=request.getParameter("accessId");
		List list=ecObjectsDao.findAllParent();
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : list) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		
		String strParentTree=bf.toString();
		
		StringBuffer bf2=new StringBuffer();
		if(StringUtils.isNotBlank(accessId)){
			//得到操作已经分配的对象
			List checkList=ecOperatorDao.findObjectListByAccessId(Long.valueOf(accessId));
			bf2.append("|");
			for (Object object : checkList) {
				EcObjects temp=(EcObjects)object;
				bf2.append(temp.getObjId());
				bf2.append("|");
			}
		}
		String checkTreeStr=bf2.toString();
		List<EcObjects> childList=ecObjectsDao.findChildObjects(Long.valueOf(parentId));
		List<JSONCheckTreeNode> listTree=new ArrayList<JSONCheckTreeNode>();
		for (EcObjects ecObject : childList) {
			JSONCheckTreeNode treeNode=new JSONCheckTreeNode();
			treeNode.setId(ConfigConstants.OBJECT_ASSIGN_TREE+ecObject.getObjId());
			treeNode.setText(ecObject.getName());
			if(strParentTree.indexOf("|"+ecObject.getObjId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			if(StringUtils.isNotBlank(accessId)){
				if(checkTreeStr.indexOf("|"+ecObject.getObjId()+"|")!=-1){
					treeNode.setChecked(true);
				}else{
					treeNode.setChecked(false);
				}
			}
			listTree.add(treeNode);
		}
		JSONArray jsonarr=JSONArray.fromObject(listTree);
		rendTextExtjs(response, jsonarr.toString());
		return null;
	}
	
	public ModelAndView findAccessByObjID(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String objId=request.getParameter("objId");
		List list=ecOperatorDao.findAccessListByObjId(Long.valueOf(objId));
		renderExtjsList(response, list, list.size(), new String[]{"ecOperators"});
		return null;
	}
	/**
	 * 根据对象ID+角色，取已分配的对象的操作列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView findAccessByObjIDAndRoleId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String objId=request.getParameter("objId");
		String roleId=request.getParameter("roleId");
		List list=ecPermissionDao.findAccessModeList(Long.valueOf(roleId),Long.valueOf(objId));
		renderExtjsList(response, list, list.size(), new String[]{"ecOperators"});
		return null;
	}
	
	public EcOperatorDao getEcOperatorDao() {
		return ecOperatorDao;
	}
	public void setEcOperatorDao(EcOperatorDao ecOperatorDao) {
		this.ecOperatorDao = ecOperatorDao;
	}

	public EcObjectsDao getEcObjectsDao() {
		return ecObjectsDao;
	}

	public void setEcObjectsDao(EcObjectsDao ecObjectsDao) {
		this.ecObjectsDao = ecObjectsDao;
	}

	public EcPermissionDao getEcPermissionDao() {
		return ecPermissionDao;
	}

	public void setEcPermissionDao(EcPermissionDao ecPermissionDao) {
		this.ecPermissionDao = ecPermissionDao;
	}
	
}
