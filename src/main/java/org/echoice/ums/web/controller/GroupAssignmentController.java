package org.echoice.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.echoice.modules.web.json.ExtJsUtil;
import org.echoice.ums.dao.EcGroupAssignmentDao;
import org.echoice.ums.util.CasUmsUtil;
import org.springframework.web.servlet.ModelAndView;

public class GroupAssignmentController extends UmsBaseController {
	private EcGroupAssignmentDao ecGroupAssignmentDao;

	public ModelAndView assignRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("组分配角色start:");
		Long groupIdArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "groupIds");
		Long roleIdsArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "roleIds");
		ecGroupAssignmentDao.saveBatch(groupIdArr,roleIdsArr);
		logger.info(CasUmsUtil.getUser(request)+"操作分配角色  groupIdArr:【"+StringUtils.join(groupIdArr,",")+"】"+"roleArr:【"+StringUtils.join(roleIdsArr,",")+"】");
		logger.debug("组分配角色end:");
		return null;
	}

	public ModelAndView removeRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("组移除角色start:");
		Long groupIdArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "groupIds");
		Long roleIdsArr[]=ExtJsUtil.transJsonIDArrayToLong(request, "roleIds");
		ecGroupAssignmentDao.removeBatch(groupIdArr,roleIdsArr);
		logger.info(CasUmsUtil.getUser(request)+"操作移除角色  groupIdArr:【"+StringUtils.join(groupIdArr,",")+"】"+"roleArr:【"+StringUtils.join(roleIdsArr,",")+"】");
		logger.debug("组移除角色end:");
		return null;
	}
	
	public EcGroupAssignmentDao getEcGroupAssignmentDao() {
		return ecGroupAssignmentDao;
	}

	public void setEcGroupAssignmentDao(EcGroupAssignmentDao ecGroupAssignmentDao) {
		this.ecGroupAssignmentDao = ecGroupAssignmentDao;
	}

}
