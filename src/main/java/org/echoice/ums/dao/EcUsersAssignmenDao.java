package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcRole;
import org.echoice.ums.domain.EcUsersAssignmen;
import org.echoice.ums.web.view.UserGroupTotalView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcUsersAssignmenDao extends JpaRepository<EcUsersAssignmen,Long> {
	/**
	 * ���������û���ɫ
	 * @param accssMode
	 * @param objects
	 */
	public void saveBatch(Long[] userIds, Long[] roleIds);
	/**
	 * ���������û���
	 * @param userIds
	 * @param groupIds
	 */
	public void saveAssignGroups(Long[] userIds, Long[] groupIds);
	/**
	 * ����ɾ���û���ɫ
	 * @param accssModeIds
	 * @param objectsIds
	 */
	public void removeBatch(Long[] userIds, Long[] roleIds);
	
	/**
	 * ɾ���û���ɫ
	 * @param accessId
	 * @param objId
	 */
	public void remove(Long userId,Long roleId);
	/**
	 * �ж��û��Ƿ�����˽�ɫ
	 * @param accessId
	 * @param objId
	 * @return
	 */
	public boolean checkIsAssign(Long userId,Long roleId);
	/**
	 * �����û������ɫ�������ж��û��Ƿ����˽�ɫ
	 * @param userAlias
	 * @param roleAlias
	 * @return
	 */
	public boolean checkIsAssignByAlias(String userAlias, String roleAlias);
	/**
	 * �����û��ѷ����ɫ
	 * @param userId
	 * @return
	 */
	public List findAssignRoleList(Long userId);
	/**
	 * �����û��ѷ����ɫ
	 * @param userAlias
	 * @return
	 */
	public List findAssignRoleList(String userAlias);
	/**
	 * �����û���ĳ��ɫ���ڵ����������һ���ӽ�ɫ��
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public List<EcRole> findAssignRoleList(Long userId,Long roleId);
	/**
	 * ���û��������Ƴ�
	 * @param userIds
	 * @param groupIds
	 */
	public void removeAssingGroup(Long[] userIds, Long[] groupIds);
	/**
	 * �����û�id��ȡ�û�ͳ������
	 * @param userIds
	 * @return
	 */
	public List<UserGroupTotalView> findUserGroupCount(Long[] userIds);
	/**
	 * �����û�id,���û���id��ȡ�û�ͳ������
	 * @param userIds
	 * @param groupIds
	 * @return
	 */
	public List<UserGroupTotalView> findUserGroupCount(Long[] userIds,Long[] groupIds);
}
