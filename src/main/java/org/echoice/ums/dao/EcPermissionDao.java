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
	 * 给角色分配相应的对象操作权限
	 * @param roleIds
	 * @param objId
	 * @param accessIds
	 */
	public void savePermission(Long roleIds[],Long objId,Long accessIds[]);
	/**
	 * 移除角色分配相应的对象操作权限
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
	 * 判断是角色否已经分配了该权限
	 * @param roleId
	 * @param operId
	 * @return
	 */
	public boolean checkIsAssign(Long roleId,Long operId);
	
	/**
	 * 根据角色名，对象名，操作名，判断该角色是否有相应的权限
	 * @param roleAlias
	 * @param objAlias
	 * @param accessModeAlias
	 * @return
	 */
	public boolean checkIsAssign(String  roleAlias,String objAlias,String accessModeAlias);
	/**
	 * 查看角色已分配对象操作权限
	 * @param permissionView
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findRolePermissionPage(PermissionView permissionView, int pageNo, int pageSize);
	/**
	 * 查看角色已经分配了哪些用户
	 * @param roldId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findRoleAssingUserPage(Long roldId, int pageNo, int pageSize);
	/**
	 * 查看角色已经分配了哪些用户
	 * @param roldId
	 * @param userAlias
	 * @param userName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findRoleAssingUserPage(Long roldId,String userAlias,String userName, int pageNo, int pageSize);
	/**
	 * 查看用户已分配角色及相应的操作权限
	 * @param permissionView
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findUserPermissionPage(UserPermissionView permissionView, int pageNo, int pageSize);
	/**
	 * 查看用户组已分配角色及相应的操作权限
	 * @param groupId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findGroupPermissionPage(GroupPermissionView permissionView, int pageNo, int pageSize);
	/**
	 * 根据权限paId，批量删除角色权限
	 * @param paIds
	 */
	public void removePermissionByPaIdsArr(Long paIds[]);
	/**
	 * 根据权限paId,删除角色权限
	 * @param paId
	 */
	public void removePermissionByPaId(Long paId);
	/**
	 * 根据对象ID+操作Id,查询已经分配角色
	 * @param objId
	 * @param accessId
	 * @return
	 */
	public List showPermissionRoleList(Long objId,Long accessId);
	/**
	 * 根据对象ID+操作Id数组,查询已经分配角色
	 * @param objId
	 * @param accessIds
	 * @return
	 */
	public List showPermissionRoleList(Long objId,Long accessIds[]);
	
	/**
	 * 判断用户，对给定对象标识+操作标识是否有相应的权限
	 * @param userName
	 * @param objAlias
	 * @param accessAlias
	 * @return
	 */
	public boolean isAssignPermission(String userAlias,String objAlias,String accessAlias);
	/**
	 * 根据用户名，查询用户分配的对象操作权限列表
	 * @param userName
	 * @param accessAlias
	 * @return
	 */
	public List findAssignPermissionList(String userAlias);
	/**
	 * 根据用户名+操作条件，查询用户分配操作对象列表
	 * @param userName
	 * @param accessAlias
	 * @return
	 */
	public List findAssignPermissionObjectList(String userAlias,String accessAlias);
	/**
	 * 根据”用户名+操作+对象父节点标识”条件，查询该用户分配操作对象列表
	 * @param userName
	 * @param accessAlias
	 * @param parentObjName
	 * @return
	 */
	public List findAssignPermissionObjectList(String userAlias,String accessAlias,String parentAlias);
	/**
	 *  根据”用户名+操作+对象父节点id数组”条件，查询该用户分配操作对象列表
	 * @param userAlias
	 * @param accessAlias
	 * @param parentIdArr
	 * @return
	 */
	public List findAssignPermissionObjectList(String userAlias,String accessAlias, Long parentIdArr[]);
	/**
	 * 根据用户名+对象条件，查询用户分配的对象操作列表
	 * @param userName
	 * @param objName
	 * @return
	 */
	public List findAssignPermissionAccessModeList(String userAlias,String objAlias);
	/**
	 * 根据对象+操作，查找已经分配相应权限的用户
	 * @param objectAlias
	 * @param accessAlias
	 * @return
	 */
	public List findPermissionUser(String objectAlias,String accessAlias);
	/**
	 * 取角色拥有的对象的操作记录
	 * @param roleId
	 * @param objId
	 * @return
	 */
	public List findAccessModeList(Long roleId,Long objId);
	
}
