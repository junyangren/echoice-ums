package org.echoice.ums.service;

import org.echoice.ums.service.bean.AccssModeSoapResp;
import org.echoice.ums.service.bean.ObjectsSoapResp;
import org.echoice.ums.service.bean.OperatorSoapResp;

public interface UmsSoapWebservice {
	public boolean auth(String userName,String password);
	/**
	 * 判断用户，对给定对象标识+操作标识是否有相应的权限
	 * @param userName
	 * @param objAlias
	 * @param accessAlias
	 * @return
	 */
	public boolean isAssignPermission(String userName,String objAlias,String accessAlias);
	
	/**
	 * 根据用户名，查询用户分配的对象操作权限列表
	 * @param userName
	 * @param accessAlias
	 * @return
	 */
	public OperatorSoapResp findAssignPermissionList(String userAlias);
	/**
	 * 根据用户名+操作条件，查询用户分配操作对象列表
	 * @param userName
	 * @param accessAlias
	 * @return
	 */
	public ObjectsSoapResp findAssignPermissionObjectList(String userAlias,String accessAlias);
	/**
	 * 根据”用户名+操作+对象父节点标识”条件，查询该用户分配操作对象列表
	 * @param userName
	 * @param accessAlias
	 * @param parentObjName
	 * @return
	 */
	public ObjectsSoapResp findAssignPermissionObjectList(String userAlias,String accessAlias,String parentAlias);
	/**
	 * 根据用户名+对象条件，查询用户分配的对象操作列表
	 * @param userName
	 * @param objName
	 * @return
	 */
	public AccssModeSoapResp findAssignPermissionAccessModeList(String userAlias,String objAlias);
	
}
