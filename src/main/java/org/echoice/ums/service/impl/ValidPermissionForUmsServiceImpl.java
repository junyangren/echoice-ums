package org.echoice.ums.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.config.LoginAuthBean;
import org.echoice.ums.dao.EcPermissionDao;
import org.echoice.ums.dao.EcUserDao;
import org.echoice.ums.dao.EcUsersAssignmenDao;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.service.ValidPermissionForUmsService;
import org.echoice.ums.util.CasUmsUtil;
import org.echoice.ums.web.view.UmsAccordionMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class ValidPermissionForUmsServiceImpl implements ValidPermissionForUmsService {
	private LoginAuthBean loginAuthBean; 
	private EcPermissionDao ecPermissionDao;
	private EcUsersAssignmenDao ecUsersAssignmenDao;	
	private EcUserDao ecUserDao;
	public void setUserPermission(HttpServletRequest request){
		String userAlias=CasUmsUtil.getUser(request);
		//得到ums系统菜单列表start
		List<EcObjects> topAccordionMenu=ecPermissionDao.findAssignPermissionObjectList(userAlias, loginAuthBean.getAuthAccessMode(),loginAuthBean.getAuthObject());
		List<UmsAccordionMenu> parentMenuList=new ArrayList<UmsAccordionMenu>();
		if(topAccordionMenu!=null&&topAccordionMenu.size()>0){
			Long level3ParenIdArr[]=new Long[topAccordionMenu.size()];
			int i=0;
			UmsAccordionMenu umsAccordionMenu=null;
			for (EcObjects ecObjects : topAccordionMenu) {
				umsAccordionMenu=new UmsAccordionMenu();
				umsAccordionMenu.setParentMenuObj(ecObjects);
				parentMenuList.add(umsAccordionMenu);
				level3ParenIdArr[i]=ecObjects.getObjId();
				i++;
			}
			List<EcObjects> level3MenuList=ecPermissionDao.findAssignPermissionObjectList(userAlias, loginAuthBean.getAuthAccessMode(), level3ParenIdArr);
			if(level3MenuList!=null&&level3MenuList.size()>0){
				Long parentId=null;
				Long parentIdTmp=null;
				for (EcObjects level3Objects : level3MenuList) {
					parentId=level3Objects.getParentId();
					for (UmsAccordionMenu umsAccordionMenu2 : parentMenuList) {
						parentIdTmp=umsAccordionMenu2.getParentMenuObj().getObjId();
						if(parentId.intValue()==parentIdTmp.intValue()){
							umsAccordionMenu2.addSumbMenuObj(level3Objects);
							break;
						}
					}
				}
			}
		}
		request.getSession().setAttribute("accordion", parentMenuList);
		//得到ums系统菜单列表end
		
		//判断用户是否为超级管理员
		boolean isAdmin=ecUsersAssignmenDao.checkIsAssignByAlias(userAlias, loginAuthBean.getAuthSysMgrRole());
		if(isAdmin){
			request.getSession().setAttribute(ConfigConstants.IS_SUPER_ADMIN, "YES");
		}else{
			//取用户所在的组
			/**
			List<EcGroup> userGroupList=ecUserDao.findGroupByUserAlias(userAlias);
			if(userGroupList!=null&&userGroupList.size()>0){
				request.getSession().setAttribute(ConfigConstants.UMS_GROUP_SESSION, userGroupList.get(0));
			}**/
		}

	}
	
	public LoginAuthBean getLoginAuthBean() {
		return loginAuthBean;
	}
	public void setLoginAuthBean(LoginAuthBean loginAuthBean) {
		this.loginAuthBean = loginAuthBean;
	}
	public EcPermissionDao getEcPermissionDao() {
		return ecPermissionDao;
	}
	public void setEcPermissionDao(EcPermissionDao ecPermissionDao) {
		this.ecPermissionDao = ecPermissionDao;
	}

	public EcUsersAssignmenDao getEcUsersAssignmenDao() {
		return ecUsersAssignmenDao;
	}

	public void setEcUsersAssignmenDao(EcUsersAssignmenDao ecUsersAssignmenDao) {
		this.ecUsersAssignmenDao = ecUsersAssignmenDao;
	}

	public EcUserDao getEcUserDao() {
		return ecUserDao;
	}

	public void setEcUserDao(EcUserDao ecUserDao) {
		this.ecUserDao = ecUserDao;
	}
	
}
