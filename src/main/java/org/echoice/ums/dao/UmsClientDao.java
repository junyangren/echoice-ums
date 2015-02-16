package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcAccssMode;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserExtend;

public interface UmsClientDao {
	/**
	 * ���Ҷ������и��ڵ�ID��¼
	 * @return
	 */
	public List<Long> findParenObjects();
	/**
	 * ���Ҷ�Ӧ�������ж��󸸽ڵ�ID��¼
	 * @param type
	 * @return
	 */
	public List<Long> findParenObjects(String type);
	/**
	 * ���Ҷ�Ӧ�������ж��󸸽ڵ�ID��¼
	 * @param typeArr
	 * @return
	 */
	public List<Long> findParenObjects(String typeArr[]);
	/**
	 * ���ݸ��ڵ�ID�������ӽڵ�����¼
	 * @param alias
	 * @return
	 */
	public List<EcObjects> findChildObjects(Long parentId);
	/**
	 * ���ݸ��ڵ�ID�����ͣ������ӽڵ�����¼
	 * @param parentId
	 * @param type
	 * @return
	 */
	public List<EcObjects> findChildObjects(Long parentId,String type);
	/**
	 * ���ݸ��ڵ�ID�����ͣ������ӽڵ�����¼
	 * @param parentId
	 * @param typeArr
	 * @return
	 */
	public List<EcObjects> findChildObjects(Long parentId,String typeArr[]);;
	/**
	 * ͨ�����ڵ�����ʶ��ȡ���ӽڵ�����б�
	 * @param alias
	 * @return
	 */
	public List<EcObjects> findObjectsByParentAlias(String alias);
	/**
	 * ͨ�������ʶ��ȡ�ö���
	 * @param alias
	 * @return
	 */
	public EcObjects getObjectsByAlias(String alias);
	/**
	 * ͨ�����ʲ����鿴�����б�
	 * @param accessModealias
	 * @return
	 */
	public List<EcObjects> findObjectsByAccessModeAlias(String accessModealias);
	/**
	 * ͨ���û��鸸�����ʶ��ȡ���Ӽ��û����б�
	 * @param parentGroupAlias
	 * @return
	 */
	public List<EcGroup> findChildGroup(String parentGroupAlias);
	/**
	 * ��ѯ������֯�������ڵ�ID��¼
	 * @return
	 */
	public List<Long> findParenGroup();
	/**
	 * ���ݸ��ڵ�ID����ѯ�¼���֯��¼�б�
	 * @param parentId
	 * @return
	 */
	public List<EcGroup> findChildGroup(Long parentId);
	/**
	 * �����û���ʶ��ȡ���û���ϸ��Ϣ
	 * @param userAlias
	 * @return
	 */
	public EcUser getUser(String userAlias);
	/**
	 * �����û���ʶ��ȡ���û���ϸ��Ϣ���������б�
	 * @param userAlias
	 * @param isGroup
	 * @return
	 */
	public EcUser getUser(String userAlias,boolean isGroup);
	/**
	 * �����û�Id��ȡ�û���չ��Ϣ
	 * @param userId
	 * @return
	 */
	public EcUserExtend getEcUserExtend(Long userId);
	/**
	 * �����û�+������ȡ���û�ӵ�еĶ�����Դ�б�
	 * @param userAlias
	 * @param accessAlias
	 * @return
	 */
	public List<EcObjects> findAssignPermissionObjectList(String userAlias,String accessAlias);
	/**
	 * �����û�+����+������ID��ȡ���û�ӵ�еĶ���(��Ҫ���ڹ������Ͳ˵�)
	 * @param userAlias
	 * @param accessAlias
	 * @param parentId
	 * @return
	 */
	public List<EcObjects> findAssignPermissionObjectList(String userAlias,String accessAlias,Long parentId);
	/**
	 * �����û�+����,ȡ������Ȩ�޵ĸ�����ID(��Ҫ���ڹ������Ͳ˵�)
	 * @param userAlias
	 * @param accessAlias
	 * @return
	 */
	public List<Long> findAssignPermissionObjectParent(String userAlias,String accessAlias);
	
	/**
	 * ���ݶ���+������ȡ��ӵ�ô�Ȩ�޵��û��б�
	 * @param objectAlias
	 * @param accessAlias
	 * @return
	 */
	public List<EcUser> findPermissionUser(String objectAlias,String accessAlias);
	/**
	 * ���ݲ��ű���+����+������ȡ��ָ��������ӵ�ô�Ȩ���û��б�
	 * @param groupId
	 * @param objectAlias
	 * @param accessAlias
	 * @return
	 */
	public List<EcUser> findPermissionUser(String groupAlias,String objectAlias,String accessAlias);
	
	/**
	 * ���ݲ��ű���+����+������ȡ��ָ��������ӵ�ô�Ȩ���û��б�
	 * @param groupAlias
	 * @param objectAlias
	 * @param accessAlias
	 * @param isChild (�Ƿ�����Ӳ���)
	 * @return
	 */
	public List<EcUser> findPermissionUser(String groupAlias,String objectAlias,String accessAlias,boolean isChild);
	/**
	 * ���ݽ�ɫ��ʶ��ȡ�ý�ɫ������û��б�
	 * @param roleAlias
	 * @return
	 */
	public List<EcUser> findUserByRole(String roleAlias);
	/**
	 * ȡ��ָ���û�ӵ�еĶ�������б�
	 * @param userAlias
	 * @param objAlias
	 * @return
	 */
	public List<EcAccssMode> findAssignPermissionAccessModeList(String userAlias,String objAlias);
	/**
	 * �ж��û���ָ���Ķ���+���� �Ƿ���Ȩ��
	 * @param userAlias
	 * @param objAlias
	 * @param accessAlias
	 * @return
	 */
	public boolean checkHasPermission(String userAlias,String objAlias,String accessAlias);
	/**
	 * �����û�����
	 * @param alias
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean updateUserPassword(String alias,String oldPassword,String newPassword);
	/**
	 * �����û���ID����ѯ���е��û���¼
	 * @param groupId
	 * @return
	 */
	public List<EcUser> findUserByGroupId(Long groupId);
	/**
	 * �����û�������ȡ�û����������б�
	 * @param alias
	 * @return
	 */
	public List<EcGroup> findGroupsByUserAlias(String userAlias);
	/**
	 * ���ݱ��������û�����Ϣ
	 * @param alias
	 * @return
	 */
	public EcGroup getGroupByAlias(String alias);
	/**
	 * �����û���ID�������û�����Ϣ
	 * @param id
	 * @return
	 */
	public EcGroup getGroupById(long id );
	/**
	 * ���ݸ��ڵ���������ͣ������ӽڵ����
	 * @param alias
	 * @param type
	 * @return
	 */
	public List<EcObjects> findObjectsByParentAlias(String alias,String type);
	/**
	 * ���ݽڵ����ͣ�ȡ�����б�
	 * @param type
	 * @return
	 */
	public List<EcObjects> findObjectsByType(String type);

	
}
