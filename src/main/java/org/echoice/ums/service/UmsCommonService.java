package org.echoice.ums.service;

import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserExtend;

public interface UmsCommonService {
	/**
	 * �����û�������Ϣ�����ڵ���
	 * @param ecUser
	 * @param userExtend
	 * @param groupId
	 */
	public void saveUserData(EcUser ecUser,EcUserExtend userExtend,Long groupId);
	/**
	 * ����ɾ���û�
	 * @param idsArr
	 */
	public void removeUsers(Object[] idsArr);
	/**
	 * �����û�������Ϣ�����ڵ���
	 * @param ecUser
	 * @param ecUserExtend
	 * @param groupIds
	 */
	public void saveUserData(EcUser ecUser, EcUserExtend ecUserExtend,String groupIds[]);
	/**
	 * 
	 */
	public void commitSyncGroup();
	/**
	 * �����û�����Ϣ
	 * @param ecGroup
	 */
	public void saveGroup(EcGroup ecGroup);
	/**
	 * �����޸��û���㼶
	 * @param dragId
	 * @param targetId
	 */
	public void saveDragGroup(Long dragId,Long targetId);
}
