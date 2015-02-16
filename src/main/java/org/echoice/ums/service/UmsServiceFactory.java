package org.echoice.ums.service;

import org.echoice.modules.spring.SpringContextHolder;
import org.echoice.ums.dao.AppPluginDao;
import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.dao.EcObjectsDao;
import org.echoice.ums.dao.EcRoleDao;
import org.echoice.ums.dao.EcUserDao;
import org.quartz.Scheduler;

public class UmsServiceFactory {
	public static ValidPermissionForUmsService getValidPermissionForUmsService(){
		return (ValidPermissionForUmsService)SpringContextHolder.getBean("validPermissionForUmsService");
	}
	
	public static EcObjectsDao getEcObjectsDao(){
		return (EcObjectsDao)SpringContextHolder.getBean("ecObjectsDao");
	}
	
	public static EcUserDao getEcUserDao(){
		return (EcUserDao)SpringContextHolder.getBean("ecUserDao");
	}
	
	public static EcRoleDao getEcRoleDao(){
		return (EcRoleDao)SpringContextHolder.getBean("ecRoleDao");
	}
	
	
	public static AppPluginDao getAppPluginDao(){
		return (AppPluginDao)SpringContextHolder.getBean("appPluginDao");
	}
	
	public static EcGroupDao getEcGroupDao(){
		return (EcGroupDao)SpringContextHolder.getBean("ecGroupDao");
	}
	
	public static Scheduler getQuartzScheduler(){
		return (Scheduler)SpringContextHolder.getBean("quartzScheduler");
	}
	
	public static UmsCommonService getUmsCommonService(){
		return (UmsCommonService)SpringContextHolder.getBean("umsCommonService");
	}
}
