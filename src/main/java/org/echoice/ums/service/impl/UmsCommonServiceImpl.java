package org.echoice.ums.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.config.LoginAuthBean;
import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.dao.EcObjectsDao;
import org.echoice.ums.dao.EcUserDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserExtend;
import org.echoice.ums.domain.EcUserGroup;
import org.echoice.ums.service.UmsCommonService;
import org.echoice.ums.util.Pinying4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class UmsCommonServiceImpl implements UmsCommonService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private EcUserDao ecUserDao;
	@Autowired
	private EcGroupDao ecGroupDao;
	@Autowired
	private EcObjectsDao ecObjectsDao;
	@Autowired
	private LoginAuthBean loginAuthBean;
	public void saveUserData(EcUser ecUser, EcUserExtend ecUserExtend,Long groupId) {
		// TODO Auto-generated method stub
		ecUserDao.save(ecUser,ecUserExtend);
		EcUserGroup ecUserGroup=ecUserDao.getUserGroup(Long.valueOf(groupId), ecUser.getUserId());
		if(ecUserGroup==null){
			ecUserGroup=new EcUserGroup();
			EcGroup ecGroup=new EcGroup();
			ecGroup.setGroupId(Long.valueOf(groupId));
				
			ecUserGroup.setEcGroup(ecGroup);
			ecUserGroup.setEcUser(ecUser);
			
			ecUserDao.saveUserGroup(ecUserGroup);
		}
	}
	
	public void saveUserData(EcUser ecUser, EcUserExtend ecUserExtend,String groupIds[]) {
		// TODO Auto-generated method stub
		ecUserDao.save(ecUser,ecUserExtend);
		Long id=null;
		for (int i = 0; i < groupIds.length; i++) {
			id=Long.valueOf(groupIds[i]);
			EcUserGroup ecUserGroup=ecUserDao.getUserGroup(id, ecUser.getUserId());
			if(ecUserGroup==null){
				ecUserGroup=new EcUserGroup();
				EcGroup ecGroup=new EcGroup();
				ecGroup.setGroupId(Long.valueOf(id));
					
				ecUserGroup.setEcGroup(ecGroup);
				ecUserGroup.setEcUser(ecUser);
				
				ecUserDao.saveUserGroup(ecUserGroup);
			}
		}
	}	
	public void removeUsers(Object[] idsArr) {
		// TODO Auto-generated method stub
		for (int i = 0; i < idsArr.length; i++) {
			Long id=new Long((Integer)idsArr[i]);
			ecUserDao.delete(id);
		}
	}
	
	public void saveGroup(EcGroup ecGroup){
		ecGroup.setOpTime(new Date());
		ecGroupDao.save(ecGroup);
		//���±�ʶ��ȡ���ڵ�
		EcGroup parentGroup= ecGroupDao.findOne(ecGroup.getParentId());
		String alias=ecGroup.getAlias();
		String groupPath=null;
		String fullName=null;
		if(loginAuthBean.getGroupAliasCreate()==1){
			if(StringUtils.isBlank(ecGroup.getAlias())){
				alias="G-"+alias;//�̶���ʶ+ID
			}
		}else if(loginAuthBean.getGroupAliasCreate()==2){
			if(parentGroup!=null){
				alias=parentGroup.getAlias()+"-"+ecGroup.getGroupId();//��ʶȫ��
				
			}else{
				alias=ecGroup.getGroupId().toString();
				
			}
		}else if(loginAuthBean.getGroupAliasCreate()==3){
			if(StringUtils.isBlank(ecGroup.getAlias())){
				alias=Pinying4jUtil.cn2FirstSpell(ecGroup.getName())+"-"+ecGroup.getGroupId();//ƴ����ʶ+ID
			}
		}
		if(parentGroup!=null){
			groupPath=parentGroup.getGroupPath()+"-"+ecGroup.getGroupId();
			fullName=parentGroup.getFullName()+"/"+ecGroup.getName();
		}else{
			groupPath=ecGroup.getGroupId().toString();
			fullName=ecGroup.getName();
		}
		
		ecGroup.setGroupPath(groupPath);
		ecGroup.setFullName(fullName);
		ecGroup.setAlias(alias);
		ecGroupDao.save(ecGroup);
	}
	
	public void saveDragGroup(Long dragId,Long targetId){
		getEcGroupDao().updateDrag(Long.valueOf(dragId), Long.valueOf(targetId));
	}
	
	public void commitSyncGroup(){
		logger.info("�û���ȫ�ơ�·��ͬ����ʼ����");
		EcObjects objects= ecObjectsDao.getObjectsByAlias(ConfigConstants.UMS_GROUP_ROOT_ID);
		String groupId="-1";
		if(objects!=null){
			groupId=objects.getNote();
		}
		try{
			int result=ecGroupDao.updateGroupFullNameByProc(Integer.valueOf(groupId));
			if(result!=0){
				logger.error("����ʧ��,�����룺"+result);
			}else{
				logger.info("�����ɹ�����");
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("����ʧ�ܣ�",e);
		}
		
		logger.info("�û���ȫ�ơ�·��ͬ����������");
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public EcObjectsDao getEcObjectsDao() {
		return ecObjectsDao;
	}

	public void setEcObjectsDao(EcObjectsDao ecObjectsDao) {
		this.ecObjectsDao = ecObjectsDao;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public EcUserDao getEcUserDao() {
		return ecUserDao;
	}
	public void setEcUserDao(EcUserDao ecUserDao) {
		this.ecUserDao = ecUserDao;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public EcGroupDao getEcGroupDao() {
		return ecGroupDao;
	}
	public void setEcGroupDao(EcGroupDao ecGroupDao) {
		this.ecGroupDao = ecGroupDao;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public LoginAuthBean getLoginAuthBean() {
		return loginAuthBean;
	}

	public void setLoginAuthBean(LoginAuthBean loginAuthBean) {
		this.loginAuthBean = loginAuthBean;
	}	
}
