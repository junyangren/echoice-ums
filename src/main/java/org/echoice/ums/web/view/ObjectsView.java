package org.echoice.ums.web.view;

import org.echoice.ums.domain.EcObjects;

public class ObjectsView extends EcObjects {
	public final static String JDBC_FIELDS[]=new String[]{"objId","alias","name","icon","type","status","opTime","parentId","parentName","taxis"};
	private String parentName;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
