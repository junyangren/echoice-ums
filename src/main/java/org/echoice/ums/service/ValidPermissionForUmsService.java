package org.echoice.ums.service;

import javax.servlet.http.HttpServletRequest;

public interface ValidPermissionForUmsService {
	/**
	 * 设置ums内部系统菜单权限
	 * @param request
	 */
	public void setUserPermission(HttpServletRequest request);
}
