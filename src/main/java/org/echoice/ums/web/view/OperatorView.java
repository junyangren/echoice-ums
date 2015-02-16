package org.echoice.ums.web.view;

public class OperatorView {
	public final static String[] JDBC_FIELDS=new String[]{"objId","objAlias","objName","accessId","accessAlias","accessName",};
	private Long objId;
	private String objAlias;
	private String objName;
	private Long accessId;
	private String accessAlias;
	private String accessName;
	public Long getObjId() {
		return objId;
	}
	public void setObjId(Long objId) {
		this.objId = objId;
	}
	public String getObjAlias() {
		return objAlias;
	}
	public void setObjAlias(String objAlias) {
		this.objAlias = objAlias;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public Long getAccessId() {
		return accessId;
	}
	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}
	public String getAccessAlias() {
		return accessAlias;
	}
	public void setAccessAlias(String accessAlias) {
		this.accessAlias = accessAlias;
	}
	public String getAccessName() {
		return accessName;
	}
	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}
	
	
}
