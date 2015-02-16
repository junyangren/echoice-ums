package org.echoice.ums.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.echoice.modules.cas.CasUtil;
import org.echoice.modules.encrypt.MD5;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.ums.config.LoginAuthBean;
import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.dao.EcUserDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.service.ValidPermissionForUmsService;
import org.echoice.ums.util.CasUmsUtil;
import org.springframework.web.servlet.ModelAndView;

public class LoginController extends UmsBaseController{
	private LoginAuthBean loginAuthBean; 
	private EcUserDao ecUserDao;
	private EcGroupDao ecGroupDao;
	private ValidPermissionForUmsService validPermissionForUmsService; 
	
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userAlias=request.getParameter("alias");
		String password=request.getParameter("password");
		String authPassword=request.getParameter("authPassword");
		//获取随机验证码
		String radomStrID=(String)request.getSession().getAttribute(CasUtil.VALIDATECODE_SESSION_NAME);
		String msg="";
		ExtJsActionView actionView=new ExtJsActionView(); 
		actionView.setSuccess(true);
		EcUser ecUser=new EcUser();
		ecUser.setAlias(userAlias);
		if(loginAuthBean.isAuth()){
			//判断用户提交的验证码是否正确
			if(radomStrID.equalsIgnoreCase(authPassword)){
				//获取登录用户的信息
				List<EcUser> list=ecUserDao.findByAlias(userAlias);
				if(list!=null&&list.size()>0){
					ecUser=list.get(0);
					String passWordDb=ecUser.getPassword();
					MD5 md5=new MD5();
					String userPassWord=md5.getMD5ofStr(userAlias+password);
					//用户密码校验
					if(passWordDb.equals(userPassWord)){
						request.getSession().setAttribute(CasUtil.CONST_CAS_ASSERTION, ecUser.getAlias());
						getValidPermissionForUmsService().setUserPermission(request);
					}else{
						logger.info(userAlias+"：用户密码错误");
						msg="对不起，用户密码错误";
					}
				}else{
					logger.info(userAlias+"：用户名错误");
					msg="对不起，用户名错误";
				}			
			}else{
				msg="对不起，验证码错误";
			}
		}
		actionView.addErrorCodeMsg("msg", msg);
		renderExtjsActionView(response, actionView);
		return null;
	}
	
	public ModelAndView selGroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String groupId=request.getParameter("groupId");
		EcGroup group=ecGroupDao.findOne(Long.valueOf(groupId));
		CasUmsUtil.setUserGroup(request, group);
		return new ModelAndView("redirect:/index.jsp");
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
	public ValidPermissionForUmsService getValidPermissionForUmsService() {
		return validPermissionForUmsService;
	}
	public void setValidPermissionForUmsService(
			ValidPermissionForUmsService validPermissionForUmsService) {
		this.validPermissionForUmsService = validPermissionForUmsService;
	}

	public EcGroupDao getEcGroupDao() {
		return ecGroupDao;
	}

	public void setEcGroupDao(EcGroupDao ecGroupDao) {
		this.ecGroupDao = ecGroupDao;
	}
}
