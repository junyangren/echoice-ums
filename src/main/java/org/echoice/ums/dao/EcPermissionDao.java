package org.echoice.ums.dao;

import java.util.List;

import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcPermission;
import org.echoice.ums.web.view.GroupPermissionView;
import org.echoice.ums.web.view.PermissionView;
import org.echoice.ums.web.view.UserPermissionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EcPermissionDao extends JpaRepository<EcPermission,Long>{
	/**
	 * ����ɫ������Ӧ�Ķ������Ȩ��
	 * @param roleIds
	 * @param objId
	 * @param accessIds
	 */
	public void savePermission(Long roleIds[],Long objId,Long accessIds[]);
	/**
	 * �Ƴ���ɫ������Ӧ�Ķ������Ȩ��
	 * @param roleIds
	 * @param objId
	 * @param accessIds
	 */
	public void removePermission(Long roleIds[],Long objId,Long accessIds[]);
	/**
	 * 
	 * @param uaIds
	 */
	public void removeRoleAssignUserByUaIdsArr(Long uaIds[]);
	/**
	 * 
	 * @param uaId
	 */
	public void removeRoleAssignUserByUaId(Long uaId);
	/**
	 * �ж��ǽ�ɫ���Ѿ������˸�Ȩ��
	 * @param roleId
	 * @param operId
	 * @return
	 */
	public boolean checkIsAssign(Long roleId,Long operId);
	
	/**
	 * ���ݽ�ɫ���������������������жϸý�ɫ�Ƿ�����Ӧ��Ȩ��
	 * @param roleAlias
	 * @param objAlias
	 * @param accessModeAlias
	 * @return
	 */
	public boolean checkIsAssign(String  roleAlias,String objAlias,String accessModeAlias);
	/**
	 * �鿴��ɫ�ѷ���������Ȩ��
	 * @param permissionView
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findRolePermissionPage(PermissionView permissionView, int pageNo, int pageSize);
	/**
	 * �鿴��ɫ�Ѿ���������Щ�û�
	 * @param roldId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findRoleAssingUserPage(Long roldId, int pageNo, int pageSize);
	/**
	 * �鿴��ɫ�Ѿ���������Щ�û�
	 * @param roldId
	 * @param userAlias
	 * @param userName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findRoleAssingUserPage(Long roldId,String userAlias,String userName, int pageNo, int pageSize);
	/**
	 * �鿴�û��ѷ����ɫ����Ӧ�Ĳ���Ȩ��
	 * @param permissionView
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findUserPermissionPage(UserPermissionView permissionView, int pageNo, int pageSize);
	/**
	 * �鿴�û����ѷ����ɫ����Ӧ�Ĳ���Ȩ��
	 * @param groupId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findGroupPermissionPage(GroupPermissionView permissionView, int pageNo, int pageSize);
	/**
	 * ����Ȩ��paId������ɾ����ɫȨ��
	 * @param paIds
	 */
	public void removePermissionByPaIdsArr(Long paIds[]);
	/**
	 * ����Ȩ��paId,ɾ����ɫȨ��
	 * @param paId
	 */
	public void removePermissionByPaId(Long paId);
	/**
	 * ���ݶ���ID+����Id,��ѯ�Ѿ������ɫ
	 * @param objId
	 * @param accessId
	 * @return
	 */
	public List showPermissionRoleList(Long objId,Long accessId);
	/**
	 * ���ݶ���ID+����Id����,��ѯ�Ѿ������ɫ
	 * @param objId
	 * @param accessIds
	 * @return
	 */
	public List showPermissionRoleList(Long objId,Long accessIds[]);
	
	/**
	 * �ж��û����Ը��������ʶ+������ʶ�Ƿ�����Ӧ��Ȩ��
	 * @param userName
	 * @param objAlias
	 * @param accessAlias
	 * @return
	 */
	public boolean isAssignPermission(String userAlias,String objAlias,String accessAlias);
	/**
	 * �����û�������ѯ�û�����Ķ������Ȩ���б�
	 * @param userName
	 * @param accessAlias
	 * @return
	 */
	public List findAssignPermissionList(String userAlias);
	/**
	 * �����û���+������������ѯ�û�������������б�
	 * @param userName
	 * @param accessAlias
	 * @return
	 */
	public List findAssignPermissionObjectList(String userAlias,String accessAlias);
	/**
	 * ���ݡ��û���+����+���󸸽ڵ��ʶ����������ѯ���û�������������б�
	 * @param userName
	 * @param accessAlias
	 * @param parentObjName
	 * @return
	 */
	public List findAssignPermissionObjectList(String userAlias,String accessAlias,String parentAlias);
	/**
	 *  ���ݡ��û���+����+���󸸽ڵ�id���顱��������ѯ���û�������������б�
	 * @param userAlias
	 * @param accessAlias
	 * @param parentIdArr
	 * @return
	 */
	public List findAssignPermissionObjectList(String userAlias,String accessAlias, Long parentIdArr[]);
	/**
	 * �����û���+������������ѯ�û�����Ķ�������б�
	 * @param userName
	 * @param objName
	 * @return
	 */
	public List findAssignPermissionAccessModeList(String userAlias,String objAlias);
	/**
	 * ���ݶ���+�����������Ѿ�������ӦȨ�޵��û�
	 * @param objectAlias
	 * @param accessAlias
	 * @return
	 */
	public List findPermissionUser(String objectAlias,String accessAlias);
	/**
	 * ȡ��ɫӵ�еĶ���Ĳ�����¼
	 * @param roleId
	 * @param objId
	 * @return
	 */
	public List findAccessModeList(Long roleId,Long objId);
	
}
