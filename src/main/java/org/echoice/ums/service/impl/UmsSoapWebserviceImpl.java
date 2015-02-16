package org.echoice.ums.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.echoice.modules.encrypt.MD5;
import org.echoice.ums.dao.EcPermissionDao;
import org.echoice.ums.dao.EcUserDao;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.service.UmsSoapWebservice;
import org.echoice.ums.service.bean.AccssModeSoapBean;
import org.echoice.ums.service.bean.AccssModeSoapResp;
import org.echoice.ums.service.bean.ObjectsSoapBean;
import org.echoice.ums.service.bean.ObjectsSoapResp;
import org.echoice.ums.service.bean.OperatorSoapBean;
import org.echoice.ums.service.bean.OperatorSoapResp;
public class UmsSoapWebserviceImpl implements UmsSoapWebservice{
	private EcUserDao ecUserDao;
	private EcPermissionDao ecPermissionDao;
	public boolean auth(String userAlias, String password) {
		// TODO Auto-generated method stub
		List<EcUser> list=ecUserDao.findByAlias(userAlias);
		if(list!=null&&list.size()>0){
			EcUser ecUser=list.get(0);
			String passWordDb=ecUser.getPassword();
			MD5 md5=new MD5();
			String userPassWord=md5.getMD5ofStr(userAlias+password);
			if(passWordDb.equals(userPassWord)){
				return true;
			}
		}	
		return false;
	}

	public boolean isAssignPermission(String userAlias, String objAlias,
			String accessAlias) {
		// TODO Auto-generated method stub
		boolean isAssign=ecPermissionDao.isAssignPermission(userAlias, objAlias, accessAlias);
		return isAssign;
	}

	public AccssModeSoapResp findAssignPermissionAccessModeList(
			String userAlias, String objAlias) {
		// TODO Auto-generated method stub
		AccssModeSoapResp resp=new AccssModeSoapResp();
		List list=ecPermissionDao.findAssignPermissionAccessModeList(userAlias, objAlias);
		if(list!=null&&list.size()>0){
			AccssModeSoapBean[] beanArr=new AccssModeSoapBean[list.size()];
			int i=0;
			for (Object object : list) {
				beanArr[i]=new AccssModeSoapBean();
				try {
					BeanUtils.copyProperties(beanArr[i], object);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			resp.setAccssModeSoapBeanArr(beanArr);
		}
		return resp;
	}

	public OperatorSoapResp findAssignPermissionList(String userAlias) {
		// TODO Auto-generated method stub
		OperatorSoapResp resp=new OperatorSoapResp();
		List list=ecPermissionDao.findAssignPermissionList(userAlias);
		if(list!=null&&list.size()>0){
			OperatorSoapBean[] beanArr=new OperatorSoapBean[list.size()];
			int i=0;
			for (Object object : list) {
				beanArr[i]=new OperatorSoapBean();
				try {
					BeanUtils.copyProperties(beanArr[i], object);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			resp.setOperatorSoapBeanArr(beanArr);
		}
		return resp;
	}

	public ObjectsSoapResp findAssignPermissionObjectList(String userAlias,
			String accessAlias) {
		// TODO Auto-generated method stub
		ObjectsSoapResp resp=new ObjectsSoapResp();
		List list=ecPermissionDao.findAssignPermissionObjectList(userAlias, accessAlias);
		if(list!=null&&list.size()>0){
			ObjectsSoapBean[] beanArr=new ObjectsSoapBean[list.size()];
			int i=0;
			for (Object object : list) {
				beanArr[i]=new ObjectsSoapBean();
				try {
					BeanUtils.copyProperties(beanArr[i], object);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			resp.setObjectsSoapBeanArr(beanArr);
		}
		return resp;
	}

	public ObjectsSoapResp findAssignPermissionObjectList(String userAlias,String accessAlias, String parentAlias) {
		// TODO Auto-generated method stub
		ObjectsSoapResp resp=new ObjectsSoapResp();
		List list=ecPermissionDao.findAssignPermissionObjectList(userAlias, accessAlias, parentAlias);
		if(list!=null&&list.size()>0){
			ObjectsSoapBean[] beanArr=new ObjectsSoapBean[list.size()];
			int i=0;
			for (Object object : list) {
				beanArr[i]=new ObjectsSoapBean();
				try {
					BeanUtils.copyProperties(beanArr[i], object);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			resp.setObjectsSoapBeanArr(beanArr);
		}
		return resp;
	}	
	
	public EcUserDao getEcUserDao() {
		return ecUserDao;
	}

	public void setEcUserDao(EcUserDao ecUserDao) {
		this.ecUserDao = ecUserDao;
	}

	public EcPermissionDao getEcPermissionDao() {
		return ecPermissionDao;
	}

	public void setEcPermissionDao(EcPermissionDao ecPermissionDao) {
		this.ecPermissionDao = ecPermissionDao;
	}	
}
