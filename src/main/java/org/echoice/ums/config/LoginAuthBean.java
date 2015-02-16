package org.echoice.ums.config;

public class LoginAuthBean {
	private boolean auth;
	private String authObject;
	private String authAccessMode;
	private String authSysMgrRole;
	private int groupModeType;
	private String groupRoleParentAlias;
	private boolean levelRoleShow=false;
	private int groupAliasCreate;
	private boolean syncGroupPath;
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	public String getAuthObject() {
		return authObject;
	}
	public void setAuthObject(String authObject) {
		this.authObject = authObject;
	}
	public String getAuthAccessMode() {
		return authAccessMode;
	}
	public void setAuthAccessMode(String authAccessMode) {
		this.authAccessMode = authAccessMode;
	}
	public String getAuthSysMgrRole() {
		return authSysMgrRole;
	}
	public void setAuthSysMgrRole(String authSysMgrRole) {
		this.authSysMgrRole = authSysMgrRole;
	}
	public int getGroupModeType() {
		return groupModeType;
	}
	public void setGroupModeType(int groupModeType) {
		this.groupModeType = groupModeType;
	}
	public String getGroupRoleParentAlias() {
		return groupRoleParentAlias;
	}
	public void setGroupRoleParentAlias(String groupRoleParentAlias) {
		this.groupRoleParentAlias = groupRoleParentAlias;
	}
	public boolean isLevelRoleShow() {
		return levelRoleShow;
	}
	public void setLevelRoleShow(boolean levelRoleShow) {
		this.levelRoleShow = levelRoleShow;
	}
	public int getGroupAliasCreate() {
		return groupAliasCreate;
	}
	public void setGroupAliasCreate(int groupAliasCreate) {
		this.groupAliasCreate = groupAliasCreate;
	}
	public boolean getSyncGroupPath() {
		return syncGroupPath;
	}
	public void setSyncGroupPath(boolean syncGroupPath) {
		this.syncGroupPath = syncGroupPath;
	}
	
	
}
