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
import org.echoice.ums.config.LoginAuthBean;
import org.echoice.ums.dao.EcGroupAssignmentDao;
import org.echoice.ums.dao.EcRoleDao;
import org.echoice.ums.dao.EcUserDao;
import org.echoice.ums.dao.EcUsersAssignmenDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcRole;
import org.echoice.ums.plugins.OAUserAassingRoleFilterCmd;
import org.echoice.ums.util.CasUmsUtil;
import org.echoice.ums.web.view.UserGroupTotalView;
import org.springframework.web.servlet.ModelAndView;

public class UsersAssignmenController extends UmsBaseController {
	private EcRoleDao ecRoleDao; 
	private EcUsersAssignmenDao ecUsersAssignmenDao;
	private EcGroupAssignmentDao ecGroupAssignmentDao;
	private EcUserDao ecUserDao;
	private LoginAuthBean loginAuthBean;
	/**
	 * ���û������ɫ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView assignRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("�û������ɫstart:");
		Long userIdArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "ids");
		Long roleIdsArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "objIds");
		boolean isAdmin=CasUmsUtil.isAdmin(request);
		boolean isPass=true;
		if(!isAdmin){
			//TODO ���ݰ���ҵ�����������ݹ���,
			OAUserAassingRoleFilterCmd filterCmd=new OAUserAassingRoleFilterCmd(userIdArr,roleIdsArr);
			isPass=filterCmd.execute(null);
			if(!isPass){
				rendText(response, "{msg:\""+filterCmd.getMsg()+"\",failure:true}");
				return null;
			}
		}
		ecUsersAssignmenDao.saveBatch(userIdArr,roleIdsArr);
		logger.info(CasUmsUtil.getUser(request)+"���������ɫ  userIdArr:��"+StringUtils.join(userIdArr,",")+"��"+"roleArr:��"+StringUtils.join(roleIdsArr,",")+"��");
		return null;
	}
	/**
	 * �û�������
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView assignGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("�û������start:");
		Long userIds[]=ExtJsUtil.transJsonIDArrayToLong(request, "ids");
		Long groupIds[]=ExtJsUtil.transJsonIDArrayToLong(request, "objIds");
		ecUsersAssignmenDao.saveAssignGroups(userIds,groupIds);
		logger.info(CasUmsUtil.getUser(request)+"�����û������  userIdArr:��"+StringUtils.join(userIds,",")+"��"+"groupArr:��"+StringUtils.join(groupIds,",")+"��");
		logger.debug("�û������end:");
		return null;
	}
	
	/**
	 * ����ɫ���û����Ƴ�
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("�û��Ƴ���ɫstart:");
		Long userIds[]=ExtJsUtil.transJsonIDArrayToLong(request, "ids");
		Long roleIds[]=ExtJsUtil.transJsonIDArrayToLong(request, "objIds");
		ecUsersAssignmenDao.removeBatch(userIds,roleIds);
		logger.info(CasUmsUtil.getUser(request)+"�����Ƴ���ɫ  userIdArr:��"+StringUtils.join(userIds,",")+"��"+"roleArr:��"+StringUtils.join(roleIds,",")+"��");
		logger.debug("�û��Ƴ���ɫend:");
		return null;
	}
	
	public ModelAndView removeAssignGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("�û�����start:");
		Long userIds[]=ExtJsUtil.transJsonIDArrayToLong(request, "ids");
		
		Long groupIds[]=ExtJsUtil.transJsonIDArrayToLong(request, "objIds");
		List<UserGroupTotalView> list= ecUsersAssignmenDao.findUserGroupCount(userIds);
		List<UserGroupTotalView> list2= ecUsersAssignmenDao.findUserGroupCount(userIds,groupIds);
		//�ж��û��Ƴ������ٱ���һ����
		long tmpId1;
		long tmpId2;
		boolean isPass=true;
		for (UserGroupTotalView userGroupTotalView : list) {
			tmpId1=userGroupTotalView.getUserId();
			for (UserGroupTotalView userGroupTotalView2 : list2) {
				tmpId2=userGroupTotalView2.getUserId();
				if(tmpId1==tmpId2){
					if(userGroupTotalView.getGroupCount()==userGroupTotalView2.getGroupCount()){
						isPass=false;
						break;
					}
				}
			}
			if(!isPass){
				break;
			}
		}
		
		if(!isPass){
			rendText(response, "{msg:\"�û��������Ƴ�ʧ�ܣ��û���������ӵ��һ�������飡��\",failure:true}");
			return null;
		}
		
		ecUsersAssignmenDao.removeAssingGroup(userIds,groupIds);
		logger.info(CasUmsUtil.getUser(request)+"�����û�����  userIdArr:��"+StringUtils.join(userIds,",")+"��"+"groupArr:��"+StringUtils.join(groupIds,",")+"��");		
		logger.debug("�û�����end:");
		return null;
	}
	
	/**
	 * ��ֻѡ��һ���û�ʱ�������г��û��ѷ���Ľ�ɫ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView roleCheckTree(HttpServletRequest request,HttpServletResponse response) throws Exception {
		boolean isAdmin=CasUmsUtil.isAdmin(request);
		// TODO Auto-generated method stub
		String parentId=request.getParameter("parentId");
		String userId=request.getParameter("userId");
		String groupId=request.getParameter("groupId");
		List list=ecRoleDao.findRoleTreeParent();
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : list) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		
		String strParentTree=bf.toString();
		
		StringBuffer bf2=new StringBuffer();
		if(StringUtils.isNotBlank(userId)){
			//�õ��û��Ѿ������ɫ
			List checkList=ecUsersAssignmenDao.findAssignRoleList(Long.valueOf(userId));
			bf2.append("|");
			for (Object object : checkList) {
				EcRole temp=(EcRole)object;
				bf2.append(temp.getRoleId());
				bf2.append("|");
			}
		}
		
		if(StringUtils.isNotBlank(groupId)){
			//�õ��û����Ѿ������ɫ
			List checkList=ecGroupAssignmentDao.findAssignRoleList(Long.valueOf(groupId));
			bf2.append("|");
			for (Object object : checkList) {
				EcRole temp=(EcRole)object;
				bf2.append(temp.getRoleId());
				bf2.append("|");
			}
		}
		String checkTreeStr=bf2.toString();
		List<JSONCheckTreeNode> listTree=null;
		
		List<EcRole> childList=ecRoleDao.findRoleTreeChild(Long.valueOf(parentId));
		boolean levelRoleShow=loginAuthBean.isLevelRoleShow();
		if(levelRoleShow){
			listTree=checkRoleByLevel(childList,strParentTree,checkTreeStr,userId,groupId,isAdmin,CasUmsUtil.getUser(request),CasUmsUtil.getUserGroup(request));
		}else{
			listTree=checkRoleByUser(childList,strParentTree,checkTreeStr,userId,groupId,isAdmin,CasUmsUtil.getUser(request));
		}
		JSONArray jsonarr=JSONArray.fromObject(listTree);
		rendTextExtjs(response, jsonarr.toString());
		return null;
	}
	
	private List<JSONCheckTreeNode> checkRoleByUser(List<EcRole> childList,String strParentTree,String checkTreeStr,String userId,String groupId,boolean isAdmin,String currUser){
		String userTreeStr=null;
		List<JSONCheckTreeNode> listTree=new ArrayList<JSONCheckTreeNode>();
		if(!isAdmin){
			StringBuffer bf3=new StringBuffer();
			//�õ��û��Ѿ������ɫTreeStringList
			List checkList=ecUsersAssignmenDao.findAssignRoleList(currUser);
			bf3.append("|");
			for (Object object : checkList) {
				EcRole temp=(EcRole)object;
				bf3.append(temp.getRoleId());
				bf3.append("|");
			}
			userTreeStr=bf3.toString();
		}
		
		for (EcRole ecRole : childList) {
			JSONCheckTreeNode treeNode=new JSONCheckTreeNode();
			treeNode.setId(ConfigConstants.ROLE_ASSIGN_TREE+ecRole.getRoleId());
			treeNode.setText(ecRole.getName());
			if(strParentTree.indexOf("|"+ecRole.getRoleId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			//��ѡ������û�ʱ���Է���Ľ�ɫѡ��
			if(StringUtils.isNotBlank(userId)){
				if(checkTreeStr.indexOf("|"+ecRole.getRoleId()+"|")!=-1){
					treeNode.setChecked(true);
				}else{
					treeNode.setChecked(false);
				}	
			}
			if(StringUtils.isNotBlank(groupId)){
				if(checkTreeStr.indexOf("|"+ecRole.getRoleId()+"|")!=-1){
					treeNode.setChecked(true);
				}else{
					treeNode.setChecked(false);
				}	
			}
			
			if(!isAdmin){
				if(userTreeStr!=null&&userTreeStr.indexOf("|"+ecRole.getRoleId()+"|")!=-1){
					treeNode.setDisabled(false);
				}else{
					treeNode.setDisabled(true);
				}
			}
			if(!treeNode.isLeaf()||!treeNode.isDisabled()){
				listTree.add(treeNode);
			}
		}
		return listTree;
	}
		
	
	private List<JSONCheckTreeNode> checkRoleByLevel(List<EcRole> childList,String strParentTree,String checkTreeStr,String userId,String groupId,boolean isAdmin,String currUser,EcGroup currGroup){
		List<JSONCheckTreeNode> listTree=new ArrayList<JSONCheckTreeNode>();
		String levelStr=null;
		if(!isAdmin){
			//�õ��û��㼶
			if(currGroup!=null){
				String tmp=currGroup.getAlias();
				String tmpArr[]=StringUtils.splitByWholeSeparator(tmp, "-");
				levelStr=String.valueOf(tmpArr.length);
			}else{
				List<EcGroup> userGroupList=ecUserDao.findGroupByUserAlias(currUser);
				 if(userGroupList!=null&&userGroupList.size()>0){
					 String tmp=userGroupList.get(0).getAlias();
					 String tmpArr[]=StringUtils.splitByWholeSeparator(tmp, "-");
					 levelStr=String.valueOf(tmpArr.length);
				 }
			}
		}
		for (EcRole ecRole : childList) {
			JSONCheckTreeNode treeNode=new JSONCheckTreeNode();
			treeNode.setId(ConfigConstants.ROLE_ASSIGN_TREE+ecRole.getRoleId());
			treeNode.setText(ecRole.getName());
			if(strParentTree.indexOf("|"+ecRole.getRoleId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			//��ѡ������û�ʱ���Է���Ľ�ɫѡ��
			if(StringUtils.isNotBlank(userId)){
				if(checkTreeStr.indexOf("|"+ecRole.getRoleId()+"|")!=-1){
					treeNode.setChecked(true);
				}else{
					treeNode.setChecked(false);
				}	
			}
			
			if(StringUtils.isNotBlank(groupId)){
				if(checkTreeStr.indexOf("|"+ecRole.getRoleId()+"|")!=-1){
					treeNode.setChecked(true);
				}else{
					treeNode.setChecked(false);
				}	
			}
			
			if(!isAdmin){
				treeNode.setDisabled(true);
				if(levelStr!=null){
					if(StringUtils.isNotBlank(ecRole.getNote())){
						if(StringUtils.indexOf(ecRole.getNote(), levelStr)!=-1){
							treeNode.setDisabled(false);
						}
					}
				}
			}
			if(!treeNode.isLeaf()||!treeNode.isDisabled()){
				listTree.add(treeNode);
			}
		}
		return listTree;
	}
	
	public EcUsersAssignmenDao getEcUsersAssignmenDao() {
		return ecUsersAssignmenDao;
	}

	public void setEcUsersAssignmenDao(EcUsersAssignmenDao ecUsersAssignmenDao) {
		this.ecUsersAssignmenDao = ecUsersAssignmenDao;
	}

	public EcRoleDao getEcRoleDao() {
		return ecRoleDao;
	}

	public void setEcRoleDao(EcRoleDao ecRoleDao) {
		this.ecRoleDao = ecRoleDao;
	}
	public EcGroupAssignmentDao getEcGroupAssignmentDao() {
		return ecGroupAssignmentDao;
	}
	public void setEcGroupAssignmentDao(EcGroupAssignmentDao ecGroupAssignmentDao) {
		this.ecGroupAssignmentDao = ecGroupAssignmentDao;
	}
	public EcUserDao getEcUserDao() {
		return ecUserDao;
	}
	public void setEcUserDao(EcUserDao ecUserDao) {
		this.ecUserDao = ecUserDao;
	}
	public LoginAuthBean getLoginAuthBean() {
		return loginAuthBean;
	}
	public void setLoginAuthBean(LoginAuthBean loginAuthBean) {
		this.loginAuthBean = loginAuthBean;
	}

	
	
}
