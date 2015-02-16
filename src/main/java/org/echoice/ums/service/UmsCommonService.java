package org.echoice.ums.service;

import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserExtend;

public interface UmsCommonService {
	/**
	 * 保存用户基本信息及所在的组
	 * @param ecUser
	 * @param userExtend
	 * @param groupId
	 */
	public void saveUserData(EcUser ecUser,EcUserExtend userExtend,Long groupId);
	/**
	 * 批量删除用户
	 * @param idsArr
	 */
	public void removeUsers(Object[] idsArr);
	/**
	 * 保存用户基本信息及所在的组
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
	 * 保存用户组信息
	 * @param ecGroup
	 */
	public void saveGroup(EcGroup ecGroup);
	/**
	 * 保存修改用户组层级
	 * @param dragId
	 * @param targetId
	 */
	public void saveDragGroup(Long dragId,Long targetId);
}
