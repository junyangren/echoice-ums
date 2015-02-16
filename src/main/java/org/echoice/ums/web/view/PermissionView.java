package org.echoice.ums.web.view;

public class PermissionView {
	private Long paId;
	private Long roleId;
	private String roleAlias;
	private String roleName;
	private Long operId;
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
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleAlias() {
		return roleAlias;
	}
	public void setRoleAlias(String roleAlias) {
		this.roleAlias = roleAlias;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getPaId() {
		return paId;
	}
	public void setPaId(Long paId) {
		this.paId = paId;
	}
	
}
