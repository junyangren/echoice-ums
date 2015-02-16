package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcGroup;

public interface AppPluginDao {
	/**
	 * �鿴�û��Ƿ���ڿ��
	 * @param groupIds
	 * @return
	 */
	public boolean checkStorageForGroup(String groupIds);
	/**
	 * �鿴�Ƿ�ΪOA�û�
	 * @param jobsNumbers
	 * @return
	 */
	public int findOAUserList(String jobsNumbers);
	/**
	 * �鿴Ҫɾ�����û��Ƿ���ڴ�������
	 * @param userIds
	 * @return
	 */
	public boolean checkUserWorkFlowTask(String userIds);
	/**
	 * ���ҷ�OA�������ڵ��û���
	 * @param userIds
	 * @return
	 */
	public List<EcGroup> findUserGroupNotOA(String userIds);
}
