package org.echoice.ums.web.view;

import org.echoice.ums.domain.EcRole;

public class RoleView extends EcRole {
	private String parentName;
	public final static String JDBC_FIELDS[]=new String[]{"roleId","alias","name","status","opTime","parentId","parentName","taxis"};
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
