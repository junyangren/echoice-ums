package org.echoice.ums.service;

import javax.servlet.http.HttpServletRequest;

public interface ValidPermissionForUmsService {
	/**
	 * ����ums�ڲ�ϵͳ�˵�Ȩ��
	 * @param request
	 */
	public void setUserPermission(HttpServletRequest request);
}
