package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcAccssMode;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserExtend;

public interface UmsClientDao {
	/**
	 * 查找对象所有父节点ID记录
	 * @return
	 */
	public List<Long> findParenObjects();
	/**
	 * 查找对应类型所有对象父节点ID记录
	 * @param type
	 * @return
	 */
	public List<Long> findParenObjects(String type);
	/**
	 * 查找对应类型所有对象父节点ID记录
	 * @param typeArr
	 * @return
	 */
	public List<Long> findParenObjects(String typeArr[]);
	/**
	 * 根据父节点ID，查找子节点对象记录
	 * @param alias
	 * @return
	 */
	public List<EcObjects> findChildObjects(Long parentId);
	/**
	 * 根据父节点ID与类型，查找子节点对象记录
	 * @param parentId
	 * @param type
	 * @return
	 */
	public List<EcObjects> findChildObjects(Long parentId,String type);
	/**
	 * 根据父节点ID与类型，查找子节点对象记录
	 * @param parentId
	 * @param typeArr
	 * @return
	 */
	public List<EcObjects> findChildObjects(Long parentId,String typeArr[]);;
	/**
	 * 通过父节点对象标识，取得子节点对象列表。
	 * @param alias
	 * @return
	 */
	public List<EcObjects> findObjectsByParentAlias(String alias);
	/**
	 * 通过对象标识，取得对象
	 * @param alias
	 * @return
	 */
	public EcObjects getObjectsByAlias(String alias);
	/**
	 * 通过访问操作查看对象列表
	 * @param accessModealias
	 * @return
	 */
	public List<EcObjects> findObjectsByAccessModeAlias(String accessModealias);
	/**
	 * 通过用户组父对象标识，取得子级用户组列表
	 * @param parentGroupAlias
	 * @return
	 */
	public List<EcGroup> findChildGroup(String parentGroupAlias);
	/**
	 * 查询所有组织机构父节点ID记录
	 * @return
	 */
	public List<Long> findParenGroup();
	/**
	 * 根据父节点ID，查询下级组织记录列表
	 * @param parentId
	 * @return
	 */
	public List<EcGroup> findChildGroup(Long parentId);
	/**
	 * 根据用户标识，取得用户详细信息
	 * @param userAlias
	 * @return
	 */
	public EcUser getUser(String userAlias);
	/**
	 * 根据用户标识，取得用户详细信息及所在组列表
	 * @param userAlias
	 * @param isGroup
	 * @return
	 */
	public EcUser getUser(String userAlias,boolean isGroup);
	/**
	 * 根据用户Id，取用户扩展信息
	 * @param userId
	 * @return
	 */
	public EcUserExtend getEcUserExtend(Long userId);
	/**
	 * 根据用户+操作，取得用户拥有的对象资源列表
	 * @param userAlias
	 * @param accessAlias
	 * @return
	 */
	public List<EcObjects> findAssignPermissionObjectList(String userAlias,String accessAlias);
	/**
	 * 根据用户+操作+父对象ID，取得用户拥有的对象(主要用于构建树型菜单)
	 * @param userAlias
	 * @param accessAlias
	 * @param parentId
	 * @return
	 */
	public List<EcObjects> findAssignPermissionObjectList(String userAlias,String accessAlias,Long parentId);
	/**
	 * 根据用户+操作,取得所有权限的父对象ID(主要用于构建树型菜单)
	 * @param userAlias
	 * @param accessAlias
	 * @return
	 */
	public List<Long> findAssignPermissionObjectParent(String userAlias,String accessAlias);
	
	/**
	 * 根据对象+操作，取得拥用此权限的用户列表
	 * @param objectAlias
	 * @param accessAlias
	 * @return
	 */
	public List<EcUser> findPermissionUser(String objectAlias,String accessAlias);
	/**
	 * 根据部门别名+对象+操作，取得指定部门下拥用此权限用户列表
	 * @param groupId
	 * @param objectAlias
	 * @param accessAlias
	 * @return
	 */
	public List<EcUser> findPermissionUser(String groupAlias,String objectAlias,String accessAlias);
	
	/**
	 * 根据部门别名+对象+操作，取得指定部门下拥用此权限用户列表
	 * @param groupAlias
	 * @param objectAlias
	 * @param accessAlias
	 * @param isChild (是否包括子部门)
	 * @return
	 */
	public List<EcUser> findPermissionUser(String groupAlias,String objectAlias,String accessAlias,boolean isChild);
	/**
	 * 根据角色标识，取得角色分配的用户列表
	 * @param roleAlias
	 * @return
	 */
	public List<EcUser> findUserByRole(String roleAlias);
	/**
	 * 取得指定用户拥有的对象操作列表
	 * @param userAlias
	 * @param objAlias
	 * @return
	 */
	public List<EcAccssMode> findAssignPermissionAccessModeList(String userAlias,String objAlias);
	/**
	 * 判断用户对指定的对象+操作 是否有权限
	 * @param userAlias
	 * @param objAlias
	 * @param accessAlias
	 * @return
	 */
	public boolean checkHasPermission(String userAlias,String objAlias,String accessAlias);
	/**
	 * 更新用户密码
	 * @param alias
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean updateUserPassword(String alias,String oldPassword,String newPassword);
	/**
	 * 根据用户组ID，查询组中的用户记录
	 * @param groupId
	 * @return
	 */
	public List<EcUser> findUserByGroupId(Long groupId);
	/**
	 * 根据用户登入名取用户组所在组列表
	 * @param alias
	 * @return
	 */
	public List<EcGroup> findGroupsByUserAlias(String userAlias);
	/**
	 * 根据别名查找用户组信息
	 * @param alias
	 * @return
	 */
	public EcGroup getGroupByAlias(String alias);
	/**
	 * 根据用户组ID，查找用户组信息
	 * @param id
	 * @return
	 */
	public EcGroup getGroupById(long id );
	/**
	 * 根据父节点别名及类型，查找子节点对象
	 * @param alias
	 * @param type
	 * @return
	 */
	public List<EcObjects> findObjectsByParentAlias(String alias,String type);
	/**
	 * 根据节点类型，取对象列表
	 * @param type
	 * @return
	 */
	public List<EcObjects> findObjectsByType(String type);

	
}
