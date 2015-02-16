package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcGroup;

public interface AppPluginDao {
	/**
	 * 查看用户是否存在库存
	 * @param groupIds
	 * @return
	 */
	public boolean checkStorageForGroup(String groupIds);
	/**
	 * 查看是否为OA用户
	 * @param jobsNumbers
	 * @return
	 */
	public int findOAUserList(String jobsNumbers);
	/**
	 * 查看要删除的用户是否存在待办任务
	 * @param userIds
	 * @return
	 */
	public boolean checkUserWorkFlowTask(String userIds);
	/**
	 * 查找非OA工号所在的用户组
	 * @param userIds
	 * @return
	 */
	public List<EcGroup> findUserGroupNotOA(String userIds);
}
