package org.echoice.ums.service;

import org.echoice.ums.service.bean.AccssModeSoapResp;
import org.echoice.ums.service.bean.ObjectsSoapResp;
import org.echoice.ums.service.bean.OperatorSoapResp;

public interface UmsSoapWebservice {
	public boolean auth(String userName,String password);
	/**
	 * �ж��û����Ը��������ʶ+������ʶ�Ƿ�����Ӧ��Ȩ��
	 * @param userName
	 * @param objAlias
	 * @param accessAlias
	 * @return
	 */
	public boolean isAssignPermission(String userName,String objAlias,String accessAlias);
	
	/**
	 * �����û�������ѯ�û�����Ķ������Ȩ���б�
	 * @param userName
	 * @param accessAlias
	 * @return
	 */
	public OperatorSoapResp findAssignPermissionList(String userAlias);
	/**
	 * �����û���+������������ѯ�û�������������б�
	 * @param userName
	 * @param accessAlias
	 * @return
	 */
	public ObjectsSoapResp findAssignPermissionObjectList(String userAlias,String accessAlias);
	/**
	 * ���ݡ��û���+����+���󸸽ڵ��ʶ����������ѯ���û�������������б�
	 * @param userName
	 * @param accessAlias
	 * @param parentObjName
	 * @return
	 */
	public ObjectsSoapResp findAssignPermissionObjectList(String userAlias,String accessAlias,String parentAlias);
	/**
	 * �����û���+������������ѯ�û�����Ķ�������б�
	 * @param userName
	 * @param objName
	 * @return
	 */
	public AccssModeSoapResp findAssignPermissionAccessModeList(String userAlias,String objAlias);
	
}
