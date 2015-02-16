package org.echoice.ums.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.echoice.modules.web.json.ExtJsUtil;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.dao.EcPermissionDao;
import org.echoice.ums.web.view.GroupPermissionView;
import org.echoice.ums.web.view.PermissionView;
import org.echoice.ums.web.view.UserPermissionView;
import org.springframework.web.servlet.ModelAndView;

public class PermissionController extends UmsBaseController {
	
	private EcPermissionDao ecPermissionDao;

	/**
	 * �����ɫ�еĶ���+����Ȩ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView assignPermission(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Long[] idsRoleLongArr=ExtJsUtil.transJsonIDArrayToLong(request, "idsRole");
		Long[] objIdsLongArr=ExtJsUtil.transJsonIDArrayToLong(request, "objIds");
		Long[] idsAccessLongArr=ExtJsUtil.transJsonIDArrayToLong(request, "idsAccess");
		ecPermissionDao.savePermission(idsRoleLongArr, objIdsLongArr[0], idsAccessLongArr);
		return null;
	}
	/**
	 * �Ƴ���ɫ�еĶ���+����Ȩ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removePermission(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Long[] idsRoleLongArr=ExtJsUtil.transJsonIDArrayToLong(request, "idsRole");
		Long[] objIdsLongArr=ExtJsUtil.transJsonIDArrayToLong(request, "objIds");
		Long[] idsAccessLongArr=ExtJsUtil.transJsonIDArrayToLong(request, "idsAccess");
		ecPermissionDao.removePermission(idsRoleLongArr, objIdsLongArr[0], idsAccessLongArr);
		return null;
	}
	
	/**
	 * �鿴��ɫӵ����Щ�������Ȩ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showRolePermission(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int extjsPage[]=getExtJsPage(request);
		//String roleId=request.getParameter("roleId");
		//String objName=request.getParameter("objName");
		//String accessName=request.getParameter("accessName");
		PermissionView permissionView=new PermissionView();
		bindObject(request, permissionView);
		
		PageBean pageBean=ecPermissionDao.findRolePermissionPage(permissionView, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(),null);
		return null;
	}
	/**
	 * �鿴��ɫ��������Щ�û�
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showRoleAssignUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int extjsPage[]=getExtJsPage(request);
		String roleId=request.getParameter("roleId");
		String alias=request.getParameter("alias");
		String name=request.getParameter("name");
		PageBean pageBean=ecPermissionDao.findRoleAssingUserPage(Long.valueOf(roleId),alias,name, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(),null);
		return null;
	}
	/**
	 * ���û�����Ȩ��ɫ���Ƴ�
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeRoleAssignUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Long[] uaIds=ExtJsUtil.transJsonIDArrayToLong(request, "ids");
		ecPermissionDao.removeRoleAssignUserByUaIdsArr(uaIds);
		return null;
	}
	/**
	 * �鿴�û������Ȩ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showUserPermission(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int extjsPage[]=getExtJsPage(request);
		//String userId=request.getParameter("userId");
		UserPermissionView permissionView=new UserPermissionView();
		bindObject(request, permissionView);
		PageBean pageBean=ecPermissionDao.findUserPermissionPage(permissionView, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(),null);
		return null;
	}
	/**
	 * ���ݶ���ID+����ID���鿴Ȩ�޷���Ľ�ɫ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showPermissionRoleList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String objId=request.getParameter("objId");
		Long[] idsAccessLongArr=ExtJsUtil.transJsonIDArrayToLong(request, "idsAccess");
		List list=ecPermissionDao.showPermissionRoleList(Long.valueOf(objId),idsAccessLongArr);
		renderExtjsList(response, list, list.size(),null);
		return null;
	}
	/**
	 * �鿴�û���ӵ�õ�Ȩ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showGroupPermission(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int extjsPage[]=getExtJsPage(request);
		String groupId=request.getParameter("groupId");
		GroupPermissionView permissionView=new GroupPermissionView();
		bindObject(request, permissionView);
		
		PageBean pageBean=ecPermissionDao.findGroupPermissionPage(permissionView, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(),null);
		return null;
	}
	/**
	 * ����PaId,��Ȩ�޴ӽ�ɫ���Ƴ�
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removePermissionByPaId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Long[] paIds=ExtJsUtil.transJsonIDArrayToLong(request, "ids");
		ecPermissionDao.removePermissionByPaIdsArr(paIds);
		return null;
	}
	
	public EcPermissionDao getEcPermissionDao() {
		return ecPermissionDao;
	}

	public void setEcPermissionDao(EcPermissionDao ecPermissionDao) {
		this.ecPermissionDao = ecPermissionDao;
	}
	
}
