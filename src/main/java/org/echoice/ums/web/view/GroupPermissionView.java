package org.echoice.ums.web.view;

public class GroupPermissionView extends PermissionView{
	private String groupAlias;
	private String groupName;
	private Long groupId;
	public String getGroupAlias() {
		return groupAlias;
	}
	public void setGroupAlias(String groupAlias) {
		this.groupAlias = groupAlias;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
}
