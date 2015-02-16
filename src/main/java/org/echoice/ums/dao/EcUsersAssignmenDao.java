package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcRole;
import org.echoice.ums.domain.EcUsersAssignmen;
import org.echoice.ums.web.view.UserGroupTotalView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcUsersAssignmenDao extends JpaRepository<EcUsersAssignmen,Long> {
	/**
	 * 批量分配用户角色
	 * @param accssMode
	 * @param objects
	 */
	public void saveBatch(Long[] userIds, Long[] roleIds);
	/**
	 * 批量分配用户组
	 * @param userIds
	 * @param groupIds
	 */
	public void saveAssignGroups(Long[] userIds, Long[] groupIds);
	/**
	 * 批量删除用户角色
	 * @param accssModeIds
	 * @param objectsIds
	 */
	public void removeBatch(Long[] userIds, Long[] roleIds);
	
	/**
	 * 删除用户角色
	 * @param accessId
	 * @param objId
	 */
	public void remove(Long userId,Long roleId);
	/**
	 * 判断用户是否分配了角色
	 * @param accessId
	 * @param objId
	 * @return
	 */
	public boolean checkIsAssign(Long userId,Long roleId);
	/**
	 * 根据用户名与角色别名，判断用户是否配了角色
	 * @param userAlias
	 * @param roleAlias
	 * @return
	 */
	public boolean checkIsAssignByAlias(String userAlias, String roleAlias);
	/**
	 * 查找用户已分配角色
	 * @param userId
	 * @return
	 */
	public List findAssignRoleList(Long userId);
	/**
	 * 查找用户已分配角色
	 * @param userAlias
	 * @return
	 */
	public List findAssignRoleList(String userAlias);
	/**
	 * 查找用户在某角色根节点下所分配第一级子角色集
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public List<EcRole> findAssignRoleList(Long userId,Long roleId);
	/**
	 * 将用户从组中移除
	 * @param userIds
	 * @param groupIds
	 */
	public void removeAssingGroup(Long[] userIds, Long[] groupIds);
	/**
	 * 根据用户id，取用户统计组数
	 * @param userIds
	 * @return
	 */
	public List<UserGroupTotalView> findUserGroupCount(Long[] userIds);
	/**
	 * 根据用户id,及用户组id，取用户统计组数
	 * @param userIds
	 * @param groupIds
	 * @return
	 */
	public List<UserGroupTotalView> findUserGroupCount(Long[] userIds,Long[] groupIds);
}
